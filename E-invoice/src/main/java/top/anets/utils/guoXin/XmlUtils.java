package top.anets.utils.guoXin;

import java.io.UnsupportedEncodingException;
import java.util.Date;
 

import sun.misc.BASE64Encoder;

public class XmlUtils {
	/**
	 * 拼通用报文(xml)
	 * 
	 * @return
	 * @throws Exception 
	 */

	public static String getSendToTaxXML(String interfaceCode, String fpqqlsh,String nsrsbh,String appid,String contentPassword, String skp_no, String callback_url,String fpdm,String fphm)
			throws Exception {
		String content = "";
		StringBuffer sb = new StringBuffer("");
		sb.append("<?xml version='1.0' encoding='UTF-8' ?>");
		sb.append("<interface xmlns=\"\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:schemaLocation=\"http://www.chinatax.gov.cn/tirip/dataspec/interfaces.xsd\" version=\"DZFPQZ0.2\"> ");
		sb.append("<globalInfo>");
		sb.append("<appId>").append(appid).append("</appId>");
		sb.append("<interfaceId></interfaceId>");
		sb.append("<interfaceCode>").append(interfaceCode).append("</interfaceCode>");
		sb.append("<requestCode>DZFPQZ</requestCode>");
		sb.append("<requestTime>").append(new Date()).append("</requestTime>");
		sb.append("<responseCode>Ds</responseCode>");
		sb.append("<dataExchangeId>").append("DZFPQZ").append(interfaceCode).append(Utils.formatToDay()).append(Utils.randNineData()).append("</dataExchangeId>");
		sb.append("</globalInfo>");
		sb.append("<returnStateInfo>");
		sb.append("<returnCode></returnCode>");
		sb.append("<returnMessage></returnMessage>");
		sb.append("</returnStateInfo>");
		sb.append("<Data>");
		sb.append("<dataDescription>");
		sb.append("<zipCode>0</zipCode>");
		sb.append("</dataDescription>");
		sb.append("<content>");
		if (interfaceCode.equals(Utils.dfxj1001)) {
			content = getUploadContent(fpqqlsh,skp_no,nsrsbh, callback_url);
		} 
		else if (interfaceCode.equals(Utils.dfxj1004)) {
			
			content = getSearchContent(nsrsbh,fpqqlsh);
			
		}else if (interfaceCode.equals(Utils.dfxj1005)) {
			content = getPaperInvoiceCancelContent(nsrsbh, callback_url, skp_no, fpdm, fphm);
		}
		content = content.replaceAll("\r\n", "").replaceAll("\n", "");//去掉空格和换行
		sb.append(content);
		sb.append("</content>");
		sb.append("<contentKey>");
		String contentMD5 = MyAES.MD5(content.getBytes("UTF-8"));//对content数据进行MD5加密
		String contentKey = MyAES.encryptBASE64(MyAES.encrypt(contentMD5.getBytes("UTF-8"), contentPassword)).replaceAll("\r\n", "").replaceAll("\n", "");//对md5后的数据进行AES加密
		sb.append(contentKey);
		sb.append("</contentKey>");
		sb.append("</Data>");
		sb.append("</interface>");
		return sb.toString();
	}
	
	/**
	 * 根据加密上传发票内容报文（发票开具）
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getUploadContent(String fpqqlsh,String skp_no,String nsrsbh, String callback_url) throws UnsupportedEncodingException {
		StringBuffer content = new StringBuffer("");
		content.append("<REQUEST_COMMON_FPKJ class=\"REQUEST_COMMON_FPKJ\">");
		content.append("<CALLBACK_URL>"+callback_url+"</CALLBACK_URL>");////回调地址，推送开票结果
		content.append("<CALLBACK_VERSION>V1.1</CALLBACK_VERSION>");//回调版本号
		content.append("<SKP_NO>").append(skp_no).append("</SKP_NO>");
		content.append("<FPQQLSH>"+fpqqlsh+ "</FPQQLSH>");
		content.append("<FPLXDM>007</FPLXDM>");
		content.append("<KPLX>0</KPLX>");
		content.append("<ZSFS>0</ZSFS>");
		content.append("<XSF_NSRSBH>"+nsrsbh+"</XSF_NSRSBH>");
		content.append("<XSF_MC>升级版测试用户4011</XSF_MC>");
		content.append("<XSF_DZDH>销售方地址、 电话</XSF_DZDH>");
		content.append("<XSF_YHZH>销售方银行账号</XSF_YHZH>");
		content.append("<GMF_NSRSBH></GMF_NSRSBH>");
		content.append("<GMF_MC>购买方名称</GMF_MC>");
		content.append("<GMF_DZDH>购买方地址、 电话</GMF_DZDH>");
		content.append("<GMF_YHZH>购买方银行账号</GMF_YHZH>");
		content.append("<GMF_SJH></GMF_SJH>");
		content.append("<GMF_DZYX></GMF_DZYX>");//测试环境电子发票开具时传入邮件，开具成功后会推送邮件
		content.append("<FPT_ZH></FPT_ZH>");
		content.append("<WX_OPENID></WX_OPENID>");
		content.append("<KPR>开票人</KPR>");
		content.append("<SKR>收款人</SKR>");
		content.append("<FHR>复核人</FHR>");
		content.append("<YFP_DM></YFP_DM>");
		content.append("<YFP_HM></YFP_HM>");
		content.append("<JSHJ>2.22</JSHJ>");
		content.append("<HJJE>2</HJJE>");
		content.append("<HJSE>0.22</HJSE>");
		content.append("<KCE></KCE>");
		content.append("<BZ>备注</BZ>");
		content.append("<WX_ORDER_ID></WX_ORDER_ID>");
		content.append("<WX_APP_ID></WX_APP_ID>");
		content.append("<ZFB_UID></ZFB_UID>");
		content.append("<TSPZ>00</TSPZ>");
		content.append("<QJ_ORDER_ID></QJ_ORDER_ID>");
		content.append("<WX_GROUP_ID></WX_GROUP_ID>");
		content.append("<COMMON_FPKJ_XMXXS class=\"COMMON_FPKJ_XMXX\" size=\"2\">");
		content.append("<COMMON_FPKJ_XMXX>");
		content.append("<FPHXZ>0</FPHXZ>");
		content.append("<SPBM>1020101000000000000</SPBM>");
		content.append("<ZXBM></ZXBM>");
		content.append("<YHZCBS>0</YHZCBS>");
		content.append("<LSLBS></LSLBS>");
		content.append("<ZZSTSGL></ZZSTSGL>");
		content.append("<XMMC>原煤</XMMC>");
		content.append("<GGXH>规格型号</GGXH>");
		content.append("<DW>单位</DW>");
		content.append("<XMSL>1</XMSL>");
		content.append("<XMDJ>1</XMDJ>");
		content.append("<XMJE>1</XMJE>");
		content.append("<SL>0.13</SL>");
		content.append("<SE>0.13</SE>");
		content.append("</COMMON_FPKJ_XMXX>");
		content.append("<COMMON_FPKJ_XMXX>");
		content.append("<FPHXZ>0</FPHXZ>");
		content.append("<SPBM>1010101020000000000</SPBM>");
		content.append("<ZXBM></ZXBM>");
		content.append("<YHZCBS>0</YHZCBS>");
		content.append("<LSLBS></LSLBS>");
		content.append("<ZZSTSGL></ZZSTSGL>");
		content.append("<XMMC>小麦</XMMC>");
		content.append("<GGXH>规格型号</GGXH>");
		content.append("<DW>单位</DW>");
		content.append("<XMSL>1</XMSL>");
		content.append("<XMDJ>1</XMDJ>");
		content.append("<XMJE>1</XMJE>");
		content.append("<SL>0.09</SL>");
		content.append("<SE>0.09</SE>");
		content.append("</COMMON_FPKJ_XMXX>");
		content.append("</COMMON_FPKJ_XMXXS>");
		content.append("</REQUEST_COMMON_FPKJ>");
		return  new BASE64Encoder().encodeBuffer(content.toString().getBytes("UTF-8"));
	}
	
	/**
	 * 获取加密查询报文内容(发票查询报文)
	 * 
	 * @param fpqqlsh
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getSearchContent(String nsrsbh ,String fpqqlsh)
			throws UnsupportedEncodingException {
		StringBuffer sb = new StringBuffer("");
		sb.append("<REQUEST_COMMON_FPCX class='REQUEST_COMMON_FPCX'>");
		sb.append("<FPQQLSH>" + fpqqlsh + "</FPQQLSH>");
		sb.append("<XSF_NSRSBH>" + nsrsbh + "</XSF_NSRSBH>");
		sb.append("</REQUEST_COMMON_FPCX>");
		return new BASE64Encoder().encodeBuffer(sb.toString().getBytes("UTF-8"));
	}

	
	
	/**
	 * 获取加密查询报文内容(纸票已开作废)
	 * @param nsrsbh
	 * @param fpqqlsh
	 * @param fpdm
	 * @param fphm
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getPaperInvoiceCancelContent(String nsrsbh,String callback_url,String skpno,String fpdm,String fphm)
			throws UnsupportedEncodingException {
		StringBuffer sb = new StringBuffer("");
		sb.append("<REQUEST_COMMON_YKFPZF class=\"REQUEST_COMMON_YKFPZF\">");
		sb.append("<CALLBACK_URL>"+callback_url+"</CALLBACK_URL>");
		sb.append("<CALLBACK_VERSION>V1.1</CALLBACK_VERSION>");
		sb.append("<NSRSBH>"+nsrsbh+"</NSRSBH>");
		sb.append("<SKPBH>"+skpno+"</SKPBH>");
		sb.append("<FPLXDM>007</FPLXDM>");
		sb.append("<ZFLX>3</ZFLX>");
		sb.append("<FPDM>"+fpdm+"</FPDM>");
		sb.append("<FPHM>"+fphm+"</FPHM>");
		sb.append("<HJJE>2</HJJE>");
		sb.append("<ZFR>作废人</ZFR>");
		sb.append("</REQUEST_COMMON_YKFPZF>");
		return new BASE64Encoder().encodeBuffer(sb.toString().getBytes("UTF-8"));
	}
}
