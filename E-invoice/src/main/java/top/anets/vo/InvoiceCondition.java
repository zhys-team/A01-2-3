package top.anets.vo;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import lombok.Data;
import top.anets.entity.InvoiceHead;
import top.anets.entity.InvoiceSplitLine;

/**
 * @author Administrator
 */

@Data 
public class InvoiceCondition {
    private String startTime;
    private String endTime;
    private String orgId;
    private String custName;
    private String custIdBill;
    private String docNum;
    private String groupStatus;
    private String goldtaxNumStart;
    private String goldtaxNumEnd;
    private String goldtaxCode;
    private String  billDateStart;
    private String  billDateEnd;
    private String  billGdateStart;
    private String  billGdateEnd;
    
    private String  billGdateStart1;
    private String  billGdateEnd1;
    private String issync; 
    private String invoiceType; 
    private long rowCount; 
    @NotNull(message = "页码不能为空")
    private Integer pageSize;
    @NotNull(message = "页码不能为空")
    private Integer pageNum;
    
    private List<InvoiceSplitLine> invoices;
    
}
