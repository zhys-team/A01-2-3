package top.anets.mapper;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.anets.entity.InvoiceSplitLine;
import top.anets.entity.InvoiceSplitLineExample;
import top.anets.entity.InvoiceSplitLineKey;
import top.anets.vo.InvoiceCondition;
@Mapper
public interface InvoiceSplitLineMapper {
    int countByExample(InvoiceSplitLineExample example);

    int deleteByExample(InvoiceSplitLineExample example);

    int deleteByPrimaryKey(InvoiceSplitLineKey key);

    int insert(InvoiceSplitLine record);

    int insertSelective(InvoiceSplitLine record);

    List<InvoiceSplitLine> selectByExample(InvoiceSplitLineExample example);

    InvoiceSplitLine selectByPrimaryKey(InvoiceSplitLineKey key);

    int updateByExampleSelective(@Param("record") InvoiceSplitLine record, @Param("example") InvoiceSplitLineExample example);

    int updateByExample(@Param("record") InvoiceSplitLine record, @Param("example") InvoiceSplitLineExample example);

    int updateByPrimaryKeySelective(InvoiceSplitLine record);

    int updateByPrimaryKey(InvoiceSplitLine record);

	/**
	* 
	*@param invoiceHeadCondition
	*@return 
	*/
	List<InvoiceSplitLine> getInvoices(InvoiceCondition invoiceCondition);

	/**  
	*@param 统计 所在组织 所在 天 的  已开发票数量
	*@return 
	*/
	List<InvoiceSplitLine> countInvoicedDay(String orgId ,String day1,String day2);
	List<InvoiceSplitLine> countInvalidDay(String orgId ,String day1,String day2);

	List<InvoiceSplitLine> count2InvoiceDay(String orgId ,String day1,String day2, String start1, String start2);
	List<InvoiceSplitLine> count9InvoiceDay(String orgId ,String day1,String day2, String start1, String start2);
 
	/**
	* 
	*@param docNum
	*@param object
	*@return 
	*/
	List<InvoiceSplitLine> countInvoiced(String docNum, String orgId);

	/**
	* 
	*@param orgId
	*@param object
	*@param day4
	*@param start1
	*@param start2
	*@return 
	*/
	List<InvoiceSplitLine> countWaitTaskDay(String orgId, String day1, String day2, String start1, String start2);
	List<InvoiceSplitLine> countWaitInvoice(String orgId, String day1, String day2, String start1, String start2);
	List<InvoiceSplitLine> countWaitInvalid(String orgId, String day1, String day2, String start1, String start2);

	/**
	* 
	*@param start
	*@param end
	*@return 
	*/
	Integer countMoneyInvoicedHsjZhen(String orgId,String start1, String end1,String start2,String end2);
	
	Integer countMoneyInvoicedHsjFu(String orgId,String start1, String end1,String start2,String end2);
	Integer countMoneyInvoicedSe(String orgId,String start1, String end1,String start2,String end2);
	Integer countMoneyInvoicedZheKou(String orgId,String start1, String end1,String start2,String end2);
	Integer countMoneyAll(String orgId,String start1, String end1,String start2,String end2);
	Integer countMoneyInvalidHsj(String orgId,String start1, String end1,String start2,String end2);

	/**
	* 
	*@param orgId
	*@param month_end1
	*@param month_end2
	*@param month_start1
	*@param month_start2
	*@return 
	*/
	List<InvoiceSplitLine> countSyncd(String orgId, String day1, String day2, String start1, String start2);

	/**
	* 
	*@param orgId
	*@param month_end1
	*@param month_end2
	*@param month_start1
	*@param month_start2
	*@return 
	*/
	List<InvoiceSplitLine> countWaitSync(String orgId, String day1, String day2, String start1, String start2);
	
	List<HashMap<String,Object>> getStaticNum(String orgId, String dateStart);
	
	List<InvoiceSplitLine> countDayined(String orgId, String day1, String day2, String start1, String start2);
	List<InvoiceSplitLine> countWaitDayin(String orgId, String day1, String day2, String start1, String start2);

	/**
	* 
	*@param splitLine 
	*/
	void changeDelStateById(InvoiceSplitLine splitLine);

	/**
	* 
	*@param orgId
	*@return 
	*/
	 List<Map<String, Object>> countWxAndSeThisMouth(String orgId,String mouth1,String mouth2);

	
}