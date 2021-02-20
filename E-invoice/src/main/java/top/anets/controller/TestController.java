/**
 * 
 */
package top.anets.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import top.anets.annotation.InvoiceHeadValid;
import top.anets.entity.InvoiceHead;
import top.anets.mapper.InvoiceHeadMapper;
import top.anets.redis.RedisService;
import top.anets.service.ThirdInvoiceService;
//import top.anets.service.impl.XuanjiInvocieServiceImpl;
import top.anets.utils.Result;
import top.anets.vo.BusinessVo;
import top.anets.vo.InvoiceResult;
import top.anets.vo.Logs;

/**
 * @author Administrator
 * 
 */
@RestController
public class TestController {
	
	
	@Autowired
	private RedisService redisService;
	 
	@Autowired
	private InvoiceHeadMapper mapper;
	
	@Autowired
	@Qualifier("guoXinInvoiceService")
	private ThirdInvoiceService thirdInvoiceService;
	
    @RequestMapping("/invoice")
    @InvoiceHeadValid 
    public InvoiceResult invoice( @RequestBody  InvoiceHead invoiceHead,HttpServletRequest request) {
    	
    	System.out.println("开始-----------------测试");
        BusinessVo business=new BusinessVo();
        
        business.setOrgId("2000");
        String appid = "6d29f136114544bcc73edcce960c430231183cc192c433e2b9ebcad56e8ceb08";//appid
		String contentPassword = "5EE6C2C11DD421F2";//AES加密密钥
		
		
		
        business.setAppId(appid);
        business.setAppSecret(contentPassword);
		//    	mapper.selectByPrimaryKey("ssss"); 
//    	return  xuanjiInvocieService.invoice(invoiceHead);
    	return thirdInvoiceService.InvoiceElectron(invoiceHead, business);
    }
    
    @RequestMapping("/query")
    @InvoiceHeadValid 
    public InvoiceResult query( @RequestBody  InvoiceHead invoiceHead,HttpServletRequest request) {
    	
    	System.out.println("开始-----------------测试");
        BusinessVo business=new BusinessVo(); 
        business.setOrgId("2000");
        String appid = "6d29f136114544bcc73edcce960c430231183cc192c433e2b9ebcad56e8ceb08";//appid
		String contentPassword = "5EE6C2C11DD421F2";//AES加密密钥
		
		
		
        business.setAppId(appid);
        business.setAppSecret(contentPassword);
		//    	mapper.selectByPrimaryKey("ssss"); 
//    	return  xuanjiInvocieService.invoice(invoiceHead);
    	return thirdInvoiceService.invoiceQuery(invoiceHead, business);
    }
    
    
     
    @Autowired
	private DataSourceTransactionManager dataSourceTransactionManager;
	@Autowired
	private TransactionDefinition transactionDefinition;
    
    @RequestMapping("insert1")
    @Transactional(propagation = Propagation.REQUIRED)
    public Result testShiwu() {
    	TransactionStatus transactionStatus=dataSourceTransactionManager.getTransaction(transactionDefinition);
        InvoiceHead record = new InvoiceHead();
        record.setOrgId("2222");
        record.setDocNum("6667");
        mapper.insert(record );
      //手动提交
		dataSourceTransactionManager.commit(transactionStatus);
        try {
			int i = 1/0;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("发生异常,进行手动回滚！");
			//手动回滚事物
			dataSourceTransactionManager.rollback(transactionStatus);
			// TODO: handle exception
		}  
    	return Result.Success();
    }
    
    
    @RequestMapping("insert0")
    @Transactional(propagation = Propagation.REQUIRED)
    public Result testShiwu0() {
        InvoiceHead record = new InvoiceHead();
        record.setOrgId("2222");
        record.setDocNum("6667");
        mapper.insert(record );
        try {
			int i = 1/0;
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}  
    	return Result.Success();
    }
    
    @RequestMapping("insert2") 
    public Result testShiwu2() {
    	TransactionStatus transactionStatus=dataSourceTransactionManager.getTransaction(transactionDefinition);
        InvoiceHead record = new InvoiceHead();
        record.setOrgId("2222");
        record.setDocNum("6667");
        mapper.insert(record );
      //手动提交
		dataSourceTransactionManager.commit(transactionStatus);
        try {
			int i = 1/0;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("发生异常,进行手动回滚！");
			//手动回滚事物
			dataSourceTransactionManager.rollback(transactionStatus);
			// TODO: handle exception
		}  
    	return Result.Success();
    }
    
    
    @RequestMapping("insert3") 
    public Result testShiwu3() {
    	TransactionStatus transactionStatus=dataSourceTransactionManager.getTransaction(transactionDefinition);
        InvoiceHead record = new InvoiceHead();
        record.setOrgId("2222");
        record.setDocNum("6667");
        mapper.insert(record );
        try {
//			int i = 1/0;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("发生异常,进行手动回滚！");
			//手动回滚事物
			dataSourceTransactionManager.rollback(transactionStatus);
			// TODO: handle exception
		}  
    	return Result.Success();
    }
    
    
    @RequestMapping("insert4") 
    @Transactional
    public Result testShiwu4() { 
        InvoiceHead record = new InvoiceHead();
        record.setOrgId("2222");
        record.setDocNum("6667");
        mapper.insert(record );
        try {
			int i = 1/0;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("发生异常,进行手动回滚！");
			//手动回滚事物
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();  
			// TODO: handle exception
		}  
    	return Result.Success();
    }
    
    
}
