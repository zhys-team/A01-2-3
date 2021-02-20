package com.invoice.fegin;

import java.util.Date;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fapiao.neon.model.CallResult;
import com.fapiao.neon.model.in.ApplyDeductResultInfo;
import com.invoice.po.IndexEntity;
import com.invoice.po.InvoiceHead;
import com.invoice.po.UserEntity;
import com.invoice.pojo.HeadList;
import com.invoice.pojo.IncomeCount;
import com.invoice.pojo.InvoiceHeadPoJo;
import com.invoice.pojo.InvoiceMsg;
import com.lycheeframework.core.cmp.kit.EasyPage;
import com.lycheeframework.core.cmp.kit.Pages;
import com.zhys.base.ResponseBase;
import com.zhys.po.DeductParamBody;
import com.zhys.po.DeductRecord;
import com.zhys.po.Dkhz;

@Component

@FeignClient(value="invoice",path="/invoiceHeads")
public interface InvoiceHeadServiceFegin  {	

	/**
     * 
     * @param t
     * @return
     */
	@PostMapping("/save")
	InvoiceHead save(@RequestBody InvoiceHead t);

	


    /**
     * 查询单个实体
     * @return
     */
	@GetMapping("/queryByEntity")
    InvoiceHead queryByEntity(InvoiceHead t);

    /**
     * 查询带分页
     * @param t page
     * @return
     */
	@PostMapping("/pagesByPojo")
    Pages<List<InvoiceHead>> pagesByPojo(@RequestBody InvoiceHeadPoJo t,@RequestParam("pageSize") Integer pageSize,@RequestParam("pageNum") Integer pageNum);
	
	
	/**
     * 查询
     * @param t
     * @return
     */
	@PostMapping("/queryList")
    List<InvoiceHead> queryList(@RequestBody InvoiceHead t);
	
	@PostMapping("/queryListByPoJo")
    List<InvoiceHead> queryListByPoJo(@RequestBody InvoiceHeadPoJo e);
	
	@PostMapping("/changeDelStateById")
	public Integer changeDelStateById(@RequestBody InvoiceHead t) ;

	@PostMapping("/changeDelStateByIds")
	public Integer changeDelStateByIds(@RequestBody InvoiceHeadPoJo t) ;
	
	
	/**
     * 获取主页指标信息
     * @param t
     * @return
     */
	@GetMapping("/indexMsg")
	List<IndexEntity> indexMsg(@RequestParam("orgId") String orgId,@RequestParam("kprqStart") String kprqStart,@RequestParam("kprqEnd") String kprqEnd,@RequestParam("mx") String mx );
	
	/**
     * 查验发票
     * @param String InvoiceCode,String InvoiceNumber,String BillingDate,String TotalAmount,String CheckCode,String fply
     * @return
     */
	@PostMapping("/checkInvoice")
	InvoiceMsg checkInvoice(@RequestParam(name = "openId",required=false)String openId,@RequestParam("invoiceCode") String invoiceCode,
			@RequestParam("invoiceNumber") String invoiceNumber,@RequestParam("billingDate") String billingDate,
			@RequestParam("totalAmount") String totalAmount,@RequestParam("checkCode") String CheckCode,
			@RequestParam("fply") String fply,@RequestParam("pdfUrl") String pdfUrl,@RequestParam("kind") String kind,@RequestParam("fplx") String fplx  );
	
	
	
	
	

	@PostMapping("/fileUpload")
    public String fileUpload(@RequestBody HeadList heads) ;
	
	@PostMapping("/jxtj")
    public List<IncomeCount> jxtj(@RequestBody IncomeCount incomeCount) ;
	
	/**
	 * 发票复查
	 */
	
	@GetMapping("/invoiceReview")
	public Object invoiceReview(@RequestParam("ids") String ids);
	
	/**
	 * 勾选
	 * @return 
	 */
	@PostMapping("/gx")
	public Object gx(@RequestBody DeductParamBody body) ;
	
	  @PostMapping({"/updateRzState"})
	  public Integer updateRzState(@RequestBody InvoiceHeadPoJo ih);
	  
	  @PostMapping({"/updateRzStateAll"})
	  public Integer updateRzStateAll(@RequestBody InvoiceHeadPoJo ih);
	  
	  @GetMapping({"/getDkhz"})
	  List<Dkhz> getDkhz(@RequestParam("nf") String nf);
	  
	  @PostMapping("/DeductPages")
	  Pages<List<DeductRecord>> DeductPages(@RequestBody DeductRecord body,@RequestParam("pageSize") Integer pageSize,@RequestParam("pageNum") Integer pageNum);
	  
	  @PostMapping("/cancel_gx")
		public Object cancel_gx(@RequestBody DeductParamBody body) ;
		
	  
}
