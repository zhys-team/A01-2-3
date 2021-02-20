/**
 * 
 */
package top.anets;

import java.util.Date;

import top.anets.utils.guoXin.JsonUtils;
import top.anets.utils.guoXin.RequestUtils;
import top.anets.utils.guoXin.Utils;
import top.anets.utils.guoXin.XmlUtils;

/**
 * @author Administrator
 *
 */
public class GuoXinTest {
	public static void main(String[] args) throws Exception {
		Date startDate = new Date();//初始化开始时间
		// 初始化参数
		String requestData = null;//初始化请求报文
		String rsData = null;//初始化结果报文
		String requestMethod = null;//初始化请求方法
		String requestUrlMethod = null;//初始化连接方法
		
		String requestUrl = "https://dev.fapiao.com:18944/fpt-cloudservice/";//初始化地址
		String appid = "667bf7c79996c7a507af365a8f340a6f9009c519d28a59ae3be23cc86c2e1630";//appid
		String contentPassword = "F6AD954A498AEDBE";//AES加密密钥
		String fpqqlsh = "TEST2019041715000001";// 需要查询发票的流水号
		String nsrsbh = "500102010004011";
		String skp_no = "499000138208";////税控盘盘号
		
		String fpdm = "050000000001";//发票代码
		String fphm = "61025346";//发票号码
		String callback_url = "http://192.168.74.128:8080/cloudserviceTest/callBackDemoAction";//回调接口地址
		// 通过注释选择语言
		String interfaceLau = Utils.interfaceLau_xml;
//		String interfaceLau = Utils.interfaceLau_json;

		// 通过注释选择接口
		 String interfaceCode = Utils.dfxj1001;//开具
//		 String interfaceCode = Utils.dfxj1004;//查询
//		 String interfaceCode = Utils.dfxj1005;//发票已开作废
		if(Utils.dfxj1001.equals(interfaceCode)){
			callback_url = callback_url+"?method=FPKJ";//拼接开具的方法名
		}else if(Utils.dfxj1005.equals(interfaceCode)){
			callback_url = callback_url+"?method=FPZF";//拼接作废的方法名
		}
		//通过注释选择调用方式
//		String requestInterface = Utils.webservice_axis;//请求方式使用axis的webservice
//		String requestInterface = Utils.webservice_xfire;//请求方式使用xfire的webservice		
		String requestInterface = Utils.post_https;//使用post请求方式
		
		// 组装请求报文
		if (Utils.interfaceLau_xml.equals(interfaceLau)) {
			requestData = XmlUtils.getSendToTaxXML(interfaceCode, fpqqlsh, nsrsbh, appid,contentPassword, skp_no, callback_url,fpdm,fphm);
			System.out.println("===xml=="+requestData);
			requestMethod = Utils.requestMethod_xml;//xml的请求方法
			requestUrlMethod = Utils.requestUrlMethod_xml;//xml的连接后缀
		} else if (Utils.interfaceLau_json.equals(interfaceLau)) {
			requestData = JsonUtils.getSendToTaxJson(interfaceCode, fpqqlsh, nsrsbh,appid,contentPassword, skp_no, callback_url,fpdm,fphm);
			requestMethod = Utils.requestMethod_json;//json的请求方法
			requestUrlMethod = Utils.requestUrlMethod_json;//json的连接后缀
		} else{
			System.out.println("请选择语言！");
		}
		
		System.out.println("组装报文完毕,请求使用的语言是:"+interfaceLau+",请求的方式是："+requestInterface+",请求报文为:"+requestData+",开始请求。");
		Date requestStartDate = new Date();//初始化请求开始时间
		// 调用接口
		if(Utils.webservice_axis.equals(requestInterface)){
			rsData = RequestUtils.webServiceAxis(requestData,requestMethod,requestUrl+requestUrlMethod);
			System.out.println("-->"+requestUrl+requestUrlMethod);
		}else if(Utils.webservice_xfire.equals(requestInterface)){
			rsData = RequestUtils.webServiceXfile(requestData, requestMethod, requestUrl+requestUrlMethod+"?wsdl");
			System.out.println("-->"+requestUrl+requestUrlMethod+"?wsdl");
		}else if(Utils.post_https.equals(requestInterface)){
			System.out.println("-->"+requestData);
			rsData = RequestUtils.getHttpConnectResult(requestData,requestUrl+"invoice");
		}
		Date requestEndDate = new Date();//初始化请求结束时间
		System.out.println("请求完毕，耗时【"+(requestEndDate.getTime()-requestStartDate.getTime())+"ms】");
		Date endDate = new Date();//初始化结束时间
		System.out.println("请求接口结束，获得结果:" + rsData);
		System.out.println("总耗时【"+(endDate.getTime()-startDate.getTime())+"ms】");
	}
} 
