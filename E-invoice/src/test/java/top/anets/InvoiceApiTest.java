//
//package top.anets;
//
//import static org.assertj.core.api.Assertions.in;
//
//import java.io.ByteArrayOutputStream;
//import java.io.InputStream;
//import java.io.UnsupportedEncodingException;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.PostConstruct;
//
//import org.apache.commons.lang3.ArrayUtils;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import top.anets.entity.InvoiceHead;
//import top.anets.entity.InvoiceSplitLine;
//import top.anets.redis.RedisKey;
//import top.anets.redis.RedisService;
//import top.anets.service.InvoiceService;
//import top.anets.service.impl.XuanjiInvocieServiceImpl;
//import top.anets.utils.Base64;
//import top.anets.utils.CommonUtils;
//import top.anets.utils.Result;
//import top.anets.utils.XmlHelper;
//import top.anets.utils.Xuanji;
//import top.anets.vo.BusinessVo;
//import top.anets.vo.Logs;
//
///**
// * @author Administrator
// *
// */
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class InvoiceApiTest {
//	public static void main(String[] args)   {
////		InvoiceApiTest test = new InvoiceApiTest();
////		test.getToken(); 
//		
//		try {
//			int i=1/0;
//		} catch (Exception e) {
//			System.out.println(e.getLocalizedMessage());
//			System.out.println(e.getMessage());
//			System.out.println(System.err);
////			StackTraceElement[] trace = e.getStackTrace();
//			e.printStackTrace();
//			Throwable cause = e.getCause();
////			System.out.println(cause);
////			for (StackTraceElement stackTraceElement : trace) {
////				
////				System.out.println(stackTraceElement.getClassName()+"."+stackTraceElement.getMethodName()+":"+stackTraceElement.getLineNumber()); 				 
////			}
//		}
//	}
//	
////	    获取授权token
//	/**
//	 * @throws Exception 
//	*/
//	@Autowired
////	private XuanjiInvocieServiceImpl xuanjiInvocieService;
//	@Test
//	public void getToken() throws Exception {
////		Result query = xuanjiInvocieService.invoiceQuery("1314tyb374y203j29", "null", "1");
////		InvoiceSplitLine splitLine = (InvoiceSplitLine) query.getData();
////		System.out.println(splitLine);
//	}
//	
//	@Autowired
//	private BusinessVo business;
//	@Autowired
//	private RedisService redisService;
//	@Test
//	public void testInterface() throws Exception{ 
//		Map<String, String> all = redisService.hgetAll(RedisKey.Storj_log.getKey()+"-*");
//		System.out.println(all.size());
// 	}
//	
////	给静态变量注入值
//	@Autowired 
//	private InvoiceService invoiceService;
//	
//	
//	@Test
//	public void testSave(){
//		InvoiceHead invoiceHead =new InvoiceHead();
//		invoiceHead.setDocNum("3333468");
//		invoiceHead.setDocStatus("5");
//		List<InvoiceSplitLine> invoiceSplitLines=new ArrayList<>();
//		InvoiceSplitLine invoiceSplitLine = new InvoiceSplitLine();
//		invoiceSplitLine.setGroupNum("222");
//		invoiceSplitLines.add(invoiceSplitLine);
//		invoiceHead.setInvoiceSplitLines(invoiceSplitLines);
//	    Result save = invoiceService.save(invoiceHead );
//	    System.out.println(save);
//		
//	}
//	
//	
//	
//	@Test
//	public void  testLog(){
//		Logs instance = Logs.getInstance("222", "33", "SSS" , "SSSS", "SSS");
//		Logs instance1 = Logs.getInstance("333", "33", "SSS" , "SSSS", "SSS");
//		Logs instance2 = Logs.getInstance("444", "33", "SSS" , "SSSS", "SSS");
//		System.out.println(instance);
//		System.out.println(instance2);
//		System.out.println(instance1);
//	}
//	
//	
//	
//	@Test
//	public void testQuery() {
////		List<InvoiceHead> invoiceHeads = invoiceService.getInvoicingInvoiceHead();
////		for (InvoiceHead invoiceHead : invoiceHeads) {
////			System.out.println("========================"+invoiceHead.getDocNum());
////			List<InvoiceSplitLine> splitLines = invoiceHead.getInvoiceSplitLines();
////			for (InvoiceSplitLine splitLine : splitLines) {
////				System.out.println(splitLine.getGroupNum()+":"+splitLine.getDocLine());
////			}
////		}
//	}
//	
//	
//	@Test
//	public void query() {
//		try {
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//
//	
//	
//
//}
