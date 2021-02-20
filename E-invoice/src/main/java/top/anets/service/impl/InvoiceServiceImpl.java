/**
 * 
 */
package top.anets.service.impl;

import static org.hamcrest.CoreMatchers.nullValue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.acl.Group;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestBody;

import com.alibaba.druid.support.json.JSONUtils;
import com.google.gson.Gson;
import com.sap.tc.logging.interfaces.IFileLog;

import lombok.extern.slf4j.Slf4j;
import top.anets.entity.Business;
import top.anets.entity.InvoiceHead;
import top.anets.entity.InvoiceHeadExample;
import top.anets.entity.InvoiceSplitLine;
import top.anets.entity.InvoiceSplitLineExample;
import top.anets.entity.InvoiceSplitLineExample.Criteria;
import top.anets.mapper.InvoiceHeadMapper;
import top.anets.mapper.InvoiceSplitLineMapper;
import top.anets.redis.RedisKey;
import top.anets.redis.RedisService;
import top.anets.service.InvoiceService;
import top.anets.service.SapService;
import top.anets.service.ThirdInvoiceService;
import top.anets.utils.CheckUtils;
import top.anets.utils.InvoiceUtils;
import top.anets.utils.Result;
import top.anets.vo.BusinessVo;
import top.anets.vo.DeviceInfo;
import top.anets.vo.InvoiceResult;
import top.anets.vo.Logs;
import top.anets.vo.Message;

/**
 * @author Administrator
 */ 
@Service
@Slf4j

public class InvoiceServiceImpl implements InvoiceService{
	@Autowired
	private SapService sapService;
    @Autowired
    private InvoiceHeadMapper invoiceHeadMapper; 
    @Autowired
    private InvoiceSplitLineMapper invoiceSplitLineMapper;
    @Autowired 
    private RedisService redisService;
    
    @Value("${httpclient.dev_or_prod}")
	  private String dev_or_prod; 
    
    @Autowired()
    @Qualifier("guoXinInvoiceService")
//    @Qualifier("xuanjiInvocieService")
    private ThirdInvoiceService thirdInvoiceService;
    
    
	@Override
	@Transactional
	public Result invoice(InvoiceHead invoiceHead,BusinessVo business) {
//		匹配线路
		InvoiceUtils.matchPath(invoiceHead, business);
		
		this.updateSplitLinesInvoicing(invoiceHead.getDocNum(),invoiceHead.getInvoiceSplitLines().get(0).getGroupNum());
		//判断是否严格控制税差
		Boolean strictControlSc=null;
		 strictControlSc = InvoiceUtils.strictControlSc(invoiceHead);
		if(business.getIsStrict()&&strictControlSc) {  
				  return Result.Error("500","税差超过了6分",null); 
		}
		
		
		InvoiceHead invoiceHeadNew =null;
		if(invoiceHead.getInvoiceWay().equals("2")) {
		    invoiceHeadNew = new Gson().fromJson(new Gson().toJson(invoiceHead), InvoiceHead.class);
//			判断是否重新计算税额
			if(!business.getIsStrict()&&strictControlSc){
//				重新计算税额
				InvoiceUtils.reCalculateSe(invoiceHeadNew,business);
			}
			InvoiceUtils.handleZhekou(invoiceHeadNew,business);
		}else { 
			if(!business.getIsStrict()&&strictControlSc){
				invoiceHeadNew = new Gson().fromJson(new Gson().toJson(invoiceHead), InvoiceHead.class);
                InvoiceUtils.reCalculateSe(invoiceHeadNew,business);
			}else {
				invoiceHeadNew=invoiceHead;
			}
		}
		 
//		判断是否超限额
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		String mouth = format.format(new Date());
		String ws = redisService.get(invoiceHead.getOrgId()+mouth+"wxLimit") ;
		String se = redisService.get(invoiceHead.getOrgId()+mouth+"seLimit") ;
		Long wxLimit = business.getWxLimit();
    	Long seLimit = business.getSeLimit();
		if(ws!=null&&se!=null) {
			BigDecimal wsj = new BigDecimal(ws);
	    	BigDecimal sej = new BigDecimal(se); 
	    	if(wxLimit!=null&&new BigDecimal(wxLimit).compareTo(wsj)<0) {
	    		return Result.Error("本月累计未税金额:"+wsj.toString()+"超过限定:"+wxLimit);
	    	}
            if(seLimit!=null&&new BigDecimal(seLimit).compareTo(sej)<0) {
            	return Result.Error("本月累计税额:"+sej.toString()+"超过限定:"+seLimit);
	    	}  
		}
		if( invoiceHeadNew.getInvoiceType().equals("4")) { 
			InvoiceResult invoiceResult = thirdInvoiceService.InvoiceElectron(invoiceHeadNew, business);
			if(invoiceResult.getSuccess()) {
				try {
					InvoiceUtils.ExtendHandleInvoiceResult(invoiceHead,invoiceResult,business);  
				} catch (Exception e) {
					e.printStackTrace();
					log.info(e.getMessage());
					InvoiceUtils.stopInvoice(business);
				}
				this.updateInvoiceData(invoiceHead);
				return Result.Success(invoiceHead);
			}else {
				return Result.Error(invoiceResult.getCode(), invoiceResult.getMsg(), invoiceResult.getData());
			}
		}else {
			 return Result.Error("暂时只支持电票开具");
		}
	}
	
	
    
	
	@Override
	public Result invalid(InvoiceHead invoice,Business business) {
		this.updateSplitLinesInvaling(invoice.getDocNum(),invoice.getInvoiceSplitLines().get(0).getGroupNum());
		return thirdInvoiceService.invalid(invoice,business);
	}
    
    
	@Override
	public Result save(InvoiceHead invoiceHead) {
//		校验数据
		CheckUtils.checkInvoiceHead(invoiceHead);
//		保存数据
		List<InvoiceSplitLine> splitLines = invoiceHead.getInvoiceSplitLines();
		if(splitLines==null||splitLines.size()<=0) {
			return Result.Error("发票体数据不能为为空");
		}
		
		try { 
			invoiceHeadMapper.insertSelective(invoiceHead);
		} catch (Exception e) { 
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();  
		    return Result.Error("单据号重复或者字段长度不匹配", e.getMessage());
		}
		
//		存发票体数据
		for (InvoiceSplitLine invoiceSplitLine : splitLines) {
			try {
				invoiceSplitLineMapper.insertSelective(invoiceSplitLine);
			} catch (Exception e) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();  
				return Result.Error("存入发票体数据时错误", e.getMessage());
			}
		}
		return Result.Success();
	}
     
	
	
	/**
	 * 得到正在开票的数据
	 */
	@Override
	public List<InvoiceHead> getInvoicingInvoiceHead(BusinessVo business) { 
		List<InvoiceHead> invoiceHeads = invoiceHeadMapper.getInvoicingInvoiceHead(business.getOrgId(),business.getOrgMachine(),business.getInvoiceType());
//		发票分组
		List<InvoiceHead> heads =this.group(invoiceHeads);
		return  heads;
	}
	
	
	/**
	* 传入的invoiceHeads中invoiceSplitlines中必须经过排序才行
	*@param invoiceHeads
	*@return 
	*/
	private List<InvoiceHead> group(List<InvoiceHead> invoiceHeads) {
		if(invoiceHeads==null||invoiceHeads.size()<=0) {
			return null;
		} 
		List<InvoiceHead> heads = new ArrayList<>();
		for (InvoiceHead invoiceHead : invoiceHeads) {
			List<InvoiceHead> list = this.group(invoiceHead);
			heads.addAll(list);
		}
		return heads;
	}
	
	@Override
	public List<InvoiceHead> group(InvoiceHead invoiceHead) {
        if(invoiceHead==null||invoiceHead.getInvoiceSplitLines()==null||invoiceHead.getInvoiceSplitLines().size()<=0) {
        	return null;
        }
        String group = invoiceHead.getInvoiceSplitLines().get(0).getGroupNum(); 
        HashMap<String,List<InvoiceSplitLine>> hashMap = new HashMap<>();
        for (InvoiceSplitLine splitLine : invoiceHead.getInvoiceSplitLines()) {
			List<InvoiceSplitLine> list = hashMap.get(splitLine.getGroupNum());
			if(list==null) {
				List<InvoiceSplitLine> arrayList = new ArrayList<>();
				arrayList.add(splitLine);
				hashMap.put(splitLine.getGroupNum(), arrayList);
			}else {
				list.add(splitLine);
				hashMap.put(splitLine.getGroupNum(), list);
			}
		}
        
        List<InvoiceHead> heads = new ArrayList<>();
        Set<Entry<String, List<InvoiceSplitLine>>> entrySet = hashMap.entrySet();
        for (Entry<String, List<InvoiceSplitLine>> entry : entrySet) {
			 InvoiceHead head = new InvoiceHead();
			 BeanUtils.copyProperties(invoiceHead, head);
			 head.setInvoiceSplitLines(entry.getValue());
			 heads.add(head);
		}
        
        return heads;
	}

	@Override
	public Result  queryAndHandleInvoiceResult(InvoiceHead invoiceHead,BusinessVo business) {
//		匹配线路
		InvoiceUtils.matchPath(invoiceHead, business);
		
		Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO, invoiceHead.getDocNum()+"/"+invoiceHead.getInvoiceSplitLines().get(0).getGroupNum(), "正在查询结果 ");  		       				
		//去查询结果
		InvoiceResult query = null;    
		if(invoiceHead.getInvoiceType().equals("4")) {
			  query = thirdInvoiceService.invoiceQuery(invoiceHead, business);   
		}else {
//			   query = thirdInvoiceService.invoiceQuery(invoiceHead,business);    
			InvoiceUtils.stopInvoice(business);
			 this.updateSplitLinesInvoicingRecoverNomal(invoiceHead);
			 Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO ,null,"抱歉，暂时只支持电票"+"["+business.getOrgId()+"]");  						
			return  Result.Error("抱歉，暂时只支持电票");
		} 
    	 List<InvoiceSplitLine> lines = invoiceHead.getInvoiceSplitLines();
    	 InvoiceSplitLine spLine = invoiceHead.getInvoiceSplitLines().get(0);
    	 
    	 if(!query.success) {
    		 InvoiceUtils.stopInvoice(business,invoiceHead, Result.Error(query.getCode(), query.getMsg(), query.getData()));
    		 return  Result.Error(query.getCode(), query.getMsg(), query.getData());
    	 }else  if(query.success&&query.getGroupStatus().equals("2")) {
    		   //封装数据 
				InvoiceUtils.ExtendHandleInvoiceResult(invoiceHead, query,business);
//				更新
				this.updateInvoiceData(invoiceHead);
				Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO,query.getDocNum()+"/"+query.getGroupNum(), "查询开票成功,发票号码:"+query.getGoldtaxNum()+"["+business.getOrgId()+"]");  		
				 return  Result.Success(query.getCode(), "查询开票成功,发票号码:"+query.getGoldtaxNum());
    	 }else  if(query.success&&query.getGroupStatus().equals("9")) {
    		    //封装数据
     		    InvoiceUtils.ExtendHandleInvalidResult(invoiceHead, query);
				this.updateInvoiceData(invoiceHead);
				Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO,query.getDocNum()+"/"+query.getGroupNum(), "查询作废成功");  		  
				return  Result.Success(query.getCode(), "查询作废成功");

    	 }else if(spLine.getGroupStatus().equals("1")){
    		 this.updateSplitLinesInvoicingRecoverNomal(invoiceHead);
			 Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO,query.getDocNum()+"/"+query.getGroupNum(), "查询成功,开票失败"); 
			 return  Result.Success(query.getCode(),"查询成功,开票失败");

    	 }else  if(spLine.getGroupStatus().equals("3")){
    		 this.updateSplitLinesInvalidRecoverNomal(invoiceHead);
			 Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO,query.getDocNum()+"/"+query.getGroupNum(), "查询成功，作废失败");  		
			 return  Result.Success(query.getCode(),"查询成功,作废失败");
		}else {
			 this.updateSplitLinesInvalidRecoverNomal(invoiceHead);
			 Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO,query.getDocNum()+"/"+query.getGroupNum(), query.msg);  		             
			 InvoiceUtils.stopInvoice(business,invoiceHead, Result.Error(query.getCode(), "未知发票状态-"+spLine.getGroupStatus()+"与发票结果状态-"+query.getGroupStatus()+"|"+query.getMsg(), query.getData()));
			 return  Result.Success(query.getCode(),query.getMsg(), query.getData());
		}
    	 
    
			
	}

	
	









	/**
	* 
	*@param invoiceHead 
	*/
	@Transactional(propagation = Propagation.SUPPORTS)
	private void updateInvoiceHead(InvoiceHead invoiceHead) {
		 invoiceHeadMapper.updateByPrimaryKeySelective(invoiceHead);
		 if(!"dev".equals(dev_or_prod)) {
			 sapService.feedBackBill(invoiceHead.getDocNum());
		 } 
	}
	
	
	
	
	 

	/**
	* 
	*@param invoiceHead 
	*/
	@Override
	public void updateSplitLinesInvoicingRecoverNomal(InvoiceHead invoiceHead) {
		InvoiceSplitLineExample example=new InvoiceSplitLineExample();
		Criteria criteria = example.createCriteria();
		criteria.andDocNumEqualTo(invoiceHead.getDocNum());
		criteria.andGroupNumEqualTo(invoiceHead.getInvoiceSplitLines().get(0).getGroupNum()); 
		InvoiceSplitLine record = new InvoiceSplitLine();
		record.setGoldtaxCode("");
		invoiceSplitLineMapper.updateByExampleSelective(record, example);
	}

	@Override
	public void updateWaitToInvoiceData(BusinessVo business) {
//		清空数据
		redisService.ltrim(business.getOrgId()+"-"+business.getOrgMachine()+"-"+RedisKey.Invoice_Wait);
		
//      从数据库查询代开票信息
		List<InvoiceHead> data = invoiceHeadMapper.getInvoiceData(business.getOrgId(),business.getOrgMachine(),"1",business.getInvoiceType());
		List<InvoiceHead> group = this.group(data);
		if(group==null) { 
			return ;
		}
		
//		存于redis
		for (InvoiceHead invoiceHead : group) {
			redisService.lpush(business.getOrgId()+"-"+business.getOrgMachine()+"-"+RedisKey.Invoice_Wait, invoiceHead);
		}  
	}
	
	
	@Override
	public void updateWaitToInvalidData(BusinessVo business) {
//		清空数据
		redisService.ltrim(business.getOrgId()+"-"+business.getOrgMachine()+"-"+RedisKey.Invoice_Invalid);
		
		log.info("取数条件:"+business.getOrgId()+" "+business.getOrgMachine()+" "+"3"+" "+business.getInvoiceType());
		
//      从数据库查询代开票信息
		List<InvoiceHead> data = invoiceHeadMapper.getInvoiceData(business.getOrgId(),business.getOrgMachine(),"3",business.getInvoiceType());
		List<InvoiceHead> group = this.group(data);
		if(group==null) {
			return ;
		}

//		存于redis
		for (InvoiceHead invoiceHead : group) {
			redisService.lpush(business.getOrgId()+"-"+business.getOrgMachine()+"-"+RedisKey.Invoice_Invalid, invoiceHead);
		}  
	}

	
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public void updateSplitLines(InvoiceSplitLine splitLine) { 
		InvoiceSplitLineExample example =new InvoiceSplitLineExample();
		Criteria criteria = example.createCriteria();
		criteria.andDocNumEqualTo(splitLine.getDocNum());
		criteria.andGroupNumEqualTo(splitLine.getGroupNum());  
		criteria.andDocLineEqualTo(splitLine.getDocLine());
		invoiceSplitLineMapper.updateByExampleSelective(splitLine, example);
		if(!"dev".equals(dev_or_prod)) {
			 sapService.feedBackBill(splitLine.getDocNum());
		} 
	}

	  
    
	@Override
	public void updateSplitLinesInvoicing(String docNum, String groupNum) {
		InvoiceSplitLine splitLine = new InvoiceSplitLine(); 
		splitLine.setGoldtaxCode("1");
		invoiceSplitLineMapper.updateByPrimaryKeySelective(splitLine);
		InvoiceSplitLineExample example=new InvoiceSplitLineExample();
		Criteria criteria = example.createCriteria();
		criteria.andDocNumEqualTo(docNum);
		criteria.andGroupNumEqualTo(groupNum);
		invoiceSplitLineMapper.updateByExampleSelective(splitLine, example);
	}

//	
//	
//	public static void main(String[] args) {
//		String originalNum = "111111111 34221927-28";
//		String newNum ="04221928";
//		String splitNum="";
//		if(originalNum==null||originalNum.length()<=3) {
//			return;
//		}
//		String substring = originalNum.substring(originalNum.length()-3);
//		boolean contains = substring.contains("-");
//		if(contains) {
//			String currentNum = originalNum.substring(originalNum.length()-11, originalNum.length()-5) + originalNum.substring(originalNum.length()-2);
//			Integer a = Integer.valueOf(currentNum)+1;
//			if(a.equals(Integer.valueOf(newNum))) {//说明是相邻的号码
//				splitNum=originalNum.substring(0,originalNum.length()-2)+newNum.substring(newNum.length()-2);
//				
//			}else {
//				splitNum=originalNum+" "+newNum; 
//			}
//			
//		}else {
//			String currentNum = originalNum.substring(originalNum.length()-8) ;
//			Integer a = Integer.valueOf(currentNum)+1;
//			if(a.equals(Integer.valueOf(newNum))) {//说明是相邻的号码
//				splitNum=originalNum.substring(0,originalNum.length()-2)+"-"+newNum.substring(newNum.length()-2);
//			}else {
//				splitNum=originalNum+" "+newNum; 
//			}
//		}
//		 
//		System.out.println(splitNum);
//	}
	/**
	 * 更新发票数据
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override 
	public   void  updateInvoiceData(InvoiceHead invoiced) {
	 
		invoiceHeadMapper.updateByPrimaryKeySelective(invoiced);
		List<InvoiceSplitLine> splitLines = invoiced.getInvoiceSplitLines();
		for (InvoiceSplitLine invoiceSplitLine : splitLines) {
			this.updateSplitLines(invoiceSplitLine);
		}
		if(!"dev".equals(dev_or_prod)) {
			 sapService.feedBackBill(invoiced.getDocNum());
		} 
	}




 
	@Override
	public InvoiceHead getInvoiceHead(String docNum) {
		// TODO Auto-generated method stub
		return invoiceHeadMapper.selectByPrimaryKey(docNum);
	}
	
	




	@Override
	public List<InvoiceHead> getInvalidingInvoice(BusinessVo business) {
		List<InvoiceHead> invoiceHeads = invoiceHeadMapper.getInvalidingInvoice(business.getOrgId(), business.getOrgMachine(), business.getInvoiceType());
//		发票分组
		List<InvoiceHead> heads =this.group(invoiceHeads);
		return  heads;
	}





	@Override
	public void updateSplitLinesInvaling(String docNum, String groupNum) {
		InvoiceSplitLine splitLine = new InvoiceSplitLine(); 
		splitLine.setCancelGdate("1");
		invoiceSplitLineMapper.updateByPrimaryKeySelective(splitLine);
		
		InvoiceSplitLineExample example=new InvoiceSplitLineExample();
		Criteria criteria = example.createCriteria();
		criteria.andDocNumEqualTo(docNum);
		criteria.andGroupNumEqualTo(groupNum);
		invoiceSplitLineMapper.updateByExampleSelective(splitLine, example);
	}





	@Override
	public void updateSplitLinesInvalidRecoverNomal(InvoiceHead invoiceHead) {
		InvoiceSplitLineExample example=new InvoiceSplitLineExample();
		Criteria criteria = example.createCriteria();
		criteria.andDocNumEqualTo(invoiceHead.getDocNum());
		criteria.andGroupNumEqualTo(invoiceHead.getInvoiceSplitLines().get(0).getGroupNum()); 
		InvoiceSplitLine record = new InvoiceSplitLine();
		record.setCancelGdate("");
		invoiceSplitLineMapper.updateByExampleSelective(record, example);
	}




	@Override
	public BusinessVo getDeviceInfo(BusinessVo businessVo) {
		return thirdInvoiceService.getDeviceInfo(businessVo); 
	}



    
	/**
	 * 统计已经开的发票数量
	 */
	@Override
	public int countInvoiced(String docNum) { 
		List<InvoiceSplitLine>  list =invoiceSplitLineMapper.countInvoiced(docNum,null);
		if(list==null) {
			return 0;
		}else {
			return list.size();
		}
	}




	@Override
	public Map<String, Object> countWxAndSeThisMouth(String orgId) {
		Date date = new Date();
		SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMM");
		SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM");
		String mouth1 = sf1.format(date)+"%";
		String mouth2 = sf2.format(date)+"%";
		List<Map<String, Object>> list =  invoiceSplitLineMapper.countWxAndSeThisMouth(orgId,mouth1,mouth2);
		 
//				List<Map<String, Object>> list = invoiceSplitLineMapper.countWxAndSeThisMouth(orgId,"202012%","2020-12%");
	    if(list==null||list.size()<=0) {
	    	return null ;
	    }else {
	    	return list.get(0);
	    }
		
	}




 





	 

	
	
	
	
	
	

}
