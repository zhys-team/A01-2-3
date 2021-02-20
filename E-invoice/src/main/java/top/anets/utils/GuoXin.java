/**
 * 
 */
package top.anets.utils;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice.This;
import sun.misc.BASE64Encoder;
import top.anets.entity.Business;
import top.anets.exception.ServiceException;
import top.anets.utils.guoXin.MyAES;
import top.anets.vo.BusinessVo;

/**
 * @author Administrator
 *
 */
@Slf4j
public class GuoXin {
    /**
     * 发票开具
    *@param intefaceCode
    *@param invoiceHeadData
    *@param linesData
    *@param map
    *@param business
    *@return
     * @throws Exception 
     */ 
	public static String toXml_FPKJ_Electron( Map<String,String> invoiceHeadData,List<Map<String,String>> linesData  ,BusinessVo business) throws Exception {
		if(linesData==null||invoiceHeadData==null) {
        	return null;
        }
        String prefix ="<REQUEST_COMMON_FPKJ class=\"REQUEST_COMMON_FPKJ\">";
        String suffix ="</REQUEST_COMMON_FPKJ>";
        String body = CommonUtils.map2Xml(invoiceHeadData);
        StringBuffer lines = new StringBuffer("");
        lines.append("<COMMON_FPKJ_XMXXS class=\"COMMON_FPKJ_XMXX\" size=\""+linesData.size()+"\">");
        for (int i=0;i<linesData.size();i++) {
			String linePrefix ="<COMMON_FPKJ_XMXX>";
			String lineBody =CommonUtils.map2Xml(linesData.get(i));
			String lineSuffix ="</COMMON_FPKJ_XMXX>";
			
			lines.append((linePrefix+lineBody+lineSuffix));
		}
        lines.append("</COMMON_FPKJ_XMXXS>");  
        String content =prefix+body+lines+suffix;
       
//        content="<REQUEST_COMMON_FPKJ class=\"REQUEST_COMMON_FPKJ\"> \r\n" + 
//        		"  <FPQQLSH>WYtest201909231410</FPQQLSH>  \r\n" + 
//        		"  <KPLX>0</KPLX>\r\n" + 
//        		"  <BMB_BBH></BMB_BBH>\r\n" + 
//        		"  <ZSFS>0</ZSFS>  \r\n" + 
//        		"  <XSF_NSRSBH>110109500321655</XSF_NSRSBH>\r\n" + 
//        		"  <XSF_MC>百旺电子测试2</XSF_MC>  \r\n" + 
//        		"  <XSF_DZDH>南山区蛇口83484949</XSF_DZDH>  \r\n" + 
//        		"  <XSF_YHZH>610118191919191919</XSF_YHZH>  \r\n" + 
//        		"  <GMF_NSRSBH/>  \r\n" + 
//        		"  <GMF_MC>测试</GMF_MC>  \r\n" + 
//        		"  <GMF_DZDH/>  \r\n" + 
//        		"  <GMF_YHZH/> \r\n" + 
//        		"  <GMF_SJH>18200710647</GMF_SJH>  \r\n" + 
//        		"  <GMF_DZYX>596529063@qq.com</GMF_DZYX> \r\n" + 
//        		"  <FPT_ZH></FPT_ZH>   \r\n" + 
//        		"  <WX_OPENID>微信openId</WX_OPENID>\r\n" + 
//        		"  <KPR>小明</KPR>  \r\n" + 
//        		"  <SKR>李四</SKR>  \r\n" + 
//        		"  <FHR>小王</FHR>  \r\n" + 
//        		"  <YFP_DM/>  \r\n" + 
//        		"  <YFP_HM/>  \r\n" + 
//        		"  <JSHJ>106</JSHJ>\r\n" + 
//        		"  <HJJE>100</HJJE>\r\n" + 
//        		"  <HJSE>6</HJSE> \r\n" + 
//        		"  <KCE></KCE>\r\n" + 
//        		"  <BZ>备注</BZ>  \r\n" + 
//        		"  <HYLX>0</HYLX>\r\n" + 
//        		"  <BY1>1</BY1>  \r\n" + 
//        		"  <BY2></BY2>  \r\n" + 
//        		"  <BY3></BY3>  \r\n" + 
//        		"  <BY4></BY4>  \r\n" + 
//        		"  <BY5></BY5>  \r\n" + 
//        		"  <BY6></BY6>  \r\n" + 
//        		"  <BY7></BY7>  \r\n" + 
//        		"  <BY8></BY8>  \r\n" + 
//        		"  <BY9></BY9>  \r\n" + 
//        		"  <BY10></BY10>  \r\n" + 
//        		"  <WX_ORDER_ID></WX_ORDER_ID>\r\n" + 
//        		"  <WX_APP_ID></WX_APP_ID>\r\n" + 
//        		"  <ZFB_UID></ZFB_UID>\r\n" + 
//        		"  <TSPZ>00</TSPZ>\r\n" + 
//        		"  <QJ_ORDER_ID></QJ_ORDER_ID>\r\n" + 
//        		"  <WX_GROUP_ID></WX_GROUP_ID>\r\n" + 
//        		"<COMMON_FPKJ_XMXXS class=\"COMMON_FPKJ_XMXX\" size=\"1\">\r\n" + 
//        		"   <COMMON_FPKJ_XMXX> \r\n" + 
//        		"      <FPHXZ>0</FPHXZ>\r\n" + 
//        		"      <SPBM>3070401000000000000</SPBM>	\r\n" + 
//        		"      <ZXBM></ZXBM>	 \r\n" + 
//        		"      <YHZCBS></YHZCBS>\r\n" + 
//        		"      <LSLBS></LSLBS>\r\n" + 
//        		"      <ZZSTSGL></ZZSTSGL>	  \r\n" + 
//        		"      <XMMC>餐饮服务</XMMC>  \r\n" + 
//        		"      <GGXH></GGXH>  \r\n" + 
//        		"      <DW></DW>  \r\n" + 
//        		"      <XMSL>2</XMSL>\r\n" + 
//        		"      <XMDJ>50.000000</XMDJ>\r\n" + 
//        		"      <XMJE>100.00</XMJE>\r\n" + 
//        		"      <SL>0.06</SL>\r\n" + 
//        		"      <SE>6</SE>\r\n" + 
//        		"      <BY1></BY1>  \r\n" + 
//        		"      <BY2></BY2>  \r\n" + 
//        		"      <BY3></BY3>  \r\n" + 
//        		"      <BY4></BY4>  \r\n" + 
//        		"      <BY5></BY5>  \r\n" + 
//        		"    </COMMON_FPKJ_XMXX>\r\n" + 
//        		"  </COMMON_FPKJ_XMXXS> \r\n" + 
//        		"</REQUEST_COMMON_FPKJ>\r\n" + 
//        		"\r\n" + 
//        		"";
//       
      
        log.info("请求xml内容:"+content);
        content= new BASE64Encoder().encodeBuffer(content.toString().getBytes("UTF-8"));
        content=content.replaceAll("\r\n", "").replaceAll("\n", "") ;
        String intefaceCode = "DFXJ1001"; 
		return packageInfo(intefaceCode, content, business);
	}
	
	
	public static String toXml_FPCX_Electron( Map<String,String> map ,BusinessVo business) throws Exception {
		if(map==null) {
        	return null;
        }
        String prefix ="<REQUEST_COMMON_FPCX class=\"REQUEST_COMMON_FPCX\">";
        String suffix ="</REQUEST_COMMON_FPCX>";
        String body = CommonUtils.map2Xml(map); 
        String content =prefix+body+suffix;
        log.info("请求xml内容:"+content);
        content= new BASE64Encoder().encodeBuffer(content.toString().getBytes("UTF-8")); 
        content=content.replaceAll("\r\n", "").replaceAll("\n", "") ;
		
        String intefaceCode = "DFXJ1004";
        
		return packageInfo(intefaceCode, content, business);  
	}
	
	
	public static String toXml_FPKCCX_Electron( Map<String,String> map ,BusinessVo business) throws Exception {
		if(map==null) {
        	return null;
        }
        String prefix ="<REQUEST_COMMON_FPKCCX class=\"REQUEST_COMMON_FPKCCX\">";
        String suffix ="</REQUEST_COMMON_FPKCCX>";
        String body = CommonUtils.map2Xml(map); 
        String content =prefix+body+suffix;
        log.info("请求xml内容:"+content);
        content= new BASE64Encoder().encodeBuffer(content.toString().getBytes("UTF-8")); 
        content=content.replaceAll("\r\n", "").replaceAll("\n", "") ;
        String intefaceCode = "DFXJ1003";
		return packageInfo(intefaceCode, content, business);  
	}
	
	private static String packageInfo(String intefaceCode,String content,Business business) throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = dateFormat.format(new Date()); 
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyyMMdd");
		String data8 = dateFormat2.format(new Date()); 
		String requestCode="DZFPQZ";
		if(business.getAppSecret()==null) {
			throw new ServiceException("秘钥不能为空");
		}
		String contentMD5 = MyAES.MD5(content.getBytes("UTF-8"));//对content数据进行MD5加密
		 log.info("秘钥:"+business.getAppSecret());
		String contentKey = MyAES.encryptBASE64(MyAES.encrypt(contentMD5.getBytes("UTF-8"), business.getAppSecret())).replaceAll("\r\n", "").replaceAll("\n", "");//对md5后的数据进行AES加密
		     
		//数据交换流水号 
		String dataExchangeId=requestCode+intefaceCode +data8+UUID.randomUUID().toString().substring(0, 9); 
		
		String req="<?xml version='1.0' encoding='UTF-8'?> <interface xmlns=\"\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:schemaLocation=\"http://www.chinatax.gov.cn/tirip/dataspec/interfaces.xsd\" version=\"DZFPQZ0.2\">"
				+ " <globalInfo> <appId>"+business.getAppId()+"</appId> <interfaceId></interfaceId>"
				+ "<interfaceCode>"+intefaceCode+"</interfaceCode> "
						+ "<requestCode>"+requestCode+"</requestCode> "
				+ "<requestTime>"+time+"</requestTime>"
				+ " <responseCode>DS</responseCode> "
				+ "<dataExchangeId>"+dataExchangeId+"</dataExchangeId> "
				+ "</globalInfo> <returnStateInfo> <returnCode></returnCode> "
				+ "<returnMessage></returnMessage> </returnStateInfo> <Data><dataDescription> "
				+ "<zipCode>0</zipCode> </dataDescription> "
				+ "<content>"+content+"</content> "
				+ "<contentKey>"+contentKey+"</contentKey> "
				+ "</Data> </interface>";
 
		return req;
	}
	
	
	
	// 将 BASE64 编码的字符串 s 进行解码
    public static String getFromBASE64(String s) {  
        if (s == null)  
            return null;  
        sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();  
        try {  
            byte[] b = decoder.decodeBuffer(s);  
            return new String(b);  
        } catch (Exception e) {  
            return null;  
        }  
    }  
 
}
