/**
 * 
 */
package top.anets.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import top.anets.utils.Result;
import top.anets.vo.BusinessVo;
import top.anets.vo.Invoice;
import top.anets.vo.InvoiceCondition;

/**
 * @author Administrator
 *
 */
public interface InvoiceViewService { 
	/**
	* 
	*@param invoiceCondition
	*@return 
	*/
	InvoiceCondition getInvoices(@Valid InvoiceCondition invoiceCondition);

	/**
	* 
	*@param 解锁发票 
	*@return 
	*/
	Result unlock(Invoice invoice);

	/**
	* 
	*@param docNum
	*@return 
	*/
	Result toInvoice(String docNum);

	/**
	* 
	*@param docNum
	*@param groupNum
	*@return 
	*/
	Result toInvoice(String docNum, String groupNum);
	
	
	/**
	 * 统计近30天的，最后1天的要动态的数据
	 * 统计每天的开票量，每天的作废量，每天开的专票量，每天开的普票量 ，每天待开/作废量
	 */
  
	Result statisticalTask(String orgId, Boolean refresh);

	/**
	* 
	*@param orgId
	*@param refresh
	*@return 
	*/
	Result statisticalMoney(String orgId, Boolean refresh);

	
	/**
	 * 统计当天的任务
	* 
	*@param businessVo
	*@param day
	*@return
	 */
	Map<String, Long> getTask(String orgId, Integer day);
	 
	/**
	* 
	*@param orgId
	*@param refresh
	*@param time
	*@return 
	*/
	Result statisticalHealth(String orgId, Boolean refresh, String time);

	/**
	* 
	*@param docNum
	*@param groupNum
	*@return 
	*/
	Result getInvoiceDetail(@NotBlank String docNum, @NotBlank String groupNum);

	 
 

	/**
	* 
	*@param orgId
	*@param yyyyMM
	*@return 
	*/
	Result undoCommited(String orgId, String yyyyMM);
}
