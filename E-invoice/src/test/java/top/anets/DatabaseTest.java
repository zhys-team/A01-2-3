///**
// * 
// */
//package top.anets;
//
//import static org.assertj.core.api.Assertions.in;
//
//import java.io.File;
//import java.math.BigDecimal;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.Set;
//
//import javax.annotation.PostConstruct;
//
//import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import top.anets.entity.InvoiceHead;
//import top.anets.entity.InvoiceHeadExample;
//import top.anets.mapper.InvoiceHeadMapper;
//import top.anets.redis.RedisService;
//import top.anets.service.FileService;
//import top.anets.service.InvoiceService;
//import top.anets.utils.InvoiceUtils;
//import top.anets.vo.InvoiceResult;
//
///**
// * @author Administrator
// *
// */
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class DatabaseTest { 
//   
//   @Autowired
//   private RedisService redisService;
//   
//   @Autowired
//   private FileService  fileService;
//   
//   @Test
//   public void fun() throws InterruptedException{
////	   下载图片  
//	   InvoiceResult invoiceResult = new InvoiceResult();
//	   invoiceResult.setGoldtaxNum("333");
//	   invoiceResult.setDocNum("322223");
//	   invoiceResult.setGroupNum("555");
//	   invoiceResult.setePdfUrl("http://dev.fapiao.com:19080/dzfp-web/pdf/download?request=e5uhf8WETIOMgaa2cCUMtucDkBY4U1cuRSSQWwAXfQe2X8lyvF8kx2zAEuN9xhZSL4U8tSgiBqE_%5EeEbjHfhBj");
//		String root ="C:\\Users\\Administrator\\Documents\\invoice";//根目录
////		-子级目录
//		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyyMM");
//		SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd");
//		String yyyyMM = dateFormat1.format(new Date());
//		String dd = dateFormat2.format(new Date());
//       String fileName =  invoiceResult.getDocNum()+"_"+invoiceResult.getGroupNum()+"_"+invoiceResult.getGoldtaxNum();
//		//		-开始下载 
//		 top.anets.entity.File downloadUrlFile = InvoiceUtils.downloadUrlFile(invoiceResult.getePdfUrl(), root+"/"+yyyyMM+"/"+dd,fileName );
//
//		 String filenameFull =downloadUrlFile.getFname()+"."+downloadUrlFile.getSuffix();
//		//		-持久化数据库 
//		long pid = fileService.mkdirs(yyyyMM+"/"+dd,1);
//		
////		检查文件是否存在
//		List<top.anets.entity.File> repeatFileByFname = fileService.getRepeatFileByFname(pid, -1, downloadUrlFile.getFname());
//		if(repeatFileByFname!=null&&repeatFileByFname.size()>0) {
//			 for (top.anets.entity.File file : repeatFileByFname) {
//				fileService.deleteFile(file.getFid());
//			}
//		}
//		fileService.upLoadFile("/"+yyyyMM+"/"+dd+"/"+filenameFull, downloadUrlFile.getFname(), downloadUrlFile.getSuffix(), downloadUrlFile.getSize(), pid, -1);
//		
//	   
//   }
//   
//   public static void main(String[] args) {
//	   
//	   File file = new File("F:\\zhys\\客户\\涂鸦-电子发票核心板项目\\文档\\服务器版企业接口规范20200701\\服务器版企业接口规范\\证书\\testISSUE.pfx");
//	System.out.println(file.exists());
//}
//    
//}
// 
