package com.zhys.bw;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.dom4j.DocumentException;


public class Fpcy {

	private static String client_id = "152877251001";
    private static String client_secret = "b8c37e33defde51cf91e1e03e51657da";
    private static String sqm = "1c65cef3dfd1e00c0b03923a1c591db4";
    private static String Client = "杭州中航云税科技有限公司"; //公司名称
    private static String GTAXID = "91330105MA2B0AD277";//公司税号
	
    public static void main(String[] args) {
    	//testFpcy0("21A856F2CEC54EFB92A2C43337189569","C4B6AEB83A44F635857236DEE52C4136");
    	//testFpcy1("B61C246C59347A7EEFB626FA56A91C52","1BC0EE3E7BED396EF08E49FF1569DC85");
    	//getToken();//B61C246C59347A7EEFB626FA56A91C52
    	//getOpenId("B61C246C59347A7EEFB626FA56A91C52");1BC0EE3E7BED396EF08E49FF1569DC85
    	
    	// testFpcy2("3300183130","26023304","B61C246C59347A7EEFB626FA56A91C52","1BC0EE3E7BED396EF08E49FF1569DC85");
    	 testFpcy("3700173130","09157113","20191023","2858.49","","F84D07A120EA146617570235E77E6D04","ECC344D063D7DF1BBC8A997E2BC5ACC6");
    }
    
    public static String testFpcy(String InvoiceCode,String InvoiceNumber,String BillingDate,String TotalAmount,String CheckCode_6,String accessToken,String openId) {
        //Map<String, String> mapp = getResult();//access_token 与 openID 存在缓存，可以失效时在调用此方法获取
        Map<String, Object> f1 = new HashMap<String, Object>();
        Map<String, Object> f2 = new HashMap<String, Object>();
        Map<String, Object> f4 = new HashMap<String, Object>();
        f4.put("Client", Client);
        f4.put("Time", new Date());
        f4.put("GTAXID", GTAXID);
        Map<String, String> invMap1 = new HashMap<String, String>();

        invMap1.put("InvoiceCode", InvoiceCode);
        invMap1.put("InvoiceNumber",InvoiceNumber );
        invMap1.put("BillingDate", BillingDate);
        invMap1.put("TotalAmount", TotalAmount);
        invMap1.put("CheckCode_6", CheckCode_6);

        f2.put("BODY", invMap1);
        f2.put("HEAD", f4);
        f1.put("REQUEST", f2);

        String url = "https://www.fapiao.com/Entoauth/thirdApi";
        Map<String, String> params = new HashMap<String, String>();
        params.put("sign", "getSecondAllQuery");
        params.put("client_id", client_id);
        params.put("sqm", sqm);
        params.put("dataType", "0");
        params.put("busiType", "vat_income");
        params.put("access_token", accessToken);
        params.put("openID", openId);
        params.put("data", Base64Util.getBase64(JSONObject.fromObject(f1).toString()));
        
        Map<String, Object> str = HttpUtil.post(url, params);
        String result = str.get("response").toString();
        //System.out.println(params);
        System.out.println("=============" + result);
        return result;
    }
    
    
    public static String testFpcy0(String accessToken,String openId) {
        //Map<String, String> mapp = getResult();//access_token 与 openID 存在缓存，可以失效时在调用此方法获取
        Map<String, Object> f1 = new HashMap<String, Object>();
        Map<String, Object> f2 = new HashMap<String, Object>();
        Map<String, Object> f4 = new HashMap<String, Object>();
        f4.put("Client", Client);
        f4.put("Time", new Date());
        f4.put("GTAXID", GTAXID);
        Map<String, String> invMap1 = new HashMap<String, String>();

        invMap1.put("TaxNo", GTAXID);
        invMap1.put("Action","zpsjtb" );
        invMap1.put("SyncType", "01");
        invMap1.put("SyncCondition", "2019-02-27");

        f2.put("BODY", invMap1);
        f2.put("HEAD", f4);
        f1.put("REQUEST", f2);

        String url = "https://www.fapiao.com/Entoauth/thirdApi";
        Map<String, String> params = new HashMap<String, String>();
        params.put("sign", "FW_DZDZK_SERVICE");
        params.put("client_id", client_id);
        params.put("sqm", sqm);
        params.put("dataType", "0");
        params.put("busiType", "vat_income");
        params.put("access_token", accessToken);
        params.put("openID", openId);
        String ss = Base64Util.getBase64(JSONObject.fromObject(f1).toString());
        params.put("data", ss);
        
        Map<String, Object> str = HttpUtil.post(url, params);
        String result = str.get("response").toString();
        //System.out.println(params);
        System.out.println("=============" + result);
        //{"BODY":{"Period":"201902","InvoiceList":[{"BillingDate":"2019-02-27","DeductiblePeriod":"201902","PurchaserTaxNo":"91330105MA2B0AD277","Deductible":"1","InvoiceNumber":"26023304","DeductibleDate":"2019-03-12","PurchaserName":"杭州中航云税科技有限公司","SalesTaxNo":"91330105MA27X4A44R","InvoiceType":"01","InvoiceCode":"3300183130","State":"0","TotalTax":2066.04,"SalesTaxName":"杭州鑫泰科技有限公司","TotalAmount":34433.96}],"DateFrameBegin":"2018-02-06","DateFrameEnd":"2019-02-28","OperationDateEnd":"2019-03-15","Msg":"数据同步成功","InvoiceQuantity":1,"Code":"0000"},"ROOT":{"SERVICE":{"REPLYCODE":"1000","REPLYMSG":"接口调用成功"}}}
        return result;
    }
    
    public static String testFpcy1(String accessToken,String openId) {
        //Map<String, String> mapp = getResult();//access_token 与 openID 存在缓存，可以失效时在调用此方法获取
        Map<String, Object> f1 = new HashMap<String, Object>();
        Map<String, Object> f2 = new HashMap<String, Object>();
        Map<String, Object> f4 = new HashMap<String, Object>();
        f4.put("Client", Client);
        f4.put("Time", new Date());
        f4.put("GTAXID", GTAXID);
        Map<String, String> invMap1 = new HashMap<String, String>();

        invMap1.put("TaxNo", GTAXID);
        invMap1.put("Action","zpsjtb" );
        invMap1.put("SyncType", "02");
        invMap1.put("SyncCondition", "2019-08");

        f2.put("BODY", invMap1);
        f2.put("HEAD", f4);
        f1.put("REQUEST", f2);

        String url = "https://www.fapiao.com/Entoauth/thirdApi";
        Map<String, String> params = new HashMap<String, String>();
        params.put("sign", "FW_DZDZK_SERVICE");
        params.put("client_id", client_id);
        params.put("sqm", sqm);
        params.put("dataType", "0");
        params.put("busiType", "vat_income");
        params.put("access_token", accessToken);
        params.put("openID", openId);
        String ss = Base64Util.getBase64(JSONObject.fromObject(f1).toString());
        params.put("data", ss);
        
        Map<String, Object> str = HttpUtil.post(url, params);
        String result = str.get("response").toString();
        //System.out.println(params);
        System.out.println("=============" + result);
        //{"BODY":{"Period":"201902","InvoiceList":[{"BillingDate":"2019-02-27","PurchaserTaxNo":"91330105MA2B0AD277","Deductible":"0","InvoiceNumber":"26023304","DeductibleDate":"","PurchaserName":"杭州中航云税科技有限公司","SalesTaxNo":"91330105MA27X4A44R","InvoiceType":"01","InvoiceCode":"3300183130","State":"0","TotalTax":2066.04,"SalesTaxName":"杭州鑫泰科技有限公司","TotalAmount":34433.96}],"DateFrameBegin":"2018-02-06","DateFrameEnd":"2019-02-28","OperationDateEnd":"2019-03-15","Msg":"数据同步成功","InvoiceQuantity":1,"Code":"0000"},"ROOT":{"SERVICE":{"REPLYCODE":"1000","REPLYMSG":"接口调用成功"}}}
        return result;
    }
    
    
    //认证
    public static String testFpcy2(String InvoiceCode,String InvoiceNumber,String accessToken,String openId) {
        //Map<String, String> mapp = getResult();//access_token 与 openID 存在缓存，可以失效时在调用此方法获取
        Map<String, Object> f1 = new HashMap<String, Object>();
        Map<String, Object> f2 = new HashMap<String, Object>();
        Map<String, Object> f4 = new HashMap<String, Object>();
        f4.put("Client", Client);
        f4.put("Time", new Date());
        f4.put("GTAXID", GTAXID);
        Map<String, Object> invMap1 = new HashMap<String, Object>();
        List<Map<String, String>> lm = new ArrayList<Map<String, String>>();
        Map<String, String> m1 = new HashMap<>();
        m1.put("InvoiceCode", InvoiceCode);
        m1.put("InvoiceNumber", InvoiceNumber);
        //m1.put("PurchaserTaxNo", "01");
        lm.add(m1);
        invMap1.put("TaxNo", GTAXID);
        invMap1.put("Action","zprz" );
        invMap1.put("DeductibleMode", "1");
        invMap1.put("UserType", "01");
        invMap1.put("Period", "201908");
        invMap1.put("InvoiceList", lm);

        f2.put("BODY", invMap1);
        f2.put("HEAD", f4);
        f1.put("REQUEST", f2);

        String url = "https://www.fapiao.com/Entoauth/thirdApi";
        Map<String, String> params = new HashMap<String, String>();
        params.put("sign", "FW_DZDZK_SERVICE");
        params.put("client_id", client_id);
        params.put("sqm", sqm);
        params.put("dataType", "0");
        params.put("busiType", "vat_income");
        params.put("access_token", accessToken);
        params.put("openID", openId);
        String ss = JSONObject.fromObject(f1).toString();
        System.out.println(ss);
        params.put("data", Base64Util.getBase64(ss));
        System.out.println(JSONObject.fromObject(params).toString());
        Map<String, Object> str = HttpUtil.post(url, params);
        String result = str.get("response").toString();
        //System.out.println(params);
        System.out.println("=============" + result);
        //{"BODY":{"Data":[{"InvoiceCode":"3300183130","DeductiblePeriod":"201902","DeductibleResult":"1","InvoiceNumber":"26023304","DeductibleDate":"2019-03-12 10:30:07"}],"Msg":"认证成功！","Code":"0000"},"ROOT":{"SERVICE":{"REPLYCODE":"1000","REPLYMSG":"接口调用成功"}}}
        return result;
    }
    
    
    
    
    public static Map<String, String> getResult() {
        String access_token = "3BC20C950BCD06B6F89A78D93C219E31";
        String openid = "356E4E8DC8562AB661386FC4E29504F4";
        Map<String, String> result = new HashMap<String, String>();
        result.put("accessToken", access_token);
        result.put("openId", openid);
        
//        String sb = getToken();
//        Map tempMap = new HashMap();
//        try {
//            tempMap = XmlMapUtil.xml2Map(sb);
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }
//        if (tempMap.get("BODY") != null || !tempMap.get("BODY").equals("")) {
//            access_token = ((Map) tempMap.get("BODY")).get("access_token").toString();
//            result.put("accessToken", access_token);
//        }
//        String sb1 = getOpenId(access_token);
//        Map tempMap1 = new HashMap();
//        try {
//            tempMap1 = XmlMapUtil.xml2Map(sb1);
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }
//        if (tempMap1.get("BODY") != null || !tempMap1.get("BODY").equals("")) {
//            openid = ((Map) tempMap1.get("BODY")).get("openID").toString();
//            result.put("openId", openid);
//        }
        return result;
    }
    
    public static String getToken() {
        String url = "https://www.fapiao.com/Entoauth/TokenAction?sign=token" + "&client_id="
                + client_id + "&client_secret=" + client_secret;
        String str = HttpUtil.get(url, null).get("response").toString();
        System.out.println("token:"+str);
        return str;
    }

    public static String getOpenId(String accessToken) {
        String url = "https://www.fapiao.com/Entoauth/OpenAction?sign=open" + "&client_id="
                + client_id + "&access_token=" + accessToken;
        String str = HttpUtil.get(url, null).get("response").toString();
        System.out.println("openid:"+str);
        return str;
    }
    
}
