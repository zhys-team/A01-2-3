/**
 * 
 */
package top.anets.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import top.anets.entity.Business;
import top.anets.entity.InvoiceHead;
import top.anets.entity.InvoiceSplitLine;
import top.anets.utils.Result;
import top.anets.vo.BusinessVo;
import top.anets.vo.DeviceInfo;

/**
 * @author Administrator
 *
 */
public interface InvoiceService {  
  /**
   * 发票开具
   * 返回结果，success代表成功，有开票数据 。success是false为失败，如果 code 等于 1000 ，代表 已经开过了，需要 去查询数据
   */
	Result invoice(InvoiceHead invoiceHead, BusinessVo business);
    
    
   /**
     * 保存发票数据
     */
    Result save(InvoiceHead invoiceHead);



/**
* 查询正在开票的数据
*@return 
*/
    List<InvoiceHead> getInvoicingInvoiceHead(BusinessVo business);



/**
*  查询并处理发票结果
*@param invoicingInvoiceHeads 
 * @return 
*/
    Result queryAndHandleInvoiceResult(InvoiceHead invoiceHead,BusinessVo business);
    
   
/**
 * 更新 等待开票的数据
* 
*@param business
*@return
 */
    public void updateWaitToInvoiceData(BusinessVo business);
    
    
   
    /**
     * 根据docNum和 groupNum 更新发票信息
     */
    public void updateSplitLines(InvoiceSplitLine splitLine);

    

	/**
	*使对应的发票 处在正在开票的状态
	*@param docNum
	*@param groupNum 
	*/
	void updateSplitLinesInvoicing(String docNum, String groupNum);



	/**
	* 更新发票信息
	*@param invoiced 
	*/
	void updateInvoiceData(InvoiceHead invoiced);



	/** 
	* 使对应的发票 从 正在开票的状态 恢复 正常
	*@param invoiceHead 
	*/
	void updateSplitLinesInvoicingRecoverNomal(InvoiceHead invoiceHead);


	/**
	* 
	*@param docNum
	*@return 
	*/
	InvoiceHead getInvoiceHead(String docNum);


	/**
	* 
	*@param invoice
	*@return 
	*/
	Result invalid(InvoiceHead invoice,Business business);


	/**
	* 
	*@param business 
	*/
	void updateWaitToInvalidData(BusinessVo business);


	/** 
	*@return 
	*/
	List<InvoiceHead> getInvalidingInvoice(BusinessVo business);


	/**
	* 
	*@param docNum
	*@param groupNum 
	*/
	void updateSplitLinesInvaling(String docNum, String groupNum);


	/**
	* 
	*@param invoice 
	*/
	void updateSplitLinesInvalidRecoverNomal(InvoiceHead invoice);


	/**
	* 
	*@param invoiceHead
	*@return 
	*/
	List<InvoiceHead> group(InvoiceHead invoiceHead);

 
	


    /**
     * 获取设备信息
     */
    BusinessVo getDeviceInfo(BusinessVo businessVo);


	/**
	* 
	*@param docNum
	*@return 
	*/
	int countInvoiced(String docNum);


	/**
	* 
	*@param orgId
	*@return 
	*/
	Map<String, Object> countWxAndSeThisMouth(String orgId);

 
    
    
     
    
 
}
