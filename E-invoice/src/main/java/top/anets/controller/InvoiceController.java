/**
 * 
 */
package top.anets.controller;

 
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.IgnoreForbiddenApisErrors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.support.json.JSONUtils;
import com.google.gson.Gson;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javassist.expr.NewArray;
import lombok.experimental.var;
import top.anets.entity.Business;
import top.anets.entity.InvoiceHead;
import top.anets.entity.InvoiceSplitLine;
import top.anets.exception.ServiceException;
import top.anets.redis.RedisKey;
import top.anets.redis.RedisService;
import top.anets.service.InvoiceService;
import top.anets.utils.InvoiceUtils;
import top.anets.utils.Result;
import top.anets.utils.Status;
import top.anets.utils.ThreadPoolUtils;
import top.anets.vo.BusinessVo;
import top.anets.vo.InvoiceResult;
import top.anets.vo.Logs;
import top.anets.vo.Message;

/**
 * @author Administrator
 *
 */ 
@RestController
@RequestMapping("invoice") 
public class InvoiceController {
	 
     @Autowired
     private InvoiceService invoiceService;
    
     @Autowired
     private RedisService redisService;
     
       @Value("${httpclient.dev_or_prod}")
     private String dev_or_prod; 
     
     /**
      * 保存开票信息
      */
     @RequestMapping("/save")
     public Result saveInvoice(@RequestBody InvoiceHead invoiceHead) {
    	 return invoiceService.save(invoiceHead);
     }
     
     
     /*
      * 停止作废
      */
     @RequestMapping("stopInvalid")
     public Result stopInvalid(@RequestBody @Valid BusinessVo business) {    	 
    	 String userId =business.getOrgId()+"-"+business.getOrgMachine();
    	 try {
    		 redisService.hset(userId, RedisKey.Invaliding.getKey(), false);//开票标志
    		 return Result.Success("停止作废成功!");
		} catch (Exception e) {
			 return Result.Error("停止作废失败!"+e.getMessage());
		} 
     }
     
     /*
      * 停止开票（异步或者实时开票）
      */
     @RequestMapping("stopInvoice")
     public Result stopInvoice(@RequestBody @Valid BusinessVo business) {    	 
    	String userId =business.getOrgId()+"-"+business.getOrgMachine();
    	try {
    		 redisService.hset(userId, RedisKey.Invoicing.getKey(), false);//开票标志 
    		 return Result.Success("停止开票成功!");
		} catch (Exception e) {
			// TODO: handle exception
			 return Result.Success("停止开票失败!"+e.getMessage());
		}
     }
      
     /*
      * 启动开票（异步或者实时开票） 
      */
     @RequestMapping("startInvoice")
     public Result startInvoice(@RequestBody @Valid BusinessVo business) { 
    	 
    	 if("dev".equals(dev_or_prod)) { 
    	        String appid = "6d29f136114544bcc73edcce960c430231183cc192c433e2b9ebcad56e8ceb08";//appid
    			String contentPassword = "5EE6C2C11DD421F2";//AES加密密钥 
    	        business.setAppId(appid);
    	        business.setAppSecret(contentPassword);
    	 }

    	 String lock = redisService.get(business.getOrgId()+"-"+business.getOrgMachine()+"lock");
    	 if(lock!=null) {
    		 InvoiceUtils.stopInvalid(business);
    		 return Result.Error("请求过于频繁😒,请"+redisService.ttl(business.getOrgId()+"-"+business.getOrgMachine()+"lock")+"秒后再试😊"); 
    	 } 
    	 redisService.set(business.getOrgId()+"-"+business.getOrgMachine()+"lock", "lock", 20+business.getSpeed());
    	// 判断是否已经启动开票  
    	try { 
    		InvoiceUtils.startInvoice(business);//启动开票
		} catch (Exception e) {
			Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO, null,"启动开票失败,redis服务或者线路异常");
			return Result.Error("启动开票失败",e.getMessage(),e); 
		}
    	
    	Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO, null,"启动开票成功");
//		 初始化操作，检测 数据库中正在开票的数据（没有结果的数据）
		List<InvoiceHead> invoicingInvoiceHeads = invoiceService.getInvoicingInvoiceHead(business);
		
		if(invoicingInvoiceHeads!=null&&invoicingInvoiceHeads.size()>0) { 
			 Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO, "初始化操作", "正在恢复历史中断数据...");  	
		     for (InvoiceHead invoiceHead : invoicingInvoiceHeads) {
		    	Result result = invoiceService.queryAndHandleInvoiceResult(invoiceHead,business);
		    	if(!result.success) {
		    		Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO, "初始化操作", result.getMsg());  	
		    		return result;
		    	}
			 }
		} 
				
//		修复错误数据(无需修复，因为已经有结果了)
//	   Boolean recoverErrorData = this.recoverErrorDataInvoice(business);
// 	   if(!recoverErrorData) {
// 		   Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO, null,"监测到已停止开票");
//  		  Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO, null,"停止开票");
//  		  return Result.Error("启动开票失败,初始化恢复错误数据异常！");
// 	   }
//    	 加载数据
    	 this.loadWaitToInvoiceData(business);
    	 InvoiceController _this =this;
    	 if(business.getInvoiceWay()==0) {//异步开票
//    		 开启线程去 执行开票
    		ThreadPoolUtils.getPool().execute(new Runnable() { 
				@Override
				public void run() { 
					_this.startAsyncInvoice(business); //开启一个线程 
				}
			});
    		 return Result.Success("启动成功,正在异步开票!");
    	 }else if(business.getInvoiceWay()==1) {//同步开票
//    		 InvoiceController _this =this;
//    		 new Thread(){
// 				@Override
// 				public void run() {
// 					 _this.startSynchroInvoice(); //开启一个线程
// 				}
// 			}.start();
    		 return Result.Success("启动成功,正在同步开票!");
    	 }else {
    		 throw new ServiceException("开票方式只支持异步开票0与同步开票1");
    	 }
    	 
     }
     
     
     
     
     /*
      * 启动开票（异步或者实时开票） 
      */
     @RequestMapping("startInvalid")
     public Result startInvalid(@RequestBody @Valid BusinessVo business) { 
    	// 判断是否已经启动开票 
    	 if(InvoiceUtils.isInvalidInvoice(business)) {
    		 return Result.Error("正在作废中，请勿重复请求!"); }
    	 
    	try {
    		InvoiceUtils.startInvalid(business);//启动开票
		} catch (Exception e) {
			Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO, null,"启动作废失败,redis服务可能异常");
			 return Result.Error("启动作废失败",e.getMessage(),e); 
		}
    	Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO, null,"启动作废成功");
//		 初始化操作，检测 数据库中正在开票的数据（没有结果的数据）
		List<InvoiceHead> invoicingInvoiceHeads = invoiceService.getInvalidingInvoice(business);
		if(invoicingInvoiceHeads!=null&&invoicingInvoiceHeads.size()>0) { 
			 Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO, "初始化操作", "正在恢复历史中断数据...");  	
		     for (InvoiceHead invoiceHead : invoicingInvoiceHeads) {
		    	invoiceService.queryAndHandleInvoiceResult(invoiceHead,business);
			 }
		} 
		
//		修复错误数据
	   Boolean recoverErrorData = this.recoverErrorDataInvalid(business);
 	   if(!recoverErrorData) {
 		   Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO, null,"监测到已停止开票");
  		  Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO, null,"停止开票");
  		  return Result.Error("启动作废失败,初始化恢复错误数据异常！");
 	   }
		
//    	 加载数据
    	 this.loadWaitToInvalidData(business);
    	 InvoiceController _this =this;
//		 开启线程去 执行开票
		ThreadPoolUtils.getPool().execute(new Runnable() { 
			@Override
			public void run() { 
				_this.startInvalidInvoice(business); //开启一个线程 
			}
		});
		 return Result.Success("启动成功,正在作废!");
     }
     
 	/**
	* 
	*@param business 
	*/
	private void loadWaitToInvalidData(@Valid BusinessVo business) {
//		 如果不是sap的话，那么直接加载代开票数据 
		 Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO, null,"正在加载数据到代废池...");
		 invoiceService.updateWaitToInvalidData(business);
	}

	/**
	* 加载数据从sap中 或者 数据库中
	*@param business 
	*/
	private void loadWaitToInvoiceData(@Valid BusinessVo business) { 
		 Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO, null,"正在加载数据到代开票池...");
  		 invoiceService.updateWaitToInvoiceData(business);
	}

	/*
      * 启动开票（异步开票）
     */ 
     private void  startAsyncInvoice(BusinessVo business)  {  
          
    	  if(!InvoiceUtils.isStartedInvoice(business)) {//如果 停止开票了就返回
    		  Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO, null,"监测到已停止开票");
    		  Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO, null,"停止开票");
    		  return;
    	  }
    	  
//    	  检测是否有错误数据 ，若有，去处理
    	   Boolean recoverErrorData = this.recoverErrorDataInvoice(business);
    	   if(!recoverErrorData) {
    		   Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO, null,"监测到已停止开票");
     		  Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO, null,"停止开票");
     		  return;
    	   }
    	   
    	 
//    	 从代开票队列 取1条数据，修改数据 为  正在 开票
    	 InvoiceHead invoice =  (InvoiceHead) redisService.rpopObject(business.getOrgId()+"-"+business.getOrgMachine()+"-"+RedisKey.Invoice_Wait,InvoiceHead.class);
    	 
    	 if(invoice==null) {//待开票没有数据
    		try {
    			Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO, null, "待开票池没有数据..."); 
				Thread.sleep(10000);
				this.loadWaitToInvoiceData(business); 
			} catch (InterruptedException e) { 
				InvoiceUtils.stopInvoice(business);
				return ;
			}
    		 this.startAsyncInvoice(business);
    		 return ;
    	 }
    	  
    	 //    		 进行开票 
//    	 if(business.getLimit()==0) {//张数用完，停止开票
//    		 InvoiceUtils.stopInvoice(business);
//    		 Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO,null , "已停止开票，剩余"+business.getLimit()+"张，已用完");  
//    		 return ;
//    	 }else {
//    		 Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO,invoice.getDocNum()+"/"+invoice.getInvoiceSplitLines().get(0).getGroupNum(), "正在开票，剩余"+business.getLimit()+"张");  
//    	 }
    	 Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO,invoice.getDocNum()+"/"+invoice.getInvoiceSplitLines().get(0).getGroupNum(), "正在开票	");  

    	 
    	 Result result = invoiceService.invoice(invoice,business);
    		 if(result.success) { 
    			 System.out.println("发票结果");
    			 invoice = (InvoiceHead) result.getData(); 
    			 Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO, invoice.getDocNum()+"/"+invoice.getInvoiceSplitLines().get(0).getGroupNum(), "发票开立成功:"+invoice.getInvoiceSplitLines().get(0).getGoldtaxCode()+"-"+invoice.getInvoiceSplitLines().get(0).getGoldtaxNum()+"->"+invoice.getCustName()+"["+business.getOrgId()+"]"); 
//    			 继续开票
    			 try {
					Thread.sleep(1000*business.getSpeed());
				} catch (InterruptedException e) {
					e.printStackTrace();
					InvoiceUtils.stopInvoice(business);
					return ;
				}
//    			 business.setLimit(business.getLimit()-1);
    			 this.startAsyncInvoice(business); 
    		 }else if(result.getCode()!=null&&result.getCode().equals(Status.NeedQuery.code())) {//已经开过
    			 System.out.println("已经开过了");
    			 Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO, invoice.getDocNum()+"/"+invoice.getInvoiceSplitLines().get(0).getGroupNum(), "此发票已经开具过"); 
    			 invoiceService.queryAndHandleInvoiceResult(invoice, business);
    			 this.startAsyncInvoice(business);
    		 }else {
//    			 开票异常
    			 InvoiceUtils.stopInvoice(business, invoice, result);
    			 invoiceService.updateSplitLinesInvoicingRecoverNomal(invoice);
    		 }
     }
     
     
     
     
 	/*
      * 启动作废 
     */ 
     private void  startInvalidInvoice(BusinessVo business)  { 
    	  if(!InvoiceUtils.isInvalidInvoice(business)) {//如果 停止开票了就返回
    		  Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO, null,"监测到已停止作废");
    		  Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO, null,"停止作废开票");
    		  return;
    	  }
    	 
//    	  检测是否有错误数据 ，若有，去处理
    	 Boolean recoverErrorData = this.recoverErrorDataInvalid(business);
    	 if(!recoverErrorData) {
    		 Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO, null,"监测到已停止作废");
   		  Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO, null,"停止作废开票");
   		  return;
    	 }
    	 
//    	 从代作废开票队列 取1条数据，修改数据 为  正在 作废 
    	 InvoiceHead invoice =  (InvoiceHead) redisService.rpopObject(business.getOrgId()+"-"+business.getOrgMachine()+"-"+RedisKey.Invoice_Invalid,InvoiceHead.class);
    	 if(invoice==null ) {//待开票没有数据
    		try {
    			Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO, null, "待作废池没有数据..."); 
				Thread.sleep(10000);
				this.loadWaitToInvalidData(business);
			} catch (InterruptedException e) { 
				InvoiceUtils.stopInvalid(business);
				return ;
			}
    		 this.startInvalidInvoice(business);
    		 return ;
    	 }
         
    	 
    	 
    	 
    	 Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO,invoice.getDocNum()+"/"+invoice.getInvoiceSplitLines().get(0).getGroupNum(), "正在作废");  
//    		 进行开票
    	     Result result = invoiceService.invalid(invoice,business);
    		 if(result.success) { 
    			 InvoiceHead invoiced = (InvoiceHead) result.getData();
    			 invoiceService.updateInvoiceData(invoiced);
    			 Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO, invoiced.getDocNum()+"/"+invoiced.getInvoiceSplitLines().get(0).getGroupNum(), "发票作废开立成功:"+invoiced.getInvoiceSplitLines().get(0).getGoldtaxCode()+"-"+invoiced.getInvoiceSplitLines().get(0).getGoldtaxNum()+"->"+invoiced.getCustName()); 
//    			 继续开票
    			 this.startInvalidInvoice(business);
    		 }else if(result.getCode()!=null&&result.getCode().equals(Status.NeedQuery.code())) {//已经开过
    			 Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO, invoice.getDocNum()+"/"+invoice.getInvoiceSplitLines().get(0).getGroupNum(), "此发票已经作废开具过"); 
    			 invoiceService.queryAndHandleInvoiceResult(invoice, business);
    			 this.startInvalidInvoice(business);
    		 }else {
//    			 开票异常
    			 InvoiceUtils.stopInvalid(business, invoice, result);
    			 invoiceService.updateSplitLinesInvalidRecoverNomal(invoice);
    		 }
    
     }
 
	/**
	*  恢复异常数据,返回true代表ok，返回false代表停止了 
	*/
	private Boolean recoverErrorDataInvoice(BusinessVo business) {
		return true;
//		InvoiceHead invoiceError =  (InvoiceHead) redisService.rpopObject(business.getOrgId()+"-"+business.getOrgMachine()+"-"+RedisKey.Invoice_Error,InvoiceHead.class);
//   	    if(invoiceError!=null) { 
//   	    	Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO, invoiceError.getDocNum()+"/"+invoiceError.getInvoiceSplitLines().get(0).getGroupNum(), "正在恢复异常数据"); 
//            //查询不到发票信息，可能恢复不了,
//   	    	Result result = invoiceService.queryAndHandleInvoiceResult(invoiceError, business);
//   	    	
////   	    	if(!result.success) {
////	    		Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO, "初始化操作", result.getMsg());  	
////	    		return false;
////	    	} 
//   	    	
//   	    	
//   	    	if( !InvoiceUtils.isStartedInvoice(business)  ) {
//   	    		//如果 停止开票或者作废了就返回
//      		    return false;
//      	    } else {
//      	    	try {
//					Thread.sleep(2000);
//				} catch (InterruptedException e) { 
//					 Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO, null,"线程异常");
//					e.printStackTrace();
//				}
//      	    	return this.recoverErrorDataInvoice(business);
//      	    }
//            
//            
//   	    }else {
//   	    	return true;
//   	    }
	}

	
	
	/**
	*  恢复异常数据,返回true代表ok，返回false代表停止了 
	*/
	private Boolean recoverErrorDataInvalid(BusinessVo business) {
		InvoiceHead invoiceError =  (InvoiceHead) redisService.rpopObject(business.getOrgId()+"-"+business.getOrgMachine()+"-"+RedisKey.Invoice_Error,InvoiceHead.class);
   	    if(invoiceError!=null) { 
   	    	Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO, invoiceError.getDocNum()+"/"+invoiceError.getInvoiceSplitLines().get(0).getGroupNum(), "正在恢复异常数据"); 
            //查询不到发票信息，可能恢复不了,
   	    	invoiceService.queryAndHandleInvoiceResult(invoiceError, business);
   	    	
   	    	if(!InvoiceUtils.isInvalidInvoice(business) ) {
   	    		//如果 停止开票或者作废了就返回
      		    return false;
      	    } else {
      	    	try {
					Thread.sleep(2000);
				} catch (InterruptedException e) { 
					 Logs.notify(RedisKey.Channel_log.getKey(), Message.Type_Log, business, Logs.INFO, null,"线程异常");
					e.printStackTrace();
				}
      	    	return this.recoverErrorDataInvalid(business);
      	    }
   	    }else {
   	    	return true;
   	    }
	}
	/*
      * 启动开票（同步开票）
      */ 
     private void startSynchroInvoice() {
    	 
     }
     
     
     /*
      * 启动开票（实时开票）
      */
     public Result startIntimeInvoice() {
    	 return Result.Success();
     }
     
     
     
     
     /**
      * 获取日志中的所有天数
      */
     @GetMapping("log/days")
     public Result getDays() {
//    	 redisService.hset(RedisKey.Storj_log.getKey()+"-"+day, time, "["+title+"]"+content,RedisKey.Storj_log.getSeconds());
    	 return null;
     }
     
     
      
     
} 
