/**
 * 
 */
package top.anets.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSONObject;
import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

import lombok.extern.slf4j.Slf4j;
import top.anets.config.JCoDestinationPool;
import top.anets.entity.InvoiceHead;
import top.anets.entity.InvoiceSplitLine;
import top.anets.entity.InvoiceSplitLineExample;
import top.anets.entity.InvoiceSplitLineExample.Criteria;
import top.anets.entity.InvoiceSplitLineKey;
import top.anets.exception.ServiceException;
import top.anets.mapper.InvoiceHeadMapper;
import top.anets.mapper.InvoiceSplitLineMapper;
import top.anets.redis.RedisService;
import top.anets.service.SapService;
import top.anets.utils.Result;

/**
 * @author Administrator
 *
 */
@Service
@Slf4j
public class SapServiceImpl implements SapService{
	
	@Autowired
	private InvoiceHeadMapper invoiceHeadMapper;
	
	@Autowired
	private InvoiceSplitLineMapper invoiceSplitLineMapper;
	
	@Autowired
	private JCoDestinationPool pool;
    
	@Transactional
	@Override
	public Object revoke(String orgId, String docStatus, String IV_N) {
		log.info("从SAP获取信息开始》》》》》》》》》组织:{}，单据状态:{}，获取条数:{}", new Object[] { orgId, docStatus, IV_N });
		JCoDestination destination = this.pool.getJCoDestination();
		JCoFunction function2 = null;
		try {
			
			if(destination==null||destination.getRepository()==null) {
				log.info("未从sap获取到函数对象");
				return null;
			}
			function2 = destination.getRepository().getFunction("ZFI_JSI01");                      //sap专用字段
		} catch (JCoException e) {
			e.printStackTrace();
			log.info("获取远程函数出错！");
			log.info(e.getMessage());
		} 
		if (function2 == null)
			throw new RuntimeException("ZFI_JSI01 not found in SAP. | 函数未从sap中找到"); 
		function2.getImportParameterList().setValue("IV_STATUS", docStatus);   //单据状态，1（准备开票的状态）
		function2.getImportParameterList().setValue("IV_N", IV_N);    //取得条数，如果是1的话代表每次取1条单据所对应的数据
		function2.getImportParameterList().setValue("IV_ID", orgId);   //公司组织号


		try{
			//    执行函数，从ERP数据库把符合参数条件的一条单据相关数据 抓过来
			log.info("执行函数");
			function2.execute(destination);
			log.info("函数执行完毕");
		} catch (Exception e) {
			log.info("从SAP获取信息异常:" + e.getMessage());
			System.out.println(e.toString());
		} 

		//  从远程参数列表中获取到待开票 参数：EX_TYPE，EX_MESSAGE  （返回的消息类型 / 内容）
		String EX_TYPE2 = function2.getExportParameterList().getString("EX_TYPE");  //EX_Type是消息类型，E代表失败 ，S代表成功
		log.info("待开票EX_TYPE " + EX_TYPE2 + ":\n");
		String EX_MESSAGE2 = function2.getExportParameterList().getString("EX_MESSAGE");//消息内容
		log.info("待开票EX_MESSAGE " + EX_MESSAGE2 + ":\n");


		//  从这个函数  从 sap中  ---获取 表 IT_TABLE_HE  发票头表   ，如果 从sap中 这条数据 没有取到
		JCoTable returnStructure2 = function2.getTableParameterList().getTable("IT_TABLE_HE");
		if (returnStructure2.getNumRows() == 0)  return null; 


		List<InvoiceHead> invoiceHeads = new ArrayList<>();
		for (int i = 0; i < returnStructure2.getNumRows(); i++) {
			returnStructure2.setRow(i);
			//    从sap中取到发票头信息封装到invoiceHead中
			InvoiceHead invoiceHead=this.getInvoiceHeadFromSap(returnStructure2);
			//    测试时的逻辑
//			if (StringUtils.isNotEmpty(this.dev_or_prod) && this.dev_or_prod.equals("dev")) {
				//        if ("4".equals(invoiceHead.getInvoiceType())) {
				//          invoiceHead.setOrgName("升级版测试用户4011");
				//          invoiceHead.setOrgTaxcode("500102010004011");
				//        }
				//        else {
				//          invoiceHead.setOrgName("升级版测试用户4011");
				//          invoiceHead.setOrgTaxcode("500102010004011");
				//        }
//			}
			invoiceHeads.add(invoiceHead);
		} 
		
		List<InvoiceSplitLine> splitLines = new ArrayList<>();
		JCoTable returnStructure3 = function2.getTableParameterList().getTable("IT_TABLE_GR");
		for (int j = 0; j < returnStructure3.getNumRows(); j++) {
			returnStructure3.setRow(j);
			//    假如我设置的是取一个单据，那么取IT_TABLE_HE会从中取一条单据A，
			//    而取IT_TABLE_GR是不是只会取到该单据A下的行项目，而取不到其他单据号的行项目，所以这里放心，不会取到不想关单据号的行项目
			InvoiceSplitLine invoiceSplitLine=this.getInvoiceSplitLineFromSap(returnStructure3);
			//    测试用
//			if (StringUtils.isNotEmpty(this.dev_or_prod) && this.dev_or_prod.equals("dev") && StringUtils.isEmpty(invoiceSplitLine.getTaxCatecode())){
//				invoiceSplitLine.setTaxCatecode("1070223010000000000"); 
//			}
			splitLines.add(invoiceSplitLine);
		}

		if (invoiceHeads != null && invoiceHeads.size() > 0){
			if (docStatus.equals("3")) {
				log.info("状态3：取到了sap中准备作废的数据，在数据库中标记作废》》》》");
				this.markInvalid(invoiceHeads.get(0),splitLines);

			} else if ("2".equals(((InvoiceHead)invoiceHeads.get(0)).getDocStatus())) {
				log.info("状态2：对sap中单据状态为2，但是组状态为1（部分未完成开票的）进行处理》》》》");
				this.needContinueOpenInvoice(splitLines);
			} else {
				log.info("状态1或者其他：准备开票的状态，需要存储到数据库等待排队等待开票》》》》");
				this.incomingQueueWaitForOpenInvoice(invoiceHeads.get(0),splitLines);
			}
		}
		return null;
	}
	
	
	
	
	
	/**
	 * ==============================================================================================================================
	 * ===========================================================辅助方法===================================================================
	 *@param 从sap取出发票头信息封装到InvoiceHead中 
	 *@return 
	 */
	private InvoiceHead getInvoiceHeadFromSap(JCoTable returnStructure2) {
		System.out.println("MANDT:" + returnStructure2.getString("MANDT"));
		log.info("获取到SAP待开票头信息，单据号：" + returnStructure2.getString("DOC_NUM"));
		InvoiceHead invoiceHead = new InvoiceHead();
		invoiceHead.setMandt(returnStructure2.getString("MANDT"));
		invoiceHead.setDocNum(returnStructure2.getString("DOC_NUM"));
		invoiceHead.setDocDate(returnStructure2.getString("DOC_DATE"));
		invoiceHead.setDocStatus(returnStructure2.getString("DOC_STATUS"));
		invoiceHead.setOrgId(returnStructure2.getString("ORG_ID"));
		invoiceHead.setOrgName(returnStructure2.getString("ORG_NAME"));
		invoiceHead.setOrgTaxcode(returnStructure2.getString("ORG_TAXCODE"));
		invoiceHead.setOrgMachine(returnStructure2.getString("ORG_MACHINE"));
		invoiceHead.setOrgAddress(returnStructure2.getString("ORG_ADDRESS"));
		invoiceHead.setOrgTelephone(returnStructure2.getString("ORG_TELEPHONE"));
		invoiceHead.setOrgBankname(returnStructure2.getString("ORG_BANKNAME"));
		invoiceHead.setOrgBankaccount(returnStructure2.getString("ORG_BANKACCOUNT"));
		invoiceHead.setOrgTaxexceed(returnStructure2.getBigDecimal("ORG_TAXEXCEED"));
		invoiceHead.setOrgControltax(returnStructure2.getString("ORG_CONTROLTAX"));
		invoiceHead.setCustIdAr(returnStructure2.getString("CUST_ID_AR"));
		invoiceHead.setCustNameAr(returnStructure2.getString("CUST_NAME_AR"));
		invoiceHead.setCustIdBill(returnStructure2.getString("CUST_ID_BILL"));
		invoiceHead.setCustNameBill(returnStructure2.getString("CUST_NAME_BILL"));
		invoiceHead.setCustName(returnStructure2.getString("CUST_NAME"));
		invoiceHead.setCustTaxcode(returnStructure2.getString("CUST_TAXCODE"));
		invoiceHead.setCustAddress(returnStructure2.getString("CUST_ADDRESS"));
		invoiceHead.setCustTelephone(returnStructure2.getString("CUST_TELEPHONE"));
		invoiceHead.setCustBankname(returnStructure2.getString("CUST_BANKNAME"));
		invoiceHead.setCustBankaccount(returnStructure2.getString("CUST_BANKACCOUNT"));
		invoiceHead.setCustEmail(returnStructure2.getString("CUST_EMAIL"));
		invoiceHead.setCustMobile(returnStructure2.getString("CUST_MOBILE"));
		invoiceHead.setInvoiceType(returnStructure2.getString("INVOICE_TYPE"));
		invoiceHead.setInvoiceTypes(returnStructure2.getString("INVOICE_TYPES"));
		invoiceHead.setInvoiceBase(returnStructure2.getString("INVOICE_BASE"));
		invoiceHead.setInvoiceWay(returnStructure2.getString("INVOICE_WAY"));
		invoiceHead.setMergeAmt(returnStructure2.getBigDecimal("MERGE_AMT"));
		invoiceHead.setMergeQty(returnStructure2.getBigDecimal("MERGE_QTY"));
		invoiceHead.setTaxRate(returnStructure2.getString("TAX_RATE"));
		invoiceHead.setDiscountType(returnStructure2.getString("DISCOUNT_TYPE"));
		invoiceHead.setDiscountRate(returnStructure2.getBigDecimal("DISCOUNT_RATE"));
		invoiceHead.setMergeGift(returnStructure2.getString("MERGE_GIFT"));
		invoiceHead.setInvoiceList(returnStructure2.getString("INVOICE_LIST"));
		invoiceHead.setInvoiceRed(returnStructure2.getString("INVOICE_RED"));
		invoiceHead.setMergefType(returnStructure2.getString("MERGEF_TYPE"));
		invoiceHead.setMergesType(returnStructure2.getString("MERGES_TYPE"));
		invoiceHead.setGoldtaxCode(returnStructure2.getString("GOLDTAX_CODE"));
		invoiceHead.setGoldtaxNum(returnStructure2.getString("GOLDTAX_NUM"));
		invoiceHead.setUserId(returnStructure2.getString("USER_ID"));
		invoiceHead.setUserName(returnStructure2.getString("USER_NAME"));
		invoiceHead.setCheckName(returnStructure2.getString("CHECK_NAME"));
		invoiceHead.setPayeeName(returnStructure2.getString("PAYEE_NAME"));
		invoiceHead.setBillDate(returnStructure2.getString("BILL_DATE"));
		invoiceHead.setCancelDate(returnStructure2.getString("CANCEL_DATE"));
		invoiceHead.setBillGdate(returnStructure2.getString("BILL_GDATE"));
		invoiceHead.setCancelGdate(returnStructure2.getString("CANCEL_GDATE"));
		invoiceHead.setInvoiceRedReqm(returnStructure2.getString("INVOICE_RED_REQM"));
		invoiceHead.setInvoiceRedXxbm(returnStructure2.getString("INVOICE_RED_XXBM"));
		invoiceHead.setInvoiceRedFpdm(returnStructure2.getString("INVOICE_RED_FPDM"));
		invoiceHead.setInvoiceRedFphm(returnStructure2.getString("INVOICE_RED_FPHM"));
		invoiceHead.setBillRemark(returnStructure2.getString("BILL_REMARK"));
		invoiceHead.setZamountHswc(returnStructure2.getBigDecimal("ZAMOUNT_HSWC"));
		invoiceHead.setZamountWswc(returnStructure2.getBigDecimal("ZAMOUNT_WSWC"));
		invoiceHead.setZamountSewc(returnStructure2.getBigDecimal("ZAMOUNT_SEWC"));
		invoiceHead.setCreatedBy(returnStructure2.getString("CREATED_BY"));
//		invoiceHead.setCreationDate(returnStructure2.getString("CREATION_DATE"));
		invoiceHead.setLastUpdatedBy(returnStructure2.getString("LAST_UPDATED_BY"));
		invoiceHead.setLastUpdatedDat(returnStructure2.getString("LAST_UPDATED_DAT"));
		invoiceHead.setAttributf1(returnStructure2.getString("ATTRIBUTF1"));
		invoiceHead.setCustProvEx(returnStructure2.getString("CUST_PROV_EX"));
		invoiceHead.setCustCity(returnStructure2.getString("CUST_CITY"));
		invoiceHead.setCustDistrict(returnStructure2.getString("CUST_DISTRICT"));
		invoiceHead.setCustAddrEx(returnStructure2.getString("CUST_ADDR_EX"));
		invoiceHead.setExName(returnStructure2.getString("EX_NAME"));
		invoiceHead.setExTelephone(returnStructure2.getString("EX_TELEPHONE"));
		return invoiceHead;
	}
	/**
	 * 从sap取出发票拆分行信息封装到 InvoiceSplitLine中 
	 *@param returnStructure3
	 *@return 
	 */
	private InvoiceSplitLine getInvoiceSplitLineFromSap(JCoTable returnStructure3) {
		log.info("获取到SAP待开票拆分行信息，单据号：" + returnStructure3.getString("DOC_NUM") + ",分组号：" + returnStructure3.getString("GROUP_NUM") + ",行号：" + returnStructure3.getString("DOC_LINE"));
		InvoiceSplitLine invoiceSplitLine = new InvoiceSplitLine();
		invoiceSplitLine.setMandt(returnStructure3.getString("MANDT"));
		invoiceSplitLine.setDocNum(returnStructure3.getString("DOC_NUM"));
		invoiceSplitLine.setDocLine(returnStructure3.getString("DOC_LINE"));
		invoiceSplitLine.setGroupNum(returnStructure3.getString("GROUP_NUM"));
		invoiceSplitLine.setGroupStatus(returnStructure3.getString("GROUP_STATUS"));
		invoiceSplitLine.setGoldtaxCode(returnStructure3.getString("GOLDTAX_CODE"));
		invoiceSplitLine.setGoldtaxNum(returnStructure3.getString("GOLDTAX_NUM"));
		invoiceSplitLine.setItemName(returnStructure3.getString("ITEM_NAME"));
		invoiceSplitLine.setItemSpec(returnStructure3.getString("ITEM_SPEC"));
		invoiceSplitLine.setUnitName(returnStructure3.getString("UNIT_NAME"));
		invoiceSplitLine.setTaxCatecode(returnStructure3.getString("TAX_CATECODE"));
		invoiceSplitLine.setQuantity(returnStructure3.getBigDecimal("QUANTITY"));
		invoiceSplitLine.setTaxRate(returnStructure3.getBigDecimal("TAX_RATE"));
		invoiceSplitLine.setGiftFlag(returnStructure3.getString("GIFT_FLAG"));
		invoiceSplitLine.setZamountHsj(returnStructure3.getBigDecimal("ZAMOUNT_HSJ"));
		invoiceSplitLine.setZamountWsj(returnStructure3.getBigDecimal("ZAMOUNT_WSJ"));
		invoiceSplitLine.setZamountSej(returnStructure3.getBigDecimal("ZAMOUNT_SEJ"));
		invoiceSplitLine.setZamountHzhs(returnStructure3.getBigDecimal("ZAMOUNT_HZHS"));
		invoiceSplitLine.setZamountHzws(returnStructure3.getBigDecimal("ZAMOUNT_HZWS"));
		invoiceSplitLine.setZamountHzse(returnStructure3.getBigDecimal("ZAMOUNT_HZSE"));
		invoiceSplitLine.setZamountHsy(returnStructure3.getBigDecimal("ZAMOUNT_HSY"));
		invoiceSplitLine.setZamountWsy(returnStructure3.getBigDecimal("ZAMOUNT_WSY"));
		invoiceSplitLine.setZamountSey(returnStructure3.getBigDecimal("ZAMOUNT_SEY"));
		invoiceSplitLine.setZpriceHsj(returnStructure3.getBigDecimal("ZPRICE_HSJ"));
		log.info("===========================单价：{}" + invoiceSplitLine.getZpriceHsj());
		invoiceSplitLine.setZpriceWsj(returnStructure3.getBigDecimal("ZPRICE_WSJ"));
		invoiceSplitLine.setZpriceHsy(returnStructure3.getBigDecimal("ZPRICE_HSY"));
		invoiceSplitLine.setZpriceWsy(returnStructure3.getBigDecimal("ZPRICE_WSY"));
		invoiceSplitLine.setBillDate(returnStructure3.getString("BILL_DATE"));
		invoiceSplitLine.setCancelDate(returnStructure3.getString("CANCEL_DATE"));
		invoiceSplitLine.setBillGdate(returnStructure3.getString("BILL_GDATE"));
		invoiceSplitLine.setCancelGdate(returnStructure3.getString("CANCEL_GDATE"));
		invoiceSplitLine.setGroupCopyfrom(returnStructure3.getString("GROUP_COPYFROM"));
		invoiceSplitLine.setAttributf1(returnStructure3.getString("ATTRIBUTF1"));
		return invoiceSplitLine;
	}
	
	
	/**
	 * 标记作废，对一个单据号 一个组的某一行进行标记作废，决定的一张发票的作废
	 *@param invoiceHead
	 *@param splitLines 
	 */
	private void markInvalid(InvoiceHead invoiceHead, List<InvoiceSplitLine> splitLines) {
		//	更改发票头单据状态为作废状态
//		this.manager.update("invoice_head.invalid", (IPO)invoiceHead);
		invoiceHeadMapper.updateByPrimaryKeySelective(invoiceHead);
		if (splitLines != null && splitLines.size() > 0) {
			List<InvoiceSplitLine> iss = new ArrayList<>();
			InvoiceSplitLine tem = new InvoiceSplitLine();
			tem.setDocNum("sisis");
			tem.setGroupNum("jfjf");
			//     把从sap取得的数据， 当前项和上一项的  单据号一样 ，组号不一样的准备作废状态的放入 集合中 准备作废标记
			//     这是因为 一个单据号 一个组（多个行，用一行代表一组），决定了一张发票，只能作废一张这样的发票，但是一个组有多个行，所以只需要对同一组的某行标记一下就行
			//      对一个单据号 一个组的某一行进行标记作废，决定的一张发票的作废
			for (InvoiceSplitLine is : splitLines) {
				if (tem.getDocNum().equals("sisis") && tem.getGroupNum().equals("jfjf") && is.getGroupStatus().equals("3"))
					iss.add(is); 
				if (tem.getDocNum().equals(is.getDocNum()) && !tem.getGroupNum().equals(is.getGroupNum()) && is.getGroupStatus().equals("3"))
					iss.add(is); 
				tem.setDocNum(is.getDocNum());
				tem.setGroupNum(is.getGroupNum());
			}
			if ((((iss != null) ? 1 : 0) & ((iss.size() > 0) ? 1 : 0)) != 0)
				for (InvoiceSplitLine splitLine : iss)
					invoiceSplitLineMapper.updateByPrimaryKeySelective(splitLine);
//					this.manager.update("invoice_split_line.invalidOne", (IPO)splitLine);
		}
	}

	/**
	 *入库排队等待调用开票
	 *@param invoiceHead
	 *@param splitLines 
	 */
	 @Autowired
	 private RedisService  redisService;
	
	@Transactional(propagation = Propagation.REQUIRED)
	private void incomingQueueWaitForOpenInvoice(InvoiceHead invoiceHead, List<InvoiceSplitLine> splitLines) { 
		try {
			log.info("新增开始》》》》》");
//			this.manager.insert("invoice_head.create", (IPO)invoiceHead);
		    invoiceHeadMapper.insert(invoiceHead); 
			int i=0;
			if (splitLines != null && splitLines.size() > 0)
				for (InvoiceSplitLine splitLine : splitLines) {
//					this.manager.insert("invoice_split_line.create", (IPO)splitLine);
					invoiceSplitLineMapper.insert(splitLine);
					i++;
				} 
			log.info("新增结束》》》》》");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("从sap获取数据保存到db失败！原因：{}", e.getMessage());
			
			SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
			Date date = new Date();
			String  day = dayFormat.format(date);
			String  time = timeFormat.format(date);
			
			redisService.lpush(invoiceHead.getOrgId()+"-"+"error", "["+day+" "+time+"]"+"单据"+invoiceHead.getDocNum()+"取数异常"+"-"+e.getMessage()); 
			redisService.expire(invoiceHead.getOrgId()+"-"+"error",60*60*24*30);
			log.error("执行回传解锁");
			try {
//				这里要处理一下
				invoiceHead.setInvoiceSplitLines(splitLines);
				this.feedbackInvoiceHeadAndSplitlinesForUnlock(invoiceHead);
				//手动回滚
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
				
			} catch (JCoException e1) {
				e1.printStackTrace();
				log.error("执行回传解锁异常"); 
				throw new ServiceException("执行回传解锁异常"); 
			}
			
			
			
//			try {
//				this.manager.delete("invoice_head.del", (IPO)invoiceHead);
//			} catch (Exception e2) {
////				JSONObject jSONObject = new JSONObject();
////				jSONObject.put("msg", "数据发生错误回滚异常，请重试");
////				jSONObject.put("success", Boolean.valueOf(false));
////				throw new BusinessException(ResultCode.INTERFACE_OUTTER_INVOKE_ERROR, jSONObject);
//				throw new ServiceException("数据发生错误回滚异常，请重试");
//			}
			
//			JSONObject jSONObject = new JSONObject();
//			jSONObject.put("msg", "提交的数据有异常，异常定位DOC_NUM："+splitLines.get(0).getDocNum());
//			jSONObject.put("success", Boolean.valueOf(false));
//			throw new BusinessException(ResultCode.INTERFACE_OUTTER_INVOKE_ERROR, jSONObject);
			throw new ServiceException("提交的数据有异常，异常定位DOC_NUM："+splitLines.get(0).getDocNum() );
		} 
	}



	/**
	 *针对传入单据状态为2，从sap中取得单据状态为2的数据中，把组状态为1的行项目插入到队列中，准备继续开票
	 *@param splitLines 
	 */
	private void needContinueOpenInvoice(List<InvoiceSplitLine> splitLines) {
		if (splitLines != null && splitLines.size() > 0) {
			List<InvoiceSplitLine> iss = new ArrayList<>();
			for (InvoiceSplitLine is : splitLines) {
				if (is.getGroupStatus().equals("1"))
					iss.add(is); 
			} 
			if ((((iss != null) ? 1 : 0) & ((iss.size() > 0) ? 1 : 0)) != 0)
				for (InvoiceSplitLine splitLine : iss)
//					这个有问题
					invoiceSplitLineMapper.insert(splitLine);
//					this.manager.insert("invoice_split_line.create", (IPO)splitLine);
		} 
	}


	/**
	 *回传发票头和发票拆分行数据给sap端,需要一个回传函数
	 *@param function4
	 *@param invoiceHead 
	 * @throws JCoException 
	 */
	@Override
	 public JCoFunction feedbackInvoiceHeadAndSplitlinesForUnlock(InvoiceHead invoiceHead) throws JCoException {
		//  如果这个单据得所有组都还在准备开票，那么可以撤销，更改单据状态
		JCoDestination destination = this.pool.getJCoDestination();
		if (destination == null||destination.getRepository()==null)
			throw new RuntimeException("sap can not connect");
		JCoFunction function4 = destination.getRepository().getFunction("ZFI_JSI02");
		if (function4 == null)
			throw new RuntimeException("ZFI_JSI02 not found in SAP.");
		JCoTable options = function4.getTableParameterList().getTable("IT_TABLE_HE");
		options.appendRow();
		options.setValue("MANDT", invoiceHead.getMandt());
		options.setValue("DOC_NUM", invoiceHead.getDocNum());
		options.setValue("DOC_DATE", invoiceHead.getDocDate());
		options.setValue("DOC_STATUS", invoiceHead.getDocStatus());
		options.setValue("ORG_ID", invoiceHead.getOrgId());
		options.setValue("ORG_NAME", invoiceHead.getOrgName());
		options.setValue("ORG_TAXCODE", invoiceHead.getOrgTaxcode());
		options.setValue("ORG_MACHINE", invoiceHead.getOrgMachine());
		options.setValue("ORG_ADDRESS", invoiceHead.getOrgAddress());
		options.setValue("ORG_TELEPHONE", invoiceHead.getOrgTelephone());
		options.setValue("ORG_BANKNAME", invoiceHead.getOrgBankname());
		options.setValue("ORG_BANKACCOUNT", invoiceHead.getOrgBankaccount());
		if (invoiceHead.getOrgTaxexceed() != null)
			options.setValue("ORG_TAXEXCEED", invoiceHead.getOrgTaxexceed()); 
		options.setValue("ORG_CONTROLTAX", invoiceHead.getOrgControltax());
		options.setValue("CUST_ID_AR", invoiceHead.getCustIdAr());
		options.setValue("CUST_NAME_AR", invoiceHead.getCustNameAr());
		options.setValue("CUST_ID_BILL", invoiceHead.getCustIdBill());
		options.setValue("CUST_NAME_BILL", invoiceHead.getCustNameBill());
		options.setValue("CUST_NAME", invoiceHead.getCustName());
		options.setValue("CUST_TAXCODE", invoiceHead.getCustTaxcode());
		options.setValue("CUST_ADDRESS", invoiceHead.getCustAddress());
		options.setValue("CUST_TELEPHONE", invoiceHead.getCustTelephone());
		options.setValue("CUST_BANKNAME", invoiceHead.getCustBankname());
		options.setValue("CUST_BANKACCOUNT", invoiceHead.getCustBankaccount());
		options.setValue("CUST_EMAIL", invoiceHead.getCustEmail());
		options.setValue("CUST_MOBILE", invoiceHead.getCustMobile());
		options.setValue("INVOICE_TYPE", invoiceHead.getInvoiceType());
		options.setValue("INVOICE_TYPES", invoiceHead.getInvoiceTypes());
		options.setValue("INVOICE_BASE", invoiceHead.getInvoiceBase());
		options.setValue("INVOICE_WAY", invoiceHead.getInvoiceWay());
		if (invoiceHead.getMergeAmt() != null)
			options.setValue("MERGE_AMT", invoiceHead.getMergeAmt()); 
		if (invoiceHead.getMergeQty() != null)
			options.setValue("MERGE_QTY", invoiceHead.getMergeQty()); 
		options.setValue("TAX_RATE", invoiceHead.getTaxRate());
		options.setValue("DISCOUNT_TYPE", invoiceHead.getDiscountType());
		if (invoiceHead.getDiscountRate() != null)
			options.setValue("DISCOUNT_RATE", invoiceHead.getDiscountRate()); 
		options.setValue("MERGE_GIFT", invoiceHead.getMergeGift());
		options.setValue("INVOICE_LIST", invoiceHead.getInvoiceList());
		options.setValue("INVOICE_RED", invoiceHead.getInvoiceRed());
		options.setValue("MERGEF_TYPE", invoiceHead.getMergefType());
		options.setValue("MERGES_TYPE", invoiceHead.getMergesType());
		options.setValue("GOLDTAX_CODE", invoiceHead.getGoldtaxCode());
		options.setValue("GOLDTAX_NUM", invoiceHead.getGoldtaxNum());
		options.setValue("USER_ID", invoiceHead.getUserId());
		options.setValue("USER_NAME", invoiceHead.getUserName());
		options.setValue("CHECK_NAME", invoiceHead.getCheckName());
		options.setValue("PAYEE_NAME", invoiceHead.getPayeeName());
		options.setValue("BILL_DATE", invoiceHead.getBillDate());
		options.setValue("CANCEL_DATE", invoiceHead.getCancelDate());
		options.setValue("BILL_GDATE", invoiceHead.getBillGdate());
		options.setValue("CANCEL_GDATE", invoiceHead.getCancelGdate());
		options.setValue("INVOICE_RED_REQM", invoiceHead.getInvoiceRedReqm());
		options.setValue("INVOICE_RED_XXBM", invoiceHead.getInvoiceRedXxbm());
		options.setValue("INVOICE_RED_FPDM", invoiceHead.getInvoiceRedFpdm());
		options.setValue("INVOICE_RED_FPHM", invoiceHead.getInvoiceRedFphm());
		options.setValue("BILL_REMARK", invoiceHead.getBillRemark());
		if (invoiceHead.getZamountHswc() != null)
			options.setValue("ZAMOUNT_HSWC", invoiceHead.getZamountHswc()); 
		if (invoiceHead.getZamountWswc() != null)
			options.setValue("ZAMOUNT_WSWC", invoiceHead.getZamountWswc()); 
		if (invoiceHead.getZamountSewc() != null)
			options.setValue("ZAMOUNT_SEWC", invoiceHead.getZamountSewc()); 
		options.setValue("CREATED_BY", invoiceHead.getCreatedBy());
		options.setValue("CREATION_DATE", invoiceHead.getCreationDate());
		options.setValue("LAST_UPDATED_BY", invoiceHead.getLastUpdatedBy());
		options.setValue("LAST_UPDATED_DAT", invoiceHead.getLastUpdatedDat());
		options.setValue("ATTRIBUTF1", invoiceHead.getAttributf1());
		JCoTable options1 = function4.getTableParameterList().getTable("IT_TABLE_GR");
		for (InvoiceSplitLine invoiceSplitLine : invoiceHead.getInvoiceSplitLines()) {
			options1.appendRow();
			if ("1".equals(invoiceSplitLine.getGoldtaxCode()))
				invoiceSplitLine.setGoldtaxCode(""); 
			options1.setValue("MANDT", invoiceSplitLine.getMandt());
			options1.setValue("DOC_NUM", invoiceSplitLine.getDocNum());
			options1.setValue("DOC_LINE", invoiceSplitLine.getDocLine());
			options1.setValue("GROUP_NUM", invoiceSplitLine.getGroupNum());
			options1.setValue("GROUP_STATUS", invoiceSplitLine.getGroupStatus());
			options1.setValue("GOLDTAX_CODE", invoiceSplitLine.getGoldtaxCode());
			options1.setValue("GOLDTAX_NUM", invoiceSplitLine.getGoldtaxNum());
			options1.setValue("ITEM_NAME", invoiceSplitLine.getItemName());
			options1.setValue("ITEM_SPEC", invoiceSplitLine.getItemSpec());
			options1.setValue("UNIT_NAME", invoiceSplitLine.getUnitName());
			options1.setValue("TAX_CATECODE", invoiceSplitLine.getTaxCatecode());
			if (invoiceSplitLine.getQuantity() != null)
				options1.setValue("QUANTITY", invoiceSplitLine.getQuantity()); 
			if (invoiceSplitLine.getTaxRate() != null)
				options1.setValue("TAX_RATE", invoiceSplitLine.getTaxRate()); 
			options1.setValue("GIFT_FLAG", invoiceSplitLine.getGiftFlag());
			if (invoiceSplitLine.getZamountHsj() != null)
				options1.setValue("ZAMOUNT_HSJ", invoiceSplitLine.getZamountHsj()); 
			if (invoiceSplitLine.getZamountWsj() != null)
				options1.setValue("ZAMOUNT_WSJ", invoiceSplitLine.getZamountWsj()); 
			if (invoiceSplitLine.getZamountSej() != null)
				options1.setValue("ZAMOUNT_SEJ", invoiceSplitLine.getZamountSej()); 
			if (invoiceSplitLine.getZamountHzhs() != null)
				options1.setValue("ZAMOUNT_HZHS", invoiceSplitLine.getZamountHzhs()); 
			if (invoiceSplitLine.getZamountHzws() != null)
				options1.setValue("ZAMOUNT_HZWS", invoiceSplitLine.getZamountHzws()); 
			if (invoiceSplitLine.getZamountHzse() != null)
				options1.setValue("ZAMOUNT_HZSE", invoiceSplitLine.getZamountHzse()); 
			if (invoiceSplitLine.getZamountHsy() != null)
				options1.setValue("ZAMOUNT_HSY", invoiceSplitLine.getZamountHsy()); 
			if (invoiceSplitLine.getZamountWsy() != null)
				options1.setValue("ZAMOUNT_WSY", invoiceSplitLine.getZamountWsy()); 
			if (invoiceSplitLine.getZamountSey() != null)
				options1.setValue("ZAMOUNT_SEY", invoiceSplitLine.getZamountSey()); 
			if (invoiceSplitLine.getZpriceHsj() != null)
				options1.setValue("ZPRICE_HSJ", invoiceSplitLine.getZpriceHsj()); 
			if (invoiceSplitLine.getZpriceWsj() != null)
				options1.setValue("ZPRICE_WSJ", invoiceSplitLine.getZpriceWsj()); 
			if (invoiceSplitLine.getZpriceHsy() != null)
				options1.setValue("ZPRICE_HSY", invoiceSplitLine.getZpriceHsy()); 
			if (invoiceSplitLine.getZpriceWsy() != null)
				options1.setValue("ZPRICE_WSY", invoiceSplitLine.getZpriceWsy()); 
			options1.setValue("BILL_DATE", invoiceSplitLine.getBillDate());
			options1.setValue("CANCEL_DATE", invoiceSplitLine.getCancelDate());
			options1.setValue("BILL_GDATE", invoiceSplitLine.getBillGdate());
			options1.setValue("CANCEL_GDATE", invoiceSplitLine.getCancelGdate());
			options1.setValue("GROUP_COPYFROM", invoiceSplitLine.getGroupCopyfrom());
			options1.setValue("ATTRIBUTF1", invoiceSplitLine.getAttributf1());
		} 


		try {
			function4.execute(destination);
		} catch (AbapException e) {
			log.info(e.toString());
//			JSONObject jSONObject = new JSONObject();
//			jSONObject.put("msg", "错误原因：执行回传解锁异常");
//			jSONObject.put("success", Boolean.valueOf(false));
//			throw new BusinessException(ResultCode.INTERFACE_OUTTER_INVOKE_ERROR, jSONObject);
			throw new ServiceException("错误原因：执行回传解锁异常:"+e.toString() );
		} 
		return function4; 
	}





	@Override
	@Async
	public Result feedBackInvoice(InvoiceHead invoiceHead) {
		try {
			JCoFunction function4 = this.feedbackInvoiceHeadAndSplitlinesForUnlock(invoiceHead);
			String EX_TYPE = function4.getExportParameterList().getString("EX_TYPE");
            System.out.println("发票回传EX_TYPE "  + EX_TYPE + ":\n");
            String EX_MESSAGE = function4.getExportParameterList().getString("EX_MESSAGE");
            log.info(invoiceHead.getDocNum()+"发票回传EX_MESSAGE "+ EX_MESSAGE + ":\n");
            if(EX_TYPE.equals("S")){ //代表回传成功
                //更新发票同步状态
                invoiceHead.setIssync("2");
                invoiceHeadMapper.updateByPrimaryKeySelective(invoiceHead);
                return Result.Success("回传成功:"+invoiceHead.getDocNum());
            }else {
            	return Result.Error("回传失败:"+invoiceHead.getDocNum()+"-"+EX_TYPE+"-"+EX_MESSAGE);
            }
		} catch (JCoException e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
			return Result.Error(e.getMessage());
		} 
	}
	
	/**
	 * 回传不代表解锁 
	 */
	@Override
	@Async
	public Result feedBackBill(String docNum) { 
		InvoiceHead invoiceHead = invoiceHeadMapper.selectByPrimaryKey(docNum); 
		InvoiceSplitLineExample example = new InvoiceSplitLineExample();
		Criteria criteria = example.createCriteria();
		criteria.andDocNumEqualTo(docNum);
		List<InvoiceSplitLine> splitLines = invoiceSplitLineMapper.selectByExample(example );
		invoiceHead.setInvoiceSplitLines(splitLines);
	    return this.feedBackInvoice(invoiceHead);
	}




}
