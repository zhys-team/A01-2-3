package top.anets.entity;

import java.math.BigDecimal;

public class InvoiceSplitLine extends InvoiceSplitLineKey {
    private String mandt;

    private String groupNum;

    private String groupStatus;

    private String goldtaxCode;

    private String goldtaxNum;

    private String itemName;

    private String itemSpec;

    private String unitName;

    private String taxCatecode;

    private BigDecimal quantity;

    private BigDecimal taxRate;

    private String giftFlag;

    private BigDecimal zamountHsj;

    private BigDecimal zamountWsj;

    private BigDecimal zamountSej;

    private BigDecimal zamountHzhs;

    private BigDecimal zamountHzws;

    private BigDecimal zamountHzse;

    private BigDecimal zamountHsy;

    private BigDecimal zamountWsy;

    private BigDecimal zamountSey;

    private BigDecimal zpriceHsj;

    private BigDecimal zpriceWsj;

    private BigDecimal zpriceHsy;

    private BigDecimal zpriceWsy;

    private String billDate;

    private String cancelDate;

    private String billGdate;

    private String cancelGdate;

    private String groupCopyfrom;

    private String attribute1;

    private String attribute2;

    private String attribute3;

    private String dayin;

    private String jianyan;

    private String hsje;

    private String wsje;

    private String se;

    private String hsjec;

    private String wsjec;

    private String sec;

    private String groupid;

    private String invoiceList;

    private String eInvId;

    private String eInvMsg;

    private String ePdfUrl;

    private String billSdate;

    private String invoiceRedXxbm;

    private String invoiceRedFpdm;

    private String invoiceRedFphm;

    private String docNuml;

    private String billRemark;

    private String exnum;

    private String attributf1;

    private String exid;

    private String exbz;
    
    
    private InvoiceHead invoiceHead;
    

    public InvoiceHead getInvoiceHead() {
		return invoiceHead;
	}

	public void setInvoiceHead(InvoiceHead invoiceHead) {
		this.invoiceHead = invoiceHead;
	}

	public String getMandt() {
        return mandt;
    }

    public void setMandt(String mandt) {
        this.mandt = mandt == null ? null : mandt.trim();
    }

    public String getGroupNum() {
        return groupNum;
    }

    @Override
	public String toString() {
		return "InvoiceSplitLine [mandt=" + mandt + ", groupNum=" + groupNum + ", groupStatus=" + groupStatus
				+ ", goldtaxCode=" + goldtaxCode + ", goldtaxNum=" + goldtaxNum + ", itemName=" + itemName
				+ ", itemSpec=" + itemSpec + ", unitName=" + unitName + ", taxCatecode=" + taxCatecode + ", quantity="
				+ quantity + ", taxRate=" + taxRate + ", giftFlag=" + giftFlag + ", zamountHsj=" + zamountHsj
				+ ", zamountWsj=" + zamountWsj + ", zamountSej=" + zamountSej + ", zamountHzhs=" + zamountHzhs
				+ ", zamountHzws=" + zamountHzws + ", zamountHzse=" + zamountHzse + ", zamountHsy=" + zamountHsy
				+ ", zamountWsy=" + zamountWsy + ", zamountSey=" + zamountSey + ", zpriceHsj=" + zpriceHsj
				+ ", zpriceWsj=" + zpriceWsj + ", zpriceHsy=" + zpriceHsy + ", zpriceWsy=" + zpriceWsy + ", billDate="
				+ billDate + ", cancelDate=" + cancelDate + ", billGdate=" + billGdate + ", cancelGdate=" + cancelGdate
				+ ", groupCopyfrom=" + groupCopyfrom + ", attribute1=" + attribute1 + ", attribute2=" + attribute2
				+ ", attribute3=" + attribute3 + ", dayin=" + dayin + ", jianyan=" + jianyan + ", hsje=" + hsje
				+ ", wsje=" + wsje + ", se=" + se + ", hsjec=" + hsjec + ", wsjec=" + wsjec + ", sec=" + sec
				+ ", groupid=" + groupid + ", invoiceList=" + invoiceList + ", eInvId=" + eInvId + ", eInvMsg="
				+ eInvMsg + ", ePdfUrl=" + ePdfUrl + ", billSdate=" + billSdate + ", invoiceRedXxbm=" + invoiceRedXxbm
				+ ", invoiceRedFpdm=" + invoiceRedFpdm + ", invoiceRedFphm=" + invoiceRedFphm + ", docNuml=" + docNuml
				+ ", billRemark=" + billRemark + ", exnum=" + exnum + ", attributf1=" + attributf1 + ", exid=" + exid
				+ ", exbz=" + exbz + "]";
	}

	public void setGroupNum(String groupNum) {
        this.groupNum = groupNum == null ? null : groupNum.trim();
    }

    public String getGroupStatus() {
        return groupStatus;
    }

    public void setGroupStatus(String groupStatus) {
        this.groupStatus = groupStatus == null ? null : groupStatus.trim();
    }

    public String getGoldtaxCode() {
        return goldtaxCode;
    }

    public void setGoldtaxCode(String goldtaxCode) {
        this.goldtaxCode = goldtaxCode == null ? null : goldtaxCode.trim();
    }

    public String getGoldtaxNum() {
        return goldtaxNum;
    }

    public void setGoldtaxNum(String goldtaxNum) {
        this.goldtaxNum = goldtaxNum == null ? null : goldtaxNum.trim();
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName == null ? null : itemName.trim();
    }

    public String getItemSpec() {
        return itemSpec;
    }

    public void setItemSpec(String itemSpec) {
        this.itemSpec = itemSpec == null ? null : itemSpec.trim();
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName == null ? null : unitName.trim();
    }

    public String getTaxCatecode() {
        return taxCatecode;
    }

    public void setTaxCatecode(String taxCatecode) {
        this.taxCatecode = taxCatecode == null ? null : taxCatecode.trim();
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public String getGiftFlag() {
        return giftFlag;
    }

    public void setGiftFlag(String giftFlag) {
        this.giftFlag = giftFlag == null ? null : giftFlag.trim();
    }

    public BigDecimal getZamountHsj() {
        return zamountHsj;
    }

    public void setZamountHsj(BigDecimal zamountHsj) {
        this.zamountHsj = zamountHsj;
    }

    public BigDecimal getZamountWsj() {
        return zamountWsj;
    }

    public void setZamountWsj(BigDecimal zamountWsj) {
        this.zamountWsj = zamountWsj;
    }

    public BigDecimal getZamountSej() {
        return zamountSej;
    }

    public void setZamountSej(BigDecimal zamountSej) {
        this.zamountSej = zamountSej;
    }

    public BigDecimal getZamountHzhs() {
        return zamountHzhs;
    }

    public void setZamountHzhs(BigDecimal zamountHzhs) {
        this.zamountHzhs = zamountHzhs;
    }

    public BigDecimal getZamountHzws() {
        return zamountHzws;
    }

    public void setZamountHzws(BigDecimal zamountHzws) {
        this.zamountHzws = zamountHzws;
    }

    public BigDecimal getZamountHzse() {
        return zamountHzse;
    }

    public void setZamountHzse(BigDecimal zamountHzse) {
        this.zamountHzse = zamountHzse;
    }

    public BigDecimal getZamountHsy() {
        return zamountHsy;
    }

    public void setZamountHsy(BigDecimal zamountHsy) {
        this.zamountHsy = zamountHsy;
    }

    public BigDecimal getZamountWsy() {
        return zamountWsy;
    }

    public void setZamountWsy(BigDecimal zamountWsy) {
        this.zamountWsy = zamountWsy;
    }

    public BigDecimal getZamountSey() {
        return zamountSey;
    }

    public void setZamountSey(BigDecimal zamountSey) {
        this.zamountSey = zamountSey;
    }

    public BigDecimal getZpriceHsj() {
        return zpriceHsj;
    }

    public void setZpriceHsj(BigDecimal zpriceHsj) {
        this.zpriceHsj = zpriceHsj;
    }

    public BigDecimal getZpriceWsj() {
        return zpriceWsj;
    }

    public void setZpriceWsj(BigDecimal zpriceWsj) {
        this.zpriceWsj = zpriceWsj;
    }

    public BigDecimal getZpriceHsy() {
        return zpriceHsy;
    }

    public void setZpriceHsy(BigDecimal zpriceHsy) {
        this.zpriceHsy = zpriceHsy;
    }

    public BigDecimal getZpriceWsy() {
        return zpriceWsy;
    }

    public void setZpriceWsy(BigDecimal zpriceWsy) {
        this.zpriceWsy = zpriceWsy;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate == null ? null : billDate.trim();
    }

    public String getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(String cancelDate) {
        this.cancelDate = cancelDate == null ? null : cancelDate.trim();
    }

    public String getBillGdate() {
        return billGdate;
    }

    public void setBillGdate(String billGdate) {
        this.billGdate = billGdate == null ? null : billGdate.trim();
    }

    public String getCancelGdate() {
        return cancelGdate;
    }

    public void setCancelGdate(String cancelGdate) {
        this.cancelGdate = cancelGdate == null ? null : cancelGdate.trim();
    }

    public String getGroupCopyfrom() {
        return groupCopyfrom;
    }

    public void setGroupCopyfrom(String groupCopyfrom) {
        this.groupCopyfrom = groupCopyfrom == null ? null : groupCopyfrom.trim();
    }

    public String getAttribute1() {
        return attribute1;
    }

    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1 == null ? null : attribute1.trim();
    }

    public String getAttribute2() {
        return attribute2;
    }

    public void setAttribute2(String attribute2) {
        this.attribute2 = attribute2 == null ? null : attribute2.trim();
    }

    public String getAttribute3() {
        return attribute3;
    }

    public void setAttribute3(String attribute3) {
        this.attribute3 = attribute3 == null ? null : attribute3.trim();
    }

    public String getDayin() {
        return dayin;
    }

    public void setDayin(String dayin) {
        this.dayin = dayin == null ? null : dayin.trim();
    }

    public String getJianyan() {
        return jianyan;
    }

    public void setJianyan(String jianyan) {
        this.jianyan = jianyan == null ? null : jianyan.trim();
    }

    public String getHsje() {
        return hsje;
    }

    public void setHsje(String hsje) {
        this.hsje = hsje == null ? null : hsje.trim();
    }

    public String getWsje() {
        return wsje;
    }

    public void setWsje(String wsje) {
        this.wsje = wsje == null ? null : wsje.trim();
    }

    public String getSe() {
        return se;
    }

    public void setSe(String se) {
        this.se = se == null ? null : se.trim();
    }

    public String getHsjec() {
        return hsjec;
    }

    public void setHsjec(String hsjec) {
        this.hsjec = hsjec == null ? null : hsjec.trim();
    }

    public String getWsjec() {
        return wsjec;
    }

    public void setWsjec(String wsjec) {
        this.wsjec = wsjec == null ? null : wsjec.trim();
    }

    public String getSec() {
        return sec;
    }

    public void setSec(String sec) {
        this.sec = sec == null ? null : sec.trim();
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid == null ? null : groupid.trim();
    }

    public String getInvoiceList() {
        return invoiceList;
    }

    public void setInvoiceList(String invoiceList) {
        this.invoiceList = invoiceList == null ? null : invoiceList.trim();
    }

    public String geteInvId() {
        return eInvId;
    }

    public void seteInvId(String eInvId) {
        this.eInvId = eInvId == null ? null : eInvId.trim();
    }

    public String geteInvMsg() {
        return eInvMsg;
    }

    public void seteInvMsg(String eInvMsg) {
        this.eInvMsg = eInvMsg == null ? null : eInvMsg.trim();
    }

    public String getePdfUrl() {
        return ePdfUrl;
    }

    public void setePdfUrl(String ePdfUrl) {
        this.ePdfUrl = ePdfUrl == null ? null : ePdfUrl.trim();
    }

    public String getBillSdate() {
        return billSdate;
    }

    public void setBillSdate(String billSdate) {
        this.billSdate = billSdate == null ? null : billSdate.trim();
    }

    public String getInvoiceRedXxbm() {
        return invoiceRedXxbm;
    }

    public void setInvoiceRedXxbm(String invoiceRedXxbm) {
        this.invoiceRedXxbm = invoiceRedXxbm == null ? null : invoiceRedXxbm.trim();
    }

    public String getInvoiceRedFpdm() {
        return invoiceRedFpdm;
    }

    public void setInvoiceRedFpdm(String invoiceRedFpdm) {
        this.invoiceRedFpdm = invoiceRedFpdm == null ? null : invoiceRedFpdm.trim();
    }

    public String getInvoiceRedFphm() {
        return invoiceRedFphm;
    }

    public void setInvoiceRedFphm(String invoiceRedFphm) {
        this.invoiceRedFphm = invoiceRedFphm == null ? null : invoiceRedFphm.trim();
    }

    public String getDocNuml() {
        return docNuml;
    }

    public void setDocNuml(String docNuml) {
        this.docNuml = docNuml == null ? null : docNuml.trim();
    }

    public String getBillRemark() {
        return billRemark;
    }

    public void setBillRemark(String billRemark) {
        this.billRemark = billRemark == null ? null : billRemark.trim();
    }

    public String getExnum() {
        return exnum;
    }

    public void setExnum(String exnum) {
        this.exnum = exnum == null ? null : exnum.trim();
    }

    public String getAttributf1() {
        return attributf1;
    }

    public void setAttributf1(String attributf1) {
        this.attributf1 = attributf1 == null ? null : attributf1.trim();
    }

    public String getExid() {
        return exid;
    }

    public void setExid(String exid) {
        this.exid = exid == null ? null : exid.trim();
    }

    public String getExbz() {
        return exbz;
    }

    public void setExbz(String exbz) {
        this.exbz = exbz == null ? null : exbz.trim();
    }
}