/**
 * 
 */
package top.anets.utils;

import static org.hamcrest.CoreMatchers.nullValue;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
//import org.icepdf.core.pobjects.Document;
//import org.icepdf.core.pobjects.Page;
//import org.icepdf.core.util.GraphicsRenderingHints;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice.This;
import top.anets.entity.Business;
import top.anets.entity.InvoiceHead;
import top.anets.entity.InvoiceSplitLine;
import top.anets.entity.Path;
import top.anets.entity.PathExample;
import top.anets.exception.ServiceException;
import top.anets.mapper.PathMapper;
import top.anets.redis.RedisKey;
import top.anets.redis.RedisService;
import top.anets.service.FileService;
import top.anets.service.InvoiceService;
import top.anets.vo.BusinessVo;
import top.anets.vo.InvoiceResult;
import top.anets.vo.Logs;
import top.anets.vo.Message;

/**
 * @author Administrator
 *
 */
@Component
@Slf4j

public class InvoiceUtils {
	
	
	
	@Value("${zhys.invoice}")
	private String invoice1;
	
	 private static final String Boolean = null;
	@Autowired
	    private  RedisService redisService1;
	@Autowired
	private PathMapper pathMapper1;
	
	@Autowired
	private   FileService fileService1; 
	
	private static FileService fileService; 
	
	    private static RedisService redisService; 
	    private static PathMapper pathMapper; 
	    public static Logs newInstance = null;
	    private static String invoice; 
		@PostConstruct  
	    public void init() {   
			InvoiceUtils.redisService=redisService1;
			InvoiceUtils.pathMapper = pathMapper1 ;
			InvoiceUtils.fileService = fileService1 ;
			InvoiceUtils.invoice = invoice1 ;
	    }  
		
		
	/**
	 * 
	* 停止开票
	*@param target
	*@param errorInvoice
	*@param invoiceResult
	 */
    public static void stopInvoice(BusinessVo target,InvoiceHead errorInvoice,Result invoiceResult) {
    	 System.out.println("停止开票=========================================================");
//    	 警告:无需存储错误数据
//    	 Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log,target, Logs.INFO, null,"正在存储开票错误数据");  
//    	 redisService.lpush(target.getOrgId()+"-"+target.getOrgMachine()+"-"+RedisKey.Invoice_Error, errorInvoice);
    	 
		 redisService.hset(target.getOrgId()+"-"+target.getOrgMachine(), RedisKey.Invoicing.getKey(), false);
		 
		 Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log,target, Logs.ERROR, errorInvoice.getDocNum()+"/"+errorInvoice.getInvoiceSplitLines().get(0).getGroupNum(),invoiceResult.getCode()+invoiceResult.getMsg());  	
		
		
		 Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log,target, Logs.INFO, null,"请撤销修改错误数据并重试");  
//		 Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log,target, Logs.INFO, null,"已停止开票");  
		 System.out.println("停止开票结束=========================================================");
    }

    /**
	 * 
	* 停止开票
	*@param target
	*@param errorInvoice
	*@param invoiceResult
	 */
    public static void stopInvoice(BusinessVo target) {
    	 System.out.println("停止开票=========================================================");   
    	 Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log,target, Logs.INFO, null,"正在停止开票");  
		 redisService.hset(target.getOrgId()+"-"+target.getOrgMachine(), RedisKey.Invoicing.getKey(), false); 
		 System.out.println("停止开票结束=========================================================");
    }
	/**
	* 启动开票
	*@param business 
	*/
	public static void startInvoice(  BusinessVo business) {
       
		String userId =business.getOrgId()+"-"+business.getOrgMachine();
		 redisService.hset(userId, RedisKey.Invoicing.getKey(), true);//开票标志
		 
	}
    
	/**
	* 匹配线路
	*@param business 
	*/
	public static void matchPath(InvoiceHead invoiceHead,Business business) {
		Path path = null;
		String key   = RedisKey.Path.getKey()+invoiceHead.getOrgId()+invoiceHead.getOrgMachine()+invoiceHead.getInvoiceType();
		if(StringUtils.isNotBlank(redisService.get(key)) ) {
			path=(Path) redisService.getObject(key,Path.class);
		}else {
			PathExample example = new PathExample();
	        example.createCriteria().andOrgIdEqualTo(invoiceHead.getOrgId()).andInvoiceTypeEqualTo(invoiceHead.getInvoiceType()+"").andOrgMachineEqualTo(invoiceHead.getOrgMachine());
	        //根据组织/发票类型 查询线路信息
			List<Path> list = pathMapper.selectByExample(example );
			if(list==null||list.size()!=1) {
				BusinessVo businessVo = new BusinessVo();
				BeanUtils.copyProperties(business, businessVo);
				Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log,businessVo, Logs.INFO, invoiceHead.getDocNum()+"/"+invoiceHead.getInvoiceSplitLines().get(0).getGroupNum(),"盘号-"+invoiceHead.getOrgMachine()+",发票类型-"+invoiceHead.getInvoiceType()+"匹配不了线路，请维护发票数据或线路");  
				InvoiceUtils.stopInvoice(businessVo);
				throw new ServiceException("线路异常，请维护线路");
			}
			path = list.get(0); 
			redisService.set(key, path, 0); 
		}
//		business.setOrgId(invoiceHead.getOrgId());
//		business.setOrgMachine(invoiceHead.getOrgMachine());
		
		business.setAppId(path.getAppId());
		business.setAppSecret(path.getAppSecret());
	}


	/**
	* 判断开票服务是否被 
	*@param business 
	*/
	public static Boolean isStartedInvoice( BusinessVo business) {
		String userId =business.getOrgId()+"-"+business.getOrgMachine();
		    Boolean result= (Boolean) redisService.hgetObject(userId, RedisKey.Invoicing.getKey());
		    if(result==null||result==false) {
		    	return false;
		    }else {
		    	return true;
		    }
	}


	/**
	* 
	*@param business
	*@return 
	*/
	public static boolean isInvalidInvoice(BusinessVo business) {
		String userId =business.getOrgId()+"-"+business.getOrgMachine();
	    Boolean result= (Boolean) redisService.hgetObject(userId, RedisKey.Invaliding.getKey());
	    if(result==null||result==false) {
	    	return false;
	    }else {
	    	return true;
	    }
	}


	/**
	*@param business 
	*/
	public static void stopInvalid(BusinessVo business) {
		String userId =business.getOrgId()+"-"+business.getOrgMachine();
		 redisService.hset(userId, RedisKey.Invaliding.getKey(), false);
	}


	/**
	* 
	*@param business
	*@param invoice
	*@param result 
	*/
	public static void stopInvalid(BusinessVo target, InvoiceHead errorInvoice, Result invoiceResult) {
		 System.out.println("停止作废=========================================================");
    	 Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log,target, Logs.INFO, null,"正在存储作废错误数据");  
    	 redisService.lpush(target.getOrgId()+"-"+target.getOrgMachine()+"-"+RedisKey.Invoice_Error, errorInvoice);
    	 
		 redisService.hset(target.getOrgId()+"-"+target.getOrgMachine(), RedisKey.Invoicing.getKey(), false);
		 Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log,target, Logs.ERROR, errorInvoice.getDocNum()+"/"+errorInvoice.getInvoiceSplitLines().get(0).getGroupNum(),invoiceResult.getCode()+invoiceResult.getMsg());  	
		 Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log,target, Logs.INFO, null,"请撤销修改错误数据并重试");  
//		 Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log,target, Logs.INFO, null,"已停止开票");  
		 System.out.println("停止作废结束=========================================================");
	}


	/**
	* 
	*@param business 
	*/
	public static void startInvalid(@Valid BusinessVo business) {
		String userId =business.getOrgId()+"-"+business.getOrgMachine();
		 redisService.hset(userId, RedisKey.Invaliding.getKey(), true);//开票标志
	}
	
	@Autowired
	private  InvoiceService invoiceServices;
	
	 
	private static InvoiceService invoiceService=null;
	
	@PostConstruct
	public void setInvoiceService() {
		this.invoiceService=invoiceServices;
	}
	
	/**
	* 
	*@param invoiceHead
	*@param invoice 
	*/
	public static void ExtendHandleInvoiceResult(InvoiceHead invoice, InvoiceResult invoiceResult,BusinessVo businessVo) {
		InvoiceHead invoiceHead= invoiceService.getInvoiceHead(invoice.getDocNum());
		
		
//		 封装结果
		InvoiceUtils.copyResult(invoiceResult, invoice);
//		 发票组的尾差处理,发票头的尾差计算
	    InvoiceUtils.handleWc(invoiceResult,invoiceHead , invoice , businessVo);
        
        
		//拼接号码 
		String joinNum = InvoiceUtils.joinNum(invoice.getGoldtaxNum(),invoiceHead.getGoldtaxNum(),invoice.getDocNum());
		invoice.setGoldtaxNum(joinNum);
		System.out.println("原号码:"+invoiceHead.getGoldtaxNum());
		System.out.println("新号码:"+invoice.getGoldtaxNum());
		System.out.println("拼接号码"+joinNum);
		
		
		
		InvoiceUtils.handleMoneyLimit(invoiceResult,invoice,businessVo);
		InvoiceUtils.persistInvoiceImg(invoiceResult,businessVo);
		
		
	}
	
	/**
	* 
	*@param invoiceResult
	*@param invoice
	*@param businessVo 
	*/
	private static void handleWc(InvoiceResult invoiceResult,InvoiceHead originalInvoiceHead , InvoiceHead invoice, BusinessVo businessVo) {
		  BigDecimal originalSe=new BigDecimal("0");
		    BigDecimal originalHsje=new BigDecimal("0");
		    BigDecimal originalWsje=new BigDecimal("0");
	        for (InvoiceSplitLine invoiceSplitLines : invoice.getInvoiceSplitLines()) { 
	        	  originalSe=originalSe.add(invoiceSplitLines.getZamountSej());
	        	  originalHsje=originalHsje.add(invoiceSplitLines.getZamountHsj());
	        	  originalWsje=originalWsje.add(invoiceSplitLines.getZamountWsj());
			} 
	        
	        String Hsjec = null;
	        String Wsjec=null;
	        String Sec=null;
	        
	        if(StringUtils.isBlank(invoiceResult.getJshj())||StringUtils.isBlank(invoiceResult.getHjje())||StringUtils.isBlank(invoiceResult.getHjse())) {
	        	 Hsjec = "0";
	             Wsjec="0";
	             Sec="0";
	        }else {
	        	 Hsjec = String.valueOf(new BigDecimal(invoiceResult.getJshj()).subtract(originalHsje));
	             Wsjec=String.valueOf(new BigDecimal(invoiceResult.getHjje()).subtract(originalWsje));
	             Sec=String.valueOf(new BigDecimal(invoiceResult.getHjse()).subtract(originalSe));
	        }
	        
	        for (InvoiceSplitLine invoiceSplitLine : invoice.getInvoiceSplitLines()) { 
	      	  invoiceSplitLine.setHsjec(Hsjec);
	      	  invoiceSplitLine.setWsjec(Wsjec);
	      	  invoiceSplitLine.setSec(Sec); 
			} 
	        
	        
	        BigDecimal zamountHswc = originalInvoiceHead.getZamountHswc();
	        BigDecimal zamountWswc = originalInvoiceHead.getZamountWswc();
	        BigDecimal zamountSewc = originalInvoiceHead.getZamountSewc();
	        
	        if(zamountHswc==null)  zamountHswc=new BigDecimal("0");
	        if(zamountWswc==null)  zamountWswc=new BigDecimal("0");
	        if(zamountSewc==null)  zamountSewc=new BigDecimal("0");
	        
	        zamountHswc.add(new BigDecimal(Hsjec));
	        zamountWswc.add(new BigDecimal(Wsjec));
	        zamountSewc.add(new BigDecimal(Sec));
	        
	        invoice.setZamountHswc(zamountHswc);
	        invoice.setZamountSewc(zamountSewc);
	        invoice.setZamountWswc(zamountWswc);
	}

	
	
	 
	/**
	* 存储发票图片
	*@param invoiceResult 
	*/
	private static void persistInvoiceImg(InvoiceResult invoiceResult,BusinessVo businessVo) {
		if(StringUtils.isBlank(invoiceResult.getePdfUrl())) return ;
		
//		下载图片 
		String root =invoice;//根目录
//		-子级目录
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyyMM");
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd");
		String yyyyMM = dateFormat1.format(new Date());
		String dd = dateFormat2.format(new Date());
        String fileName =  invoiceResult.getDocNum()+"_"+invoiceResult.getGroupNum()+"_"+invoiceResult.getGoldtaxNum();
		//		-开始下载 
		 top.anets.entity.File downloadUrlFile = downloadUrlFile(invoiceResult.getePdfUrl(), root+"/"+yyyyMM+"/"+dd,fileName );

		 String filenameFull =downloadUrlFile.getFname()+"."+downloadUrlFile.getSuffix();
//		 生成缩略图
		 String preview=null;
		 if(downloadUrlFile.getSuffix().equals("pdf")) {
			 File file = new File(root+"/"+"preview");
			 if(!file.exists()) {
				 file.mkdirs();
			 }
			 preview="/"+"preview"+"/"+fileName+".jpg";
			 PDFUtils.pdfTojpg(root+"/"+yyyyMM+"/"+dd+"/"+filenameFull, root+preview, 2d);
		 }
		 
		//		-持久化数据库 
		long pid = fileService.mkdirs(yyyyMM+"/"+dd,1,-1);
//		检查文件是否存在
		List<top.anets.entity.File> repeatFileByFname = fileService.getRepeatFileByFname(pid, -1, downloadUrlFile.getFname());
		if(repeatFileByFname!=null&&repeatFileByFname.size()>0) {
			 for (top.anets.entity.File file : repeatFileByFname) {
				fileService.deleteFile(file.getFid());
			}
		}
		fileService.upLoadFile("/"+yyyyMM+"/"+dd+"/"+filenameFull, downloadUrlFile.getFname(), downloadUrlFile.getSuffix(), downloadUrlFile.getSize(), pid, -1,preview);
		
 
	}
  
	

	public static void main(String[] args) throws  Exception { 
//		downloadUrlFile("http://dev.fapiao.com:19080/dzfp-web/pdf/download?request=e5uhf8WETIOMgaa2cCUMttqug2l.sSAnpWGyVouPxckMNqSBA4qLmPE8tTM1c7BYbLg4Pp0SBII_%5ECaAihieDIj"
//				,"C:\\Users\\Administrator\\Desktop\\新建文件夹 (3)\\","b" 
//				 );	
		Date date = new Date("2021-01-11T16:00:00.000Z");
	}
	
	

    /**
     * filename不包括后缀
    * 
    *@param strUrl
    *@param location
    *@return 返回 完整的文件名
     */
	public static top.anets.entity.File downloadUrlFile(String strUrl,String targetDir,String fileName) {
		 
        String ofileName = null;
        URL url = null; 
        /**
         * 根据url获取文件名
         */
        try {
            url = new URL(strUrl);
            URLConnection uc = url.openConnection();
            ofileName = uc.getHeaderField("Content-Disposition");
            ofileName = new String(ofileName.getBytes("ISO-8859-1"), "GBK");
            ofileName = URLDecoder.decode(ofileName.substring(ofileName.indexOf("filename=") + 9), "UTF-8"); 
        } catch (Exception e) {
            // 获取文件名第二种方式
            ofileName = strUrl.substring(strUrl.lastIndexOf('/') + 1);
        }
        String dir = targetDir;
        File dirfile = new File(dir);	
        if (!dirfile.exists()) {
            dirfile.mkdirs();
        }
        String suffix = null;
        File file =null;
        try {
//        	获取文件后缀
        	suffix =ofileName.substring(ofileName.lastIndexOf("."));
//          将文件转存至本地
        	log.info(dir+fileName+suffix);
        	file = new File(dir+"\\"+fileName+suffix);
            FileUtils.copyURLToFile(url, file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        log.info("下载文件结束");
        
        top.anets.entity.File files = new top.anets.entity.File();
        files.setFname(fileName);
        files.setSize(files.getSize());
        files.setSuffix(suffix.substring(1));
        return files;
    }
	  
	/**
	* 处理月限额问题
	*@param invoiceResult 
	*/
	private static void handleMoneyLimit(InvoiceResult invoiceResult,InvoiceHead invoiceHead,BusinessVo businessVo) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		String mouth = format.format(new Date());
		
//		预处理
		 String Wsje = "";
		 String Hjse=""; 
	   if(StringUtils.isBlank(invoiceResult.getJshj())||StringUtils.isBlank(invoiceResult.getHjje())||StringUtils.isBlank(invoiceResult.getHjse())) {
		   BigDecimal originalSe=new BigDecimal("0");
		    BigDecimal originalHsje=new BigDecimal("0");
		    BigDecimal originalWsje=new BigDecimal("0");
	        for (InvoiceSplitLine invoiceSplitLines : invoiceHead.getInvoiceSplitLines()) { 
	        	  originalSe=originalSe.add(invoiceSplitLines.getZamountSej());
	        	  originalHsje=originalHsje.add(invoiceSplitLines.getZamountHsj());
	        	  originalWsje=originalWsje.add(invoiceSplitLines.getZamountWsj());
			} 
	        Wsje = originalWsje.toString();
			Hjse= originalSe.toString();
		       
       }else {
    	    Wsje = invoiceResult.getHjje();
			Hjse=invoiceResult.getHjse();
       }
		
		//计算累计金额 
		if(redisService.get(invoiceHead.getOrgId()+mouth+"wxLimit")==null||redisService.get(invoiceHead.getOrgId()+mouth+"seLimit")==null) {
//			从数据库计算本月的开票金额之和
		    Map<String, Object> map = invoiceService.countWxAndSeThisMouth(invoiceHead.getOrgId());
		    
		    if(map==null) {
				redisService.set(invoiceHead.getOrgId()+mouth+"wxLimit",Wsje, 60*60*24*33);
				redisService.set(invoiceHead.getOrgId()+mouth+"seLimit",Hjse, 60*60*24*33);
		    }else {
		    	BigDecimal wsj = (BigDecimal) map.get("wsj");
		    	BigDecimal sej = (BigDecimal) map.get("sej");
		    	BigDecimal wsh = wsj.add(new BigDecimal(invoiceResult.getHjje()));
		    	BigDecimal seh = sej.add(new BigDecimal(invoiceResult.getHjse()));
		    	redisService.set(invoiceHead.getOrgId()+mouth+"wxLimit",wsh.toString(), 60*60*24*33);
				redisService.set(invoiceHead.getOrgId()+mouth+"seLimit",seh.toString(), 60*60*24*33);
		    }
		}else {
			String ws = redisService.get(invoiceHead.getOrgId()+mouth+"wxLimit") ;
			String se = redisService.get(invoiceHead.getOrgId()+mouth+"seLimit") ;
			BigDecimal wsj = new BigDecimal(ws);
	    	BigDecimal sej = new BigDecimal(se);
	    	BigDecimal wsh = wsj.add(new BigDecimal(invoiceResult.getHjje()));
	    	BigDecimal seh = sej.add(new BigDecimal(invoiceResult.getHjse()));
	    	redisService.set(invoiceHead.getOrgId()+mouth+"wxLimit",wsh.toString(), 60*60*24*33);
			redisService.set(invoiceHead.getOrgId()+mouth+"seLimit",seh.toString(), 60*60*24*33);
		}
		 	

	}


	/**
	* 
	*@param invoiceResult
	*@param invoice 
	*/
	private static void copyResult(InvoiceResult invoiceResult, InvoiceHead invoice) {
		 invoice.setDocStatus(invoiceResult.getGroupStatus());
		 invoice.setBillGdate(invoiceResult.getBillGdate()); 
		 invoice.setGoldtaxCode(invoiceResult.getGoldtaxCode());
		 invoice.setGoldtaxNum(invoiceResult.getGoldtaxNum());
		 for (InvoiceSplitLine invoiceSplitLine : invoice.getInvoiceSplitLines()) {
			invoiceSplitLine.setGroupStatus(invoiceResult.getGroupStatus());
			invoiceSplitLine.setGoldtaxCode(invoiceResult.getGoldtaxCode());
			invoiceSplitLine.setGoldtaxNum(invoiceResult.getGoldtaxNum());
			invoiceSplitLine.setBillGdate(invoiceResult.getBillGdate());
			invoiceSplitLine.setePdfUrl(invoiceResult.getePdfUrl());
		 }
	}


	/**
	 * 扩展处理:
	 * 处理发票头  的号码拼接
	 */
//	public static void ExtendHandle(InvoiceHead invoice) { 
//		//拼接号码
//				InvoiceHead invoiceHead = invoiceService.getInvoiceHead(invoice.getDocNum());
//				String joinNum = InvoiceUtils.joinNum(invoice.getGoldtaxNum(),invoiceHead.getGoldtaxNum());
//				invoice.setGoldtaxNum(joinNum);
//				
//				System.out.println("原号码:"+invoiceHead.getGoldtaxNum());
//				System.out.println("新号码:"+invoice.getGoldtaxNum());
//				System.out.println("拼接号码"+joinNum);
//				//一个单据的最后一张发票开完之后的处理
//				
//	}
//	
	
 
	
	
	private static String joinNum(String currentGoldtaxNum,String oldGoldtaxNum,String docNum) {
//		"09542477-09542479 09542485   09542487-09542489"  
	 
//		初始化 - 对账单 
		int count = invoiceService.countInvoiced(docNum);
		if(count==0) {
			oldGoldtaxNum=null;
		}
		
		
		String originalNum = oldGoldtaxNum; 
		String newNum = currentGoldtaxNum;
		String splitNum="";
		if(originalNum==null) {
			return newNum;
		}
		
		
		
		
		String[] split = originalNum.split(" ");
		if(originalNum.length()<8||originalNum.length()>512||split==null) {//不需要拼接(为空或者不是一个正确的发票号码)，返回新号码 
			log.error("头发票号码异常:"+originalNum);
			return "error";
		}
		
		//检测新号码是否包含 在 拼接过的号码中 或者小于
		boolean isInclude =false;
		for (int i = 0; i < split.length; i++) {//检查新发票号码是否在某一组中
			String item =split[i];
			if(StringUtils.isBlank(item)) {
				continue;
			} 
			
			if(!item.contains("-")&&Integer.valueOf(newNum).equals(Integer.valueOf(item)-1)) {//判断新号码是否小于旧的号码 
					split[i]=newNum+"-"+item.substring(newNum.length()-2);
					isInclude=true; 
			}
			
			if(item.contains("-")) {
				String[] split2 = item.split("-");
				String pre=split2[0];
				String next=pre.substring(0,pre.length()-2)+split2[1];
				if(next.compareTo(newNum)>=0&&pre.compareTo(newNum)<=0) {
					isInclude=true;
				}
				if(Integer.valueOf(newNum).equals(Integer.valueOf(pre)-1)) {
					split[i]=newNum+"-"+next.substring(newNum.length()-2);;
					isInclude=true;
				}
			}
		}
		
		
		if(isInclude) {
			var result="";
			for (String string : split) {
				result=result+string+" ";
			}
			return result;
		}
		
		
		 
		 
		
		 
		  
		String substring = originalNum.substring(originalNum.length()-3);
		boolean contains = substring.contains("-");
		if(contains) {
			String currentNum = originalNum.substring(originalNum.length()-11, originalNum.length()-5) + originalNum.substring(originalNum.length()-2);
			Integer a = Integer.valueOf(currentNum)+1;
			if(a.equals(Integer.valueOf(newNum))) {//说明是相邻的号码
				splitNum=originalNum.substring(0,originalNum.length()-2)+newNum.substring(newNum.length()-2);
				
			}else if(Integer.valueOf(currentNum).equals(Integer.valueOf(newNum))){
				splitNum=originalNum;
			}else{
				splitNum=originalNum+" "+newNum; 
			}
			
		}else {
			String currentNum = originalNum.substring(originalNum.length()-8) ;
			Integer a = Integer.valueOf(currentNum)+1;
			if(a.equals(Integer.valueOf(newNum))) {//说明是相邻的号码
				splitNum=originalNum +"-"+newNum.substring(newNum.length()-2);
			}else if(Integer.valueOf(currentNum).equals(Integer.valueOf(newNum))){
				splitNum=originalNum;
			}else {
				splitNum=originalNum+" "+newNum; 
			}
		}
		return splitNum;
	}


	/**
	* 
	*@param invoiceHead
	*@param query 
	*/
	public static void ExtendHandleInvalidResult(InvoiceHead invoice, InvoiceResult invoiceResult) {
//		 封装结果
		InvoiceUtils.copyResult(invoiceResult, invoice);
	}


	/**
	* 重新计算税额
	*@param invoiceHead 
	*/
	public static void reCalculateSe(InvoiceHead invoiceHeadNew,BusinessVo business) {
		
		if(invoiceHeadNew.getInvoiceRed().equals("X")) {//红票不用重新计算税额
			return;
		}
		
        String invoiceBase = invoiceHeadNew.getInvoiceBase();
        if(invoiceBase==null) {
        	InvoiceUtils.stopInvoice(business);
        	Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO,invoiceHeadNew.getDocNum(), "基准方式不能为空"+"["+business.getOrgId()+"]");  		
        	throw new ServiceException("基准不能为空");
        }
        if(invoiceBase.equals("1")) {//含税金额固定
        	Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO,invoiceHeadNew.getDocNum(), "重新计算税额->含税金额固定"+"["+business.getOrgId()+"]");  		
        	List<InvoiceSplitLine> invoiceSplitLines =invoiceHeadNew.getInvoiceSplitLines();
        	for (InvoiceSplitLine invoiceSplitLine : invoiceSplitLines) {
				BigDecimal zamountHsj = invoiceSplitLine.getZamountHsj();
				BigDecimal sl = invoiceSplitLine.getTaxRate();
				BigDecimal quantity = invoiceSplitLine.getQuantity();
//				计算
				BigDecimal zamountWsj = zamountHsj.divide(sl.add(new BigDecimal("1")),2,RoundingMode.HALF_UP);
				BigDecimal zamountSe = zamountHsj.subtract(zamountWsj);
				
				BigDecimal priceHsj =zamountHsj.divide(quantity,10,RoundingMode.HALF_UP);
				BigDecimal priceWsj =zamountWsj.divide(quantity,10,RoundingMode.HALF_UP);
                
				BigDecimal Hzhs = invoiceSplitLine.getZamountHsy().subtract(zamountHsj);
				BigDecimal Hzws = invoiceSplitLine.getZamountWsy().subtract(zamountWsj); 
				BigDecimal Hzse =Hzhs.subtract(Hzws);
				
				invoiceSplitLine.setZamountWsj(zamountWsj);
				invoiceSplitLine.setZamountSej(zamountSe);
				invoiceSplitLine.setZpriceHsj(priceHsj);
				invoiceSplitLine.setZpriceWsj(priceWsj);
				invoiceSplitLine.setZamountHzhs(Hzhs);
				invoiceSplitLine.setZamountHzws(Hzws);
				invoiceSplitLine.setZamountHzse(Hzse);
				Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO,
						invoiceSplitLine.getGroupNum()+"/"+invoiceSplitLine.getDocLine(), 
						"原含税="+invoiceSplitLine.getZamountHsj()+",原未税="+invoiceSplitLine.getZamountWsj()+",原税额="+invoiceSplitLine.getZamountSej()+"/新含税="+zamountHsj+",新未税="+zamountWsj+",新税额="+zamountSe+"["+business.getOrgId()+"]");  		

			}
        }
        
        if(invoiceBase.equals("2")) {//未税金额固定
        	Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO,invoiceHeadNew.getDocNum(), "重新计算税额-未税金额固定"+"["+business.getOrgId()+"]");  		
        	List<InvoiceSplitLine> invoiceSplitLines =invoiceHeadNew.getInvoiceSplitLines();
        	for (InvoiceSplitLine invoiceSplitLine : invoiceSplitLines) {
				BigDecimal zamountWsj = invoiceSplitLine.getZamountWsj();
				BigDecimal sl = invoiceSplitLine.getTaxRate() ;
				BigDecimal quantity = invoiceSplitLine.getQuantity();
//				计算
				BigDecimal zamountSe = zamountWsj.multiply(sl).setScale(2, BigDecimal.ROUND_HALF_UP);
				BigDecimal zamountHsj =zamountSe.add(zamountWsj);
				
				
				BigDecimal priceHsj =zamountHsj.divide(quantity,10,RoundingMode.HALF_UP);
				BigDecimal priceWsj =zamountWsj.divide(quantity,10,RoundingMode.HALF_UP);
                
				BigDecimal Hzhs = invoiceSplitLine.getZamountHsy().subtract(zamountHsj);
				BigDecimal Hzws = invoiceSplitLine.getZamountWsy().subtract(zamountWsj); 
				BigDecimal Hzse =Hzhs.subtract(Hzws);
				
				invoiceSplitLine.setZamountHsj(zamountHsj);
				invoiceSplitLine.setZamountSej(zamountSe);
				invoiceSplitLine.setZpriceHsj(priceHsj);
				invoiceSplitLine.setZpriceWsj(priceWsj);
				invoiceSplitLine.setZamountHzhs(Hzhs);
				invoiceSplitLine.setZamountHzws(Hzws);
				invoiceSplitLine.setZamountHzse(Hzse);
				Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO,
						invoiceSplitLine.getGroupNum()+"/"+invoiceSplitLine.getDocLine(), 
						"原含税="+invoiceSplitLine.getZamountHsj()+",原未税="+invoiceSplitLine.getZamountWsj()+",原税额="+invoiceSplitLine.getZamountSej()+"/新含税="+zamountHsj+",新未税="+zamountWsj+",新税额="+zamountSe+"["+business.getOrgId()+"]");  		

			}
        }
        
        if(invoiceBase.equals("3")) {//含税单价固定
        	Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO,invoiceHeadNew.getDocNum(), "重新计算税额-含税单价固定"+"["+business.getOrgId()+"]");  		
        	List<InvoiceSplitLine> invoiceSplitLines =invoiceHeadNew.getInvoiceSplitLines();
        	for (InvoiceSplitLine invoiceSplitLine : invoiceSplitLines) {
        		BigDecimal zpriceHsj = invoiceSplitLine.getZpriceHsj();
        		 
				BigDecimal sl = invoiceSplitLine.getTaxRate() ;
				BigDecimal quantity = invoiceSplitLine.getQuantity();
//				计算
				BigDecimal zamountHsj = zpriceHsj.multiply(quantity).setScale(2, BigDecimal.ROUND_HALF_UP);
				BigDecimal zamountWsj = zamountHsj.divide(sl.add(new BigDecimal("1")),10,RoundingMode.HALF_UP);
				BigDecimal zamountSe = zamountHsj.subtract(zamountWsj);
				
				 
				BigDecimal priceWsj =zamountWsj.divide(quantity,10,RoundingMode.HALF_UP);
                
				BigDecimal Hzhs = invoiceSplitLine.getZamountHsy().subtract(zamountHsj);
				BigDecimal Hzws = invoiceSplitLine.getZamountWsy().subtract(zamountWsj); 
				BigDecimal Hzse =Hzhs.subtract(Hzws);
				
				invoiceSplitLine.setZamountWsj(zamountWsj);
				invoiceSplitLine.setZamountSej(zamountSe); 
				invoiceSplitLine.setZpriceWsj(priceWsj);
				invoiceSplitLine.setZamountHzhs(Hzhs);
				invoiceSplitLine.setZamountHzws(Hzws);
				invoiceSplitLine.setZamountHzse(Hzse);
				Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO,
						invoiceSplitLine.getGroupNum()+"/"+invoiceSplitLine.getDocLine(), 
						"原含税="+invoiceSplitLine.getZamountHsj()+",原未税="+invoiceSplitLine.getZamountWsj()+",原税额="+invoiceSplitLine.getZamountSej()+"/新含税="+zamountHsj+",新未税="+zamountWsj+",新税额="+zamountSe+"["+business.getOrgId()+"]");  		

        		
        	 
			}
        }
        
        
        if(invoiceBase.equals("4")) {//未税单价金额固定
        	Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO,invoiceHeadNew.getDocNum(), "重新计算税额-未税单价固定"+"["+business.getOrgId()+"]");  		
        	List<InvoiceSplitLine> invoiceSplitLines =invoiceHeadNew.getInvoiceSplitLines();
        	for (InvoiceSplitLine invoiceSplitLine : invoiceSplitLines) {
        		BigDecimal zpriceWsj = invoiceSplitLine.getZpriceWsj();
				
				BigDecimal sl = invoiceSplitLine.getTaxRate() ;
				BigDecimal quantity = invoiceSplitLine.getQuantity();
				BigDecimal zamountWsj = zpriceWsj.multiply(quantity).setScale(2, BigDecimal.ROUND_HALF_UP);
//				计算
				BigDecimal zamountSe = zamountWsj.multiply(sl);
				BigDecimal zamountHsj =zamountSe.add(zamountWsj);
				
				
				BigDecimal priceHsj =zamountHsj.divide(quantity,10,RoundingMode.HALF_UP); 
                
				BigDecimal Hzhs = invoiceSplitLine.getZamountHsy().subtract(zamountHsj);
				BigDecimal Hzws = invoiceSplitLine.getZamountWsy().subtract(zamountWsj); 
				BigDecimal Hzse =Hzhs.subtract(Hzws);
				
				invoiceSplitLine.setZamountHsj(zamountHsj);
				invoiceSplitLine.setZamountSej(zamountSe);
				invoiceSplitLine.setZpriceHsj(priceHsj); 
				invoiceSplitLine.setZamountHzhs(Hzhs);
				invoiceSplitLine.setZamountHzws(Hzws);
				invoiceSplitLine.setZamountHzse(Hzse);
				Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO,
						invoiceSplitLine.getGroupNum()+"/"+invoiceSplitLine.getDocLine(), 
						"原含税="+invoiceSplitLine.getZamountHsj()+",原未税="+invoiceSplitLine.getZamountWsj()+",原税额="+invoiceSplitLine.getZamountSej()+"/新含税="+zamountHsj+",新未税="+zamountWsj+",新税额="+zamountSe+"["+business.getOrgId()+"]");  		

			}
        }
	}


	
//	public static void main(String[] args) {
//		InvoiceHead invoiceHead = new InvoiceHead();
//		ArrayList<InvoiceSplitLine> list = new ArrayList<>();
//		InvoiceSplitLine splitLine = new InvoiceSplitLine();
//		splitLine.setZamountHsj(new BigDecimal("596700"));
//		splitLine.setZamountWsj(new BigDecimal(528053.1));
//		splitLine.setZamountSej(new BigDecimal(68646.9));
//		splitLine.setTaxRate(new BigDecimal(0.13));
//		list.add(splitLine);
//		invoiceHead.setInvoiceSplitLines(list);
//		java.lang.Boolean strictControlSc = InvoiceUtils.strictControlSc(invoiceHead);
//		System.out.println(strictControlSc); 
//	}
	/**
	* 严格控制税差,返回true，则超过了6分
	*@param invoiceHead
	*@return 
	*/
	public static Boolean strictControlSc(InvoiceHead invoiceHead) {
		List<InvoiceSplitLine> splitLines = invoiceHead.getInvoiceSplitLines();
		for (InvoiceSplitLine invoiceSplitLine : splitLines) {
			  BigDecimal hsje = invoiceSplitLine.getZamountHsj();
			  BigDecimal wsje = invoiceSplitLine.getZamountWsj();
			  BigDecimal se = invoiceSplitLine.getZamountSej();
			  BigDecimal sl = invoiceSplitLine.getTaxRate();
			 if (hsje != null   && se != null   && sl != null)
	            { 
				 BigDecimal ce = hsje.multiply(sl)
				.divide(
						new BigDecimal("1").
						add(sl),10,RoundingMode.HALF_UP);
				 BigDecimal  a1=ce.subtract(se);
				 BigDecimal  a2=se.subtract(ce);
				 BigDecimal  b= new BigDecimal("0.06");
				 System.out.println(a1);
				 System.out.println(a2);
	                if (a1.compareTo(b)==1 || a2.compareTo(b)==1)
	                {
	                	return true; 
	                }
	            } 
	            if ( wsje != null && se != null &&  sl != null )
	            { 
	            	BigDecimal ce = wsje.multiply(sl); 
	            	BigDecimal  a1=ce.subtract(se);
					 BigDecimal  a2=se.subtract(ce);
					 BigDecimal  b= new BigDecimal("0.06");
					 System.out.println(a1);
					 System.out.println(a2);
					 if (a1.compareTo(b)==1 || a2.compareTo(b)==1){ 
						 return true;
		              }
	            }
		}
		
		return false;
	}


	/**
	* 
	*@param invoiceHeadNew 
	*/
	public static void handleZhekou(InvoiceHead invoiceHeadNew) {
		for (int i= 0;i< invoiceHeadNew.getInvoiceSplitLines().size();i++) { 
			 InvoiceSplitLine linez = invoiceHeadNew.getInvoiceSplitLines().get(i);
			 BigDecimal zamountHzhs = linez.getZamountHzhs(); 
			 BigDecimal zamountHzws = linez.getZamountHzws(); 
			 BigDecimal zamountHzse = linez.getZamountHzse(); 
			 if(zamountHzhs.compareTo(new BigDecimal("0"))<0) {//折扣行
//				 折扣标记,拆分
				 InvoiceSplitLine zhekou =  new Gson().fromJson(new Gson().toJson(linez), InvoiceSplitLine.class);
				 zhekou.setGiftFlag("1"); //标记折扣
				 linez.setGiftFlag("-1");//-1是被折扣
				 zhekou.setZamountHsj(zamountHzhs);
				 zhekou.setZamountSej(zamountHzse);
				 zhekou.setZamountWsj(zamountHzws);
//				 把折扣行插入体
				 invoiceHeadNew.getInvoiceSplitLines().add(i, zhekou);
			 }
       }
	}


	/**
	* 
	*@param invoiceHeadNew
	*@param business 
	*/
	public static void handleZhekou(InvoiceHead invoiceHeadNew, BusinessVo business) {
		for (int i= 0;i< invoiceHeadNew.getInvoiceSplitLines().size();i++) { 
			 InvoiceSplitLine linez = invoiceHeadNew.getInvoiceSplitLines().get(i);
			 BigDecimal zamountHzhs = linez.getZamountHzhs(); 
			 BigDecimal zamountHzws = linez.getZamountHzws(); 
			 BigDecimal zamountHzse = linez.getZamountHzse(); 
			 if(zamountHzhs.compareTo(new BigDecimal("0"))<0) {//折扣行
				 Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO,invoiceHeadNew.getDocNum()+"/"+linez.getGroupNum()+"/"+linez.getDocLine(), "折扣处理"+"["+business.getOrgId()+"]");  		
//				 折扣标记,拆分
				 InvoiceSplitLine zhekou =  new Gson().fromJson(new Gson().toJson(linez), InvoiceSplitLine.class);
				 zhekou.setGiftFlag("1"); //标记折扣
				 linez.setGiftFlag("-1");//-1是被折扣
				 zhekou.setZamountHsj(zamountHzhs);
				 zhekou.setZamountSej(zamountHzse);
				 zhekou.setZamountWsj(zamountHzws);
				 Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO,invoiceHeadNew.getDocNum()+"/"+linez.getGroupNum()+"/"+linez.getDocLine(), "折扣金额:含税="+zamountHzhs+",未税="+zamountHzws+",税额="+zamountHzse+"["+business.getOrgId()+"]");  		
//				 把折扣行插入体
				 invoiceHeadNew.getInvoiceSplitLines().add(i+1, zhekou);
			 }
 		  
      } 
	}


	/**
	* 
	*@param business 
	*/
	public static void stopInvoiceNotNotify(BusinessVo target) { 
		 redisService.hset(target.getOrgId()+"-"+target.getOrgMachine(), RedisKey.Invoicing.getKey(), false); 
	}


	/**
	* 
	*@param business 
	*/
	public static void stopInvalidNotNotify(BusinessVo target) { 
		 redisService.hset(target.getOrgId()+"-"+target.getOrgMachine(), RedisKey.Invaliding.getKey(), false); 
		
	}


	
	
	
	
	
	

	
	
}
