/**
 * 
 */
package top.anets.service.impl;

import static org.hamcrest.CoreMatchers.nullValue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.tc.logging.interfaces.IFileLog;

import lombok.extern.slf4j.Slf4j;
import top.anets.entity.Business;
import top.anets.entity.BusinessExample;
import top.anets.entity.InvoiceHead;
import top.anets.entity.InvoiceHeadExample;
import top.anets.entity.InvoiceHeadExample.Criteria;
import top.anets.entity.InvoiceSplitLine;
import top.anets.entity.InvoiceSplitLineExample;
import top.anets.entity.InvoiceSplitLineKey;
import top.anets.mapper.BusinessMapper;
import top.anets.mapper.InvoiceHeadMapper;
import top.anets.mapper.InvoiceSplitLineMapper;
import top.anets.redis.RedisKey;
import top.anets.redis.RedisService;
import top.anets.service.InvoiceService;
import top.anets.service.InvoiceViewService;
import top.anets.service.SapService;
import top.anets.utils.Clone;
import top.anets.utils.DateUtils;
import top.anets.utils.InvoiceUtils;
import top.anets.utils.Result;
import top.anets.utils.TimeUtils;
import top.anets.vo.BusinessVo;
import top.anets.vo.Invoice;
import top.anets.vo.InvoiceCondition;
import top.anets.vo.Logs;
import top.anets.vo.Message;

/**
 * @author Administrator
 *
 */
@Slf4j
@Service
@Transactional
public class InvoiceViewServiceImpl implements InvoiceViewService{
	
	
	
	
	
	@Autowired
	private RedisService redisService; 
	
	@Value("${httpclient.dev_or_prod}")
	  private String dev_or_prod; 
	
	@Autowired
	private InvoiceService invoiceService;
	
	@Autowired
	private BusinessMapper  businessMapper;
	
	@Autowired
    private InvoiceSplitLineMapper invoiceSplitLineMapper;
	
	@Autowired
	private InvoiceHeadMapper invoiceHeadMapper;

	
	@Autowired
	private SapService sapService;
	
	@Override
	public InvoiceCondition getInvoices(InvoiceCondition invoiceHeadCondition) {
		PageHelper.startPage(invoiceHeadCondition.getPageNum(),invoiceHeadCondition.getPageSize());
		 
//		张琪=================================时间处理
		String billDateStart = invoiceHeadCondition.getBillDateStart();
        if (billDateStart != null && !"".equals(billDateStart)) {
            invoiceHeadCondition.setBillDateStart(TimeUtils.transfor(billDateStart));
            System.out.println("billDateStart:"+billDateStart);
        }
        String billDateEnd = invoiceHeadCondition.getBillDateEnd();
        if (billDateEnd != null && !"".equals(billDateEnd)) {
            invoiceHeadCondition.setBillDateEnd(TimeUtils.transforAdd(billDateEnd, 1));
            System.out.println("billDateEnd:"+billDateEnd);
        } 
        
        
        String billGDateStart = invoiceHeadCondition.getBillGdateStart();
        if (billGDateStart != null && !"".equals(billGDateStart)) {
            invoiceHeadCondition.setBillGdateStart(TimeUtils.transfor(billGDateStart));
            SimpleDateFormat tyb1 = new SimpleDateFormat("yyyyMMddHHmmss");
            try {
				Date parse = tyb1.parse(invoiceHeadCondition.getBillGdateStart());
				SimpleDateFormat tyb3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String billGDateStart1 = tyb3.format(parse);
				invoiceHeadCondition.setBillGdateStart1(billGDateStart1);
				
			} catch (ParseException e) { 
				e.printStackTrace();
			}
            System.out.println("billGDateStart:"+invoiceHeadCondition.getBillGdateStart()+"/"+invoiceHeadCondition.getBillGdateStart1());
        }
        String billGDateEnd = invoiceHeadCondition.getBillGdateEnd(); 
        if (billGDateEnd != null && !"".equals(billGDateEnd)) {
            invoiceHeadCondition.setBillGdateEnd(TimeUtils.transforAdd(billGDateEnd, 1));
            SimpleDateFormat tyb2 = new SimpleDateFormat("yyyyMMddHHmmss");
            try {
				Date parse2 = tyb2.parse(invoiceHeadCondition.getBillGdateEnd());
				SimpleDateFormat tyb4= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String billGDateEnd1 = tyb4.format(parse2);
				invoiceHeadCondition.setBillGdateEnd1(billGDateEnd1);
				
			} catch (ParseException e) { 
				e.printStackTrace();
			}
            
            System.out.println("billGDateEnd:"+invoiceHeadCondition.getBillGdateEnd()+"/"+invoiceHeadCondition.getBillGdateEnd1());
        } 
         
		List<InvoiceSplitLine> list = invoiceSplitLineMapper.getInvoices(invoiceHeadCondition);
		
		
		 invoiceHeadCondition.setBillGdateEnd(billGDateEnd);
		 invoiceHeadCondition.setBillGdateStart(billGDateStart);
		 invoiceHeadCondition.setBillDateEnd(billDateEnd);
		 invoiceHeadCondition.setBillDateStart(billDateStart);
		PageInfo<InvoiceSplitLine> pageInfo = new PageInfo<>(list);
		
		
		invoiceHeadCondition.setInvoices(list);
		long total = pageInfo.getTotal();
		invoiceHeadCondition.setRowCount(total);  
		
		return invoiceHeadCondition;
	}
	@Autowired
	private DataSourceTransactionManager dataSourceTransactionManager;
	@Autowired
	private TransactionDefinition transactionDefinition;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Result unlock(Invoice invoice) {
		
		String lock = redisService.get("lock"+invoice.getDocNum()+invoice.getGroupNum()+"unlock");
		if(StringUtils.isNoneBlank(lock)) { 
			return Result.Error("正在解锁，请不要重复请求!!剩"+redisService.ttl("lock"+invoice.getDocNum()+invoice.getGroupNum()+"unlock")+"s");
		}
		redisService.set("lock"+invoice.getGroupNum()+"unlock", "true",20);
		
	 
		
		TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
//		停止开票 
		BusinessExample example = new BusinessExample();
		top.anets.entity.BusinessExample.Criteria criteria = example.createCriteria();
		criteria.andOrgIdEqualTo(invoice.getOrgId());
		List<Business> list = businessMapper.selectByExample(example );
		
		if(list==null || list.size()<=0) {
			redisService.expire("lock"+invoice.getDocNum()+invoice.getGroupNum()+"unlock", -1);
			return Result.Error("没有查询到该发票所在组织");
		}
		BusinessVo business = new BusinessVo();
		BeanUtils.copyProperties(list.get(0),  business); 
		InvoiceUtils.stopInvoice(business); 
		InvoiceUtils.stopInvalid(business); 
		
//		匹配线路 
		
//      处理缓存中的错误数据
		Result errorData = this.recoverErrorData(business); 
		if(!errorData.success) {
			return errorData;
		}
//		处理正在开票的状态
		log.info("处理正在开票的数据");
        List<InvoiceHead> invoicingInvoiceHeads = invoiceService.getInvoicingInvoiceHead(business);
		if(invoicingInvoiceHeads!=null&&invoicingInvoiceHeads.size()>0) { 
			 for (InvoiceHead invoiceHead : invoicingInvoiceHeads) {
		    	Result result = invoiceService.queryAndHandleInvoiceResult(invoiceHead,business);
		    	log.info("处理结果"+result); 
		    	if(!result.success) {
		    		redisService.expire("lock"+invoice.getDocNum()+invoice.getGroupNum()+"unlock", -1);
		    		return result;
		    	}
			 }
		}
		
//		获取整个单据
		InvoiceHead invoiceHead = invoiceHeadMapper.selectByPrimaryKey(invoice.getDocNum());
		if(invoiceHead==null) {
			redisService.expire("lock"+invoice.getDocNum()+invoice.getGroupNum()+"unlock", -1);
			return Result.Error("对不起,数据库已无此发票");
		}
		InvoiceSplitLineExample examplez = new InvoiceSplitLineExample();
		examplez.createCriteria().andDocNumEqualTo(invoice.getDocNum());
		List<InvoiceSplitLine> splitLines = invoiceSplitLineMapper.selectByExample(examplez);
		invoiceHead.setInvoiceSplitLines(splitLines); 
		
		String json = new Gson().toJson(invoiceHead);
		InvoiceHead originalInvoiceHead =new Gson().fromJson(json, InvoiceHead.class);
		for (InvoiceSplitLine invoiceSplitLine : invoiceHead.getInvoiceSplitLines()) {
			//正在开票的情况下不允许回传，其他的都允许回传 
			if(InvoiceUtils.isStartedInvoice(business)){
				redisService.expire("lock"+invoice.getDocNum()+invoice.getGroupNum()+"unlock", -1);
				return Result.Error("解锁时请不要启动开票!");
			}
			
			if( invoiceSplitLine.getGoldtaxCode().equals("1")) { 
				return Result.Error("严重错误，请联系管理员!");
			} 
			if((invoiceSplitLine.getGroupStatus().equals("1")||invoiceSplitLine.getGroupStatus().equals("3"))) {
				 invoiceSplitLine.setGroupStatus("0");
				 invoiceHead.setDocStatus("0");
			}
		} 
		
		Result result =null;
		
		if("dev".equals(dev_or_prod)) {
			invoiceHead.setIssync("2");
			invoiceHeadMapper.updateByPrimaryKey(invoiceHead);
			result =Result.Success("测试环境,同步OK");
		}else {
			result= sapService.feedBackInvoice(invoiceHead); 
		}
		
		if(result!=null&&result.success)  {
//			对于状态是1和3的发票，删除 
			List<InvoiceHead> invoices = invoiceService.group(originalInvoiceHead);
			for (InvoiceHead item : invoices) {
				List<InvoiceSplitLine> splitLines2 = item.getInvoiceSplitLines();
				for (InvoiceSplitLine splitLine2 : splitLines2) {
					if((splitLine2.getGroupStatus().equals("1")||splitLine2.getGroupStatus().equals("3"))) {
						InvoiceSplitLineKey key = new InvoiceSplitLineKey();
						key.setDocLine(splitLine2.getDocLine());
						key.setDocNum(splitLine2.getDocNum());
						invoiceSplitLineMapper.deleteByPrimaryKey(key);
					}
				}
			}

			
//			看体是否全部删除，若删除，砍掉头
			InvoiceSplitLineExample examplezz = new InvoiceSplitLineExample();
	        examplezz.createCriteria().andDocNumEqualTo(invoice.getDocNum());
			int count = invoiceSplitLineMapper.countByExample(examplezz);
			if(count==0) {
				invoiceHeadMapper.deleteByPrimaryKey(invoice.getDocNum());
			}
			
			dataSourceTransactionManager.commit(transactionStatus);
		}else {
			dataSourceTransactionManager.rollback(transactionStatus);
		}
		redisService.expire("lock"+invoice.getDocNum()+invoice.getGroupNum()+"unlock", -1);
//		回传
		return result;
	}

	
	
	
	/**
	* 
	*@param business
	*@return 
	*/
	private Result recoverErrorData(BusinessVo business) {
//		不需要处理了
		return Result.Success();
//		InvoiceHead invoiceError =  (InvoiceHead) redisService.rpopObject(business.getOrgId()+"-"+business.getOrgMachine()+"-"+RedisKey.Invoice_Error,InvoiceHead.class);
//   	    if(invoiceError!=null) { 
//   	    	//查询不到发票信息，可能恢复不了,
//   	    	Result result = invoiceService.queryAndHandleInvoiceResult(invoiceError, business);
//   	    	if(!result.success) {
//   	    		return result;
//   	    	}
//   	    	return this.recoverErrorData(business);
//   	    }else {
//   	    	return Result.Success();
//   	    }
	}




	@Override
	public Result toInvoice(String docNum) {
		log.info("整单开具:"+docNum);
		
		// 查询单据
		InvoiceHead head = invoiceHeadMapper.selectByPrimaryKey(docNum);
		//查询所在组织
		BusinessExample example2=new BusinessExample();
		example2.createCriteria().andOrgIdEqualTo(head.getOrgId());
		List<Business> businesses = businessMapper.selectByExample(example2); 
		if (businesses==null||businesses.size()<=0) {
			return Result.Error("该单据的组织异常!");
		} 
		Business business = businesses.get(0);
//		匹配线路
		BusinessVo businessVo = new BusinessVo(); 
		BeanUtils.copyProperties(business, businessVo);
		
		
//		查询正在开票的数据
		InvoiceSplitLineExample example0 = new InvoiceSplitLineExample();
		example0.createCriteria().andDocNumEqualTo(docNum).andGoldtaxCodeEqualTo("1");
		List<InvoiceSplitLine> invoiceing = invoiceSplitLineMapper.selectByExample(example0);
		if(invoiceing!=null&&invoiceing.size()>0) {//存在未知结果的发票
			head.setInvoiceSplitLines(invoiceing);
//			对发票进行分组
			List<InvoiceHead> invoicez = invoiceService.group(head); 
//			对发票进行开票
			String nums="";
			for (InvoiceHead invoice : invoicez) { 
				Result result = invoiceService.queryAndHandleInvoiceResult(invoice, businessVo);
				if(!result.success) {
					return result;
				}
			}
		}
		head.setInvoiceSplitLines(null);
		
		
		InvoiceSplitLineExample example = new InvoiceSplitLineExample();
		example.createCriteria().andDocNumEqualTo(docNum).andGroupStatusEqualTo("1");
		List<InvoiceSplitLine> list = invoiceSplitLineMapper.selectByExample(example); 
		if(list==null||list.size()<=0) {
			return Result.Success("整单已开出，无需再开😄");
		} 
		head.setInvoiceSplitLines(list); 
		log.info("单据信息:"+new Gson().toJson(head));

//		对发票进行分组
		List<InvoiceHead> invoices = invoiceService.group(head);
		log.info("发票组信息:"+new Gson().toJson(invoices));
//		对发票进行开票
		String nums="";
		for (InvoiceHead invoice : invoices) { 
			Result result = invoiceService.invoice(invoice,  businessVo);
			log.info("发票"+invoice.getDocNum()+"/"+invoice.getInvoiceSplitLines().get(0).getGroupNum()+"开立结果:"+new Gson().toJson(result));
			if(!result.success&&StringUtils.isBlank(nums)) {
				return Result.Error("开票遇到异常,终止操作!"+result.getMsg(),null);
			}else if(!result.success&&!StringUtils.isBlank(nums)){
				return Result.Error("已开发票:"+nums+",单据未完全开具，原因:"+result.getMsg(),null);
			}else {
				InvoiceHead invoiceResult = (InvoiceHead) result.getData(); 
				String goldtaxNum = invoiceResult.getInvoiceSplitLines().get(0).getGoldtaxNum();
				nums+=goldtaxNum+" ";
			}
		}
		return Result.Success("整单开立成功!发票号码:"+nums);
	}




	@Override
	/**
	 * 整组开具
	 */
	public Result toInvoice(String docNum, String groupNum) {
		log.info("整组开具:"+docNum+"/"+groupNum);
		// 查询单据
		InvoiceHead head = invoiceHeadMapper.selectByPrimaryKey(docNum);
		
		//查询所在组织
		BusinessExample example2=new BusinessExample();
		example2.createCriteria().andOrgIdEqualTo(head.getOrgId());
		List<Business> businesses = businessMapper.selectByExample(example2); 
		if (businesses==null||businesses.size()<=0) {
			return Result.Error("该单据的组织异常!");
		} 
		Business business = businesses.get(0);
//		匹配线路
		BusinessVo businessVo = new BusinessVo(); 
		BeanUtils.copyProperties(business, businessVo); 
		
//		查询正在开票的数据
		InvoiceSplitLineExample example0 = new InvoiceSplitLineExample();
		example0.createCriteria().andDocNumEqualTo(docNum).andGoldtaxCodeEqualTo("1").andGroupNumEqualTo(groupNum);
		List<InvoiceSplitLine> invoiceing = invoiceSplitLineMapper.selectByExample(example0);
		if(invoiceing!=null&&invoiceing.size()>0) {//存在未知结果的发票
			head.setInvoiceSplitLines(invoiceing); 
			List<InvoiceHead> invoicez = invoiceService.group(head);  
			for (InvoiceHead invoice : invoicez) { 
				Result result = invoiceService.queryAndHandleInvoiceResult(invoice, businessVo);
				if(!result.success) {
					return result;
				}
			}
		}
		head.setInvoiceSplitLines(null);
		
		
		InvoiceSplitLineExample example = new InvoiceSplitLineExample();
		example.createCriteria().andDocNumEqualTo(docNum).andGroupStatusEqualTo("1").andGroupNumEqualTo(groupNum);
		List<InvoiceSplitLine> list = invoiceSplitLineMapper.selectByExample(example);
		if(list==null||list.size()<=0) {
			return Result.Success("整单已开出，无需再开😄");
		}
		
		head.setInvoiceSplitLines(list); 
		log.info("组信息:"+new Gson().toJson(head));
	
//		对发票进行分组
		List<InvoiceHead> invoices = invoiceService.group(head);
		log.info("组信息:"+new Gson().toJson(invoices));
//		对发票进行开票
		String nums="";
		for (InvoiceHead invoice : invoices) { 
			
			Result result = invoiceService.invoice(invoice,  businessVo);
			log.info("发票"+invoice.getDocNum()+"/"+invoice.getInvoiceSplitLines().get(0).getGroupNum()+"开立结果:"+new Gson().toJson(result));
			if(!result.success) {
				return Result.Error("开票遇到异常,终止操作!"+result.getMsg(),null);
			}else {
//				开具成功的处理
				
				InvoiceHead invoiceResult = (InvoiceHead) result.getData(); 
				String goldtaxNum = invoiceResult.getInvoiceSplitLines().get(0).getGoldtaxNum();
				nums+=goldtaxNum+" ";
			}
		}
		return Result.Success("组开立成功!发票号码:"+nums);
	}




	@Override
	public Result statisticalTask(String orgId,Boolean refresh) {
		if(refresh!=null&&refresh==true) {
			redisService.expire(orgId+"countInvalidDay_list", -1);
		}
		ArrayList<Integer> countInvalidDay_list = new ArrayList<>();
		 ArrayList<Integer> countInvoicedDay_list = new ArrayList<>();
		 ArrayList<Integer> count9InvoiceDay_list = new ArrayList<>();
		 ArrayList<Integer> count2InvoiceDay_list = new ArrayList<>();
		 ArrayList<Integer> countWaitTaskDay_list = new ArrayList<>();
		 ArrayList<Integer> days_list = new ArrayList<>();
		 
		 
		 
		 
		 Calendar cal0=Calendar.getInstance();
	        cal0.add(Calendar.DATE,-30);
	        Date d0=cal0.getTime(); 
	        SimpleDateFormat sp0 =new SimpleDateFormat("yyyy-MM-dd 00:00:00");
	        String start1=sp0.format(d0);//获取昨天日期
	        SimpleDateFormat sp00=new SimpleDateFormat("yyyyMMdd000000");
	        String start2=sp00.format(d0);//获取昨天日期 
	        
		if(redisService.getObject(orgId+"countInvalidDay_list")==null) {//首次
			log.info("重新取值");
			  countInvalidDay_list = new ArrayList<>();
			  countInvoicedDay_list = new ArrayList<>();
			  count9InvoiceDay_list = new ArrayList<>();
			  count2InvoiceDay_list = new ArrayList<>();
			  countWaitTaskDay_list = new ArrayList<>();
			  days_list = new ArrayList<>();
			for(int i =29 ;i>=0;i--) {
				Calendar cal=Calendar.getInstance();
		        cal.add(Calendar.DATE,-i);
		        Date d=cal.getTime(); 
		        SimpleDateFormat sp=new SimpleDateFormat("yyyy-MM-dd");
		        String day1=sp.format(d)+"%";//获取昨天日期
		        SimpleDateFormat sp1=new SimpleDateFormat("yyyyMMdd");
		        String day2=sp1.format(d)+"%";//获取昨天日期
		        SimpleDateFormat sp2=new SimpleDateFormat("dd");
		        String day=sp2.format(d);//获取昨天日期
		        
		        
		        
		        
		        
		        Calendar cal3=Calendar.getInstance();
		        cal3.add(Calendar.DATE,-i);
		        Date d3=cal3.getTime(); 
		        SimpleDateFormat sp3=new SimpleDateFormat("yyyy-MM-dd 24:60:60");
		        String day3=sp3.format(d3);//获取昨天日期
		        SimpleDateFormat sp4=new SimpleDateFormat("yyyyMMdd246060");
		        String day4=sp4.format(d3);//获取昨天日期 
		        
		        
		        List<InvoiceSplitLine> countInvalidDay = invoiceSplitLineMapper.countInvalidDay(orgId, day1, day2);
		        List<InvoiceSplitLine> countInvoicedDay = invoiceSplitLineMapper.countInvoicedDay(orgId, day1, day2);
		        List<InvoiceSplitLine> count9InvoiceDay = invoiceSplitLineMapper.count9InvoiceDay(orgId, day3, day4,start1,start2);
		        List<InvoiceSplitLine> count2InvoiceDay = invoiceSplitLineMapper.count2InvoiceDay(orgId, day3, day4,start1,start2);	        
		        List<InvoiceSplitLine> countWaitTaskDay = invoiceSplitLineMapper.countWaitTaskDay(orgId, null, day4,start1,start2);
		        
		        countInvalidDay_list.add(countInvalidDay==null?0:countInvalidDay.size());
		        countInvoicedDay_list.add(countInvoicedDay==null?0:countInvoicedDay.size());
		        count9InvoiceDay_list.add(count9InvoiceDay==null?0:count9InvoiceDay.size());
		        count2InvoiceDay_list.add(count2InvoiceDay==null?0:count2InvoiceDay.size());
		        countWaitTaskDay_list.add(countWaitTaskDay==null?0:countWaitTaskDay.size()); 
		        days_list.add(Integer.valueOf(day));
		        redisService.set(orgId+"countInvalidDay_list",countInvalidDay_list, 0);
				redisService.set(orgId+"countInvoicedDay_list",countInvoicedDay_list, 0);
				redisService.set(orgId+"count9InvoiceDay_list",count9InvoiceDay_list, 0);
				redisService.set(orgId+"count2InvoiceDay_list",count2InvoiceDay_list, 0);
				redisService.set(orgId+"countWaitTaskDay_list",countWaitTaskDay_list, 0);
				redisService.set(orgId+"days_list",days_list, 0);
			}
		}else {
			
			 log.info("走缓存线路");
			 countInvalidDay_list = (ArrayList<Integer>) redisService.getObject(orgId+"countInvalidDay_list" );
			 countInvoicedDay_list = (ArrayList<Integer>)redisService.getObject(orgId+"countInvoicedDay_list" );
			  count9InvoiceDay_list =(ArrayList<Integer>)redisService.getObject(orgId+"count9InvoiceDay_list" );
			 count2InvoiceDay_list = (ArrayList<Integer>)redisService.getObject(orgId+"count2InvoiceDay_list");
			  countWaitTaskDay_list  = (ArrayList<Integer>)redisService.getObject(orgId+"countWaitTaskDay_list");
			  days_list  = (ArrayList<Integer>)redisService.getObject(orgId+"days_list");
			
			
			Calendar cal=Calendar.getInstance();
	        cal.add(Calendar.DATE,-0);
	        Date d=cal.getTime(); 
	        SimpleDateFormat sp=new SimpleDateFormat("yyyy-MM-dd");
	        String day1=sp.format(d)+"%";//获取昨天日期
	        SimpleDateFormat sp1=new SimpleDateFormat("yyyyMMdd");
	        String day2=sp1.format(d)+"%";//获取昨天日期
	        
	        
	        
	        SimpleDateFormat sp2=new SimpleDateFormat("dd");
	        String day=sp2.format(d);//获取昨天日期


	        
	        Calendar cal3=Calendar.getInstance();
	        cal3.add(Calendar.DATE,-0);
	        Date d3=cal3.getTime(); 
	        SimpleDateFormat sp3=new SimpleDateFormat("yyyy-MM-dd 24:60:60");
	        String day3=sp3.format(d3);//获取昨天日期
	        SimpleDateFormat sp4=new SimpleDateFormat("yyyyMMdd246060");
	        String day4=sp4.format(d3);//获取昨天日期 
	        
	        
	        
	        
	        List<InvoiceSplitLine> countInvalidDay = invoiceSplitLineMapper.countInvalidDay(orgId, day1, day2);
	        List<InvoiceSplitLine> countInvoicedDay = invoiceSplitLineMapper.countInvoicedDay(orgId, day1, day2);
	        List<InvoiceSplitLine> count9InvoiceDay = invoiceSplitLineMapper.count9InvoiceDay(orgId, day3, day4,start1,start2);
	        List<InvoiceSplitLine> count2InvoiceDay = invoiceSplitLineMapper.count2InvoiceDay(orgId, day3, day4,start1,start2);	        
	        List<InvoiceSplitLine> countWaitTaskDay = invoiceSplitLineMapper.countWaitTaskDay(orgId, null, day4,start1,start2);
	        log.info(day);
	        day=Integer.valueOf(day)+"";
	         Integer fetch_day = days_list.get(days_list.size()-1); 
	        if(!day.equals(String.valueOf(fetch_day))) {//当前天，如果它的索引是最后一个，说明不用更新,否则，需要更新 
	        	countInvalidDay_list.remove(0);
				countInvoicedDay_list.remove(0);
				count9InvoiceDay_list.remove(0);
				count2InvoiceDay_list.remove(0);
				countWaitTaskDay_list.remove(0);
				days_list.remove(0); 
	        }else { //如果是当前天，跟新最后一项
	        	countInvalidDay_list.remove(countInvalidDay_list.size()-1);
				countInvoicedDay_list.remove(countInvoicedDay_list.size()-1);
				count9InvoiceDay_list.remove(count9InvoiceDay_list.size()-1);
				count2InvoiceDay_list.remove(count2InvoiceDay_list.size()-1);
				countWaitTaskDay_list.remove(countWaitTaskDay_list.size()-1);
				days_list.remove(days_list.size()-1); 
	        }
			
	        countInvalidDay_list.add(countInvalidDay==null?0:countInvalidDay.size());
	        countInvoicedDay_list.add(countInvoicedDay==null?0:countInvoicedDay.size());
	        count9InvoiceDay_list.add(count9InvoiceDay==null?0:count9InvoiceDay.size());
	        count2InvoiceDay_list.add(count2InvoiceDay==null?0:count2InvoiceDay.size());
	        countWaitTaskDay_list.add(countWaitTaskDay==null?0:countWaitTaskDay.size()); 
	        
	        days_list.add(Integer.valueOf(day));
	        
	        redisService.set(orgId+"countInvalidDay_list",countInvalidDay_list, 0);
			redisService.set(orgId+"countInvoicedDay_list",countInvoicedDay_list, 0);
			redisService.set(orgId+"count9InvoiceDay_list",count9InvoiceDay_list, 0);
			redisService.set(orgId+"count2InvoiceDay_list",count2InvoiceDay_list, 0);
			redisService.set(orgId+"countWaitTaskDay_list",countWaitTaskDay_list, 0);
			redisService.set(orgId+"days_list",days_list, 0);
		}
		
		 
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("countInvalidDay_list", countInvalidDay_list);
		map.put("countInvoicedDay_list",countInvoicedDay_list);
		map.put("count9InvoiceDay_list", count9InvoiceDay_list);
		map.put("count2InvoiceDay_list", count2InvoiceDay_list);
		map.put("countWaitTaskDay_list", countWaitTaskDay_list);
		map.put("days_list", days_list);
		
		 
		return Result.Success(map);
	}
	 
	public static void main(String[] args) {
		
		  Date yeard0 = DateUtils.getBeginOfHalfYear();
	        SimpleDateFormat yearsp5=new SimpleDateFormat("yyyy-MM-dd 00:00:00");
	        String year_start1=yearsp5.format(yeard0);
	        SimpleDateFormat yearsp6=new SimpleDateFormat("yyyyMMdd000000");
	        String year_start2=yearsp6.format(yeard0);
			Date yeard1 =   DateUtils.getEndOfHalfYear();
	        SimpleDateFormat yearsp3=new SimpleDateFormat("yyyy-MM-dd 24:60:60");
	        String year_end1=yearsp3.format(yeard1);
	        SimpleDateFormat yearsp4=new SimpleDateFormat("yyyyMMdd246060");
	        String year_end2=yearsp4.format(yeard1); 
	        System.out.println(year_start2);
	        System.out.println(year_end2);
		
	}




	@Override
	public Result statisticalMoney(String orgId, Boolean refresh) {
		
//		每周日更新数据
		
		
//		获取今天的
		Date d0 = DateUtils.getDayBegin();
        SimpleDateFormat sp5=new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String day_start1=sp5.format(d0);
        SimpleDateFormat sp6=new SimpleDateFormat("yyyyMMdd000000");
        String day_start2=sp6.format(d0);
		Date d1 = DateUtils.getDayEnd();
        SimpleDateFormat sp3=new SimpleDateFormat("yyyy-MM-dd 24:60:60");
        String day_end1=sp3.format(d1);
        SimpleDateFormat sp4=new SimpleDateFormat("yyyyMMdd246060");
        String day_end2=sp4.format(d1);  
        
        Integer day_countMoneyAll = invoiceSplitLineMapper.countMoneyAll(orgId, day_start1, day_end1, day_start2, day_end2); 
        Integer day_countMoneyInvalidHsj = invoiceSplitLineMapper.countMoneyInvalidHsj(orgId, day_start1, day_end1, day_start2, day_end2); 
        Integer day_countMoneyInvoicedHsjFu = invoiceSplitLineMapper.countMoneyInvoicedHsjFu(orgId, day_start1, day_end1, day_start2, day_end2); 
        Integer day_countMoneyInvoicedHsjZhen = invoiceSplitLineMapper.countMoneyInvoicedHsjZhen(orgId, day_start1, day_end1, day_start2, day_end2); 
        Integer day_countMoneyInvoicedSe = invoiceSplitLineMapper.countMoneyInvoicedSe(orgId, day_start1, day_end1, day_start2, day_end2); 
        Integer day_countMoneyInvoicedZheKou = invoiceSplitLineMapper.countMoneyInvoicedZheKou(orgId, day_start1, day_end1, day_start2, day_end2); 
        
        
		
//		获取本周的
        Date weekd0 = DateUtils.getBeginDayOfWeek();
        SimpleDateFormat weeksp5=new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String week_start1=weeksp5.format(weekd0);
        SimpleDateFormat weeksp6=new SimpleDateFormat("yyyyMMdd000000");
        String week_start2=weeksp6.format(weekd0);
		Date weekd1 =   DateUtils.getEndDayOfWeek();
        SimpleDateFormat weeksp3=new SimpleDateFormat("yyyy-MM-dd 24:60:60");
        String week_end1=weeksp3.format(weekd1);
        SimpleDateFormat weeksp4=new SimpleDateFormat("yyyyMMdd246060");
        String week_end2=weeksp4.format(weekd1); 
        Integer week_countMoneyAll = invoiceSplitLineMapper.countMoneyAll(orgId, week_start1, week_end1, week_start2, week_end2); 
        Integer week_countMoneyInvalidHsj = invoiceSplitLineMapper.countMoneyInvalidHsj(orgId, week_start1, week_end1, week_start2, week_end2); 
        Integer week_countMoneyInvoicedHsjFu = invoiceSplitLineMapper.countMoneyInvoicedHsjFu(orgId, week_start1, week_end1, week_start2, week_end2); 
        Integer week_countMoneyInvoicedHsjZhen = invoiceSplitLineMapper.countMoneyInvoicedHsjZhen(orgId, week_start1, week_end1, week_start2, week_end2); 
        Integer week_countMoneyInvoicedSe = invoiceSplitLineMapper.countMoneyInvoicedSe(orgId, week_start1, week_end1, week_start2, week_end2); 
        Integer week_countMoneyInvoicedZheKou = invoiceSplitLineMapper.countMoneyInvoicedZheKou(orgId, week_start1, week_end1, week_start2, week_end2); 
        
		
		
//		获取本月的
        Date monthd0 = DateUtils.getBeginDayOfMonth();
        SimpleDateFormat monthsp5=new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String month_start1=monthsp5.format(monthd0);
        SimpleDateFormat monthsp6=new SimpleDateFormat("yyyyMMdd000000");
        String month_start2=monthsp6.format(monthd0);
		Date monthd1 =   DateUtils.getEndDayOfMonth();
        SimpleDateFormat monthsp3=new SimpleDateFormat("yyyy-MM-dd 24:60:60");
        String month_end1=monthsp3.format(monthd1);
        SimpleDateFormat monthsp4=new SimpleDateFormat("yyyyMMdd246060");
        String month_end2=monthsp4.format(monthd1); 
        
        Integer month_countMoneyAll = invoiceSplitLineMapper.countMoneyAll(orgId, month_start1, month_end1, month_start2, month_end2); 
              Integer month_countMoneyInvalidHsj = invoiceSplitLineMapper.countMoneyInvalidHsj(orgId, month_start1, month_end1, month_start2, month_end2); 
              Integer month_countMoneyInvoicedHsjFu = invoiceSplitLineMapper.countMoneyInvoicedHsjFu(orgId, month_start1, month_end1, month_start2, month_end2); 
              Integer month_countMoneyInvoicedHsjZhen = invoiceSplitLineMapper.countMoneyInvoicedHsjZhen(orgId, month_start1, month_end1, month_start2, month_end2); 
              Integer month_countMoneyInvoicedSe = invoiceSplitLineMapper.countMoneyInvoicedSe(orgId, month_start1, month_end1, month_start2, month_end2); 
              Integer month_countMoneyInvoicedZheKou = invoiceSplitLineMapper.countMoneyInvoicedZheKou(orgId, month_start1, month_end1, month_start2, month_end2); 
        
//      获取本季度的
        Date Quarterd0 = DateUtils.getBeginOfQuarter();
        SimpleDateFormat Quartersp5=new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String Quarter_start1=Quartersp5.format(Quarterd0);
        SimpleDateFormat Quartersp6=new SimpleDateFormat("yyyyMMdd000000");
        String Quarter_start2=Quartersp6.format(Quarterd0);
		Date Quarterd1 =   DateUtils.getEndOfQuarter();
        SimpleDateFormat Quartersp3=new SimpleDateFormat("yyyy-MM-dd 24:60:60");
        String Quarter_end1=Quartersp3.format(Quarterd1);
        SimpleDateFormat Quartersp4=new SimpleDateFormat("yyyyMMdd246060");
        String Quarter_end2=Quartersp4.format(Quarterd1); 
        
        Integer Quarter_countMoneyAll = invoiceSplitLineMapper.countMoneyAll(orgId, Quarter_start1, Quarter_end1, Quarter_start2, Quarter_end2); 
              Integer Quarter_countMoneyInvalidHsj = invoiceSplitLineMapper.countMoneyInvalidHsj(orgId, Quarter_start1, Quarter_end1, Quarter_start2, Quarter_end2); 
              Integer Quarter_countMoneyInvoicedHsjFu = invoiceSplitLineMapper.countMoneyInvoicedHsjFu(orgId, Quarter_start1, Quarter_end1, Quarter_start2, Quarter_end2); 
              Integer Quarter_countMoneyInvoicedHsjZhen = invoiceSplitLineMapper.countMoneyInvoicedHsjZhen(orgId, Quarter_start1, Quarter_end1, Quarter_start2, Quarter_end2); 
              Integer Quarter_countMoneyInvoicedSe = invoiceSplitLineMapper.countMoneyInvoicedSe(orgId, Quarter_start1, Quarter_end1, Quarter_start2, Quarter_end2); 
              Integer Quarter_countMoneyInvoicedZheKou = invoiceSplitLineMapper.countMoneyInvoicedZheKou(orgId, Quarter_start1, Quarter_end1, Quarter_start2, Quarter_end2); 
        
		
//		获取半年的 
        
        Date hyeard0 = DateUtils.getBeginOfHalfYear();
        SimpleDateFormat hyearsp5=new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String hyear_start1=hyearsp5.format(hyeard0);
        SimpleDateFormat hyearsp6=new SimpleDateFormat("yyyyMMdd000000");
        String hyear_start2=hyearsp6.format(hyeard0);
		Date hyeard1 =   DateUtils.getEndOfHalfYear();
        SimpleDateFormat hyearsp3=new SimpleDateFormat("yyyy-MM-dd 24:60:60");
        String hyear_end1=hyearsp3.format(hyeard1);
        SimpleDateFormat hyearsp4=new SimpleDateFormat("yyyyMMdd246060");
        String hyear_end2=hyearsp4.format(hyeard1); 
        
        Integer hyear_countMoneyAll = invoiceSplitLineMapper.countMoneyAll(orgId, hyear_start1, hyear_end1, hyear_start2, hyear_end2); 
              Integer hyear_countMoneyInvalidHsj = invoiceSplitLineMapper.countMoneyInvalidHsj(orgId, hyear_start1, hyear_end1, hyear_start2, hyear_end2); 
              Integer hyear_countMoneyInvoicedHsjFu = invoiceSplitLineMapper.countMoneyInvoicedHsjFu(orgId, hyear_start1, hyear_end1, hyear_start2, hyear_end2); 
              Integer hyear_countMoneyInvoicedHsjZhen = invoiceSplitLineMapper.countMoneyInvoicedHsjZhen(orgId, hyear_start1, hyear_end1, hyear_start2, hyear_end2); 
              Integer hyear_countMoneyInvoicedSe = invoiceSplitLineMapper.countMoneyInvoicedSe(orgId, hyear_start1, hyear_end1, hyear_start2, hyear_end2); 
              Integer hyear_countMoneyInvoicedZheKou = invoiceSplitLineMapper.countMoneyInvoicedZheKou(orgId, hyear_start1, hyear_end1, hyear_start2, hyear_end2); 
        
        

		
		
//		获取今年的
        Date yeard0 = DateUtils.getBeginDayOfMonth();
        SimpleDateFormat yearsp5=new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String year_start1=yearsp5.format(yeard0);
        SimpleDateFormat yearsp6=new SimpleDateFormat("yyyyMMdd000000");
        String year_start2=yearsp6.format(yeard0);
		Date yeard1 =   DateUtils.getEndDayOfMonth();
        SimpleDateFormat yearsp3=new SimpleDateFormat("yyyy-MM-dd 24:60:60");
        String year_end1=yearsp3.format(yeard1);
        SimpleDateFormat yearsp4=new SimpleDateFormat("yyyyMMdd246060");
        String year_end2=yearsp4.format(yeard1); 
        
        
        Integer year_countMoneyAll = invoiceSplitLineMapper.countMoneyAll(orgId, year_start1, year_end1, year_start2, year_end2); 
        Integer year_countMoneyInvalidHsj = invoiceSplitLineMapper.countMoneyInvalidHsj(orgId, year_start1, year_end1, year_start2, year_end2); 
        Integer year_countMoneyInvoicedHsjFu = invoiceSplitLineMapper.countMoneyInvoicedHsjFu(orgId, year_start1, year_end1, year_start2, year_end2); 
        Integer year_countMoneyInvoicedHsjZhen = invoiceSplitLineMapper.countMoneyInvoicedHsjZhen(orgId, year_start1, year_end1, year_start2, year_end2); 
        Integer year_countMoneyInvoicedSe = invoiceSplitLineMapper.countMoneyInvoicedSe(orgId, year_start1, year_end1, year_start2, year_end2); 
        Integer year_countMoneyInvoicedZheKou = invoiceSplitLineMapper.countMoneyInvoicedZheKou(orgId, year_start1, year_end1, year_start2, year_end2); 
		
        
        ArrayList<Integer> countMoneyAll = new ArrayList<>();
        ArrayList<Integer> countMoneyInvalidHsj = new ArrayList<>();
        ArrayList<Integer> countMoneyInvoicedHsjFu = new ArrayList<>();
        ArrayList<Integer> countMoneyInvoicedHsjZhen= new ArrayList<>();
        ArrayList<Integer> countMoneyInvoicedSe = new ArrayList<>();
        ArrayList<Integer> countMoneyInvoicedZheKou = new ArrayList<>();
        
        countMoneyAll.add(day_countMoneyAll);
        countMoneyAll.add(week_countMoneyAll);
        countMoneyAll.add(Quarter_countMoneyAll);
        countMoneyAll.add(hyear_countMoneyAll);
        countMoneyAll.add(year_countMoneyAll);

        countMoneyInvalidHsj.add(day_countMoneyInvalidHsj);
        countMoneyInvalidHsj.add(week_countMoneyInvalidHsj);
        countMoneyInvalidHsj.add(Quarter_countMoneyInvalidHsj);
        countMoneyInvalidHsj.add(hyear_countMoneyInvalidHsj);
        countMoneyInvalidHsj.add(year_countMoneyInvalidHsj);
        
        countMoneyInvoicedHsjFu.add(day_countMoneyInvoicedHsjFu);
        countMoneyInvoicedHsjFu.add(week_countMoneyInvoicedHsjFu);
        countMoneyInvoicedHsjFu.add(Quarter_countMoneyInvoicedHsjFu);
        countMoneyInvoicedHsjFu.add(hyear_countMoneyInvoicedHsjFu);
        countMoneyInvoicedHsjFu.add(year_countMoneyInvoicedHsjFu);
        
        countMoneyInvoicedHsjZhen.add(day_countMoneyInvoicedHsjZhen);
        countMoneyInvoicedHsjZhen.add(week_countMoneyInvoicedHsjZhen);
        countMoneyInvoicedHsjZhen.add(Quarter_countMoneyInvoicedHsjZhen);
        countMoneyInvoicedHsjZhen.add(hyear_countMoneyInvoicedHsjZhen);
        countMoneyInvoicedHsjZhen.add(year_countMoneyInvoicedHsjZhen);
        
        countMoneyInvoicedSe.add(day_countMoneyInvoicedSe);
        countMoneyInvoicedSe.add(week_countMoneyInvoicedSe);
        countMoneyInvoicedSe.add(Quarter_countMoneyInvoicedSe);
        countMoneyInvoicedSe.add(hyear_countMoneyInvoicedSe);
        countMoneyInvoicedSe.add(year_countMoneyInvoicedSe);
        
        countMoneyInvoicedZheKou.add(day_countMoneyInvoicedZheKou);
        countMoneyInvoicedZheKou.add(week_countMoneyInvoicedZheKou);
        countMoneyInvoicedZheKou.add(Quarter_countMoneyInvoicedZheKou);
        countMoneyInvoicedZheKou.add(hyear_countMoneyInvoicedZheKou);
        countMoneyInvoicedZheKou.add(year_countMoneyInvoicedZheKou);
        
        
         
        HashMap<String,Object> map = new HashMap<>();
        map.put("countMoneyAll", countMoneyAll);
        map.put("countMoneyInvalidHsj", countMoneyInvalidHsj);
        map.put("countMoneyInvoicedHsjFu", countMoneyInvoicedHsjFu);
        map.put("countMoneyInvoicedHsjZhen",countMoneyInvoicedHsjZhen);
        map.put("countMoneyInvoicedSe", countMoneyInvoicedSe);
        map.put("countMoneyInvoicedZheKou", countMoneyInvoicedZheKou);
        
        
              
              return Result.Success(map);
	}



//	 * 统计 月度
//	 * 待打印
//	 *待作废
//	 *待开票
//	 *待推送
//	 *待同步
//	 *的信息
	@Override
	public Result statisticalHealth(String orgId, Boolean refresh,String time) {
		 Date monthd0 = null;
		 Date monthd1 = null;
		if(time.equals("year")) {
			monthd0  = DateUtils.getBeginDayOfYear();
			  monthd1 =   DateUtils.getEndDayOfYear();
		}else if(time.equals("quarter")) {
			monthd0  = DateUtils.getBeginOfQuarter();
			  monthd1 =   DateUtils.getEndOfQuarter();
		}else {
			monthd0  = DateUtils.getBeginDayOfMonth();
			  monthd1 =   DateUtils.getEndDayOfMonth();
		}
		
		
	        SimpleDateFormat monthsp5=new SimpleDateFormat("yyyy-MM-dd 00:00:00");
	        String month_start1=monthsp5.format(monthd0);
	        SimpleDateFormat monthsp6=new SimpleDateFormat("yyyyMMdd000000");
	        String month_start2=monthsp6.format(monthd0);
			
	        SimpleDateFormat monthsp3=new SimpleDateFormat("yyyy-MM-dd 24:60:60");
	        String month_end1=monthsp3.format(monthd1);
	        SimpleDateFormat monthsp4=new SimpleDateFormat("yyyyMMdd246060");
	        String month_end2=monthsp4.format(monthd1); 
	        
	        
	     // 待开票 
	        List<InvoiceSplitLine> countWaitInvoice = invoiceSplitLineMapper.countWaitInvoice(orgId, month_end1, month_end2, month_start1, month_start2);
	        List<InvoiceSplitLine> count2InvoiceDay = invoiceSplitLineMapper.count2InvoiceDay(orgId, month_end1, month_end2, month_start1, month_start2);
	        Integer countWaitInvoice_d =countWaitInvoice==null?0:countWaitInvoice.size();
	        Integer count2Invoice=count2InvoiceDay==null?0:count2InvoiceDay.size();
	        Integer count1_2 = countWaitInvoice_d+count2Invoice; 
	        
	     // 待作废
	        List<InvoiceSplitLine> countWaitInvalid = invoiceSplitLineMapper.countWaitInvalid(orgId, month_end1, month_end2, month_start1, month_start2);
	        List<InvoiceSplitLine> count9InvalidDay = invoiceSplitLineMapper.count9InvoiceDay(orgId, month_end1, month_end2, month_start1, month_start2);
	        Integer countWaitInvalid_d =countWaitInvalid==null?0:countWaitInvalid.size();
	        Integer count9Invoice=count9InvalidDay==null?0:count9InvalidDay.size();
	        Integer count3_9 = countWaitInvalid_d+count9Invoice; 
	         
	        
         //	待同步
	        List<InvoiceSplitLine> countSyncd = invoiceSplitLineMapper.countSyncd(orgId, month_end1, month_end2, month_start1, month_start2);
	        List<InvoiceSplitLine> countWaitSync = invoiceSplitLineMapper.countWaitSync(orgId, month_end1, month_end2, month_start1, month_start2);
	        Integer countSyncd_d =countSyncd==null?0:countSyncd.size();
	        Integer countWaitSync_d=countWaitSync==null?0:countWaitSync.size();
	        Integer countSync = countWaitSync_d+countSyncd_d; 
	        
	        
	        
//        待打印的发票
	        List<InvoiceSplitLine> countDayined = invoiceSplitLineMapper.countDayined(orgId, month_end1, month_end2, month_start1, month_start2);
	        List<InvoiceSplitLine> countWaitDayin = invoiceSplitLineMapper.countWaitDayin(orgId, month_end1, month_end2, month_start1, month_start2);
	        Integer countDayined_d =countDayined==null?0:countDayined.size();
	        Integer countWaitDayin_d=countWaitDayin==null?0:countWaitDayin.size();
	        Integer countDayin = countDayined_d+countWaitDayin_d;
	        ArrayList<Integer> countDone = new ArrayList<>();
	        ArrayList<Integer> countAll = new ArrayList<>();
	        
	        countDone.add(count2Invoice);
	        countAll.add(count1_2);
	        countDone.add(count9Invoice);
	        countAll.add(count3_9);
	        countDone.add(countSyncd_d);
	        countAll.add(countSync);
	        countDone.add(countDayined_d);
	        countAll.add(countDayin);
	        
	         
	        HashMap<String, Object> map = new HashMap<>();
	        
	        
	        map.put("已完成", countDone);
	        map.put("总任务", countAll);
	        
		return Result.Success(map);
	}

	
	
	
	@Override
	public Map<String, Long> getTask(String orgId, Integer day) { 
		 Map<String, Long> map = new HashMap<>();
		String dateStart = TimeUtils.subDays(day);
		List<HashMap<String,Object>> list =  invoiceSplitLineMapper.getStaticNum(orgId,dateStart);
		
		if (list != null && !list.isEmpty()) {
			for (HashMap<String, Object> map1 : list) {
				String key = null;
				Long value = null;
				for (Map.Entry<String, Object> entry : map1.entrySet()) {
					if ("key".equals(entry.getKey())) {
						key = (String) entry.getValue();
					} else if ("value".equals(entry.getKey())) {
						value = (Long) entry.getValue();
					}
				}
				map.put(key, value);
			}
			
		}
		return map;
	}




	@Override
	public Result getInvoiceDetail(@NotBlank String docNum, @NotBlank String groupNum) {
		InvoiceSplitLineExample example = new InvoiceSplitLineExample();
		example.createCriteria().andDocNumEqualTo(docNum);
		example.createCriteria().andGroupNumEqualTo(groupNum); 
		List<InvoiceSplitLine> list = invoiceSplitLineMapper.selectByExample(example); 
		InvoiceHead head = invoiceHeadMapper.selectByPrimaryKey(docNum);
		if(head==null) {
			return Result.Error("发票数据异常,没有头");
		}
		head.setInvoiceSplitLines(list);
		return Result.Success(head); 
	}

 
	  /**
     * 张琪
     * 功能介绍:撤销 所有 已提交的 单据。撤销前检查。
     * 撤销之前，应该检查：是否正在开票， 
     */
    @Override
    public Result undoCommited(String orgId,String yyyyMM) {
        InvoiceHead head = new InvoiceHead();
        head.setOrgId(orgId);  
        
        String date = yyyyMM + "%";
        if(StringUtils.isBlank(yyyyMM)) {
        	SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        	 date = format.format(new Date())+"%";
        }
        log.info("解锁参数:orgId:"+orgId+"/date："+date);
        
        List<InvoiceHead> list = invoiceHeadMapper.unlock(head, date);
        if (list != null && list.size() > 0) {
            log.info("正在解锁本月的单据");
            for (InvoiceHead invoiceHead : list) {
                boolean isAble = true;
                if(invoiceHead.getInvoiceSplitLines()==null) {
                	return Result.Error("发票体数据没有取到");
                }
                for (InvoiceSplitLine invoiceSplitLine : invoiceHead.getInvoiceSplitLines()) {
                    //    如果这个单据中任意1组（一张发票）状态 不是 准备开票(有某一组已经开了) ， 那么这张单据就不能撤销
                    if (invoiceHead.getDocStatus().equals("3")) {
                        log.info(invoiceHead.getDocNum() + "是作废状态，跳出检验，允许回传");
                        break;
                    }
                    if (!invoiceSplitLine.getGroupStatus().equals("1")) {
                        isAble = false;
                        log.info("单据:" + invoiceHead.getDocNum() + "已经有组开出来了");
                    }
                    if (!StringUtils.isEmpty(invoiceSplitLine.getGoldtaxCode())) {
                        log.info("单据:" + invoiceHead.getDocNum() + "有组正在进行开票");
                        isAble = false;
                    }
                }

                if (isAble) {
//					回传
                    try {
                        JCoFunction unlockF = sapService.feedbackInvoiceHeadAndSplitlinesForUnlock(invoiceHead); 
                         
                        String EX_TYPE = unlockF.getExportParameterList().getString("EX_TYPE");
                        
                        String EX_MESSAGE = unlockF.getExportParameterList().getString("EX_MESSAGE");
                        if (EX_TYPE.equals("S") && invoiceHead.getDocStatus().equals("1")) {
                            log.info("正在删除单据:" + invoiceHead.getDocNum());
                            //这个根据这个实例中的内容删除，用封装的直接删除就好（不懂问我），不要写sql
//							delete from invoice_head where doc_num= #{docNum,jdbcType=VARCHAR};
//							delete from invoice_split_line where doc_num= #{docNum,jdbcType=VARCHAR};
//                            this.manager.delete("invoice_head.del", (IPO) invoiceHead);
                            invoiceHeadMapper.deleteByPrimaryKey(invoiceHead.getDocNum());
                            
                            InvoiceSplitLineKey invoiceSplitLineKey = new InvoiceSplitLineKey();
                            invoiceSplitLineKey.setDocNum(invoiceHead.getDocNum());
                            invoiceSplitLineMapper.deleteByPrimaryKey(invoiceSplitLineKey); 
                            
                             log.info("删除OK:" + invoiceHead.getDocNum());
                        } else if (EX_TYPE.equals("S") && invoiceHead.getDocStatus().equals("3")) {
                            invoiceHead.setDocStatus("2");
                            //这个根据这个实例中的内容更新数据，用封装的工具就好，不懂问我
//							 update invoice_head set doc_status=#{docStatus,jdbcType=VARCHAR} where doc_num= #{docNum,jdbcType=VARCHAR}
//                            this.manager.update("invoice_head.changeDocStatusById", invoiceHead); 
                            invoiceHeadMapper.updateByPrimaryKeySelective(invoiceHead); 
                            List<InvoiceSplitLine> splitLines = invoiceHead.getInvoiceSplitLines();
                            if (splitLines != null && splitLines.size() > 0) {
                                for (InvoiceSplitLine splitLine : splitLines) {
//                            		 update invoice_head set del_state=#{delState,jdbcType=VARCHAR} where doc_num= #{docNum,jdbcType=VARCHAR}
//                                    this.manager.update("invoice_split_line.changeDelStateById", splitLine);
                                    invoiceSplitLineMapper.changeDelStateById(splitLine);
                                }
                            }
                            log.info("作废的需要修改状态:" + invoiceHead.getDocNum());
                        } else {
                            log.info("回传失败:" + EX_MESSAGE);
                        }
                    } catch (JCoException e) {
                        log.error("解锁单据:" + invoiceHead.getDocNum() + "发生异常");
                        e.printStackTrace();
                    }
                } else {
                    log.info("单据:" + invoiceHead.getDocNum() + "不允许解锁");
                }
            }
            log.info("解锁本月的单据成功");
        }
        return Result.Success("本月解锁成功");
    }
	/**
	 * 张琪
	 */
//	@Override
//	public Result myunlock(String orgId) {
//		InvoiceHead head = new InvoiceHead();
//		head.setOrgId(orgId); 
////		这个查询bill_date为本月的数据,从invoice_head表查，查询对应orgId的头，查到invoice_head信息后，
////		根据doc_num从invoice_split_line关联 
////		select  *  
////		FROM invoice_head WHERE DATE_FORMAT(doc_date,'%Y%m') =DATE_FORMAT(NOW(),'%Y%m') 
////		AND DOC_STATUS IN (1,3)
// 
//		
////		select *
////		FROM (SELECT * FROM invoice_split_line WHERE doc_num =  #{docNum,jdbcType=VARCHAR} ORDER BY GOLDTAX_NUM  DESC   LIMIT 10000) a GROUP BY GROUP_NUM  ORDER BY GOLDTAX_NUM
//
////		List<InvoiceHead> list =  (List<InvoiceHead>)manager.list("invoice_head.unlock",head);
////		List<InvoiceHead> list = InvoiceViewService.queruBE(head);
////		
////		if(list!=null&&list.size()>0) {
////			log.info("正在解锁本月的单据");
////			for (InvoiceHead invoiceHead : list) { 
////				boolean isAble=true; 
////				for (InvoiceSplitLine invoiceSplitLine : invoiceHead.getInvoiceSplitLines()) {
////				//    如果这个单据中任意1组（一张发票）状态 不是 准备开票(有某一组已经开了) ， 那么这张单据就不能撤销
////				   if(invoiceHead.getDocStatus().equals("3")) {
////					    log.info(invoiceHead.getDocNum()+"是作废状态，跳出检验，允许回传");
////						break;
////				   }
////				   if (!invoiceSplitLine.getGroupStatus().equals("1") ) {
////	                   isAble=false;
////	                   log.info("单据:"+invoiceHead.getDocNum()+"已经有组开出来了");
////				   } 
////				   if(!StringUtils.isEmpty(invoiceSplitLine.getGoldtaxCode())) {
////					   log.info("单据:"+invoiceHead.getDocNum()+"有组正在进行开票");
////					   isAble=false;
////				   }
////			    } 
////				
////				if(isAble) { 
//////					回传
////					try {
////						JCoFunction unlockF = sapService.feedbackInvoiceHeadAndSplitlinesForUnlock(invoiceHead);
////						String EX_TYPE = unlockF.getExportParameterList().getString("EX_TYPE");
////						String EX_MESSAGE = unlockF.getExportParameterList().getString("EX_MESSAGE");
////						if (EX_TYPE.equals("S")&&invoiceHead.getDocStatus().equals("1")) {  
////							log.info("正在删除单据:"+invoiceHead.getDocNum()); 
////							//这个根据这个实例中的内容删除，用封装的直接删除就好（不懂问我），不要写sql
//////							delete from invoice_head where doc_num= #{docNum,jdbcType=VARCHAR};
//////							delete from invoice_split_line where doc_num= #{docNum,jdbcType=VARCHAR};
////							this.manager.delete("invoice_head.del", (IPO)invoiceHead); 
////							
////							
////							log.info("删除OK:"+invoiceHead.getDocNum());
////						} else if (EX_TYPE.equals("S")&&invoiceHead.getDocStatus().equals("3")){
////							 invoiceHead.setDocStatus("2");
////							 
////							//这个根据这个实例中的内容更新数据，用封装的工具就好，不懂问我
//////							 update invoice_head set doc_status=#{docStatus,jdbcType=VARCHAR} where doc_num= #{docNum,jdbcType=VARCHAR}
////                             this.manager.update("invoice_head.changeDocStatusById", invoiceHead);
////                             
////                             
////                             List<InvoiceSplitLine> splitLines = invoiceHead.getInvoiceSplitLines();
////                             if(splitLines!=null&&splitLines.size()>0) {
////                            	 for (InvoiceSplitLine  splitLine : splitLines) {
////                            		 
//////                            		 update invoice_head set del_state=#{delState,jdbcType=VARCHAR} where doc_num= #{docNum,jdbcType=VARCHAR}
////                            		 this.manager.update("invoice_split_line.changeDelStateById", splitLine);
////                            		 
////                            		 
////								 }
////                             }
////							 log.info("作废的需要修改状态:"+invoiceHead.getDocNum());
////						}else {
////							log.info("回传失败:"+EX_MESSAGE);
////						}
////					} catch (JCoException e) {
////						log.error("解锁单据:"+invoiceHead.getDocNum()+"发生异常");
////						e.printStackTrace();
////					}
////				}else {
////					log.info("单据:"+invoiceHead.getDocNum()+"不允许解锁");
////				}
////			}
////			log.info("解锁本月的单据成功");
//////		} 
//		return Result.Success("本月解锁成功"); 
//	}



	
	
	
	
	
	 
	
	
	
	
//	public Result getTask(BusinessVo businessVo,Integer day) { 
//		
//	}
}
