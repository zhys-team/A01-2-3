package top.anets.entity;

import java.util.ArrayList;
import java.util.List;

public class BusinessExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BusinessExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIsSapIsNull() {
            addCriterion("is_sap is null");
            return (Criteria) this;
        }

        public Criteria andIsSapIsNotNull() {
            addCriterion("is_sap is not null");
            return (Criteria) this;
        }

        public Criteria andIsSapEqualTo(Boolean value) {
            addCriterion("is_sap =", value, "isSap");
            return (Criteria) this;
        }

        public Criteria andIsSapNotEqualTo(Boolean value) {
            addCriterion("is_sap <>", value, "isSap");
            return (Criteria) this;
        }

        public Criteria andIsSapGreaterThan(Boolean value) {
            addCriterion("is_sap >", value, "isSap");
            return (Criteria) this;
        }

        public Criteria andIsSapGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_sap >=", value, "isSap");
            return (Criteria) this;
        }

        public Criteria andIsSapLessThan(Boolean value) {
            addCriterion("is_sap <", value, "isSap");
            return (Criteria) this;
        }

        public Criteria andIsSapLessThanOrEqualTo(Boolean value) {
            addCriterion("is_sap <=", value, "isSap");
            return (Criteria) this;
        }

        public Criteria andIsSapIn(List<Boolean> values) {
            addCriterion("is_sap in", values, "isSap");
            return (Criteria) this;
        }

        public Criteria andIsSapNotIn(List<Boolean> values) {
            addCriterion("is_sap not in", values, "isSap");
            return (Criteria) this;
        }

        public Criteria andIsSapBetween(Boolean value1, Boolean value2) {
            addCriterion("is_sap between", value1, value2, "isSap");
            return (Criteria) this;
        }

        public Criteria andIsSapNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_sap not between", value1, value2, "isSap");
            return (Criteria) this;
        }

        public Criteria andInvoiceWayIsNull() {
            addCriterion("invoice_way is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceWayIsNotNull() {
            addCriterion("invoice_way is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceWayEqualTo(Integer value) {
            addCriterion("invoice_way =", value, "invoiceWay");
            return (Criteria) this;
        }

        public Criteria andInvoiceWayNotEqualTo(Integer value) {
            addCriterion("invoice_way <>", value, "invoiceWay");
            return (Criteria) this;
        }

        public Criteria andInvoiceWayGreaterThan(Integer value) {
            addCriterion("invoice_way >", value, "invoiceWay");
            return (Criteria) this;
        }

        public Criteria andInvoiceWayGreaterThanOrEqualTo(Integer value) {
            addCriterion("invoice_way >=", value, "invoiceWay");
            return (Criteria) this;
        }

        public Criteria andInvoiceWayLessThan(Integer value) {
            addCriterion("invoice_way <", value, "invoiceWay");
            return (Criteria) this;
        }

        public Criteria andInvoiceWayLessThanOrEqualTo(Integer value) {
            addCriterion("invoice_way <=", value, "invoiceWay");
            return (Criteria) this;
        }

        public Criteria andInvoiceWayIn(List<Integer> values) {
            addCriterion("invoice_way in", values, "invoiceWay");
            return (Criteria) this;
        }

        public Criteria andInvoiceWayNotIn(List<Integer> values) {
            addCriterion("invoice_way not in", values, "invoiceWay");
            return (Criteria) this;
        }

        public Criteria andInvoiceWayBetween(Integer value1, Integer value2) {
            addCriterion("invoice_way between", value1, value2, "invoiceWay");
            return (Criteria) this;
        }

        public Criteria andInvoiceWayNotBetween(Integer value1, Integer value2) {
            addCriterion("invoice_way not between", value1, value2, "invoiceWay");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeIsNull() {
            addCriterion("invoice_type is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeIsNotNull() {
            addCriterion("invoice_type is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeEqualTo(Integer value) {
            addCriterion("invoice_type =", value, "invoiceType");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeNotEqualTo(Integer value) {
            addCriterion("invoice_type <>", value, "invoiceType");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeGreaterThan(Integer value) {
            addCriterion("invoice_type >", value, "invoiceType");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("invoice_type >=", value, "invoiceType");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeLessThan(Integer value) {
            addCriterion("invoice_type <", value, "invoiceType");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeLessThanOrEqualTo(Integer value) {
            addCriterion("invoice_type <=", value, "invoiceType");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeIn(List<Integer> values) {
            addCriterion("invoice_type in", values, "invoiceType");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeNotIn(List<Integer> values) {
            addCriterion("invoice_type not in", values, "invoiceType");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeBetween(Integer value1, Integer value2) {
            addCriterion("invoice_type between", value1, value2, "invoiceType");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("invoice_type not between", value1, value2, "invoiceType");
            return (Criteria) this;
        }

        public Criteria andAppIdIsNull() {
            addCriterion("app_id is null");
            return (Criteria) this;
        }

        public Criteria andAppIdIsNotNull() {
            addCriterion("app_id is not null");
            return (Criteria) this;
        }

        public Criteria andAppIdEqualTo(String value) {
            addCriterion("app_id =", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotEqualTo(String value) {
            addCriterion("app_id <>", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdGreaterThan(String value) {
            addCriterion("app_id >", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdGreaterThanOrEqualTo(String value) {
            addCriterion("app_id >=", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdLessThan(String value) {
            addCriterion("app_id <", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdLessThanOrEqualTo(String value) {
            addCriterion("app_id <=", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdLike(String value) {
            addCriterion("app_id like", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotLike(String value) {
            addCriterion("app_id not like", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdIn(List<String> values) {
            addCriterion("app_id in", values, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotIn(List<String> values) {
            addCriterion("app_id not in", values, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdBetween(String value1, String value2) {
            addCriterion("app_id between", value1, value2, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotBetween(String value1, String value2) {
            addCriterion("app_id not between", value1, value2, "appId");
            return (Criteria) this;
        }

        public Criteria andAppSecretIsNull() {
            addCriterion("app_secret is null");
            return (Criteria) this;
        }

        public Criteria andAppSecretIsNotNull() {
            addCriterion("app_secret is not null");
            return (Criteria) this;
        }

        public Criteria andAppSecretEqualTo(String value) {
            addCriterion("app_secret =", value, "appSecret");
            return (Criteria) this;
        }

        public Criteria andAppSecretNotEqualTo(String value) {
            addCriterion("app_secret <>", value, "appSecret");
            return (Criteria) this;
        }

        public Criteria andAppSecretGreaterThan(String value) {
            addCriterion("app_secret >", value, "appSecret");
            return (Criteria) this;
        }

        public Criteria andAppSecretGreaterThanOrEqualTo(String value) {
            addCriterion("app_secret >=", value, "appSecret");
            return (Criteria) this;
        }

        public Criteria andAppSecretLessThan(String value) {
            addCriterion("app_secret <", value, "appSecret");
            return (Criteria) this;
        }

        public Criteria andAppSecretLessThanOrEqualTo(String value) {
            addCriterion("app_secret <=", value, "appSecret");
            return (Criteria) this;
        }

        public Criteria andAppSecretLike(String value) {
            addCriterion("app_secret like", value, "appSecret");
            return (Criteria) this;
        }

        public Criteria andAppSecretNotLike(String value) {
            addCriterion("app_secret not like", value, "appSecret");
            return (Criteria) this;
        }

        public Criteria andAppSecretIn(List<String> values) {
            addCriterion("app_secret in", values, "appSecret");
            return (Criteria) this;
        }

        public Criteria andAppSecretNotIn(List<String> values) {
            addCriterion("app_secret not in", values, "appSecret");
            return (Criteria) this;
        }

        public Criteria andAppSecretBetween(String value1, String value2) {
            addCriterion("app_secret between", value1, value2, "appSecret");
            return (Criteria) this;
        }

        public Criteria andAppSecretNotBetween(String value1, String value2) {
            addCriterion("app_secret not between", value1, value2, "appSecret");
            return (Criteria) this;
        }

        public Criteria andUserCodeIsNull() {
            addCriterion("user_code is null");
            return (Criteria) this;
        }

        public Criteria andUserCodeIsNotNull() {
            addCriterion("user_code is not null");
            return (Criteria) this;
        }

        public Criteria andUserCodeEqualTo(String value) {
            addCriterion("user_code =", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeNotEqualTo(String value) {
            addCriterion("user_code <>", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeGreaterThan(String value) {
            addCriterion("user_code >", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeGreaterThanOrEqualTo(String value) {
            addCriterion("user_code >=", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeLessThan(String value) {
            addCriterion("user_code <", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeLessThanOrEqualTo(String value) {
            addCriterion("user_code <=", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeLike(String value) {
            addCriterion("user_code like", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeNotLike(String value) {
            addCriterion("user_code not like", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeIn(List<String> values) {
            addCriterion("user_code in", values, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeNotIn(List<String> values) {
            addCriterion("user_code not in", values, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeBetween(String value1, String value2) {
            addCriterion("user_code between", value1, value2, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeNotBetween(String value1, String value2) {
            addCriterion("user_code not between", value1, value2, "userCode");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNull() {
            addCriterion("password is null");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNotNull() {
            addCriterion("password is not null");
            return (Criteria) this;
        }

        public Criteria andPasswordEqualTo(String value) {
            addCriterion("password =", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotEqualTo(String value) {
            addCriterion("password <>", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThan(String value) {
            addCriterion("password >", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("password >=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThan(String value) {
            addCriterion("password <", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThanOrEqualTo(String value) {
            addCriterion("password <=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLike(String value) {
            addCriterion("password like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotLike(String value) {
            addCriterion("password not like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordIn(List<String> values) {
            addCriterion("password in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotIn(List<String> values) {
            addCriterion("password not in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordBetween(String value1, String value2) {
            addCriterion("password between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotBetween(String value1, String value2) {
            addCriterion("password not between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andWxLimitIsNull() {
            addCriterion("wx_limit is null");
            return (Criteria) this;
        }

        public Criteria andWxLimitIsNotNull() {
            addCriterion("wx_limit is not null");
            return (Criteria) this;
        }

        public Criteria andWxLimitEqualTo(Long value) {
            addCriterion("wx_limit =", value, "wxLimit");
            return (Criteria) this;
        }

        public Criteria andWxLimitNotEqualTo(Long value) {
            addCriterion("wx_limit <>", value, "wxLimit");
            return (Criteria) this;
        }

        public Criteria andWxLimitGreaterThan(Long value) {
            addCriterion("wx_limit >", value, "wxLimit");
            return (Criteria) this;
        }

        public Criteria andWxLimitGreaterThanOrEqualTo(Long value) {
            addCriterion("wx_limit >=", value, "wxLimit");
            return (Criteria) this;
        }

        public Criteria andWxLimitLessThan(Long value) {
            addCriterion("wx_limit <", value, "wxLimit");
            return (Criteria) this;
        }

        public Criteria andWxLimitLessThanOrEqualTo(Long value) {
            addCriterion("wx_limit <=", value, "wxLimit");
            return (Criteria) this;
        }

        public Criteria andWxLimitIn(List<Long> values) {
            addCriterion("wx_limit in", values, "wxLimit");
            return (Criteria) this;
        }

        public Criteria andWxLimitNotIn(List<Long> values) {
            addCriterion("wx_limit not in", values, "wxLimit");
            return (Criteria) this;
        }

        public Criteria andWxLimitBetween(Long value1, Long value2) {
            addCriterion("wx_limit between", value1, value2, "wxLimit");
            return (Criteria) this;
        }

        public Criteria andWxLimitNotBetween(Long value1, Long value2) {
            addCriterion("wx_limit not between", value1, value2, "wxLimit");
            return (Criteria) this;
        }

        public Criteria andSeLimitIsNull() {
            addCriterion("se_limit is null");
            return (Criteria) this;
        }

        public Criteria andSeLimitIsNotNull() {
            addCriterion("se_limit is not null");
            return (Criteria) this;
        }

        public Criteria andSeLimitEqualTo(Long value) {
            addCriterion("se_limit =", value, "seLimit");
            return (Criteria) this;
        }

        public Criteria andSeLimitNotEqualTo(Long value) {
            addCriterion("se_limit <>", value, "seLimit");
            return (Criteria) this;
        }

        public Criteria andSeLimitGreaterThan(Long value) {
            addCriterion("se_limit >", value, "seLimit");
            return (Criteria) this;
        }

        public Criteria andSeLimitGreaterThanOrEqualTo(Long value) {
            addCriterion("se_limit >=", value, "seLimit");
            return (Criteria) this;
        }

        public Criteria andSeLimitLessThan(Long value) {
            addCriterion("se_limit <", value, "seLimit");
            return (Criteria) this;
        }

        public Criteria andSeLimitLessThanOrEqualTo(Long value) {
            addCriterion("se_limit <=", value, "seLimit");
            return (Criteria) this;
        }

        public Criteria andSeLimitIn(List<Long> values) {
            addCriterion("se_limit in", values, "seLimit");
            return (Criteria) this;
        }

        public Criteria andSeLimitNotIn(List<Long> values) {
            addCriterion("se_limit not in", values, "seLimit");
            return (Criteria) this;
        }

        public Criteria andSeLimitBetween(Long value1, Long value2) {
            addCriterion("se_limit between", value1, value2, "seLimit");
            return (Criteria) this;
        }

        public Criteria andSeLimitNotBetween(Long value1, Long value2) {
            addCriterion("se_limit not between", value1, value2, "seLimit");
            return (Criteria) this;
        }

        public Criteria andOrgNameIsNull() {
            addCriterion("org_name is null");
            return (Criteria) this;
        }

        public Criteria andOrgNameIsNotNull() {
            addCriterion("org_name is not null");
            return (Criteria) this;
        }

        public Criteria andOrgNameEqualTo(String value) {
            addCriterion("org_name =", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameNotEqualTo(String value) {
            addCriterion("org_name <>", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameGreaterThan(String value) {
            addCriterion("org_name >", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameGreaterThanOrEqualTo(String value) {
            addCriterion("org_name >=", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameLessThan(String value) {
            addCriterion("org_name <", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameLessThanOrEqualTo(String value) {
            addCriterion("org_name <=", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameLike(String value) {
            addCriterion("org_name like", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameNotLike(String value) {
            addCriterion("org_name not like", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameIn(List<String> values) {
            addCriterion("org_name in", values, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameNotIn(List<String> values) {
            addCriterion("org_name not in", values, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameBetween(String value1, String value2) {
            addCriterion("org_name between", value1, value2, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameNotBetween(String value1, String value2) {
            addCriterion("org_name not between", value1, value2, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgIdIsNull() {
            addCriterion("org_id is null");
            return (Criteria) this;
        }

        public Criteria andOrgIdIsNotNull() {
            addCriterion("org_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrgIdEqualTo(String value) {
            addCriterion("org_id =", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdNotEqualTo(String value) {
            addCriterion("org_id <>", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdGreaterThan(String value) {
            addCriterion("org_id >", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdGreaterThanOrEqualTo(String value) {
            addCriterion("org_id >=", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdLessThan(String value) {
            addCriterion("org_id <", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdLessThanOrEqualTo(String value) {
            addCriterion("org_id <=", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdLike(String value) {
            addCriterion("org_id like", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdNotLike(String value) {
            addCriterion("org_id not like", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdIn(List<String> values) {
            addCriterion("org_id in", values, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdNotIn(List<String> values) {
            addCriterion("org_id not in", values, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdBetween(String value1, String value2) {
            addCriterion("org_id between", value1, value2, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdNotBetween(String value1, String value2) {
            addCriterion("org_id not between", value1, value2, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgTaxcodeIsNull() {
            addCriterion("org_taxcode is null");
            return (Criteria) this;
        }

        public Criteria andOrgTaxcodeIsNotNull() {
            addCriterion("org_taxcode is not null");
            return (Criteria) this;
        }

        public Criteria andOrgTaxcodeEqualTo(String value) {
            addCriterion("org_taxcode =", value, "orgTaxcode");
            return (Criteria) this;
        }

        public Criteria andOrgTaxcodeNotEqualTo(String value) {
            addCriterion("org_taxcode <>", value, "orgTaxcode");
            return (Criteria) this;
        }

        public Criteria andOrgTaxcodeGreaterThan(String value) {
            addCriterion("org_taxcode >", value, "orgTaxcode");
            return (Criteria) this;
        }

        public Criteria andOrgTaxcodeGreaterThanOrEqualTo(String value) {
            addCriterion("org_taxcode >=", value, "orgTaxcode");
            return (Criteria) this;
        }

        public Criteria andOrgTaxcodeLessThan(String value) {
            addCriterion("org_taxcode <", value, "orgTaxcode");
            return (Criteria) this;
        }

        public Criteria andOrgTaxcodeLessThanOrEqualTo(String value) {
            addCriterion("org_taxcode <=", value, "orgTaxcode");
            return (Criteria) this;
        }

        public Criteria andOrgTaxcodeLike(String value) {
            addCriterion("org_taxcode like", value, "orgTaxcode");
            return (Criteria) this;
        }

        public Criteria andOrgTaxcodeNotLike(String value) {
            addCriterion("org_taxcode not like", value, "orgTaxcode");
            return (Criteria) this;
        }

        public Criteria andOrgTaxcodeIn(List<String> values) {
            addCriterion("org_taxcode in", values, "orgTaxcode");
            return (Criteria) this;
        }

        public Criteria andOrgTaxcodeNotIn(List<String> values) {
            addCriterion("org_taxcode not in", values, "orgTaxcode");
            return (Criteria) this;
        }

        public Criteria andOrgTaxcodeBetween(String value1, String value2) {
            addCriterion("org_taxcode between", value1, value2, "orgTaxcode");
            return (Criteria) this;
        }

        public Criteria andOrgTaxcodeNotBetween(String value1, String value2) {
            addCriterion("org_taxcode not between", value1, value2, "orgTaxcode");
            return (Criteria) this;
        }

        public Criteria andOrgMachineIsNull() {
            addCriterion("org_machine is null");
            return (Criteria) this;
        }

        public Criteria andOrgMachineIsNotNull() {
            addCriterion("org_machine is not null");
            return (Criteria) this;
        }

        public Criteria andOrgMachineEqualTo(String value) {
            addCriterion("org_machine =", value, "orgMachine");
            return (Criteria) this;
        }

        public Criteria andOrgMachineNotEqualTo(String value) {
            addCriterion("org_machine <>", value, "orgMachine");
            return (Criteria) this;
        }

        public Criteria andOrgMachineGreaterThan(String value) {
            addCriterion("org_machine >", value, "orgMachine");
            return (Criteria) this;
        }

        public Criteria andOrgMachineGreaterThanOrEqualTo(String value) {
            addCriterion("org_machine >=", value, "orgMachine");
            return (Criteria) this;
        }

        public Criteria andOrgMachineLessThan(String value) {
            addCriterion("org_machine <", value, "orgMachine");
            return (Criteria) this;
        }

        public Criteria andOrgMachineLessThanOrEqualTo(String value) {
            addCriterion("org_machine <=", value, "orgMachine");
            return (Criteria) this;
        }

        public Criteria andOrgMachineLike(String value) {
            addCriterion("org_machine like", value, "orgMachine");
            return (Criteria) this;
        }

        public Criteria andOrgMachineNotLike(String value) {
            addCriterion("org_machine not like", value, "orgMachine");
            return (Criteria) this;
        }

        public Criteria andOrgMachineIn(List<String> values) {
            addCriterion("org_machine in", values, "orgMachine");
            return (Criteria) this;
        }

        public Criteria andOrgMachineNotIn(List<String> values) {
            addCriterion("org_machine not in", values, "orgMachine");
            return (Criteria) this;
        }

        public Criteria andOrgMachineBetween(String value1, String value2) {
            addCriterion("org_machine between", value1, value2, "orgMachine");
            return (Criteria) this;
        }

        public Criteria andOrgMachineNotBetween(String value1, String value2) {
            addCriterion("org_machine not between", value1, value2, "orgMachine");
            return (Criteria) this;
        }

        public Criteria andOrgMachineIdIsNull() {
            addCriterion("org_machine_id is null");
            return (Criteria) this;
        }

        public Criteria andOrgMachineIdIsNotNull() {
            addCriterion("org_machine_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrgMachineIdEqualTo(String value) {
            addCriterion("org_machine_id =", value, "orgMachineId");
            return (Criteria) this;
        }

        public Criteria andOrgMachineIdNotEqualTo(String value) {
            addCriterion("org_machine_id <>", value, "orgMachineId");
            return (Criteria) this;
        }

        public Criteria andOrgMachineIdGreaterThan(String value) {
            addCriterion("org_machine_id >", value, "orgMachineId");
            return (Criteria) this;
        }

        public Criteria andOrgMachineIdGreaterThanOrEqualTo(String value) {
            addCriterion("org_machine_id >=", value, "orgMachineId");
            return (Criteria) this;
        }

        public Criteria andOrgMachineIdLessThan(String value) {
            addCriterion("org_machine_id <", value, "orgMachineId");
            return (Criteria) this;
        }

        public Criteria andOrgMachineIdLessThanOrEqualTo(String value) {
            addCriterion("org_machine_id <=", value, "orgMachineId");
            return (Criteria) this;
        }

        public Criteria andOrgMachineIdLike(String value) {
            addCriterion("org_machine_id like", value, "orgMachineId");
            return (Criteria) this;
        }

        public Criteria andOrgMachineIdNotLike(String value) {
            addCriterion("org_machine_id not like", value, "orgMachineId");
            return (Criteria) this;
        }

        public Criteria andOrgMachineIdIn(List<String> values) {
            addCriterion("org_machine_id in", values, "orgMachineId");
            return (Criteria) this;
        }

        public Criteria andOrgMachineIdNotIn(List<String> values) {
            addCriterion("org_machine_id not in", values, "orgMachineId");
            return (Criteria) this;
        }

        public Criteria andOrgMachineIdBetween(String value1, String value2) {
            addCriterion("org_machine_id between", value1, value2, "orgMachineId");
            return (Criteria) this;
        }

        public Criteria andOrgMachineIdNotBetween(String value1, String value2) {
            addCriterion("org_machine_id not between", value1, value2, "orgMachineId");
            return (Criteria) this;
        }

        public Criteria andIsSkpIsNull() {
            addCriterion("is_skp is null");
            return (Criteria) this;
        }

        public Criteria andIsSkpIsNotNull() {
            addCriterion("is_skp is not null");
            return (Criteria) this;
        }

        public Criteria andIsSkpEqualTo(Boolean value) {
            addCriterion("is_skp =", value, "isSkp");
            return (Criteria) this;
        }

        public Criteria andIsSkpNotEqualTo(Boolean value) {
            addCriterion("is_skp <>", value, "isSkp");
            return (Criteria) this;
        }

        public Criteria andIsSkpGreaterThan(Boolean value) {
            addCriterion("is_skp >", value, "isSkp");
            return (Criteria) this;
        }

        public Criteria andIsSkpGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_skp >=", value, "isSkp");
            return (Criteria) this;
        }

        public Criteria andIsSkpLessThan(Boolean value) {
            addCriterion("is_skp <", value, "isSkp");
            return (Criteria) this;
        }

        public Criteria andIsSkpLessThanOrEqualTo(Boolean value) {
            addCriterion("is_skp <=", value, "isSkp");
            return (Criteria) this;
        }

        public Criteria andIsSkpIn(List<Boolean> values) {
            addCriterion("is_skp in", values, "isSkp");
            return (Criteria) this;
        }

        public Criteria andIsSkpNotIn(List<Boolean> values) {
            addCriterion("is_skp not in", values, "isSkp");
            return (Criteria) this;
        }

        public Criteria andIsSkpBetween(Boolean value1, Boolean value2) {
            addCriterion("is_skp between", value1, value2, "isSkp");
            return (Criteria) this;
        }

        public Criteria andIsSkpNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_skp not between", value1, value2, "isSkp");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseIsNull() {
            addCriterion("is_enterprise is null");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseIsNotNull() {
            addCriterion("is_enterprise is not null");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseEqualTo(Boolean value) {
            addCriterion("is_enterprise =", value, "isEnterprise");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseNotEqualTo(Boolean value) {
            addCriterion("is_enterprise <>", value, "isEnterprise");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseGreaterThan(Boolean value) {
            addCriterion("is_enterprise >", value, "isEnterprise");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_enterprise >=", value, "isEnterprise");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseLessThan(Boolean value) {
            addCriterion("is_enterprise <", value, "isEnterprise");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseLessThanOrEqualTo(Boolean value) {
            addCriterion("is_enterprise <=", value, "isEnterprise");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseIn(List<Boolean> values) {
            addCriterion("is_enterprise in", values, "isEnterprise");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseNotIn(List<Boolean> values) {
            addCriterion("is_enterprise not in", values, "isEnterprise");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseBetween(Boolean value1, Boolean value2) {
            addCriterion("is_enterprise between", value1, value2, "isEnterprise");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_enterprise not between", value1, value2, "isEnterprise");
            return (Criteria) this;
        }

        public Criteria andIsStrictIsNull() {
            addCriterion("is_strict is null");
            return (Criteria) this;
        }

        public Criteria andIsStrictIsNotNull() {
            addCriterion("is_strict is not null");
            return (Criteria) this;
        }

        public Criteria andIsStrictEqualTo(Boolean value) {
            addCriterion("is_strict =", value, "isStrict");
            return (Criteria) this;
        }

        public Criteria andIsStrictNotEqualTo(Boolean value) {
            addCriterion("is_strict <>", value, "isStrict");
            return (Criteria) this;
        }

        public Criteria andIsStrictGreaterThan(Boolean value) {
            addCriterion("is_strict >", value, "isStrict");
            return (Criteria) this;
        }

        public Criteria andIsStrictGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_strict >=", value, "isStrict");
            return (Criteria) this;
        }

        public Criteria andIsStrictLessThan(Boolean value) {
            addCriterion("is_strict <", value, "isStrict");
            return (Criteria) this;
        }

        public Criteria andIsStrictLessThanOrEqualTo(Boolean value) {
            addCriterion("is_strict <=", value, "isStrict");
            return (Criteria) this;
        }

        public Criteria andIsStrictIn(List<Boolean> values) {
            addCriterion("is_strict in", values, "isStrict");
            return (Criteria) this;
        }

        public Criteria andIsStrictNotIn(List<Boolean> values) {
            addCriterion("is_strict not in", values, "isStrict");
            return (Criteria) this;
        }

        public Criteria andIsStrictBetween(Boolean value1, Boolean value2) {
            addCriterion("is_strict between", value1, value2, "isStrict");
            return (Criteria) this;
        }

        public Criteria andIsStrictNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_strict not between", value1, value2, "isStrict");
            return (Criteria) this;
        }

        public Criteria andUserTypeIsNull() {
            addCriterion("user_type is null");
            return (Criteria) this;
        }

        public Criteria andUserTypeIsNotNull() {
            addCriterion("user_type is not null");
            return (Criteria) this;
        }

        public Criteria andUserTypeEqualTo(String value) {
            addCriterion("user_type =", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotEqualTo(String value) {
            addCriterion("user_type <>", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeGreaterThan(String value) {
            addCriterion("user_type >", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeGreaterThanOrEqualTo(String value) {
            addCriterion("user_type >=", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeLessThan(String value) {
            addCriterion("user_type <", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeLessThanOrEqualTo(String value) {
            addCriterion("user_type <=", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeLike(String value) {
            addCriterion("user_type like", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotLike(String value) {
            addCriterion("user_type not like", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeIn(List<String> values) {
            addCriterion("user_type in", values, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotIn(List<String> values) {
            addCriterion("user_type not in", values, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeBetween(String value1, String value2) {
            addCriterion("user_type between", value1, value2, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotBetween(String value1, String value2) {
            addCriterion("user_type not between", value1, value2, "userType");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}