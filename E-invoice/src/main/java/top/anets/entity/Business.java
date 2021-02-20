package top.anets.entity;

public class Business {
    private Integer id;

    private Boolean isSap;

    private Integer invoiceWay;

    private Integer invoiceType;

    private String appId;

    private String appSecret;

    private String userCode;

    private String password;

    private Long wxLimit;

    private Long seLimit;

    private String orgName;

    private String orgId;

    private String orgTaxcode;

    private String orgMachine;

    private String orgMachineId;

    private Boolean isSkp;

    private Boolean isEnterprise;

    private Boolean isStrict;

    private String userType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIsSap() {
        return isSap;
    }

    public void setIsSap(Boolean isSap) {
        this.isSap = isSap;
    }

    public Integer getInvoiceWay() {
        return invoiceWay;
    }

    public void setInvoiceWay(Integer invoiceWay) {
        this.invoiceWay = invoiceWay;
    }

    public Integer getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Integer invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret == null ? null : appSecret.trim();
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Long getWxLimit() {
        return wxLimit;
    }

    public void setWxLimit(Long wxLimit) {
        this.wxLimit = wxLimit;
    }

    public Long getSeLimit() {
        return seLimit;
    }

    public void setSeLimit(Long seLimit) {
        this.seLimit = seLimit;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getOrgTaxcode() {
        return orgTaxcode;
    }

    public void setOrgTaxcode(String orgTaxcode) {
        this.orgTaxcode = orgTaxcode == null ? null : orgTaxcode.trim();
    }

    public String getOrgMachine() {
        return orgMachine;
    }

    public void setOrgMachine(String orgMachine) {
        this.orgMachine = orgMachine == null ? null : orgMachine.trim();
    }

    public String getOrgMachineId() {
        return orgMachineId;
    }

    public void setOrgMachineId(String orgMachineId) {
        this.orgMachineId = orgMachineId == null ? null : orgMachineId.trim();
    }

    public Boolean getIsSkp() {
        return isSkp;
    }

    public void setIsSkp(Boolean isSkp) {
        this.isSkp = isSkp;
    }

    public Boolean getIsEnterprise() {
        return isEnterprise;
    }

    public void setIsEnterprise(Boolean isEnterprise) {
        this.isEnterprise = isEnterprise;
    }

    public Boolean getIsStrict() {
        return isStrict;
    }

    public void setIsStrict(Boolean isStrict) {
        this.isStrict = isStrict;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType == null ? null : userType.trim();
    }
}