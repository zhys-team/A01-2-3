package top.anets.utils.guoXin;

import java.io.UnsupportedEncodingException;
import java.util.Date;

 

import sun.misc.BASE64Encoder;

public class JsonUtils {

	/**
	 * 拼通用报文(json)
	 * 
	 * @return
	 * @throws Exception 
	 */

	public static String getSendToTaxJson(String interfaceCode, String fpqqlsh,String nsrsbh,String appid,String contentPassword, String skp_no, String callback_url,String fpdm,String fphm)
			throws Exception {
		String content = "";
		StringBuffer sb = new StringBuffer("");
		
		sb.append("{");
		sb.append("\"interface\": {");
		sb.append("\"globalInfo\": {");
		sb.append("\"appId\": \"").append(appid).append("\","); 
		sb.append("\"interfaceId\": \"\","); 
		sb.append("\"interfaceCode\": \"").append(interfaceCode).append("\","); 
		sb.append("\"requestCode\": \"DZFPQZ\",");
		sb.append("\"requestTime\": \"").append(new Date()).append("\","); 
		sb.append("\"responseCode\": \"Ds\","); 
		sb.append("\"dataExchangeId\": \"").append("DZFPQZ").append(interfaceCode).append(Utils.formatToDay()).append( Utils.randNineData()).append( "\"");
		sb.append("},"); 
		sb.append("\"returnStateInfo\": {");
		sb.append("\"returnCode\": \"\","); 
		sb.append("\"returnMessage\": \"\"");
		sb.append("},"); 
		sb.append("\"Data\": {");
		sb.append("\"dataDescription\": {");
		sb.append("\"zipCode\": \"0\"");
		sb.append("},"); 
		sb.append("\"content\": \"");
		if (interfaceCode.equals(Utils.dfxj1001)) {
			content = getUploadContent(fpqqlsh,skp_no,nsrsbh, callback_url);
		} 
		else if (interfaceCode.equals(Utils.dfxj1004)) {
			content = getSearchContent(nsrsbh,fpqqlsh);
		}
		else if (interfaceCode.equals(Utils.dfxj1005)) {
			content = getPaperInvoiceCancelContent(nsrsbh, callback_url, skp_no, fpdm, fphm);
		}
		content  = content.replace("\r\n", "").replace("\n", "").replace("\r", "");//json的报文不允许有换行，base64会产生。因此此处做去换行处理。
		sb.append(content).append("\",");
		sb.append("\"contentKey\":\"");
		String contentMD5 = MyAES.MD5(content.getBytes("UTF-8"));
		String contentKey = MyAES.encryptBASE64(MyAES.encrypt(contentMD5.getBytes("UTF-8"), contentPassword)).replaceAll("\r\n", "").replaceAll("\n", "").replace("\r", "");
		sb.append(contentKey).append("\"");;
		sb.append("}");
		sb.append("}");
		sb.append("}");
		return sb.toString();
	}
	
	/**
	 * 根据加密上传发票内容报文（发票开具）
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getUploadContent(String fpqqlsh,String skp_no,String nsrsbh, String callback_url) throws UnsupportedEncodingException {
		StringBuffer content = new StringBuffer("{");
		content.append("\"REQUEST_COMMON_FPKJ\": {");
		content.append("\"CALLBACK_URL\":\""+callback_url+"\",");
		content.append("\"CALLBACK_VERSION\":\"V1.1\",");
		content.append("\"SKP_NO\":\"" +skp_no + "\",");
		content.append("\"SKP_LX\": \"1\",");
		content.append("\"FPQQLSH\": \"" + fpqqlsh + ""+ "\",");
		content.append("\"FPLXDM\": \"026\",");
		content.append("\"ZSFS\": \"0\",");
		content.append("\"KPLX\": \"0\",");
		content.append("\"XSF_NSRSBH\": \""+nsrsbh+"\",");
		content.append("\"XSF_MC\": \"升级版测试用户4011\",");
		content.append("\"XSF_DZDH\": \"销售方地址、 电话\",");
		content.append("\"XSF_YHZH\": \"销售方银行账号\",");
		content.append("\"GMF_NSRSBH\": \"\",");
		content.append("\"GMF_MC\": \"购买方名称\",");
		content.append("\"GMF_DZDH\": \"购买方地址、电话\",");//测试环境电子发票开具时传入邮件，开具成功后会推送邮件
		content.append("\"GMF_YHZH\": \"购买方银行账号\",");
		content.append("\"GMF_SJH\": \"\",");
		content.append("\"GMF_DZYX\": \"\",");
		content.append("\"FPT_ZH\": \"\",");
		content.append("\"WX_OPENID\": \"\",");
		content.append("\"KPR\": \"开票人\",");
		content.append("\"SKR\": \"收款人\",");
		content.append("\"FHR\": \"复核人\",");
		content.append("\"YFP_DM\": \"\",");
		content.append("\"YFP_HM\": \"\",");
		content.append("\"JSHJ\": \"2.22\",");
		content.append("\"HJJE\": \"2\",");
		content.append("\"HJSE\": \"0.22\",");
		content.append("\"KCE\": \"\",");
		content.append("\"BZ\": \"json测试开票备注\",");
		content.append("\"BY1\": \"\",");
		content.append("\"BY2\": \"\",");
		content.append("\"BY3\": \"\",");
		content.append("\"BY4\": \"\",");
		content.append("\"BY5\": \"\",");
		content.append("\"BY6\": \"\",");
		content.append("\"BY7\": \"\",");
		content.append("\"BY8\": \"\",");
		content.append("\"BY9\": \"\",");
		content.append("\"BY10\": \"\",");
		content.append("\"WX_ORDER_ID\": \"\",");
		content.append("\"WX_APP_ID\": \"\",");
		content.append("\"ZFB_UID\": \"\",");
		content.append("\"TSPZ\": \"00\",");
		content.append("\"QJ_ORDER_ID\": \"\",");
		content.append("\"COMMON_FPKJ_XMXXS\": {");
		content.append("\"COMMON_FPKJ_XMXX\": [{");
		content.append("\"FPHXZ\": \"0\",");
		content.append("\"SPBM\": \"1020101000000000000\",");
		content.append("\"ZXBM\": \"\",");
		content.append("\"YHZCBS\": \"\",");
		content.append("\"LSLBS\": \"\",");
		content.append("\"ZZSTSGL\": \"\",");
		content.append("\"XMMC\": \"原煤\",");
		content.append("\"GGXH\": \"规格型号\",");
		content.append("\"DW\": \"单位\",");
		content.append("\"XMSL\": \"1\",");
		content.append("\"XMDJ\": \"1\",");
		content.append("\"XMJE\": \"1\",");
		content.append("\"SL\": \"0.13\",");
		content.append("\"SE\": \"0.13\"},");
		content.append("{");
		content.append("\"FPHXZ\": \"0\",");
		content.append("\"SPBM\": \"1010101020000000000\",");
		content.append("\"ZXBM\": \"\",");
		content.append("\"YHZCBS\": \"\",");
		content.append("\"LSLBS\": \"\",");
		content.append("\"ZZSTSGL\": \"\",");
		content.append("\"XMMC\": \"小麦\",");
		content.append("\"GGXH\": \"规格型号\",");
		content.append("\"DW\": \"单位\",");
		content.append("\"XMSL\": \"1\",");
		content.append("\"XMDJ\": \"1\",");
		content.append("\"XMJE\": \"1\",");
		content.append("\"SL\": \"0.09\",");
		content.append("\"SE\": \"0.09\"}]");
		content.append("}");
		content.append("}");
		content.append("}");
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
		StringBuffer content = new StringBuffer("{");
		content.append("\"REQUEST_COMMON_FPCX\":{");
		content.append("\"FPQQLSH\":\"").append(fpqqlsh).append("\",");
		content.append("\"XSF_NSRSBH\":\"").append(nsrsbh).append("\"}}");
		return new BASE64Encoder().encodeBuffer(content.toString().getBytes("UTF-8"));
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
		StringBuffer content = new StringBuffer("{");
		content.append("\"REQUEST_COMMON_YKFPZF\":{");
		content.append("\"CALLBACK_URL\":\"").append(callback_url).append("\",");
		content.append("\"CALLBACK_VERSION\":\"V1.1\",");
		content.append("\"NSRSBH\":\"").append(nsrsbh).append("\",");
		content.append("\"SKPBH\":\"").append(skpno).append("\",");
		content.append("\"FPLXDM\":\"007\",");
		content.append("\"ZFLX\":\"3\",");
		content.append("\"FPDM\":\"").append(fpdm).append("\",");
		content.append("\"FPHM\":\"").append(fphm).append("\",");
		content.append("\"HJJE\":\"2\",");
		content.append("\"ZFR\":\"作废人\"}}");
		return new BASE64Encoder().encodeBuffer(content.toString().getBytes("UTF-8"));
	}
}
