package top.anets.vo;

import java.math.BigDecimal;

import top.anets.utils.Result;
import top.anets.utils.Status;

/**
 * @author Administrator
 *
 */
public class InvoiceResult {
	
	/**
	 * @param success
	 * @param status
	 * @param data
	 */
	public InvoiceResult() {  
    }
	public InvoiceResult(Boolean success, String code ,String msg, Object data) { 
		this.success=success;
		this.code=code;
		this.msg=msg;
		this.data=data;
	}
	
	
	@Override
	public String toString() {
		return "InvoiceResult [data=" + data + ", code=" + code + ", success=" + success + ", msg=" + msg
				+ ", groupStatus=" + groupStatus + ", docNum=" + docNum + ", groupNum=" + groupNum + ", goldtaxCode="
				+ goldtaxCode + ", goldtaxNum=" + goldtaxNum + ", billGdate=" + billGdate + ", hjje=" + hjje + ", hjse="
				+ hjse + ", jshj=" + jshj + ", ePdfUrl=" + ePdfUrl + "]";
	}
	public static InvoiceResult Success(String code ,String msg, Object data) { 
		return new InvoiceResult(true, code, msg, data);
	}
	
	public static InvoiceResult Error(String code ,String msg, Object data) { 
		return new InvoiceResult(false, code, msg, data);
	}
	
	public static InvoiceResult Success(String msg, Object data) { 
		return new InvoiceResult(true,null, msg, data);
	}
	
	public static InvoiceResult Error( String msg, Object data) { 
		return new InvoiceResult(false, null, msg, data);
	}
	
	public static InvoiceResult Success(String msg ) { 
		return new InvoiceResult(true,null, msg,null);
	}
	
	public static InvoiceResult Error( String msg) { 
		return new InvoiceResult(false, null, msg, null);
	}
	
 

	private Object data;
	private String code;

	public Boolean success;
	
    public String msg;
      
    
    private String groupStatus;
    /**
     * 单据编码
     */
    private String docNum;
    /**
     * 单据编码
     */
    private String groupNum;

    
     
    /**
     * 金税发票代码
     */
    private String goldtaxCode;

    /**
     * 金税发票号码
     */
    private String goldtaxNum;

 
    /**
     * 金税开票日期
     */
    private String billGdate;

    
    /**
     * 独有属性     :合计金额
    *@return
     */
    private String hjje  ;
    
    /**
     * 独有属性     :合计税额 
    *@return
     */
    
    private String hjse  ;
    
    /**
     * 独有属性     :价税合计  
    *@return
     */
    private String jshj ;
    
    
    /*
     * 电子发票属性
     */
    private String ePdfUrl;
    
   
	 
	public String getGroupStatus() {
		return groupStatus;
	}
	public void setGroupStatus(String groupStatus) {
		this.groupStatus = groupStatus;
	}
	 

 

	 
	public String getCode() {
		return code;
	}

	 
	public void setCode(String code) {
		this.code = code;
	}

	public Boolean getSuccess() {
		return success;
	} 
	
	 
	public String getHjje() { 
		return hjje;
	}
	
	public void setHjje(String hjje) {
		this.hjje = hjje;
	}
	public String getHjse() {
		return hjse;
	}
	public void setHjse(String hjse) {
		this.hjse = hjse;
	}
	public String getJshj() {
		return jshj;
	}
	public void setJshj(String jshj) {
		this.jshj = jshj;
	}
	public String getePdfUrl() {
		return ePdfUrl;
	}
	public void setePdfUrl(String ePdfUrl) {
		this.ePdfUrl = ePdfUrl;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public String getGroupNum() {
		return groupNum;
	}

	public void setGroupNum(String groupNum) {
		this.groupNum = groupNum;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getDocNum() {
		return docNum;
	}

	public void setDocNum(String docNum) {
		this.docNum = docNum;
	}

	public String getGoldtaxCode() {
		return goldtaxCode;
	}

	
	public void setGoldtaxCode(String goldtaxCode) {
		this.goldtaxCode = goldtaxCode;
	}
    
	
	public String getGoldtaxNum() {
		return goldtaxNum;
	}

	public void setGoldtaxNum(String goldtaxNum) {
		this.goldtaxNum = goldtaxNum;
	}
	

	public String getBillGdate() {
		return billGdate;
	}

	public void setBillGdate(String billGdate) {
		this.billGdate = billGdate;
	}





	

 



    public Object getData() {
		return data;
	}
    
    
    public void setData(Object data) {
		 this.data=data;
	}
    
   
}
