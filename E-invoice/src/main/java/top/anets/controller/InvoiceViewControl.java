/**
 * 
 */
package top.anets.controller;

import static org.hamcrest.CoreMatchers.nullValue;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;

import top.anets.entity.InvoiceHead;
import top.anets.exception.ServiceException;
import top.anets.mapper.FileMapper;
import top.anets.redis.RedisKey;
import top.anets.redis.RedisService;
import top.anets.service.FileService;
import top.anets.service.InvoiceService;
import top.anets.service.InvoiceViewService;
import top.anets.utils.DateUtils;
import top.anets.utils.Result;
import top.anets.vo.BusinessVo;
import top.anets.vo.Invoice;
import top.anets.vo.InvoiceCondition;
import top.anets.vo.Logs;
import top.anets.vo.Message;

/**
 * @author Administrator
 *
 */
@RestController
@RequestMapping("invoiceView")
public class InvoiceViewControl {
	@Autowired
	private InvoiceViewService invoiceViewService; 
	@Autowired
	private InvoiceService invoiceService;
	@Value("${httpclient.dev_or_prod}")
	  private String dev_or_prod; 
	@Autowired
	private RedisService redisService;
	
	@Value("${zhys.invoice}")
	private String invoiceUrl;
	
	
	@Autowired
	private FileService fileService;
    /*
     * 查询发票，根据发票状态，组织,时间     ,页码,页数
     */
	@RequestMapping("querys")
	Result getInvoices(@RequestBody @Valid InvoiceCondition invoiceCondition){ 
		return Result.Success("查询成功!",invoiceViewService.getInvoices(invoiceCondition));
	}
	
	
	@RequestMapping("toInvoice")
	Result toInvoice(String docNum,String groupNum,Boolean isAll){  
		if(isAll==null||StringUtils.isBlank(docNum)||StringUtils.isBlank(groupNum)) {
			return Result.Error("参数异常");
		} 
		if(isAll==true) {
			return invoiceViewService.toInvoice(docNum);
		}else {
			return invoiceViewService.toInvoice(docNum,groupNum);
		}
		 
	}
	
	/**
	 * 解锁发票
	 */
	@RequestMapping("unlock")
	Result unlock(@RequestBody Invoice invoice) {
		return invoiceViewService.unlock(invoice); 
	}
	
	
	/**
	 * 撤销已提交的发票 
	 */
	@RequestMapping("undoCommit")
	Result undoCommit(String orgId) {
		return invoiceViewService.undoCommited(orgId, null);
	}
	
	
	
	/**
	 * 获取设备信息
	 */
	@RequestMapping("getDeviceInfo")
	Result getDeviceInfo(@RequestBody BusinessVo businessVo) {
		
//		return Result.Success(invoiceService.getDeviceInfo(businessVo));
		
//		获取本月已用限额
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		String mouth = format.format(new Date());
		String ws = redisService.get(businessVo.getOrgId()+mouth+"wxLimit") ;
		String se = redisService.get(businessVo.getOrgId()+mouth+"seLimit") ;
		Long wxLimit = businessVo.getWxLimit();
    	Long seLimit = businessVo.getSeLimit();
		
    	if(se!=null&&ws!=null) {
    		businessVo.setSeSum(Double.parseDouble(se));
        	
        	businessVo.setWsSum(Double.parseDouble(ws));
    	}
    	
		return Result.Success(businessVo);
	}
	
	/**
	 * 统计近30日开票量
	 */
	@RequestMapping("statisticalTask")
	Result statisticalTask(String orgId,Boolean refresh) { 
		return invoiceViewService.statisticalTask(orgId,refresh);
	}
	
	/**    （统计时间:天，周，月，季度，半年）
	 *    总金额
	 *    正数金额
	 *    负数金额
     *    折扣金额
     *    所缴税额
	 */
	@RequestMapping("statisticalMoney")
	Result statisticalMoney(String orgId,Boolean refresh) { 
		return invoiceViewService.statisticalMoney(orgId,refresh);
	}
	/**
	 * 统计 月度
	 * 待打印
	 *待作废
	 *待开票
	 *待推送
	 *待同步
	 *的信息
	* 
	*@param orgId
	*@param refresh
	*@return
	 */
	@RequestMapping("statisticalHealth")
	Result statisticalHealth(String orgId,Boolean refresh,String time)  { 
		return invoiceViewService.statisticalHealth(orgId,refresh,time);
	}
	/**
	  * 统计待开+已开+已作废+待作废
	  * 
	  *@return
	 */
	@RequestMapping("statisticalYuan")
	Result  test(String orgId,Integer   day) {
		Map<String, Long> task = invoiceViewService.getTask(orgId, day);
		return Result.Success(task);

	}
	
	
	
	
	/**
	 * 获取发票详情 
	 */
	@RequestMapping("getInvoiceDetail")
	Result getInvoiceDetail(@NotBlank String docNum,@NotBlank String groupNum)  {  
		return invoiceViewService.getInvoiceDetail(  docNum,  groupNum);
	}
	
	
	
	 
	/**
	 *获取日志，近20小时
	 */
	@RequestMapping("getLogs")
	Result getLogs()  {   
		File root = null ;
		 
			//获取跟目录
			ApplicationHome ah = new ApplicationHome(InvoiceViewControl.class);
			File files = ah.getSource();
			root = files.getParentFile();
		
		
		File file = new File(root, "logs");
		System.out.println("目录地址："+file);
		String[] list = file.list(); 
		HashMap<String, Object> map = new LinkedHashMap<>();
		for (String dir : list) {
			HashMap<String, Object> item = this.getLog(dir);
			map.put(dir, item);
		}
		return Result.Success(map);
	}
	
	/**
	 *获取日志，近20小时
	 */
	@RequestMapping("getLogsFromRedis")
	Result getLogsFromRedis(String orgId)  {  
		if(dev_or_prod.equals("dev")) {
			SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
			Date date = new Date();
			String  day = dayFormat.format(date);
			String  time = timeFormat.format(date); 
			redisService.lpush(orgId+"-"+"error", "["+day+time+"]"+"单据"+111111111+"取数异常"+"-"+"测试"+redisService.ttl(orgId+"-"+"error")); 
			redisService.expire(orgId+"-"+"error",60);
		}
		

		Long llen = redisService.llen(orgId+"-"+"error");
		List<Object> lrange = redisService.lrange(orgId+"-"+"error", 0, llen.intValue(), String.class);
		return Result.Success(lrange);
	}
	
	
	
//	@RequestMapping("getLog")
	HashMap<String, Object> getLog(String dir)  {   
		File root = null ;
		 
			//获取跟目录
			ApplicationHome ah = new ApplicationHome(InvoiceViewControl.class);
			File files = ah.getSource();
			root = files.getParentFile();
		 
		
		File file = new File(root, "logs/"+dir);
		System.out.println("目录地址："+file);
		String[] list = file.list(); 
		HashMap<String, Object> map = new LinkedHashMap<>();
		for (String string : list) {
			File item = new File(file,string);
			map.put(string, item.length());
		}
		return map;
	} 
	
	
	 
	@RequestMapping("downLog")
	@ResponseBody
	void downLog(String dir,String name,String orgId,String orgMachine ,HttpServletRequest request,HttpServletResponse response) throws IOException  {   
        BusinessVo business= new BusinessVo();
        business.setOrgId(orgId);
        business.setOrgMachine(orgMachine); 
		File root = null ;  
	 
			//获取跟目录
			ApplicationHome ah = new ApplicationHome(InvoiceViewControl.class);
			File files = ah.getSource();
			root = files.getParentFile();
		 
		 File file = new File(root, "logs/"+dir+"/"+name);
		 System.out.println("目录地址："+file);
		 ServletContext servletContext = request.getServletContext();
		  ServletOutputStream outputStream = response.getOutputStream(); 
		 response.setContentType("text/plain;charset=UTF-8"); 
         response.setHeader("content-disposition","attachment;filename="+file.getName()); 
         
          
         long start = System.currentTimeMillis(); 
         BufferedInputStream reader = new BufferedInputStream(new FileInputStream(file)); 
         double begin=(double)reader.available();//获取可用字节
         
       //byte[]数组的大小，根据复制文件的大小可以调整，1G一下可以5M。1G以上150M，自己多试试
         byte[] b=new byte[1024*5];
         int len=0;
         String progress=null;
         while((len=reader.read(b))!=-1){ 
        	 outputStream.write(b,0,len);
        	 outputStream.flush();
         	//显示进度
         	if(!String.format("%.2f",(1-reader.available()/begin)*100).equals(progress)){
                 progress=String.format("%.2f",(1-reader.available()/begin)*100);
                 Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_download, business, Logs.INFO, null,progress);
         		System.out.println("progress:"+progress+"%");
         	} 
         }
         reader.close();
         outputStream.close();
         long end = System.currentTimeMillis();
         System.out.println("time consuming:"+(end-start)+"ms"); 
          
	}
	
	
	
	
	@RequestMapping("downPdf")
	@ResponseBody
	public void downPdf( long fid, @RequestBody(required=false) BusinessVo  business ,HttpServletRequest request,HttpServletResponse response) throws IOException { 
//		根据fid查询文件
	    top.anets.entity.File filez = fileService.getFileByFid(fid);
		File file = new File(invoiceUrl,filez.getAddress()); 
		if(!file.exists()) {
			throw new ServiceException("对不起，"+file.getPath()+"文件不存在");
		}
			 System.out.println("文件地址："+file);
			 ServletContext servletContext = request.getServletContext();
			  ServletOutputStream outputStream = response.getOutputStream(); 
			  response.setContentType("application/pdf");//pdf预览
	         response.setHeader("content-disposition","attachment;filename="+file.getName()); 
	         
	          
	         long start = System.currentTimeMillis(); 
	         BufferedInputStream reader = new BufferedInputStream(new FileInputStream(file)); 
	         double begin=(double)reader.available();//获取可用字节
	         
	       //byte[]数组的大小，根据复制文件的大小可以调整，1G一下可以5M。1G以上150M，自己多试试
	         byte[] b=new byte[1024*5];
	         int len=0;
	         String progress=null;
	         while((len=reader.read(b))!=-1){ 
	        	 outputStream.write(b,0,len);
	        	 outputStream.flush();
	         	//显示进度
	         	if(!String.format("%.2f",(1-reader.available()/begin)*100).equals(progress)){
	                 progress=String.format("%.2f",(1-reader.available()/begin)*100);
	                 if(business!=null) {
	                	 Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_download, business, Logs.INFO, null,progress);
	                 }
	                 System.out.println("progress:"+progress+"%");
	         	} 
	         }
	         reader.close();
	         outputStream.close();
	         long end = System.currentTimeMillis();
	         System.out.println("time consuming:"+(end-start)+"ms"); 
	}
	
	
	public static void main(String[] args) throws FileNotFoundException {
		
	}
	
	
	 
	public  static void copyBytes(File from , File to,int byteSize) throws IOException{
		
	}
	
	
	
//	/*
//	 * 显示本月剩余未税金额
//	 */
//	@RequestMapping("getLogs")
//	Result getLimit(BusinessVo business)  {  
//		
//    	
//	}
//	
	
	
	
	
	
}
