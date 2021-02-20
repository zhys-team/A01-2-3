/**
 * 
 */
package top.anets.service;

import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;

import top.anets.entity.InvoiceHead;
import top.anets.utils.Result;

/**
 * @author Administrator
 *
 */
public interface SapService {

	/**
	* 
	*@param orgId
	*@param docStatus
	*@param IV_N
	*@return 
	*/
	Object revoke(String orgId, String docStatus, String IV_N);
	
	


	/**
	* 回传单据
	*@param
	*@return 
	*/
	Result feedBackBill(String docNum);




	/**
	* 回传发票,并同步状态为已经同步
	*@param invoiceHead
	*@return 
	*/
	Result feedBackInvoice(InvoiceHead invoiceHead);




	/**
	* 只回传，不修改数据库同步状态
	*@param invoiceHead
	*@return 
	 * @throws JCoException 
	*/
	JCoFunction feedbackInvoiceHeadAndSplitlinesForUnlock(InvoiceHead invoiceHead) throws JCoException;

}
