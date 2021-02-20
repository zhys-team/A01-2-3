/**
 * 
 */
package top.anets.service.impl;

import static org.hamcrest.CoreMatchers.nullValue;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;
import top.anets.entity.Business;
import top.anets.entity.InvoiceHead;
import top.anets.entity.InvoiceSplitLine;
import top.anets.exception.ServiceException;
import top.anets.service.ThirdInvoiceService;
import top.anets.utils.CommonUtils;
import top.anets.utils.GuoXin;
import top.anets.utils.Result;
import top.anets.utils.guoXin.RequestUtils;
import top.anets.vo.BusinessVo;
import top.anets.vo.DeviceInfo;
import top.anets.vo.InvoiceResult;
import top.anets.vo.Logs;

/**
 * @author Administrator
 *
 */
@Slf4j
@Service("guoXinInvoiceService")
public class GuoXinInvoiceServiceImpl  implements ThirdInvoiceService{
	
  @Value("${guoXin.requestUrl}")
  private String requestUrl; 
  
  @Value("${httpclient.dev_or_prod}")
  
  
  
  private String dev_or_prod; 
	@Override
	public Result init() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InvoiceResult invoice(InvoiceHead invoiceHead, BusinessVo business) {
		// TODO Auto-generated method stub
		return null;
	}
     
	
  
	@Override
	public InvoiceResult InvoiceElectron(InvoiceHead invoiceHead, BusinessVo business) {  
		//封装数据
	    HashMap<String, String> head = new HashMap<>(); 
	    HashMap<String, String> body = new HashMap<>(); 
	    String fpqqlsh = invoiceHead.getInvoiceSplitLines().get(0).getDocNum()+invoiceHead.getInvoiceSplitLines().get(0).getGroupNum();
	    head.put("FPQQLSH",fpqqlsh );
	    if(invoiceHead.getInvoiceRed()==null) {  
    	      return InvoiceResult.Error("发票开具检验不通过，没有开票类型（正数票/负数票）！");
        }else if(invoiceHead.getInvoiceRed().equals("Y")||StringUtils.isBlank(invoiceHead.getInvoiceRed())) {//不是红字发票
    	       head.put("KPLX", "0");
        }else if(invoiceHead.getInvoiceRed().equals("X")) {//是红字发票
        	 head.put("KPLX", "1");
        	 head.put("YFP_DM", invoiceHead.getInvoiceRedFpdm());
	    	 head.put("YFP_HM", invoiceHead.getInvoiceRedFphm());
        }else {
          	return InvoiceResult.Error("发票开具检验不通过，开票类型"+invoiceHead.getInvoiceRed()+"不正确！"); 
        }
	    
	    head.put("BMB_BBH", "");
	    head.put("ZSFS", "0");
	    head.put("XSF_NSRSBH", invoiceHead.getOrgTaxcode());
	    head.put("XSF_MC", invoiceHead.getOrgName());
	    head.put("XSF_DZDH",invoiceHead.getOrgAddress()+invoiceHead.getOrgTelephone());
	    head.put("XSF_YHZH",invoiceHead.getOrgBankname());
	    head.put("GMF_NSRSBH", invoiceHead.getCustTaxcode());
	    head.put("GMF_MC", invoiceHead.getCustName());
	    head.put("GMF_DZDH", invoiceHead.getCustAddress()+invoiceHead.getCustTelephone());
	    head.put("GMF_YHZH", invoiceHead.getCustBankname());
	    head.put("GMF_SJH", invoiceHead.getCustMobile());
	    head.put("GMF_DZYX",invoiceHead.getCustEmail());
	    head.put("KPR", invoiceHead.getUserName());
	    head.put("SKR", invoiceHead.getPayeeName());
	    head.put("FHR", invoiceHead.getCheckName());
	    
	    
	    BigDecimal originalSe=new BigDecimal("0");
	    BigDecimal originalHsje=new BigDecimal("0");
	    BigDecimal originalWsje=new BigDecimal("0");
        for (InvoiceSplitLine invoiceSplitLines : invoiceHead.getInvoiceSplitLines()) { 
        	  originalSe=originalSe.add(invoiceSplitLines.getZamountSej());
        	  originalHsje=originalHsje.add(invoiceSplitLines.getZamountHsj());
        	  originalWsje=originalWsje.add(invoiceSplitLines.getZamountWsj());
		} 
	     
	    head.put("JSHJ",  originalHsje.toString());
	    head.put("HJJE", originalWsje.toString());
	    head.put("HJSE", originalSe.toString());
	     
	    List<Map<String, String>> list = new ArrayList<>();
	    String group=invoiceHead.getInvoiceSplitLines().get(0).getGroupNum();
	    for (InvoiceSplitLine invoiceSplitLine : invoiceHead.getInvoiceSplitLines()) { 
			if(StringUtils.isNoneBlank(group)&&!group.equals(invoiceSplitLine.getGroupNum())) { 
				return InvoiceResult.Error("发票开具检验不通过，发票体出现非同组的情况！");  
			}
			HashMap<String, String> line = new HashMap<>();
//			金额计算
			if(invoiceSplitLine.getGiftFlag()!=null&&invoiceSplitLine.getGiftFlag().equals("1")) {//折扣行
				line.put("FPHXZ", "1"); 
			}else if(invoiceSplitLine.getGiftFlag()!=null&&invoiceSplitLine.getGiftFlag().equals("-1")){//被折扣行
				line.put("FPHXZ", "2");
			}else {//非折扣行
				line.put("FPHXZ", "0");
			}
			
			line.put("XMJE", ""+invoiceSplitLine.getZamountWsj());
			line.put("SE", ""+invoiceSplitLine.getZamountSej());
			line.put("XMDJ", ""+invoiceSplitLine.getZpriceWsj()); 
			BigDecimal quantity = invoiceSplitLine.getQuantity().round(new MathContext(12, RoundingMode.HALF_UP));
			line.put("XMSL",""+quantity);
			line.put("XMMC", invoiceSplitLine.getItemName());
			line.put("GGXH", invoiceSplitLine.getItemSpec());
			line.put("DW", invoiceSplitLine.getUnitName());
			line.put("SPBM", invoiceSplitLine.getTaxCatecode());
			if(invoiceHead.getInvoiceType().equals("5")) {//外销免税
				line.put("LSLBS","1");
			}else 
			{
				line.put("LSLBS","");
			}
			line.put("SL", ""+invoiceSplitLine.getTaxRate());  
			
			list.add(line);
	    }
	    String xml_FPKJ_Electron="";
	    try {
			xml_FPKJ_Electron = GuoXin.toXml_FPKJ_Electron(head, list, business);
		} catch (Exception e) {
			e.printStackTrace(); 
			return InvoiceResult.Error("发票开具时,xml转换错误"+e.getMessage());
		}
	    
	    
	    
	    Map map = null;
	    
	    log.info("请求报文:"+xml_FPKJ_Electron);
	    String result =null;
	    try {
	    	result = RequestUtils.getHttpConnectResult(xml_FPKJ_Electron,requestUrl+"invoice");
		} catch (Exception e) {
			e.printStackTrace();
			return InvoiceResult.Error("请求失败,"+e.getMessage());
		}
	    
	    
		try {  
			
		    
		    log.info("返回结果:"+result);
			map = CommonUtils.xml2map(result, false); 
			Map<String, String> returnStateInfo =  (Map<String, String>) map.get("returnStateInfo");
			Map<String, String> Data =  (Map<String, String>) map.get("Data");
			String returnCode = returnStateInfo.get("returnCode");
			String returnMessage = returnStateInfo.get("returnMessage");
			if(returnCode.equals("0000")) {
				//成功，此处发票可能已经开具过，也会返回成功的
//				获取内容
				String content = Data.get("content"); 
				log.info("获取的加密内容:"+content);
				  content = GuoXin.getFromBASE64(content);
				log.info("解析加密:"+content); 
				Map<String,String> contentMap = CommonUtils.xml2map(content, false);   
				InvoiceResult invoiceResult = new InvoiceResult();
				invoiceResult.setSuccess(true); 
				invoiceResult.setGoldtaxCode(contentMap.get("FP_DM"));
				invoiceResult.setGoldtaxNum(contentMap.get("FP_HM"));
				invoiceResult.setGroupStatus("2");
				invoiceResult.setBillGdate(contentMap.get("KPRQ"));
				invoiceResult.setePdfUrl(contentMap.get("PDF_URL"));
				invoiceResult.setDocNum(invoiceHead.getDocNum());
				invoiceResult.setGroupNum(invoiceHead.getInvoiceSplitLines().get(0).getGroupNum());
				invoiceResult.setHjje(originalWsje.toString());
				invoiceResult.setHjse(originalSe.toString());
				invoiceResult.setJshj(originalHsje.toString()); 
			 
			    
				log.info("开票结果:"+invoiceResult);
				return invoiceResult;
			}else {
				log.info(returnMessage);
				return InvoiceResult.Error(returnCode, returnMessage,null);
			}  
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			 e1.printStackTrace();
			 log.info(e1.getMessage());
			 return InvoiceResult.Error(e1.getMessage());
		}
		
	}
	    
	    
  

	@Override
	public Result invalid(InvoiceHead invoiceHead, Business business) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InvoiceResult invoiceQuery(InvoiceHead invoiceHead, BusinessVo business) {
		String fpqqlsh =null;
		String nsrsbh= null;
		if("dev".equals(dev_or_prod)) {
//			fpqqlsh="WYtest201909231410";
//			nsrsbh= "110109500321655";
			String appid = "6d29f136114544bcc73edcce960c430231183cc192c433e2b9ebcad56e8ceb08";//appid
			String contentPassword = "5EE6C2C11DD421F2";//AES加密密钥
	        business.setAppId(appid);
	        business.setAppSecret(contentPassword);
			nsrsbh=invoiceHead.getOrgTaxcode();
			fpqqlsh = invoiceHead.getInvoiceSplitLines().get(0).getDocNum()+invoiceHead.getInvoiceSplitLines().get(0).getGroupNum();			 

		}else {
			nsrsbh=invoiceHead.getOrgTaxcode();
			fpqqlsh = invoiceHead.getInvoiceSplitLines().get(0).getDocNum()+invoiceHead.getInvoiceSplitLines().get(0).getGroupNum();			 
		} 
		HashMap<String, String > body = new HashMap<>();
		body.put("FPQQLSH", fpqqlsh);
		body.put("XSF_NSRSBH", nsrsbh);
		
		String xml_FPCX_Electron="";
	    try {
	    	
			xml_FPCX_Electron = GuoXin.toXml_FPCX_Electron(body, business);
		} catch (Exception e) {
			e.printStackTrace(); 
			return InvoiceResult.Error("发票开具时,xml转换错误"+e.getMessage());
		}
	      
	    Map map = null;
	    
	    log.info("请求报文:"+xml_FPCX_Electron);
	    String result =null;
	    try {
	    	 result = RequestUtils.getHttpConnectResult(xml_FPCX_Electron,requestUrl+"invoice");
		} catch (Exception e) {
			e.printStackTrace();
			return InvoiceResult.Error("请求失败,"+e.getMessage());
		}
		try {   
		    
		    log.info("返回结果:"+result);
			map = CommonUtils.xml2map(result, false); 
			Map<String, String> returnStateInfo =  (Map<String, String>) map.get("returnStateInfo");
			Map<String, String> Data =  (Map<String, String>) map.get("Data");
			String returnCode = returnStateInfo.get("returnCode");
			String returnMessage = returnStateInfo.get("returnMessage");
			if(returnCode.equals("0000")) {
				//成功，此处发票可能已经开具过，也会返回成功的
//				获取内容
				String content = Data.get("content"); 
				log.info("获取的加密内容:"+content);
				  content = GuoXin.getFromBASE64(content);
				log.info("解析加密:"+content);
				Map<String,String> contentMap = CommonUtils.xml2map(content, false);   
				InvoiceResult invoiceResult = new InvoiceResult();
				invoiceResult.setSuccess(true); 
				invoiceResult.setGoldtaxCode(contentMap.get("FP_DM"));
				invoiceResult.setGoldtaxNum(contentMap.get("FP_HM"));
				invoiceResult.setGroupStatus("2");
				invoiceResult.setBillGdate(contentMap.get("KPRQ"));
				invoiceResult.setePdfUrl(contentMap.get("PDF_URL"));
				invoiceResult.setDocNum(invoiceHead.getDocNum());
				invoiceResult.setGroupNum(invoiceHead.getInvoiceSplitLines().get(0).getGroupNum());
				log.info("开票结果:"+invoiceResult);
				return invoiceResult;
				
			}else if(returnCode.equals("3001")){//未有开票记录
				InvoiceResult invoiceResult = new InvoiceResult();
				invoiceResult.setDocNum(invoiceHead.getDocNum());
				invoiceResult.setGroupNum(invoiceHead.getInvoiceSplitLines().get(0).getGroupNum());
				invoiceResult.setSuccess(true);  
				invoiceResult.setGroupStatus(invoiceHead.getInvoiceSplitLines().get(0).getGroupStatus());
				return invoiceResult;
			}else{
				log.info(returnMessage);
				return InvoiceResult.Error(returnCode, returnMessage,null);
			}  
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			log.info(e1.getMessage());
			return InvoiceResult.Error(e1.getMessage());
		}
		 
	}

	@Override
	public Result invoiceQueryByGoldTaxNum(String goldTaxNum, String invoiceType, Business business) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BusinessVo getDeviceInfo(BusinessVo businessVo) { 
		String nsrsbh= null;
		if("dev".equals(dev_or_prod)) {  
			nsrsbh= "110109500321655"; 
			businessVo=new BusinessVo();
			businessVo.setInvoiceType(4);
			String appid = "6d29f136114544bcc73edcce960c430231183cc192c433e2b9ebcad56e8ceb08";//appid
 			String contentPassword = "5EE6C2C11DD421F2";//AES加密密钥 
 			businessVo.setAppId(appid);
 			businessVo.setOrgTaxcode(nsrsbh);
 			businessVo.setAppSecret(contentPassword);
		}else {
			nsrsbh=businessVo.getOrgTaxcode();
		} 
		HashMap<String, String > body = new HashMap<>(); 
		body.put("XSF_NSRSBH", nsrsbh);
		body.put("DETAIL_FLAG","0") ;
		body.put("ALL_FLAG", "1");
		
		
		String xml_FPCX_Electron="";
	    try { 
			xml_FPCX_Electron = GuoXin.toXml_FPKCCX_Electron(body, businessVo);
		} catch (Exception e) {
			e.printStackTrace(); 
			throw new ServiceException("发票开具时,xml转换错误"+e.getMessage());
		}
	     
	    Map map = null; 
			try {
				log.info("请求报文:"+xml_FPCX_Electron);
			    String result = RequestUtils.getHttpConnectResult(xml_FPCX_Electron,requestUrl+"invoice");
			    log.info("返回结果:"+result);
				map = CommonUtils.xml2map(result, false); 
				Map<String, String> returnStateInfo =  (Map<String, String>) map.get("returnStateInfo");
				Map<String, String> Data =  (Map<String, String>) map.get("Data");
				String returnCode = returnStateInfo.get("returnCode");
				String returnMessage = returnStateInfo.get("returnMessage"); 
				
				if(returnCode.equals("0000")) {
					//成功
					String content = Data.get("content"); 
					log.info("获取的加密内容:"+content);
					  content = GuoXin.getFromBASE64(content);
					log.info("解析加密:"+content);
					Map contentMap = CommonUtils.xml2map(content, false);   
					
					System.out.println(contentMap);
					  
					  
					 String  UNDISTRIBUTED_NUM= (String) contentMap.get("UNDISTRIBUTED_NUM");
					  String  SUM_NUM = (String) contentMap.get("SUM_NUM");
					  System.out.println(UNDISTRIBUTED_NUM);
					  System.out.println(SUM_NUM); 
				     
					  businessVo.setUndistributedNum(UNDISTRIBUTED_NUM);
					  businessVo.setSumNum(SUM_NUM);
					  return businessVo;
				       
				} else {
					throw new ServiceException("查询异常:"+returnMessage);
				} 
			} catch (Exception e) {
				e.printStackTrace(); 
				throw new ServiceException("发票开具时,xml转换错误"+e.getMessage());
			} 
			
			 
	}

}
