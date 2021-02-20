///**
// * 
// */
//package top.anets.service.impl;
//
//import java.math.BigDecimal;
//import java.math.MathContext;
//import java.math.RoundingMode;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.lang3.StringUtils;
//import org.dom4j.DocumentException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Lettuce;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.stereotype.Service;
//
//import com.alibaba.druid.sql.ast.statement.SQLIfStatement.Else;
//
//import ch.qos.logback.core.joran.spi.XMLUtil;
//import lombok.extern.slf4j.Slf4j;
//import top.anets.entity.Business;
//import top.anets.entity.InvoiceHead;
//import top.anets.entity.InvoiceSplitLine;
//import top.anets.exception.ServiceException;
//import top.anets.redis.RedisKey;
//import top.anets.redis.RedisService;
//import top.anets.service.ThirdInvoiceService;
//import top.anets.utils.CommonUtils;
//import top.anets.utils.Result;
//import top.anets.utils.Status;
//import top.anets.utils.Xuanji;
//import top.anets.vo.BusinessVo;
//import top.anets.vo.InvoiceResult;
//
///**
// * @author Administrator
// *
// */
//
//@Service("xuanjiInvocieService")
//@Slf4j
//@PropertySource("classpath:/settings.properties")
//public class XuanjiInvocieServiceImpl implements ThirdInvoiceService{
//    
//    
//    @Autowired
//    private RedisService redisService;
//    
//    
//    @Value("${xuanJi.requestUrl}")
//    private String requestUrl;
//    
//	@Override
//	public Result init() {
//		return null;
//	}
//
//	@Override
//	public InvoiceResult invoice(InvoiceHead invoiceHead,BusinessVo business) {
////		开具校验
//		if(invoiceHead==null||invoiceHead.getInvoiceSplitLines()==null||invoiceHead.getInvoiceSplitLines().size()<=0) { 
//			return InvoiceResult.Error("发票开具检验不通过，发票头或体为空！");
//		}
////		封装头
//	    LinkedHashMap<String, String> map = new LinkedHashMap<>();
//	    map.put("fpqqlsh", ""+invoiceHead.getDocNum()+invoiceHead.getInvoiceSplitLines().get(0).getGroupNum());
//	    map.put("kpm", this.getSpm(business));
//	    if(invoiceHead.getInvoiceType()==null) {
//	    	return InvoiceResult.Error("发票开具检验不通过，没有发票类型！");
//	    }
//	    if(invoiceHead.getInvoiceType().equals("1")) {
//	    	map.put("fplxdm", Xuanji.InvoiceType_standard);
//	    }else if(invoiceHead.getInvoiceType().equals("2")) {
//	    	map.put("fplxdm", Xuanji.InvoiceType_normal);
//	    }else if(invoiceHead.getInvoiceType().equals("4")||invoiceHead.getInvoiceType().equals("5")) {
//	    	map.put("fplxdm", Xuanji.InvoiceType_electron);
//	    }else { 
//	    	return InvoiceResult.Error("发票开具检验不通过，发票类型"+invoiceHead.getInvoiceType()+"不正确！");
//	    }
//	    if(invoiceHead.getInvoiceRed()==null) {  
//	    	return InvoiceResult.Error("发票开具检验不通过，没有开票类型（正数票/负数票）！");
//	    }else if(invoiceHead.getInvoiceRed().equals("Y")||StringUtils.isBlank(invoiceHead.getInvoiceRed())) {//不是红字发票
//	    	 map.put("kplx", "0");
//	    	 map.put("yfpdm", "");
//	    	 map.put("yfphm", "");
//	    }else if(invoiceHead.getInvoiceRed().equals("X")) {//是红字发票
//	    	 map.put("kplx", "1");
//	    	 map.put("yfpdm", invoiceHead.getInvoiceRedFpdm());
//	    	 map.put("yfphm", invoiceHead.getInvoiceRedFphm());
//	    }else {
//	    	return InvoiceResult.Error("发票开具检验不通过，开票类型"+invoiceHead.getInvoiceRed()+"不正确！"); 
//	    }
//	    map.put("yhlx", business.getUserType()+"");
//	    map.put("jsbh", business.getOrgTaxcode()+"~~"+business.getOrgMachine());
//	    map.put("xhdwdzdh",invoiceHead.getOrgAddress()+invoiceHead.getOrgTelephone());
//	    map.put("xhdwyhzh",invoiceHead.getOrgBankaccount());
//	    if(StringUtils.isNoneBlank(invoiceHead.getCustIdAr())) {
//	    	map.put("ghdwsbh",invoiceHead.getCustTaxcode());
//	    }else {
//	    	map.put("ghdwsbh",invoiceHead.getCustTaxcode());
//	    }
//	    map.put("ghdwmc",invoiceHead.getCustName());
//	    map.put("ghdwdzdh",invoiceHead.getCustAddress()+invoiceHead.getCustTelephone());
//	    map.put("ghdwyhzh",invoiceHead.getCustBankaccount());
//	    map.put("bz",invoiceHead.getBillRemark());
//	    map.put("skr", invoiceHead.getPayeeName());
//	    map.put("fhr", invoiceHead.getCheckName());
//	    map.put("kpr", invoiceHead.getUserName());
//	    if(StringUtils.isNoneBlank(invoiceHead.getCustTelephone())) {
//	    	map.put("sprsjh", invoiceHead.getCustTelephone());
//	    }else if(StringUtils.isNoneBlank(invoiceHead.getCustEmail())) {
//	    	map.put("sprsjh", invoiceHead.getCustEmail());
//	    }else {
//	    	map.put("sprsjh", "");
//	    }
//	    map.put("is_apply","0");
//	    if(StringUtils.isBlank(invoiceHead.getInvoiceList())) {
//	    	 map.put("qdbz","0");     //清单标志
//	    }else if(invoiceHead.getInvoiceList().equals("X")||invoiceHead.getInvoiceList().equals("1")) {
//	    	 map.put("qdbz","1");     //清单标志
//	    }else {
//	    	 map.put("qdbz","0");     //清单标志
//	    }
//	   
//	    map.put("tzdbh", "");
////		封装体
//	    List<Map<String, String>> list = new ArrayList<>();
//	    String group=invoiceHead.getInvoiceSplitLines().get(0).getGroupNum();
//	    for (InvoiceSplitLine invoiceSplitLine : invoiceHead.getInvoiceSplitLines()) { 
//			if(StringUtils.isNoneBlank(group)&&!group.equals(invoiceSplitLine.getGroupNum())) { 
//				return InvoiceResult.Error("发票开具检验不通过，发票体出现非同组的情况！");  
//			}
//			HashMap<String, String> line = new HashMap<>();
////			金额计算
//			if(invoiceSplitLine.getGiftFlag()!=null&&invoiceSplitLine.getGiftFlag().equals("1")) {//折扣行
//				line.put("fphxz", "1");
//			}else {//非折扣行
//				line.put("fphxz", "0");
//			}
//			line.put("je", ""+invoiceSplitLine.getZamountWsj());
//			line.put("se", ""+invoiceSplitLine.getZamountSej());
//			line.put("dj", ""+invoiceSplitLine.getZpriceWsj()); 
//			BigDecimal quantity = invoiceSplitLine.getQuantity().round(new MathContext(12, RoundingMode.HALF_UP));
//			line.put("spsl",""+quantity);
//			line.put("spmc", invoiceSplitLine.getItemName());
//			line.put("ggxh", invoiceSplitLine.getItemSpec());
//			line.put("dw", invoiceSplitLine.getUnitName());
//			line.put("spbm", invoiceSplitLine.getTaxCatecode());
//			if(invoiceHead.getInvoiceType().equals("5")) {//外销免税
//				line.put("lslbs","1");
//			}else 
//			{
//				line.put("lslbs","");
//			}
//			line.put("sl", ""+invoiceSplitLine.getTaxRate());
//			line.put("yhzcbs", "");
//			line.put("spsm", "");
//			line.put("zxbm", "");
//			line.put("zzstsgl", "");
//			list.add(line);
//		}
//	    
//	    Map<String, Object> map2 = this.requestThirdServiceToInvoice(map, list,business);
//	    System.out.println(map2);
//	    Map<String,	Object> object = (Map<String, Object>) map2.get("body");
//		if(object.get("returncode").equals("0")) {
//			Map<String,	Object> data = (Map<String, Object>) object.get("returndata");
//			if(data==null) return InvoiceResult.Error("开具发票后数据没有获取到:"+object.toString());   
//			InvoiceResult  query = this.invoiceQuery(invoiceHead,business);           
//			return query;
//		}else if(object.get("returncode").equals("300006")){
//			//需要查询
//			InvoiceResult  query = this.invoiceQuery(invoiceHead,business);      
//			return query;
//		}else { 
//			
//			return InvoiceResult.Error((String)object.get("returncode"),"发票开具失败 :"+(String)object.get("returnmsg"), null);
//		} 
//	}
//
//	/**
//	* 校验开票的数据
//	*@param invoiceHead 
//	*/
//	private void checkBeforeInvoice(InvoiceHead invoiceHead) {
////		校验是否只有1组
//		
//	}
//
//	@Override
//	public Result invalid(InvoiceHead invoiceHead,Business business) {
//		String invoiceType = invoiceHead.getInvoiceType();
//		LinkedHashMap<String, String> map = new LinkedHashMap<>();
//		map.put("jsbh",business.getOrgTaxcode()+"~~"+business.getOrgMachine());
//		 if(invoiceType.equals("1")) {
//		    map.put("fplxdm", Xuanji.InvoiceType_standard);
//		 }else if(invoiceType.equals("2")) {
//		    map.put("fplxdm", Xuanji.InvoiceType_normal);
//		 }else if(invoiceType.equals("4")||invoiceType.equals("5")) {
//		    map.put("fplxdm", Xuanji.InvoiceType_electron);
//		 }else { 
//		    return Result.Error("发票查询异常，发票类型"+invoiceType+"不正确！");
//		 }
//		 map.put("zflx", "1");
//		 map.put("fpdm", invoiceHead.getGoldtaxCode());
//		 map.put("fphm", invoiceHead.getGoldtaxNum());
//		 List<InvoiceSplitLine> splitLines = invoiceHead.getInvoiceSplitLines();
//		 BigDecimal hjje=new BigDecimal("0");
//		 for (InvoiceSplitLine invoiceSplitLine : splitLines) {
//			BigDecimal zamountWsj = invoiceSplitLine.getZamountWsj();
//			hjje=hjje.add(zamountWsj);
//		 }
//		 map.put("hjje", hjje.toString());
//		 map.put("zfr", invoiceHead.getUserName()); 
//		 Map<String, Object> map2 = this.requestThirdService("FPZF", map, "发票作废",business);
//		 System.out.println(map2);
//		  Map<String,	Object> object = (Map<String, Object>) map2.get("body");
//		 if(object.get("returncode").equals("0")) {
//				Map<String,	Object> data = (Map<String, Object>) object.get("returndata");
//				if(data==null) return Result.Error("发票作废时数据没有获取到:"+object.toString());   
//				
//				for (InvoiceSplitLine invoiceSplitLine : splitLines) {
//					invoiceSplitLine.setGoldtaxCode("");
//					invoiceSplitLine.setGoldtaxNum("");
//					invoiceSplitLine.setCancelGdate((String)data.get("zfrq"));
//					invoiceSplitLine.setGroupStatus("9");
//				}
//				invoiceHead.setDocStatus("9");
//			 
//				  return Result.Success(invoiceHead);
//			}else { 
//				return Result.Error((String)object.get("returncode"),"发票作废失败 :"+(String)object.get("returnmsg"), null);
//			} 
//	}
//	 /**
//     * 
//    * 流水号
//    *@param serialNumber  单据号+组号
//    *@return
//     */
//	@Override
//	public InvoiceResult invoiceQuery(InvoiceHead invoiceHead,BusinessVo business) {
//		InvoiceResult  invoiceresult = new InvoiceResult();
//		String  docNum=invoiceHead.getDocNum();
//		String groupNum =invoiceHead.getInvoiceSplitLines().get(0).getGroupNum();
//		String invoiceType =invoiceHead.getInvoiceType();
//		if(StringUtils.isBlank(docNum)) { 
//			 invoiceresult.setSuccess(false);
//			 invoiceresult.setMsg("发票查询异常:单据号不能空");
//			return   invoiceresult;
//		}
//		if(groupNum==null)   groupNum=""; 
//		String  serialNumber = docNum + groupNum;
//		LinkedHashMap<String, String> map = new LinkedHashMap<>();
//		map.put("jsbh",business.getOrgTaxcode()+"~~"+business.getOrgMachine());
//		 if(invoiceType.equals("1")) {
//		    map.put("fplxdm", Xuanji.InvoiceType_standard);
//		 }else if(invoiceType.equals("2")) {
//		    map.put("fplxdm", Xuanji.InvoiceType_normal);
//		 }else if(invoiceType.equals("4")||invoiceType.equals("5")) {
//		    map.put("fplxdm", Xuanji.InvoiceType_electron);
//		 }else { 
//			   invoiceresult.setSuccess(false);
//			   invoiceresult.setMsg("发票查询异常，发票类型"+invoiceType+"不正确！");
//				return   invoiceresult; 
//		 }
//		 
//		 map.put("cxfs", "1");
//		 map.put("cxtj",serialNumber);
//		 Map<String, Object> result = this.requestThirdService("FPCX", map, "发票查询",business);
//		 Map<String,Object> object = (Map<String, Object>) result.get("body");
//		 if(object.get("returncode").equals("0")) {
//			 Map<String,	Object> data = (Map<String, Object>) object.get("returndata");
//			 if(data==null)  throw new ServiceException("发票查询异常:返回的数据retrundata为null:");
//			 data = (Map<String, Object>) data.get("kpxx");
//				if(data==null) throw new ServiceException("发票查询数据没有获取到:"+object.toString());
//				invoiceresult.setSuccess(true);
//				invoiceresult.setDocNum(docNum); 
//				invoiceresult.setGroupNum(groupNum);
//				invoiceresult.setBillGdate((String)data.get("kprq"));
////				返回来的开票 金额相关处理
//
//				
//				
////				电子发票地址处理
//				String url  = (String)data.get("url");
//				String fpt_url  = (String)data.get("fpt_url");
//				if(StringUtils.isNoneBlank(url)&&StringUtils.isNoneBlank(fpt_url)&&!url.equals(fpt_url)) {
//					invoiceresult.setPdfUrl(url+";"+fpt_url);
//				}else if(StringUtils.isNoneBlank(url)) {
//					invoiceresult.setPdfUrl(url);
//				}else if(StringUtils.isNoneBlank(fpt_url)){
//					invoiceresult.setPdfUrl(fpt_url);
//				}else {
//					
//				}
//				
////				发票状态处理
//				String status = (String) data.get("fpzt");
//				if(status==null) { 
//					invoiceresult.setSuccess(false);
//					invoiceresult.setMsg("发票查询异常，发票类型"+invoiceType+"不正确！");
//                    return invoiceresult;
//				}
//				
//				invoiceresult.setBillGdate((String)data.get("kprq"));
//				invoiceresult.setBillRemark ((String)data.get("bz"));
//	            
//				System.out.println("状态================"+status);
//				if(status.equals("00")||status.equals("01")) {
//					invoiceresult.setGoldtaxNum((String)data.get("fphm"));
//					invoiceresult.setGoldtaxCode((String)data.get("fpdm"));
//					invoiceresult.setInvoiceStatus("2");  
//					
//					String  wsje = (String) data.get("hjje");
//					invoiceresult.setWsje(wsje);
//					String se  =(String) data.get("hjse");
//					invoiceresult.setSe(se);
//					String hsje  =(String)data.get("jshj");
//					invoiceresult.setHsje(hsje);
//					
////					计算合计税额
//				    BigDecimal originalSe=new BigDecimal("0");
//				    BigDecimal originalHsje=new BigDecimal("0");
//				    BigDecimal originalWsje=new BigDecimal("0");
//			        for (InvoiceSplitLine invoiceSplitLines : invoiceHead.getInvoiceSplitLines()) { 
//			        	  originalSe=originalSe.add(invoiceSplitLines.getZamountSej());
//			        	  originalHsje=originalHsje.add(invoiceSplitLines.getZamountHsj());
//			        	  originalHsje=originalWsje.add(invoiceSplitLines.getZamountWsj());
//					} 
//			        
//			        invoiceresult.setHsjec(String.valueOf(new BigDecimal(hsje).subtract(originalHsje)));
//			        invoiceresult.setWsjec(String.valueOf(new BigDecimal(wsje).subtract(originalWsje)));
//			        invoiceresult.setSec(String.valueOf(new BigDecimal(se).subtract(originalSe)));
//			        
//					
//				}else if(status.equals("01")||status.equals("03")||status.equals("04")){
//					invoiceresult.setInvoiceStatus("9");				
//					invoiceresult.setGoldtaxNum("");
//					invoiceresult.setGoldtaxCode("");
//				}else { 
//					invoiceresult.setSuccess(false);
//					invoiceresult.setMsg("未知结果:"+(String) object.get("returnmsg"));
//                    return invoiceresult;
//				} 
//				 
//				return invoiceresult;
//		 }else {	
//			 
//			  if(object.get("returncode").equals("300075")) {
//				  invoiceresult.setSuccess(false);
//				  invoiceresult.setMsg("没有此发票请求流水号的信息");
//				  return invoiceresult;
//			  }else {
//				  invoiceresult.setSuccess(false);
//				  invoiceresult.setCode((String)object.get("returncode"));
//				  invoiceresult.setMsg("发票查询失败 :"+(String)object.get("returnmsg"));
//			      return invoiceresult; 
//			  }
//} 
//	}
//	
//	/**
//	 * 通过发票号码查询============================================================================================
//	 */
//	@Override
//	public Result invoiceQueryByGoldTaxNum(String goldTaxNum, String invoiceType,Business business) {
//		LinkedHashMap<String, String> map = new LinkedHashMap<>();
//		map.put("jsbh",business.getOrgTaxcode()+"~~"+business.getOrgMachine());
//		 if(invoiceType.equals("1")) {
//		    	map.put("fplxdm", Xuanji.InvoiceType_standard);
//		 }else if(invoiceType.equals("2")) {
//		    	map.put("fplxdm", Xuanji.InvoiceType_normal);
//		 }else if(invoiceType.equals("4")||invoiceType.equals("5")) {
//		    	map.put("fplxdm", Xuanji.InvoiceType_electron);
//		 }else {
//		    	throw new ServiceException("发票开具检验不通过，发票类型"+invoiceType+"不正确！");
//		 }
//		 
//		 map.put("cxfs", "0");
//		 map.put("cxtj",goldTaxNum);
//		 
//		 Map<String, Object> result = this.requestThirdService("FPCX", map, "发票查询",business);
//		 Map<String,Object> object = (Map<String, Object>) result.get("body");
//		 if(object.get("returncode").equals("0")) {
//			 Map<String,	Object> data = (Map<String, Object>) object.get("returndata");
//			 if(data==null)  throw new ServiceException("发票查询异常:返回的数据retrundata为null:");
//			 data = (Map<String, Object>) data.get("kpxx");
//				if(data==null) throw new ServiceException("发票查询数据没有获取到:"+object.toString());
//				InvoiceSplitLine splitLine = new InvoiceSplitLine();
//				 
//	            splitLine.setGoldtaxNum(goldTaxNum);
//	            splitLine.setGoldtaxCode((String)data.get("fpdm"));
//				splitLine.setBillGdate((String)data.get("kprq"));
////				返回来的开票 金额相关处理
//				
//				
////				电子发票地址处理
//				String url  = (String)data.get("url");
//				String fpt_url  = (String)data.get("fpt_url");
//				if(StringUtils.isNoneBlank(url)&&StringUtils.isNoneBlank(fpt_url)&&!url.equals(fpt_url)) {
//					splitLine.setePdfUrl(url+";"+fpt_url);
//				}else if(StringUtils.isNoneBlank(url)) {
//					splitLine.setePdfUrl(url);
//				}else if(StringUtils.isNoneBlank(fpt_url)){
//					splitLine.setePdfUrl(fpt_url);
//				}else {
//					
//				}
//				
////				发票状态处理
//				String status = (String) data.get("fpzt");
//				if(status==null) {
//					throw new ServiceException("发票查询异常:返回发票状态为null:");
//				}
//				if(status.equals("00")||status.equals("01")) {
//					splitLine.setGroupNum("2");
//				}else if(status.equals("01")||status.equals("03")||status.equals("04")){
//					splitLine.setGroupNum("9");
//					
//				}else {
//					throw new ServiceException("发票查询异常:状态不匹配-"+status);
//				}
//				return Result.Success(splitLine);
//				
//		 }else {
//				throw new ServiceException((String)object.get("returncode"),"发票查询失败 :"+(String)object.get("returnmsg"));
//		 } 
//	}
//	
//	
//	
//	
//	/**
//	 * 获取token信息
//	* 
//	*@return
//	 */
//	public String getToken(Business business) {
//		
////		检测redis缓存中是否有token
//		String token = redisService.get(RedisKey.XUANJI_TOKEN.getKey());
//		if(StringUtils.isNotBlank(token)) {
//			return token;
//		}
//		
//		
//		
//		
////		没有token或者token过期就去获取，并设置过期时间为1h
//		HashMap<String, String> map = new LinkedHashMap();
//		map.put("appId", business.getAppId());
//		map.put("appSecret", business.getAppSecret());
//		if(business.getIsEnterprise()) {
//			map.put("userCode", business.getUserCode());
//			map.put("password", business.getPassword()); 
//		}
//		
//		Map<String, Object> map2 = this.requestThirdService("GETTOKEN", map, "获取token",business);
//		Map<String,	Object> object = (Map<String, Object>) map2.get("body");
//		if(object.get("returncode").equals("0")) {
//			redisService.set(RedisKey.XUANJI_TOKEN.getKey(), (String)object.get("token"), RedisKey.XUANJI_TOKEN.getSeconds());
//			this.getDiviceInfo(Xuanji.InvoiceType_normal,business);
//			return (String) object.get("token");
//		}else {
//			throw new ServiceException((String)object.get("returncode"),"token获取失败,建议重试 :"+(String)object.get("returnmsg"));
//		}
//        
//        
//	}
//	
//	/**
//	 * 获取开票码/授权码
//	* 
//	*@return
//	 */
//	public String getSpm(Business business) {
//		HashMap<String, String> map = new LinkedHashMap();
//		map.put("nsrsbh", business.getOrgTaxcode());
//		map.put("sqm","");
//		
//		Map<String, Object> map2 = this.requestThirdService("GETKPM", map, "获取授权码",business);
//
//		Map<String,	Object> object = (Map<String, Object>) map2.get("body");
//		String code  =(String)object.get("returncode");
//		if(object.get("returncode").equals("0")) { 
//           return (String)object.get("returnmsg");
//		}else {  
//		   throw new ServiceException((String)object.get("returncode"),"开票码信息获取失败 :"+(String)object.get("returnmsg"));
//		}  
//	}
//    
//	
//	/**
//	 * 获取税控设备信息,主要是获取 公司名
//	 */
//	private void getDiviceInfo(String invoiceTypeCode,Business business){
//		HashMap<String, String> map = new LinkedHashMap();
//		map.put("jsbh", business.getOrgTaxcode()+"~~"+business.getOrgMachine());
//		
//		if(business.getIsSkp()) {
//			map.put("fplxdm",invoiceTypeCode);
//		}
//		Map<String, Object> map2 = this.requestThirdService("SKPXXCX", map, "获取税控设备信息",business);
//
//		 
//		Map<String,	Object> object = (Map<String, Object>) map2.get("body");
//		String code  =(String)object.get("returncode");
//		if(object.get("returncode").equals("0")) {
//           business.setOrgName((String)object.get("nsrmc"));
//           System.out.println(business);
//		}else { 
////			throw new ServiceException((String)object.get("returncode"),"设备信息获取失败 :"+(String)object.get("returnmsg"));
//		} 
//		
//
//		
//	}
//	
//	
//	
//	/**
//	 * 购票信息
//	 */
//	public void getInvoiceInfo(String invoiceTypeCode,Business business) {
//		HashMap<String, String> map = new LinkedHashMap();
//		map.put("jsbh", business.getOrgTaxcode()+"~~"+business.getOrgMachine());
//		
//		if(business.getIsSkp()) {
//			map.put("fplxdm",invoiceTypeCode);
//		}
//		Map<String, Object> map2 = this.requestThirdService("GPXXCX", map, "获取购票信息",business);
//
//		 
//		Map<String,	Object> object = (Map<String, Object>) map2.get("body");
//		String code  =(String)object.get("returncode");
//		if(object.get("returncode").equals("0")) {
//           
//		}else { 
//			throw new ServiceException((String)object.get("returncode"),"设备信息获取失败 :"+(String)object.get("returnmsg"));
//		} 
//		System.out.println(object);
//	}
//	
//	
//	
//	/**
//	 * 远程调用第三方接口（普通请求）
//	 */
//	
//	public Map<String,Object> requestThirdService(String method,Map<String,String> requestData,String tip,Business business) {
//		String xml = Xuanji.toXml(method, requestData);
//		String request;
//		String tokenUrl="";
//		if(method.equals("GETTOKEN")) {
//			tokenUrl=requestUrl+"token.htm";
//		}else {
//			tokenUrl=requestUrl+this.getToken(business)+".htm";
//		}
//		try {
//			request = Xuanji.getRequest(xml, tokenUrl);
//		} catch (Exception e) {
//			log.error(tip+"时出错:"+e.getMessage());
//			e.printStackTrace();
//			throw new ServiceException(tip+"时出错:"+e.getMessage()); 
//		}
//		
//		System.out.println(request);
//		
//		Map<String, Object> map2=null;
//		try {
//			map2 = CommonUtils.xml2map(request,false);
//			if(map2==null) {
//				throw new ServiceException(tip+"时请求第三方服务返回是null值！"); 
//			}
//		} catch (DocumentException e) {
//			log.error(tip+"中xml转换成map时出错:"+e.getMessage());
//			e.printStackTrace();
//			throw new ServiceException(tip+"中xml转换成map时出错:"+e.getMessage()); 
//		}
//		return map2;
//		
//	}
//	/**
//	 * 远程调用第三方接口（开票请求）
//	* 
//	*@param method
//	*@param requestData
//	*@param tip
//	*@return
//	 */
//	public Map<String,Object> requestThirdServiceToInvoice(Map<String,String> invoiceHeadData,List<Map<String,String>> linesData,Business business){
//		String xml = Xuanji.toXmlForInvoice("FPKJ", invoiceHeadData, linesData);
//		String tip ="发票开具";
//		String request;
//		String tokenUrl=requestUrl+this.getToken(business)+".htm";
//		 
//		try {
//			request = Xuanji.getRequest(xml, tokenUrl);
//		} catch (Exception e) {
//			log.error(tip+"时出错:"+e.getMessage());
//			e.printStackTrace();
//			throw new ServiceException(tip+"时出错:"+e.getMessage()); 
//		}
//		
//		System.out.println(request);
//		
//		Map<String, Object> map2=null;
//		try {
//			map2 = CommonUtils.xml2map(request,false);
//			if(map2==null) {
//				throw new ServiceException(tip+"时请求第三方服务返回是null值！"); 
//			}
//		} catch (DocumentException e) {
//			log.error(tip+"中xml转换成map时出错:"+e.getMessage());
//			e.printStackTrace();
//			throw new ServiceException(tip+"中xml转换成map时出错:"+e.getMessage()); 
//		}
//		return map2;
//	}
//
//	@Override
//	public InvoiceResult InvoiceElectron(InvoiceHead invoiceHead, BusinessVo business) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	 
//
//	 
//
//	
//   
//	
//	
//	
//}
