package top.anets.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.anets.entity.InvoiceHead;
import top.anets.entity.InvoiceHeadExample;

@Mapper
public interface InvoiceHeadMapper {
    int countByExample(InvoiceHeadExample example);

    int deleteByExample(InvoiceHeadExample example);

    int deleteByPrimaryKey(String docNum);

    int insert(InvoiceHead record);

    int insertSelective(InvoiceHead record);

    List<InvoiceHead> selectByExample(InvoiceHeadExample example);

    InvoiceHead selectByPrimaryKey(String docNum);

    int updateByExampleSelective(@Param("record") InvoiceHead record, @Param("example") InvoiceHeadExample example);

    int updateByExample(@Param("record") InvoiceHead record, @Param("example") InvoiceHeadExample example);

    int updateByPrimaryKeySelective(InvoiceHead record);

    int updateByPrimaryKey(InvoiceHead record);

	/**
	* 查询正在开票的数据
	*@return 
	*/
	List<InvoiceHead> getInvoicingInvoiceHead(String orgId, String orgMachine,Integer invoiceType);

	
	
	/**
	* 
	*@param orgId
	*@param orgMachine
	*@param string
	*@return 
	*/
	List<InvoiceHead> getInvoiceData(String orgId, String orgMachine, String docStatus,Integer invoiceType);

	/**
	* 查询正在作废的数据源
	*@return 
	*/
	List<InvoiceHead> getInvalidingInvoice(String orgId, String orgMachine,Integer invoiceType);

	/**
	* 
	*@param head
	*@param date
	*@return 
	*/
	List<InvoiceHead> unlock(InvoiceHead head, String date);
}