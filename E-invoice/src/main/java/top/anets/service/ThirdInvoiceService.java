/**
 * 
 */
package top.anets.service;

import top.anets.entity.Business;
import top.anets.entity.InvoiceHead;
import top.anets.utils.Result;
import top.anets.vo.BusinessVo;
import top.anets.vo.DeviceInfo;
import top.anets.vo.InvoiceResult;

/**
 * @author Administrator
 *开票服务
 */
public interface ThirdInvoiceService {
	/**
	 * 
	* 初始化工作，比如获取token，开票码
	*@return
	 */
	Result init();
  /**
   * 发票开具
   * 返回结果，success代表成功，有开票数据 。success是false为失败，如果 code 等于 1000 ，代表 已经开过了，需要 去查询数据
   */
    InvoiceResult invoice(InvoiceHead invoiceHead,BusinessVo business);
    
    
    
    /**
     * 专门用来开电子发票
    * 
    *@param invoiceHead
    *@param business
    *@return
     */
    InvoiceResult InvoiceElectron(InvoiceHead invoiceHead,BusinessVo business);
    
     
  /**
   * 发票作废
   */ 
	Result invalid(InvoiceHead invoiceHead, Business business);
	/**
	* 发票查询
	* 查询成功返回true，查询失败返回false，失败中 如果 状态码 是  -1 ,代表 没有 此信息 ，否则为查询异常
	*@param docNum
	*@param groupNum  如果没有写空串
	*@param invoiceType
	*@return 
	*/
	InvoiceResult invoiceQuery(InvoiceHead invoiceHead, BusinessVo business);
    /**
     * 发票查询
     */
    Result invoiceQueryByGoldTaxNum(String goldTaxNum,String invoiceType,Business business);
	/**
	* 
	*@param invoiceHead
	*@param business
	*@return 
	*/
	/**
	* 
	*@param businessVo
	*@return 
	*/
	BusinessVo getDeviceInfo(BusinessVo businessVo);
	
	
	

}
