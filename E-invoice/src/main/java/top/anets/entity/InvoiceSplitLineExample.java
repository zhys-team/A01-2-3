package top.anets.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class InvoiceSplitLineExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public InvoiceSplitLineExample() {
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

        public Criteria andDocNumIsNull() {
            addCriterion("DOC_NUM is null");
            return (Criteria) this;
        }

        public Criteria andDocNumIsNotNull() {
            addCriterion("DOC_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andDocNumEqualTo(String value) {
            addCriterion("DOC_NUM =", value, "docNum");
            return (Criteria) this;
        }

        public Criteria andDocNumNotEqualTo(String value) {
            addCriterion("DOC_NUM <>", value, "docNum");
            return (Criteria) this;
        }

        public Criteria andDocNumGreaterThan(String value) {
            addCriterion("DOC_NUM >", value, "docNum");
            return (Criteria) this;
        }

        public Criteria andDocNumGreaterThanOrEqualTo(String value) {
            addCriterion("DOC_NUM >=", value, "docNum");
            return (Criteria) this;
        }

        public Criteria andDocNumLessThan(String value) {
            addCriterion("DOC_NUM <", value, "docNum");
            return (Criteria) this;
        }

        public Criteria andDocNumLessThanOrEqualTo(String value) {
            addCriterion("DOC_NUM <=", value, "docNum");
            return (Criteria) this;
        }

        public Criteria andDocNumLike(String value) {
            addCriterion("DOC_NUM like", value, "docNum");
            return (Criteria) this;
        }

        public Criteria andDocNumNotLike(String value) {
            addCriterion("DOC_NUM not like", value, "docNum");
            return (Criteria) this;
        }

        public Criteria andDocNumIn(List<String> values) {
            addCriterion("DOC_NUM in", values, "docNum");
            return (Criteria) this;
        }

        public Criteria andDocNumNotIn(List<String> values) {
            addCriterion("DOC_NUM not in", values, "docNum");
            return (Criteria) this;
        }

        public Criteria andDocNumBetween(String value1, String value2) {
            addCriterion("DOC_NUM between", value1, value2, "docNum");
            return (Criteria) this;
        }

        public Criteria andDocNumNotBetween(String value1, String value2) {
            addCriterion("DOC_NUM not between", value1, value2, "docNum");
            return (Criteria) this;
        }

        public Criteria andDocLineIsNull() {
            addCriterion("DOC_LINE is null");
            return (Criteria) this;
        }

        public Criteria andDocLineIsNotNull() {
            addCriterion("DOC_LINE is not null");
            return (Criteria) this;
        }

        public Criteria andDocLineEqualTo(String value) {
            addCriterion("DOC_LINE =", value, "docLine");
            return (Criteria) this;
        }

        public Criteria andDocLineNotEqualTo(String value) {
            addCriterion("DOC_LINE <>", value, "docLine");
            return (Criteria) this;
        }

        public Criteria andDocLineGreaterThan(String value) {
            addCriterion("DOC_LINE >", value, "docLine");
            return (Criteria) this;
        }

        public Criteria andDocLineGreaterThanOrEqualTo(String value) {
            addCriterion("DOC_LINE >=", value, "docLine");
            return (Criteria) this;
        }

        public Criteria andDocLineLessThan(String value) {
            addCriterion("DOC_LINE <", value, "docLine");
            return (Criteria) this;
        }

        public Criteria andDocLineLessThanOrEqualTo(String value) {
            addCriterion("DOC_LINE <=", value, "docLine");
            return (Criteria) this;
        }

        public Criteria andDocLineLike(String value) {
            addCriterion("DOC_LINE like", value, "docLine");
            return (Criteria) this;
        }

        public Criteria andDocLineNotLike(String value) {
            addCriterion("DOC_LINE not like", value, "docLine");
            return (Criteria) this;
        }

        public Criteria andDocLineIn(List<String> values) {
            addCriterion("DOC_LINE in", values, "docLine");
            return (Criteria) this;
        }

        public Criteria andDocLineNotIn(List<String> values) {
            addCriterion("DOC_LINE not in", values, "docLine");
            return (Criteria) this;
        }

        public Criteria andDocLineBetween(String value1, String value2) {
            addCriterion("DOC_LINE between", value1, value2, "docLine");
            return (Criteria) this;
        }

        public Criteria andDocLineNotBetween(String value1, String value2) {
            addCriterion("DOC_LINE not between", value1, value2, "docLine");
            return (Criteria) this;
        }

        public Criteria andMandtIsNull() {
            addCriterion("MANDT is null");
            return (Criteria) this;
        }

        public Criteria andMandtIsNotNull() {
            addCriterion("MANDT is not null");
            return (Criteria) this;
        }

        public Criteria andMandtEqualTo(String value) {
            addCriterion("MANDT =", value, "mandt");
            return (Criteria) this;
        }

        public Criteria andMandtNotEqualTo(String value) {
            addCriterion("MANDT <>", value, "mandt");
            return (Criteria) this;
        }

        public Criteria andMandtGreaterThan(String value) {
            addCriterion("MANDT >", value, "mandt");
            return (Criteria) this;
        }

        public Criteria andMandtGreaterThanOrEqualTo(String value) {
            addCriterion("MANDT >=", value, "mandt");
            return (Criteria) this;
        }

        public Criteria andMandtLessThan(String value) {
            addCriterion("MANDT <", value, "mandt");
            return (Criteria) this;
        }

        public Criteria andMandtLessThanOrEqualTo(String value) {
            addCriterion("MANDT <=", value, "mandt");
            return (Criteria) this;
        }

        public Criteria andMandtLike(String value) {
            addCriterion("MANDT like", value, "mandt");
            return (Criteria) this;
        }

        public Criteria andMandtNotLike(String value) {
            addCriterion("MANDT not like", value, "mandt");
            return (Criteria) this;
        }

        public Criteria andMandtIn(List<String> values) {
            addCriterion("MANDT in", values, "mandt");
            return (Criteria) this;
        }

        public Criteria andMandtNotIn(List<String> values) {
            addCriterion("MANDT not in", values, "mandt");
            return (Criteria) this;
        }

        public Criteria andMandtBetween(String value1, String value2) {
            addCriterion("MANDT between", value1, value2, "mandt");
            return (Criteria) this;
        }

        public Criteria andMandtNotBetween(String value1, String value2) {
            addCriterion("MANDT not between", value1, value2, "mandt");
            return (Criteria) this;
        }

        public Criteria andGroupNumIsNull() {
            addCriterion("GROUP_NUM is null");
            return (Criteria) this;
        }

        public Criteria andGroupNumIsNotNull() {
            addCriterion("GROUP_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andGroupNumEqualTo(String value) {
            addCriterion("GROUP_NUM =", value, "groupNum");
            return (Criteria) this;
        }

        public Criteria andGroupNumNotEqualTo(String value) {
            addCriterion("GROUP_NUM <>", value, "groupNum");
            return (Criteria) this;
        }

        public Criteria andGroupNumGreaterThan(String value) {
            addCriterion("GROUP_NUM >", value, "groupNum");
            return (Criteria) this;
        }

        public Criteria andGroupNumGreaterThanOrEqualTo(String value) {
            addCriterion("GROUP_NUM >=", value, "groupNum");
            return (Criteria) this;
        }

        public Criteria andGroupNumLessThan(String value) {
            addCriterion("GROUP_NUM <", value, "groupNum");
            return (Criteria) this;
        }

        public Criteria andGroupNumLessThanOrEqualTo(String value) {
            addCriterion("GROUP_NUM <=", value, "groupNum");
            return (Criteria) this;
        }

        public Criteria andGroupNumLike(String value) {
            addCriterion("GROUP_NUM like", value, "groupNum");
            return (Criteria) this;
        }

        public Criteria andGroupNumNotLike(String value) {
            addCriterion("GROUP_NUM not like", value, "groupNum");
            return (Criteria) this;
        }

        public Criteria andGroupNumIn(List<String> values) {
            addCriterion("GROUP_NUM in", values, "groupNum");
            return (Criteria) this;
        }

        public Criteria andGroupNumNotIn(List<String> values) {
            addCriterion("GROUP_NUM not in", values, "groupNum");
            return (Criteria) this;
        }

        public Criteria andGroupNumBetween(String value1, String value2) {
            addCriterion("GROUP_NUM between", value1, value2, "groupNum");
            return (Criteria) this;
        }

        public Criteria andGroupNumNotBetween(String value1, String value2) {
            addCriterion("GROUP_NUM not between", value1, value2, "groupNum");
            return (Criteria) this;
        }

        public Criteria andGroupStatusIsNull() {
            addCriterion("GROUP_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andGroupStatusIsNotNull() {
            addCriterion("GROUP_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andGroupStatusEqualTo(String value) {
            addCriterion("GROUP_STATUS =", value, "groupStatus");
            return (Criteria) this;
        }

        public Criteria andGroupStatusNotEqualTo(String value) {
            addCriterion("GROUP_STATUS <>", value, "groupStatus");
            return (Criteria) this;
        }

        public Criteria andGroupStatusGreaterThan(String value) {
            addCriterion("GROUP_STATUS >", value, "groupStatus");
            return (Criteria) this;
        }

        public Criteria andGroupStatusGreaterThanOrEqualTo(String value) {
            addCriterion("GROUP_STATUS >=", value, "groupStatus");
            return (Criteria) this;
        }

        public Criteria andGroupStatusLessThan(String value) {
            addCriterion("GROUP_STATUS <", value, "groupStatus");
            return (Criteria) this;
        }

        public Criteria andGroupStatusLessThanOrEqualTo(String value) {
            addCriterion("GROUP_STATUS <=", value, "groupStatus");
            return (Criteria) this;
        }

        public Criteria andGroupStatusLike(String value) {
            addCriterion("GROUP_STATUS like", value, "groupStatus");
            return (Criteria) this;
        }

        public Criteria andGroupStatusNotLike(String value) {
            addCriterion("GROUP_STATUS not like", value, "groupStatus");
            return (Criteria) this;
        }

        public Criteria andGroupStatusIn(List<String> values) {
            addCriterion("GROUP_STATUS in", values, "groupStatus");
            return (Criteria) this;
        }

        public Criteria andGroupStatusNotIn(List<String> values) {
            addCriterion("GROUP_STATUS not in", values, "groupStatus");
            return (Criteria) this;
        }

        public Criteria andGroupStatusBetween(String value1, String value2) {
            addCriterion("GROUP_STATUS between", value1, value2, "groupStatus");
            return (Criteria) this;
        }

        public Criteria andGroupStatusNotBetween(String value1, String value2) {
            addCriterion("GROUP_STATUS not between", value1, value2, "groupStatus");
            return (Criteria) this;
        }

        public Criteria andGoldtaxCodeIsNull() {
            addCriterion("GOLDTAX_CODE is null");
            return (Criteria) this;
        }

        public Criteria andGoldtaxCodeIsNotNull() {
            addCriterion("GOLDTAX_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andGoldtaxCodeEqualTo(String value) {
            addCriterion("GOLDTAX_CODE =", value, "goldtaxCode");
            return (Criteria) this;
        }

        public Criteria andGoldtaxCodeNotEqualTo(String value) {
            addCriterion("GOLDTAX_CODE <>", value, "goldtaxCode");
            return (Criteria) this;
        }

        public Criteria andGoldtaxCodeGreaterThan(String value) {
            addCriterion("GOLDTAX_CODE >", value, "goldtaxCode");
            return (Criteria) this;
        }

        public Criteria andGoldtaxCodeGreaterThanOrEqualTo(String value) {
            addCriterion("GOLDTAX_CODE >=", value, "goldtaxCode");
            return (Criteria) this;
        }

        public Criteria andGoldtaxCodeLessThan(String value) {
            addCriterion("GOLDTAX_CODE <", value, "goldtaxCode");
            return (Criteria) this;
        }

        public Criteria andGoldtaxCodeLessThanOrEqualTo(String value) {
            addCriterion("GOLDTAX_CODE <=", value, "goldtaxCode");
            return (Criteria) this;
        }

        public Criteria andGoldtaxCodeLike(String value) {
            addCriterion("GOLDTAX_CODE like", value, "goldtaxCode");
            return (Criteria) this;
        }

        public Criteria andGoldtaxCodeNotLike(String value) {
            addCriterion("GOLDTAX_CODE not like", value, "goldtaxCode");
            return (Criteria) this;
        }

        public Criteria andGoldtaxCodeIn(List<String> values) {
            addCriterion("GOLDTAX_CODE in", values, "goldtaxCode");
            return (Criteria) this;
        }

        public Criteria andGoldtaxCodeNotIn(List<String> values) {
            addCriterion("GOLDTAX_CODE not in", values, "goldtaxCode");
            return (Criteria) this;
        }

        public Criteria andGoldtaxCodeBetween(String value1, String value2) {
            addCriterion("GOLDTAX_CODE between", value1, value2, "goldtaxCode");
            return (Criteria) this;
        }

        public Criteria andGoldtaxCodeNotBetween(String value1, String value2) {
            addCriterion("GOLDTAX_CODE not between", value1, value2, "goldtaxCode");
            return (Criteria) this;
        }

        public Criteria andGoldtaxNumIsNull() {
            addCriterion("GOLDTAX_NUM is null");
            return (Criteria) this;
        }

        public Criteria andGoldtaxNumIsNotNull() {
            addCriterion("GOLDTAX_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andGoldtaxNumEqualTo(String value) {
            addCriterion("GOLDTAX_NUM =", value, "goldtaxNum");
            return (Criteria) this;
        }

        public Criteria andGoldtaxNumNotEqualTo(String value) {
            addCriterion("GOLDTAX_NUM <>", value, "goldtaxNum");
            return (Criteria) this;
        }

        public Criteria andGoldtaxNumGreaterThan(String value) {
            addCriterion("GOLDTAX_NUM >", value, "goldtaxNum");
            return (Criteria) this;
        }

        public Criteria andGoldtaxNumGreaterThanOrEqualTo(String value) {
            addCriterion("GOLDTAX_NUM >=", value, "goldtaxNum");
            return (Criteria) this;
        }

        public Criteria andGoldtaxNumLessThan(String value) {
            addCriterion("GOLDTAX_NUM <", value, "goldtaxNum");
            return (Criteria) this;
        }

        public Criteria andGoldtaxNumLessThanOrEqualTo(String value) {
            addCriterion("GOLDTAX_NUM <=", value, "goldtaxNum");
            return (Criteria) this;
        }

        public Criteria andGoldtaxNumLike(String value) {
            addCriterion("GOLDTAX_NUM like", value, "goldtaxNum");
            return (Criteria) this;
        }

        public Criteria andGoldtaxNumNotLike(String value) {
            addCriterion("GOLDTAX_NUM not like", value, "goldtaxNum");
            return (Criteria) this;
        }

        public Criteria andGoldtaxNumIn(List<String> values) {
            addCriterion("GOLDTAX_NUM in", values, "goldtaxNum");
            return (Criteria) this;
        }

        public Criteria andGoldtaxNumNotIn(List<String> values) {
            addCriterion("GOLDTAX_NUM not in", values, "goldtaxNum");
            return (Criteria) this;
        }

        public Criteria andGoldtaxNumBetween(String value1, String value2) {
            addCriterion("GOLDTAX_NUM between", value1, value2, "goldtaxNum");
            return (Criteria) this;
        }

        public Criteria andGoldtaxNumNotBetween(String value1, String value2) {
            addCriterion("GOLDTAX_NUM not between", value1, value2, "goldtaxNum");
            return (Criteria) this;
        }

        public Criteria andItemNameIsNull() {
            addCriterion("ITEM_NAME is null");
            return (Criteria) this;
        }

        public Criteria andItemNameIsNotNull() {
            addCriterion("ITEM_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andItemNameEqualTo(String value) {
            addCriterion("ITEM_NAME =", value, "itemName");
            return (Criteria) this;
        }

        public Criteria andItemNameNotEqualTo(String value) {
            addCriterion("ITEM_NAME <>", value, "itemName");
            return (Criteria) this;
        }

        public Criteria andItemNameGreaterThan(String value) {
            addCriterion("ITEM_NAME >", value, "itemName");
            return (Criteria) this;
        }

        public Criteria andItemNameGreaterThanOrEqualTo(String value) {
            addCriterion("ITEM_NAME >=", value, "itemName");
            return (Criteria) this;
        }

        public Criteria andItemNameLessThan(String value) {
            addCriterion("ITEM_NAME <", value, "itemName");
            return (Criteria) this;
        }

        public Criteria andItemNameLessThanOrEqualTo(String value) {
            addCriterion("ITEM_NAME <=", value, "itemName");
            return (Criteria) this;
        }

        public Criteria andItemNameLike(String value) {
            addCriterion("ITEM_NAME like", value, "itemName");
            return (Criteria) this;
        }

        public Criteria andItemNameNotLike(String value) {
            addCriterion("ITEM_NAME not like", value, "itemName");
            return (Criteria) this;
        }

        public Criteria andItemNameIn(List<String> values) {
            addCriterion("ITEM_NAME in", values, "itemName");
            return (Criteria) this;
        }

        public Criteria andItemNameNotIn(List<String> values) {
            addCriterion("ITEM_NAME not in", values, "itemName");
            return (Criteria) this;
        }

        public Criteria andItemNameBetween(String value1, String value2) {
            addCriterion("ITEM_NAME between", value1, value2, "itemName");
            return (Criteria) this;
        }

        public Criteria andItemNameNotBetween(String value1, String value2) {
            addCriterion("ITEM_NAME not between", value1, value2, "itemName");
            return (Criteria) this;
        }

        public Criteria andItemSpecIsNull() {
            addCriterion("ITEM_SPEC is null");
            return (Criteria) this;
        }

        public Criteria andItemSpecIsNotNull() {
            addCriterion("ITEM_SPEC is not null");
            return (Criteria) this;
        }

        public Criteria andItemSpecEqualTo(String value) {
            addCriterion("ITEM_SPEC =", value, "itemSpec");
            return (Criteria) this;
        }

        public Criteria andItemSpecNotEqualTo(String value) {
            addCriterion("ITEM_SPEC <>", value, "itemSpec");
            return (Criteria) this;
        }

        public Criteria andItemSpecGreaterThan(String value) {
            addCriterion("ITEM_SPEC >", value, "itemSpec");
            return (Criteria) this;
        }

        public Criteria andItemSpecGreaterThanOrEqualTo(String value) {
            addCriterion("ITEM_SPEC >=", value, "itemSpec");
            return (Criteria) this;
        }

        public Criteria andItemSpecLessThan(String value) {
            addCriterion("ITEM_SPEC <", value, "itemSpec");
            return (Criteria) this;
        }

        public Criteria andItemSpecLessThanOrEqualTo(String value) {
            addCriterion("ITEM_SPEC <=", value, "itemSpec");
            return (Criteria) this;
        }

        public Criteria andItemSpecLike(String value) {
            addCriterion("ITEM_SPEC like", value, "itemSpec");
            return (Criteria) this;
        }

        public Criteria andItemSpecNotLike(String value) {
            addCriterion("ITEM_SPEC not like", value, "itemSpec");
            return (Criteria) this;
        }

        public Criteria andItemSpecIn(List<String> values) {
            addCriterion("ITEM_SPEC in", values, "itemSpec");
            return (Criteria) this;
        }

        public Criteria andItemSpecNotIn(List<String> values) {
            addCriterion("ITEM_SPEC not in", values, "itemSpec");
            return (Criteria) this;
        }

        public Criteria andItemSpecBetween(String value1, String value2) {
            addCriterion("ITEM_SPEC between", value1, value2, "itemSpec");
            return (Criteria) this;
        }

        public Criteria andItemSpecNotBetween(String value1, String value2) {
            addCriterion("ITEM_SPEC not between", value1, value2, "itemSpec");
            return (Criteria) this;
        }

        public Criteria andUnitNameIsNull() {
            addCriterion("UNIT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andUnitNameIsNotNull() {
            addCriterion("UNIT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andUnitNameEqualTo(String value) {
            addCriterion("UNIT_NAME =", value, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameNotEqualTo(String value) {
            addCriterion("UNIT_NAME <>", value, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameGreaterThan(String value) {
            addCriterion("UNIT_NAME >", value, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameGreaterThanOrEqualTo(String value) {
            addCriterion("UNIT_NAME >=", value, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameLessThan(String value) {
            addCriterion("UNIT_NAME <", value, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameLessThanOrEqualTo(String value) {
            addCriterion("UNIT_NAME <=", value, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameLike(String value) {
            addCriterion("UNIT_NAME like", value, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameNotLike(String value) {
            addCriterion("UNIT_NAME not like", value, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameIn(List<String> values) {
            addCriterion("UNIT_NAME in", values, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameNotIn(List<String> values) {
            addCriterion("UNIT_NAME not in", values, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameBetween(String value1, String value2) {
            addCriterion("UNIT_NAME between", value1, value2, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameNotBetween(String value1, String value2) {
            addCriterion("UNIT_NAME not between", value1, value2, "unitName");
            return (Criteria) this;
        }

        public Criteria andTaxCatecodeIsNull() {
            addCriterion("TAX_CATECODE is null");
            return (Criteria) this;
        }

        public Criteria andTaxCatecodeIsNotNull() {
            addCriterion("TAX_CATECODE is not null");
            return (Criteria) this;
        }

        public Criteria andTaxCatecodeEqualTo(String value) {
            addCriterion("TAX_CATECODE =", value, "taxCatecode");
            return (Criteria) this;
        }

        public Criteria andTaxCatecodeNotEqualTo(String value) {
            addCriterion("TAX_CATECODE <>", value, "taxCatecode");
            return (Criteria) this;
        }

        public Criteria andTaxCatecodeGreaterThan(String value) {
            addCriterion("TAX_CATECODE >", value, "taxCatecode");
            return (Criteria) this;
        }

        public Criteria andTaxCatecodeGreaterThanOrEqualTo(String value) {
            addCriterion("TAX_CATECODE >=", value, "taxCatecode");
            return (Criteria) this;
        }

        public Criteria andTaxCatecodeLessThan(String value) {
            addCriterion("TAX_CATECODE <", value, "taxCatecode");
            return (Criteria) this;
        }

        public Criteria andTaxCatecodeLessThanOrEqualTo(String value) {
            addCriterion("TAX_CATECODE <=", value, "taxCatecode");
            return (Criteria) this;
        }

        public Criteria andTaxCatecodeLike(String value) {
            addCriterion("TAX_CATECODE like", value, "taxCatecode");
            return (Criteria) this;
        }

        public Criteria andTaxCatecodeNotLike(String value) {
            addCriterion("TAX_CATECODE not like", value, "taxCatecode");
            return (Criteria) this;
        }

        public Criteria andTaxCatecodeIn(List<String> values) {
            addCriterion("TAX_CATECODE in", values, "taxCatecode");
            return (Criteria) this;
        }

        public Criteria andTaxCatecodeNotIn(List<String> values) {
            addCriterion("TAX_CATECODE not in", values, "taxCatecode");
            return (Criteria) this;
        }

        public Criteria andTaxCatecodeBetween(String value1, String value2) {
            addCriterion("TAX_CATECODE between", value1, value2, "taxCatecode");
            return (Criteria) this;
        }

        public Criteria andTaxCatecodeNotBetween(String value1, String value2) {
            addCriterion("TAX_CATECODE not between", value1, value2, "taxCatecode");
            return (Criteria) this;
        }

        public Criteria andQuantityIsNull() {
            addCriterion("QUANTITY is null");
            return (Criteria) this;
        }

        public Criteria andQuantityIsNotNull() {
            addCriterion("QUANTITY is not null");
            return (Criteria) this;
        }

        public Criteria andQuantityEqualTo(BigDecimal value) {
            addCriterion("QUANTITY =", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityNotEqualTo(BigDecimal value) {
            addCriterion("QUANTITY <>", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityGreaterThan(BigDecimal value) {
            addCriterion("QUANTITY >", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("QUANTITY >=", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityLessThan(BigDecimal value) {
            addCriterion("QUANTITY <", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityLessThanOrEqualTo(BigDecimal value) {
            addCriterion("QUANTITY <=", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityIn(List<BigDecimal> values) {
            addCriterion("QUANTITY in", values, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityNotIn(List<BigDecimal> values) {
            addCriterion("QUANTITY not in", values, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("QUANTITY between", value1, value2, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("QUANTITY not between", value1, value2, "quantity");
            return (Criteria) this;
        }

        public Criteria andTaxRateIsNull() {
            addCriterion("TAX_RATE is null");
            return (Criteria) this;
        }

        public Criteria andTaxRateIsNotNull() {
            addCriterion("TAX_RATE is not null");
            return (Criteria) this;
        }

        public Criteria andTaxRateEqualTo(BigDecimal value) {
            addCriterion("TAX_RATE =", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateNotEqualTo(BigDecimal value) {
            addCriterion("TAX_RATE <>", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateGreaterThan(BigDecimal value) {
            addCriterion("TAX_RATE >", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TAX_RATE >=", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateLessThan(BigDecimal value) {
            addCriterion("TAX_RATE <", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TAX_RATE <=", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateIn(List<BigDecimal> values) {
            addCriterion("TAX_RATE in", values, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateNotIn(List<BigDecimal> values) {
            addCriterion("TAX_RATE not in", values, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TAX_RATE between", value1, value2, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TAX_RATE not between", value1, value2, "taxRate");
            return (Criteria) this;
        }

        public Criteria andGiftFlagIsNull() {
            addCriterion("GIFT_FLAG is null");
            return (Criteria) this;
        }

        public Criteria andGiftFlagIsNotNull() {
            addCriterion("GIFT_FLAG is not null");
            return (Criteria) this;
        }

        public Criteria andGiftFlagEqualTo(String value) {
            addCriterion("GIFT_FLAG =", value, "giftFlag");
            return (Criteria) this;
        }

        public Criteria andGiftFlagNotEqualTo(String value) {
            addCriterion("GIFT_FLAG <>", value, "giftFlag");
            return (Criteria) this;
        }

        public Criteria andGiftFlagGreaterThan(String value) {
            addCriterion("GIFT_FLAG >", value, "giftFlag");
            return (Criteria) this;
        }

        public Criteria andGiftFlagGreaterThanOrEqualTo(String value) {
            addCriterion("GIFT_FLAG >=", value, "giftFlag");
            return (Criteria) this;
        }

        public Criteria andGiftFlagLessThan(String value) {
            addCriterion("GIFT_FLAG <", value, "giftFlag");
            return (Criteria) this;
        }

        public Criteria andGiftFlagLessThanOrEqualTo(String value) {
            addCriterion("GIFT_FLAG <=", value, "giftFlag");
            return (Criteria) this;
        }

        public Criteria andGiftFlagLike(String value) {
            addCriterion("GIFT_FLAG like", value, "giftFlag");
            return (Criteria) this;
        }

        public Criteria andGiftFlagNotLike(String value) {
            addCriterion("GIFT_FLAG not like", value, "giftFlag");
            return (Criteria) this;
        }

        public Criteria andGiftFlagIn(List<String> values) {
            addCriterion("GIFT_FLAG in", values, "giftFlag");
            return (Criteria) this;
        }

        public Criteria andGiftFlagNotIn(List<String> values) {
            addCriterion("GIFT_FLAG not in", values, "giftFlag");
            return (Criteria) this;
        }

        public Criteria andGiftFlagBetween(String value1, String value2) {
            addCriterion("GIFT_FLAG between", value1, value2, "giftFlag");
            return (Criteria) this;
        }

        public Criteria andGiftFlagNotBetween(String value1, String value2) {
            addCriterion("GIFT_FLAG not between", value1, value2, "giftFlag");
            return (Criteria) this;
        }

        public Criteria andZamountHsjIsNull() {
            addCriterion("ZAMOUNT_HSJ is null");
            return (Criteria) this;
        }

        public Criteria andZamountHsjIsNotNull() {
            addCriterion("ZAMOUNT_HSJ is not null");
            return (Criteria) this;
        }

        public Criteria andZamountHsjEqualTo(BigDecimal value) {
            addCriterion("ZAMOUNT_HSJ =", value, "zamountHsj");
            return (Criteria) this;
        }

        public Criteria andZamountHsjNotEqualTo(BigDecimal value) {
            addCriterion("ZAMOUNT_HSJ <>", value, "zamountHsj");
            return (Criteria) this;
        }

        public Criteria andZamountHsjGreaterThan(BigDecimal value) {
            addCriterion("ZAMOUNT_HSJ >", value, "zamountHsj");
            return (Criteria) this;
        }

        public Criteria andZamountHsjGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ZAMOUNT_HSJ >=", value, "zamountHsj");
            return (Criteria) this;
        }

        public Criteria andZamountHsjLessThan(BigDecimal value) {
            addCriterion("ZAMOUNT_HSJ <", value, "zamountHsj");
            return (Criteria) this;
        }

        public Criteria andZamountHsjLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ZAMOUNT_HSJ <=", value, "zamountHsj");
            return (Criteria) this;
        }

        public Criteria andZamountHsjIn(List<BigDecimal> values) {
            addCriterion("ZAMOUNT_HSJ in", values, "zamountHsj");
            return (Criteria) this;
        }

        public Criteria andZamountHsjNotIn(List<BigDecimal> values) {
            addCriterion("ZAMOUNT_HSJ not in", values, "zamountHsj");
            return (Criteria) this;
        }

        public Criteria andZamountHsjBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ZAMOUNT_HSJ between", value1, value2, "zamountHsj");
            return (Criteria) this;
        }

        public Criteria andZamountHsjNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ZAMOUNT_HSJ not between", value1, value2, "zamountHsj");
            return (Criteria) this;
        }

        public Criteria andZamountWsjIsNull() {
            addCriterion("ZAMOUNT_WSJ is null");
            return (Criteria) this;
        }

        public Criteria andZamountWsjIsNotNull() {
            addCriterion("ZAMOUNT_WSJ is not null");
            return (Criteria) this;
        }

        public Criteria andZamountWsjEqualTo(BigDecimal value) {
            addCriterion("ZAMOUNT_WSJ =", value, "zamountWsj");
            return (Criteria) this;
        }

        public Criteria andZamountWsjNotEqualTo(BigDecimal value) {
            addCriterion("ZAMOUNT_WSJ <>", value, "zamountWsj");
            return (Criteria) this;
        }

        public Criteria andZamountWsjGreaterThan(BigDecimal value) {
            addCriterion("ZAMOUNT_WSJ >", value, "zamountWsj");
            return (Criteria) this;
        }

        public Criteria andZamountWsjGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ZAMOUNT_WSJ >=", value, "zamountWsj");
            return (Criteria) this;
        }

        public Criteria andZamountWsjLessThan(BigDecimal value) {
            addCriterion("ZAMOUNT_WSJ <", value, "zamountWsj");
            return (Criteria) this;
        }

        public Criteria andZamountWsjLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ZAMOUNT_WSJ <=", value, "zamountWsj");
            return (Criteria) this;
        }

        public Criteria andZamountWsjIn(List<BigDecimal> values) {
            addCriterion("ZAMOUNT_WSJ in", values, "zamountWsj");
            return (Criteria) this;
        }

        public Criteria andZamountWsjNotIn(List<BigDecimal> values) {
            addCriterion("ZAMOUNT_WSJ not in", values, "zamountWsj");
            return (Criteria) this;
        }

        public Criteria andZamountWsjBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ZAMOUNT_WSJ between", value1, value2, "zamountWsj");
            return (Criteria) this;
        }

        public Criteria andZamountWsjNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ZAMOUNT_WSJ not between", value1, value2, "zamountWsj");
            return (Criteria) this;
        }

        public Criteria andZamountSejIsNull() {
            addCriterion("ZAMOUNT_SEJ is null");
            return (Criteria) this;
        }

        public Criteria andZamountSejIsNotNull() {
            addCriterion("ZAMOUNT_SEJ is not null");
            return (Criteria) this;
        }

        public Criteria andZamountSejEqualTo(BigDecimal value) {
            addCriterion("ZAMOUNT_SEJ =", value, "zamountSej");
            return (Criteria) this;
        }

        public Criteria andZamountSejNotEqualTo(BigDecimal value) {
            addCriterion("ZAMOUNT_SEJ <>", value, "zamountSej");
            return (Criteria) this;
        }

        public Criteria andZamountSejGreaterThan(BigDecimal value) {
            addCriterion("ZAMOUNT_SEJ >", value, "zamountSej");
            return (Criteria) this;
        }

        public Criteria andZamountSejGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ZAMOUNT_SEJ >=", value, "zamountSej");
            return (Criteria) this;
        }

        public Criteria andZamountSejLessThan(BigDecimal value) {
            addCriterion("ZAMOUNT_SEJ <", value, "zamountSej");
            return (Criteria) this;
        }

        public Criteria andZamountSejLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ZAMOUNT_SEJ <=", value, "zamountSej");
            return (Criteria) this;
        }

        public Criteria andZamountSejIn(List<BigDecimal> values) {
            addCriterion("ZAMOUNT_SEJ in", values, "zamountSej");
            return (Criteria) this;
        }

        public Criteria andZamountSejNotIn(List<BigDecimal> values) {
            addCriterion("ZAMOUNT_SEJ not in", values, "zamountSej");
            return (Criteria) this;
        }

        public Criteria andZamountSejBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ZAMOUNT_SEJ between", value1, value2, "zamountSej");
            return (Criteria) this;
        }

        public Criteria andZamountSejNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ZAMOUNT_SEJ not between", value1, value2, "zamountSej");
            return (Criteria) this;
        }

        public Criteria andZamountHzhsIsNull() {
            addCriterion("ZAMOUNT_HZHS is null");
            return (Criteria) this;
        }

        public Criteria andZamountHzhsIsNotNull() {
            addCriterion("ZAMOUNT_HZHS is not null");
            return (Criteria) this;
        }

        public Criteria andZamountHzhsEqualTo(BigDecimal value) {
            addCriterion("ZAMOUNT_HZHS =", value, "zamountHzhs");
            return (Criteria) this;
        }

        public Criteria andZamountHzhsNotEqualTo(BigDecimal value) {
            addCriterion("ZAMOUNT_HZHS <>", value, "zamountHzhs");
            return (Criteria) this;
        }

        public Criteria andZamountHzhsGreaterThan(BigDecimal value) {
            addCriterion("ZAMOUNT_HZHS >", value, "zamountHzhs");
            return (Criteria) this;
        }

        public Criteria andZamountHzhsGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ZAMOUNT_HZHS >=", value, "zamountHzhs");
            return (Criteria) this;
        }

        public Criteria andZamountHzhsLessThan(BigDecimal value) {
            addCriterion("ZAMOUNT_HZHS <", value, "zamountHzhs");
            return (Criteria) this;
        }

        public Criteria andZamountHzhsLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ZAMOUNT_HZHS <=", value, "zamountHzhs");
            return (Criteria) this;
        }

        public Criteria andZamountHzhsIn(List<BigDecimal> values) {
            addCriterion("ZAMOUNT_HZHS in", values, "zamountHzhs");
            return (Criteria) this;
        }

        public Criteria andZamountHzhsNotIn(List<BigDecimal> values) {
            addCriterion("ZAMOUNT_HZHS not in", values, "zamountHzhs");
            return (Criteria) this;
        }

        public Criteria andZamountHzhsBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ZAMOUNT_HZHS between", value1, value2, "zamountHzhs");
            return (Criteria) this;
        }

        public Criteria andZamountHzhsNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ZAMOUNT_HZHS not between", value1, value2, "zamountHzhs");
            return (Criteria) this;
        }

        public Criteria andZamountHzwsIsNull() {
            addCriterion("ZAMOUNT_HZWS is null");
            return (Criteria) this;
        }

        public Criteria andZamountHzwsIsNotNull() {
            addCriterion("ZAMOUNT_HZWS is not null");
            return (Criteria) this;
        }

        public Criteria andZamountHzwsEqualTo(BigDecimal value) {
            addCriterion("ZAMOUNT_HZWS =", value, "zamountHzws");
            return (Criteria) this;
        }

        public Criteria andZamountHzwsNotEqualTo(BigDecimal value) {
            addCriterion("ZAMOUNT_HZWS <>", value, "zamountHzws");
            return (Criteria) this;
        }

        public Criteria andZamountHzwsGreaterThan(BigDecimal value) {
            addCriterion("ZAMOUNT_HZWS >", value, "zamountHzws");
            return (Criteria) this;
        }

        public Criteria andZamountHzwsGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ZAMOUNT_HZWS >=", value, "zamountHzws");
            return (Criteria) this;
        }

        public Criteria andZamountHzwsLessThan(BigDecimal value) {
            addCriterion("ZAMOUNT_HZWS <", value, "zamountHzws");
            return (Criteria) this;
        }

        public Criteria andZamountHzwsLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ZAMOUNT_HZWS <=", value, "zamountHzws");
            return (Criteria) this;
        }

        public Criteria andZamountHzwsIn(List<BigDecimal> values) {
            addCriterion("ZAMOUNT_HZWS in", values, "zamountHzws");
            return (Criteria) this;
        }

        public Criteria andZamountHzwsNotIn(List<BigDecimal> values) {
            addCriterion("ZAMOUNT_HZWS not in", values, "zamountHzws");
            return (Criteria) this;
        }

        public Criteria andZamountHzwsBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ZAMOUNT_HZWS between", value1, value2, "zamountHzws");
            return (Criteria) this;
        }

        public Criteria andZamountHzwsNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ZAMOUNT_HZWS not between", value1, value2, "zamountHzws");
            return (Criteria) this;
        }

        public Criteria andZamountHzseIsNull() {
            addCriterion("ZAMOUNT_HZSE is null");
            return (Criteria) this;
        }

        public Criteria andZamountHzseIsNotNull() {
            addCriterion("ZAMOUNT_HZSE is not null");
            return (Criteria) this;
        }

        public Criteria andZamountHzseEqualTo(BigDecimal value) {
            addCriterion("ZAMOUNT_HZSE =", value, "zamountHzse");
            return (Criteria) this;
        }

        public Criteria andZamountHzseNotEqualTo(BigDecimal value) {
            addCriterion("ZAMOUNT_HZSE <>", value, "zamountHzse");
            return (Criteria) this;
        }

        public Criteria andZamountHzseGreaterThan(BigDecimal value) {
            addCriterion("ZAMOUNT_HZSE >", value, "zamountHzse");
            return (Criteria) this;
        }

        public Criteria andZamountHzseGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ZAMOUNT_HZSE >=", value, "zamountHzse");
            return (Criteria) this;
        }

        public Criteria andZamountHzseLessThan(BigDecimal value) {
            addCriterion("ZAMOUNT_HZSE <", value, "zamountHzse");
            return (Criteria) this;
        }

        public Criteria andZamountHzseLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ZAMOUNT_HZSE <=", value, "zamountHzse");
            return (Criteria) this;
        }

        public Criteria andZamountHzseIn(List<BigDecimal> values) {
            addCriterion("ZAMOUNT_HZSE in", values, "zamountHzse");
            return (Criteria) this;
        }

        public Criteria andZamountHzseNotIn(List<BigDecimal> values) {
            addCriterion("ZAMOUNT_HZSE not in", values, "zamountHzse");
            return (Criteria) this;
        }

        public Criteria andZamountHzseBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ZAMOUNT_HZSE between", value1, value2, "zamountHzse");
            return (Criteria) this;
        }

        public Criteria andZamountHzseNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ZAMOUNT_HZSE not between", value1, value2, "zamountHzse");
            return (Criteria) this;
        }

        public Criteria andZamountHsyIsNull() {
            addCriterion("ZAMOUNT_HSY is null");
            return (Criteria) this;
        }

        public Criteria andZamountHsyIsNotNull() {
            addCriterion("ZAMOUNT_HSY is not null");
            return (Criteria) this;
        }

        public Criteria andZamountHsyEqualTo(BigDecimal value) {
            addCriterion("ZAMOUNT_HSY =", value, "zamountHsy");
            return (Criteria) this;
        }

        public Criteria andZamountHsyNotEqualTo(BigDecimal value) {
            addCriterion("ZAMOUNT_HSY <>", value, "zamountHsy");
            return (Criteria) this;
        }

        public Criteria andZamountHsyGreaterThan(BigDecimal value) {
            addCriterion("ZAMOUNT_HSY >", value, "zamountHsy");
            return (Criteria) this;
        }

        public Criteria andZamountHsyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ZAMOUNT_HSY >=", value, "zamountHsy");
            return (Criteria) this;
        }

        public Criteria andZamountHsyLessThan(BigDecimal value) {
            addCriterion("ZAMOUNT_HSY <", value, "zamountHsy");
            return (Criteria) this;
        }

        public Criteria andZamountHsyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ZAMOUNT_HSY <=", value, "zamountHsy");
            return (Criteria) this;
        }

        public Criteria andZamountHsyIn(List<BigDecimal> values) {
            addCriterion("ZAMOUNT_HSY in", values, "zamountHsy");
            return (Criteria) this;
        }

        public Criteria andZamountHsyNotIn(List<BigDecimal> values) {
            addCriterion("ZAMOUNT_HSY not in", values, "zamountHsy");
            return (Criteria) this;
        }

        public Criteria andZamountHsyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ZAMOUNT_HSY between", value1, value2, "zamountHsy");
            return (Criteria) this;
        }

        public Criteria andZamountHsyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ZAMOUNT_HSY not between", value1, value2, "zamountHsy");
            return (Criteria) this;
        }

        public Criteria andZamountWsyIsNull() {
            addCriterion("ZAMOUNT_WSY is null");
            return (Criteria) this;
        }

        public Criteria andZamountWsyIsNotNull() {
            addCriterion("ZAMOUNT_WSY is not null");
            return (Criteria) this;
        }

        public Criteria andZamountWsyEqualTo(BigDecimal value) {
            addCriterion("ZAMOUNT_WSY =", value, "zamountWsy");
            return (Criteria) this;
        }

        public Criteria andZamountWsyNotEqualTo(BigDecimal value) {
            addCriterion("ZAMOUNT_WSY <>", value, "zamountWsy");
            return (Criteria) this;
        }

        public Criteria andZamountWsyGreaterThan(BigDecimal value) {
            addCriterion("ZAMOUNT_WSY >", value, "zamountWsy");
            return (Criteria) this;
        }

        public Criteria andZamountWsyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ZAMOUNT_WSY >=", value, "zamountWsy");
            return (Criteria) this;
        }

        public Criteria andZamountWsyLessThan(BigDecimal value) {
            addCriterion("ZAMOUNT_WSY <", value, "zamountWsy");
            return (Criteria) this;
        }

        public Criteria andZamountWsyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ZAMOUNT_WSY <=", value, "zamountWsy");
            return (Criteria) this;
        }

        public Criteria andZamountWsyIn(List<BigDecimal> values) {
            addCriterion("ZAMOUNT_WSY in", values, "zamountWsy");
            return (Criteria) this;
        }

        public Criteria andZamountWsyNotIn(List<BigDecimal> values) {
            addCriterion("ZAMOUNT_WSY not in", values, "zamountWsy");
            return (Criteria) this;
        }

        public Criteria andZamountWsyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ZAMOUNT_WSY between", value1, value2, "zamountWsy");
            return (Criteria) this;
        }

        public Criteria andZamountWsyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ZAMOUNT_WSY not between", value1, value2, "zamountWsy");
            return (Criteria) this;
        }

        public Criteria andZamountSeyIsNull() {
            addCriterion("ZAMOUNT_SEY is null");
            return (Criteria) this;
        }

        public Criteria andZamountSeyIsNotNull() {
            addCriterion("ZAMOUNT_SEY is not null");
            return (Criteria) this;
        }

        public Criteria andZamountSeyEqualTo(BigDecimal value) {
            addCriterion("ZAMOUNT_SEY =", value, "zamountSey");
            return (Criteria) this;
        }

        public Criteria andZamountSeyNotEqualTo(BigDecimal value) {
            addCriterion("ZAMOUNT_SEY <>", value, "zamountSey");
            return (Criteria) this;
        }

        public Criteria andZamountSeyGreaterThan(BigDecimal value) {
            addCriterion("ZAMOUNT_SEY >", value, "zamountSey");
            return (Criteria) this;
        }

        public Criteria andZamountSeyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ZAMOUNT_SEY >=", value, "zamountSey");
            return (Criteria) this;
        }

        public Criteria andZamountSeyLessThan(BigDecimal value) {
            addCriterion("ZAMOUNT_SEY <", value, "zamountSey");
            return (Criteria) this;
        }

        public Criteria andZamountSeyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ZAMOUNT_SEY <=", value, "zamountSey");
            return (Criteria) this;
        }

        public Criteria andZamountSeyIn(List<BigDecimal> values) {
            addCriterion("ZAMOUNT_SEY in", values, "zamountSey");
            return (Criteria) this;
        }

        public Criteria andZamountSeyNotIn(List<BigDecimal> values) {
            addCriterion("ZAMOUNT_SEY not in", values, "zamountSey");
            return (Criteria) this;
        }

        public Criteria andZamountSeyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ZAMOUNT_SEY between", value1, value2, "zamountSey");
            return (Criteria) this;
        }

        public Criteria andZamountSeyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ZAMOUNT_SEY not between", value1, value2, "zamountSey");
            return (Criteria) this;
        }

        public Criteria andZpriceHsjIsNull() {
            addCriterion("ZPRICE_HSJ is null");
            return (Criteria) this;
        }

        public Criteria andZpriceHsjIsNotNull() {
            addCriterion("ZPRICE_HSJ is not null");
            return (Criteria) this;
        }

        public Criteria andZpriceHsjEqualTo(BigDecimal value) {
            addCriterion("ZPRICE_HSJ =", value, "zpriceHsj");
            return (Criteria) this;
        }

        public Criteria andZpriceHsjNotEqualTo(BigDecimal value) {
            addCriterion("ZPRICE_HSJ <>", value, "zpriceHsj");
            return (Criteria) this;
        }

        public Criteria andZpriceHsjGreaterThan(BigDecimal value) {
            addCriterion("ZPRICE_HSJ >", value, "zpriceHsj");
            return (Criteria) this;
        }

        public Criteria andZpriceHsjGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ZPRICE_HSJ >=", value, "zpriceHsj");
            return (Criteria) this;
        }

        public Criteria andZpriceHsjLessThan(BigDecimal value) {
            addCriterion("ZPRICE_HSJ <", value, "zpriceHsj");
            return (Criteria) this;
        }

        public Criteria andZpriceHsjLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ZPRICE_HSJ <=", value, "zpriceHsj");
            return (Criteria) this;
        }

        public Criteria andZpriceHsjIn(List<BigDecimal> values) {
            addCriterion("ZPRICE_HSJ in", values, "zpriceHsj");
            return (Criteria) this;
        }

        public Criteria andZpriceHsjNotIn(List<BigDecimal> values) {
            addCriterion("ZPRICE_HSJ not in", values, "zpriceHsj");
            return (Criteria) this;
        }

        public Criteria andZpriceHsjBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ZPRICE_HSJ between", value1, value2, "zpriceHsj");
            return (Criteria) this;
        }

        public Criteria andZpriceHsjNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ZPRICE_HSJ not between", value1, value2, "zpriceHsj");
            return (Criteria) this;
        }

        public Criteria andZpriceWsjIsNull() {
            addCriterion("ZPRICE_WSJ is null");
            return (Criteria) this;
        }

        public Criteria andZpriceWsjIsNotNull() {
            addCriterion("ZPRICE_WSJ is not null");
            return (Criteria) this;
        }

        public Criteria andZpriceWsjEqualTo(BigDecimal value) {
            addCriterion("ZPRICE_WSJ =", value, "zpriceWsj");
            return (Criteria) this;
        }

        public Criteria andZpriceWsjNotEqualTo(BigDecimal value) {
            addCriterion("ZPRICE_WSJ <>", value, "zpriceWsj");
            return (Criteria) this;
        }

        public Criteria andZpriceWsjGreaterThan(BigDecimal value) {
            addCriterion("ZPRICE_WSJ >", value, "zpriceWsj");
            return (Criteria) this;
        }

        public Criteria andZpriceWsjGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ZPRICE_WSJ >=", value, "zpriceWsj");
            return (Criteria) this;
        }

        public Criteria andZpriceWsjLessThan(BigDecimal value) {
            addCriterion("ZPRICE_WSJ <", value, "zpriceWsj");
            return (Criteria) this;
        }

        public Criteria andZpriceWsjLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ZPRICE_WSJ <=", value, "zpriceWsj");
            return (Criteria) this;
        }

        public Criteria andZpriceWsjIn(List<BigDecimal> values) {
            addCriterion("ZPRICE_WSJ in", values, "zpriceWsj");
            return (Criteria) this;
        }

        public Criteria andZpriceWsjNotIn(List<BigDecimal> values) {
            addCriterion("ZPRICE_WSJ not in", values, "zpriceWsj");
            return (Criteria) this;
        }

        public Criteria andZpriceWsjBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ZPRICE_WSJ between", value1, value2, "zpriceWsj");
            return (Criteria) this;
        }

        public Criteria andZpriceWsjNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ZPRICE_WSJ not between", value1, value2, "zpriceWsj");
            return (Criteria) this;
        }

        public Criteria andZpriceHsyIsNull() {
            addCriterion("ZPRICE_HSY is null");
            return (Criteria) this;
        }

        public Criteria andZpriceHsyIsNotNull() {
            addCriterion("ZPRICE_HSY is not null");
            return (Criteria) this;
        }

        public Criteria andZpriceHsyEqualTo(BigDecimal value) {
            addCriterion("ZPRICE_HSY =", value, "zpriceHsy");
            return (Criteria) this;
        }

        public Criteria andZpriceHsyNotEqualTo(BigDecimal value) {
            addCriterion("ZPRICE_HSY <>", value, "zpriceHsy");
            return (Criteria) this;
        }

        public Criteria andZpriceHsyGreaterThan(BigDecimal value) {
            addCriterion("ZPRICE_HSY >", value, "zpriceHsy");
            return (Criteria) this;
        }

        public Criteria andZpriceHsyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ZPRICE_HSY >=", value, "zpriceHsy");
            return (Criteria) this;
        }

        public Criteria andZpriceHsyLessThan(BigDecimal value) {
            addCriterion("ZPRICE_HSY <", value, "zpriceHsy");
            return (Criteria) this;
        }

        public Criteria andZpriceHsyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ZPRICE_HSY <=", value, "zpriceHsy");
            return (Criteria) this;
        }

        public Criteria andZpriceHsyIn(List<BigDecimal> values) {
            addCriterion("ZPRICE_HSY in", values, "zpriceHsy");
            return (Criteria) this;
        }

        public Criteria andZpriceHsyNotIn(List<BigDecimal> values) {
            addCriterion("ZPRICE_HSY not in", values, "zpriceHsy");
            return (Criteria) this;
        }

        public Criteria andZpriceHsyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ZPRICE_HSY between", value1, value2, "zpriceHsy");
            return (Criteria) this;
        }

        public Criteria andZpriceHsyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ZPRICE_HSY not between", value1, value2, "zpriceHsy");
            return (Criteria) this;
        }

        public Criteria andZpriceWsyIsNull() {
            addCriterion("ZPRICE_WSY is null");
            return (Criteria) this;
        }

        public Criteria andZpriceWsyIsNotNull() {
            addCriterion("ZPRICE_WSY is not null");
            return (Criteria) this;
        }

        public Criteria andZpriceWsyEqualTo(BigDecimal value) {
            addCriterion("ZPRICE_WSY =", value, "zpriceWsy");
            return (Criteria) this;
        }

        public Criteria andZpriceWsyNotEqualTo(BigDecimal value) {
            addCriterion("ZPRICE_WSY <>", value, "zpriceWsy");
            return (Criteria) this;
        }

        public Criteria andZpriceWsyGreaterThan(BigDecimal value) {
            addCriterion("ZPRICE_WSY >", value, "zpriceWsy");
            return (Criteria) this;
        }

        public Criteria andZpriceWsyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ZPRICE_WSY >=", value, "zpriceWsy");
            return (Criteria) this;
        }

        public Criteria andZpriceWsyLessThan(BigDecimal value) {
            addCriterion("ZPRICE_WSY <", value, "zpriceWsy");
            return (Criteria) this;
        }

        public Criteria andZpriceWsyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ZPRICE_WSY <=", value, "zpriceWsy");
            return (Criteria) this;
        }

        public Criteria andZpriceWsyIn(List<BigDecimal> values) {
            addCriterion("ZPRICE_WSY in", values, "zpriceWsy");
            return (Criteria) this;
        }

        public Criteria andZpriceWsyNotIn(List<BigDecimal> values) {
            addCriterion("ZPRICE_WSY not in", values, "zpriceWsy");
            return (Criteria) this;
        }

        public Criteria andZpriceWsyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ZPRICE_WSY between", value1, value2, "zpriceWsy");
            return (Criteria) this;
        }

        public Criteria andZpriceWsyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ZPRICE_WSY not between", value1, value2, "zpriceWsy");
            return (Criteria) this;
        }

        public Criteria andBillDateIsNull() {
            addCriterion("BILL_DATE is null");
            return (Criteria) this;
        }

        public Criteria andBillDateIsNotNull() {
            addCriterion("BILL_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andBillDateEqualTo(String value) {
            addCriterion("BILL_DATE =", value, "billDate");
            return (Criteria) this;
        }

        public Criteria andBillDateNotEqualTo(String value) {
            addCriterion("BILL_DATE <>", value, "billDate");
            return (Criteria) this;
        }

        public Criteria andBillDateGreaterThan(String value) {
            addCriterion("BILL_DATE >", value, "billDate");
            return (Criteria) this;
        }

        public Criteria andBillDateGreaterThanOrEqualTo(String value) {
            addCriterion("BILL_DATE >=", value, "billDate");
            return (Criteria) this;
        }

        public Criteria andBillDateLessThan(String value) {
            addCriterion("BILL_DATE <", value, "billDate");
            return (Criteria) this;
        }

        public Criteria andBillDateLessThanOrEqualTo(String value) {
            addCriterion("BILL_DATE <=", value, "billDate");
            return (Criteria) this;
        }

        public Criteria andBillDateLike(String value) {
            addCriterion("BILL_DATE like", value, "billDate");
            return (Criteria) this;
        }

        public Criteria andBillDateNotLike(String value) {
            addCriterion("BILL_DATE not like", value, "billDate");
            return (Criteria) this;
        }

        public Criteria andBillDateIn(List<String> values) {
            addCriterion("BILL_DATE in", values, "billDate");
            return (Criteria) this;
        }

        public Criteria andBillDateNotIn(List<String> values) {
            addCriterion("BILL_DATE not in", values, "billDate");
            return (Criteria) this;
        }

        public Criteria andBillDateBetween(String value1, String value2) {
            addCriterion("BILL_DATE between", value1, value2, "billDate");
            return (Criteria) this;
        }

        public Criteria andBillDateNotBetween(String value1, String value2) {
            addCriterion("BILL_DATE not between", value1, value2, "billDate");
            return (Criteria) this;
        }

        public Criteria andCancelDateIsNull() {
            addCriterion("CANCEL_DATE is null");
            return (Criteria) this;
        }

        public Criteria andCancelDateIsNotNull() {
            addCriterion("CANCEL_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andCancelDateEqualTo(String value) {
            addCriterion("CANCEL_DATE =", value, "cancelDate");
            return (Criteria) this;
        }

        public Criteria andCancelDateNotEqualTo(String value) {
            addCriterion("CANCEL_DATE <>", value, "cancelDate");
            return (Criteria) this;
        }

        public Criteria andCancelDateGreaterThan(String value) {
            addCriterion("CANCEL_DATE >", value, "cancelDate");
            return (Criteria) this;
        }

        public Criteria andCancelDateGreaterThanOrEqualTo(String value) {
            addCriterion("CANCEL_DATE >=", value, "cancelDate");
            return (Criteria) this;
        }

        public Criteria andCancelDateLessThan(String value) {
            addCriterion("CANCEL_DATE <", value, "cancelDate");
            return (Criteria) this;
        }

        public Criteria andCancelDateLessThanOrEqualTo(String value) {
            addCriterion("CANCEL_DATE <=", value, "cancelDate");
            return (Criteria) this;
        }

        public Criteria andCancelDateLike(String value) {
            addCriterion("CANCEL_DATE like", value, "cancelDate");
            return (Criteria) this;
        }

        public Criteria andCancelDateNotLike(String value) {
            addCriterion("CANCEL_DATE not like", value, "cancelDate");
            return (Criteria) this;
        }

        public Criteria andCancelDateIn(List<String> values) {
            addCriterion("CANCEL_DATE in", values, "cancelDate");
            return (Criteria) this;
        }

        public Criteria andCancelDateNotIn(List<String> values) {
            addCriterion("CANCEL_DATE not in", values, "cancelDate");
            return (Criteria) this;
        }

        public Criteria andCancelDateBetween(String value1, String value2) {
            addCriterion("CANCEL_DATE between", value1, value2, "cancelDate");
            return (Criteria) this;
        }

        public Criteria andCancelDateNotBetween(String value1, String value2) {
            addCriterion("CANCEL_DATE not between", value1, value2, "cancelDate");
            return (Criteria) this;
        }

        public Criteria andBillGdateIsNull() {
            addCriterion("BILL_GDATE is null");
            return (Criteria) this;
        }

        public Criteria andBillGdateIsNotNull() {
            addCriterion("BILL_GDATE is not null");
            return (Criteria) this;
        }

        public Criteria andBillGdateEqualTo(String value) {
            addCriterion("BILL_GDATE =", value, "billGdate");
            return (Criteria) this;
        }

        public Criteria andBillGdateNotEqualTo(String value) {
            addCriterion("BILL_GDATE <>", value, "billGdate");
            return (Criteria) this;
        }

        public Criteria andBillGdateGreaterThan(String value) {
            addCriterion("BILL_GDATE >", value, "billGdate");
            return (Criteria) this;
        }

        public Criteria andBillGdateGreaterThanOrEqualTo(String value) {
            addCriterion("BILL_GDATE >=", value, "billGdate");
            return (Criteria) this;
        }

        public Criteria andBillGdateLessThan(String value) {
            addCriterion("BILL_GDATE <", value, "billGdate");
            return (Criteria) this;
        }

        public Criteria andBillGdateLessThanOrEqualTo(String value) {
            addCriterion("BILL_GDATE <=", value, "billGdate");
            return (Criteria) this;
        }

        public Criteria andBillGdateLike(String value) {
            addCriterion("BILL_GDATE like", value, "billGdate");
            return (Criteria) this;
        }

        public Criteria andBillGdateNotLike(String value) {
            addCriterion("BILL_GDATE not like", value, "billGdate");
            return (Criteria) this;
        }

        public Criteria andBillGdateIn(List<String> values) {
            addCriterion("BILL_GDATE in", values, "billGdate");
            return (Criteria) this;
        }

        public Criteria andBillGdateNotIn(List<String> values) {
            addCriterion("BILL_GDATE not in", values, "billGdate");
            return (Criteria) this;
        }

        public Criteria andBillGdateBetween(String value1, String value2) {
            addCriterion("BILL_GDATE between", value1, value2, "billGdate");
            return (Criteria) this;
        }

        public Criteria andBillGdateNotBetween(String value1, String value2) {
            addCriterion("BILL_GDATE not between", value1, value2, "billGdate");
            return (Criteria) this;
        }

        public Criteria andCancelGdateIsNull() {
            addCriterion("CANCEL_GDATE is null");
            return (Criteria) this;
        }

        public Criteria andCancelGdateIsNotNull() {
            addCriterion("CANCEL_GDATE is not null");
            return (Criteria) this;
        }

        public Criteria andCancelGdateEqualTo(String value) {
            addCriterion("CANCEL_GDATE =", value, "cancelGdate");
            return (Criteria) this;
        }

        public Criteria andCancelGdateNotEqualTo(String value) {
            addCriterion("CANCEL_GDATE <>", value, "cancelGdate");
            return (Criteria) this;
        }

        public Criteria andCancelGdateGreaterThan(String value) {
            addCriterion("CANCEL_GDATE >", value, "cancelGdate");
            return (Criteria) this;
        }

        public Criteria andCancelGdateGreaterThanOrEqualTo(String value) {
            addCriterion("CANCEL_GDATE >=", value, "cancelGdate");
            return (Criteria) this;
        }

        public Criteria andCancelGdateLessThan(String value) {
            addCriterion("CANCEL_GDATE <", value, "cancelGdate");
            return (Criteria) this;
        }

        public Criteria andCancelGdateLessThanOrEqualTo(String value) {
            addCriterion("CANCEL_GDATE <=", value, "cancelGdate");
            return (Criteria) this;
        }

        public Criteria andCancelGdateLike(String value) {
            addCriterion("CANCEL_GDATE like", value, "cancelGdate");
            return (Criteria) this;
        }

        public Criteria andCancelGdateNotLike(String value) {
            addCriterion("CANCEL_GDATE not like", value, "cancelGdate");
            return (Criteria) this;
        }

        public Criteria andCancelGdateIn(List<String> values) {
            addCriterion("CANCEL_GDATE in", values, "cancelGdate");
            return (Criteria) this;
        }

        public Criteria andCancelGdateNotIn(List<String> values) {
            addCriterion("CANCEL_GDATE not in", values, "cancelGdate");
            return (Criteria) this;
        }

        public Criteria andCancelGdateBetween(String value1, String value2) {
            addCriterion("CANCEL_GDATE between", value1, value2, "cancelGdate");
            return (Criteria) this;
        }

        public Criteria andCancelGdateNotBetween(String value1, String value2) {
            addCriterion("CANCEL_GDATE not between", value1, value2, "cancelGdate");
            return (Criteria) this;
        }

        public Criteria andGroupCopyfromIsNull() {
            addCriterion("GROUP_COPYFROM is null");
            return (Criteria) this;
        }

        public Criteria andGroupCopyfromIsNotNull() {
            addCriterion("GROUP_COPYFROM is not null");
            return (Criteria) this;
        }

        public Criteria andGroupCopyfromEqualTo(String value) {
            addCriterion("GROUP_COPYFROM =", value, "groupCopyfrom");
            return (Criteria) this;
        }

        public Criteria andGroupCopyfromNotEqualTo(String value) {
            addCriterion("GROUP_COPYFROM <>", value, "groupCopyfrom");
            return (Criteria) this;
        }

        public Criteria andGroupCopyfromGreaterThan(String value) {
            addCriterion("GROUP_COPYFROM >", value, "groupCopyfrom");
            return (Criteria) this;
        }

        public Criteria andGroupCopyfromGreaterThanOrEqualTo(String value) {
            addCriterion("GROUP_COPYFROM >=", value, "groupCopyfrom");
            return (Criteria) this;
        }

        public Criteria andGroupCopyfromLessThan(String value) {
            addCriterion("GROUP_COPYFROM <", value, "groupCopyfrom");
            return (Criteria) this;
        }

        public Criteria andGroupCopyfromLessThanOrEqualTo(String value) {
            addCriterion("GROUP_COPYFROM <=", value, "groupCopyfrom");
            return (Criteria) this;
        }

        public Criteria andGroupCopyfromLike(String value) {
            addCriterion("GROUP_COPYFROM like", value, "groupCopyfrom");
            return (Criteria) this;
        }

        public Criteria andGroupCopyfromNotLike(String value) {
            addCriterion("GROUP_COPYFROM not like", value, "groupCopyfrom");
            return (Criteria) this;
        }

        public Criteria andGroupCopyfromIn(List<String> values) {
            addCriterion("GROUP_COPYFROM in", values, "groupCopyfrom");
            return (Criteria) this;
        }

        public Criteria andGroupCopyfromNotIn(List<String> values) {
            addCriterion("GROUP_COPYFROM not in", values, "groupCopyfrom");
            return (Criteria) this;
        }

        public Criteria andGroupCopyfromBetween(String value1, String value2) {
            addCriterion("GROUP_COPYFROM between", value1, value2, "groupCopyfrom");
            return (Criteria) this;
        }

        public Criteria andGroupCopyfromNotBetween(String value1, String value2) {
            addCriterion("GROUP_COPYFROM not between", value1, value2, "groupCopyfrom");
            return (Criteria) this;
        }

        public Criteria andAttribute1IsNull() {
            addCriterion("ATTRIBUTE1 is null");
            return (Criteria) this;
        }

        public Criteria andAttribute1IsNotNull() {
            addCriterion("ATTRIBUTE1 is not null");
            return (Criteria) this;
        }

        public Criteria andAttribute1EqualTo(String value) {
            addCriterion("ATTRIBUTE1 =", value, "attribute1");
            return (Criteria) this;
        }

        public Criteria andAttribute1NotEqualTo(String value) {
            addCriterion("ATTRIBUTE1 <>", value, "attribute1");
            return (Criteria) this;
        }

        public Criteria andAttribute1GreaterThan(String value) {
            addCriterion("ATTRIBUTE1 >", value, "attribute1");
            return (Criteria) this;
        }

        public Criteria andAttribute1GreaterThanOrEqualTo(String value) {
            addCriterion("ATTRIBUTE1 >=", value, "attribute1");
            return (Criteria) this;
        }

        public Criteria andAttribute1LessThan(String value) {
            addCriterion("ATTRIBUTE1 <", value, "attribute1");
            return (Criteria) this;
        }

        public Criteria andAttribute1LessThanOrEqualTo(String value) {
            addCriterion("ATTRIBUTE1 <=", value, "attribute1");
            return (Criteria) this;
        }

        public Criteria andAttribute1Like(String value) {
            addCriterion("ATTRIBUTE1 like", value, "attribute1");
            return (Criteria) this;
        }

        public Criteria andAttribute1NotLike(String value) {
            addCriterion("ATTRIBUTE1 not like", value, "attribute1");
            return (Criteria) this;
        }

        public Criteria andAttribute1In(List<String> values) {
            addCriterion("ATTRIBUTE1 in", values, "attribute1");
            return (Criteria) this;
        }

        public Criteria andAttribute1NotIn(List<String> values) {
            addCriterion("ATTRIBUTE1 not in", values, "attribute1");
            return (Criteria) this;
        }

        public Criteria andAttribute1Between(String value1, String value2) {
            addCriterion("ATTRIBUTE1 between", value1, value2, "attribute1");
            return (Criteria) this;
        }

        public Criteria andAttribute1NotBetween(String value1, String value2) {
            addCriterion("ATTRIBUTE1 not between", value1, value2, "attribute1");
            return (Criteria) this;
        }

        public Criteria andAttribute2IsNull() {
            addCriterion("ATTRIBUTE2 is null");
            return (Criteria) this;
        }

        public Criteria andAttribute2IsNotNull() {
            addCriterion("ATTRIBUTE2 is not null");
            return (Criteria) this;
        }

        public Criteria andAttribute2EqualTo(String value) {
            addCriterion("ATTRIBUTE2 =", value, "attribute2");
            return (Criteria) this;
        }

        public Criteria andAttribute2NotEqualTo(String value) {
            addCriterion("ATTRIBUTE2 <>", value, "attribute2");
            return (Criteria) this;
        }

        public Criteria andAttribute2GreaterThan(String value) {
            addCriterion("ATTRIBUTE2 >", value, "attribute2");
            return (Criteria) this;
        }

        public Criteria andAttribute2GreaterThanOrEqualTo(String value) {
            addCriterion("ATTRIBUTE2 >=", value, "attribute2");
            return (Criteria) this;
        }

        public Criteria andAttribute2LessThan(String value) {
            addCriterion("ATTRIBUTE2 <", value, "attribute2");
            return (Criteria) this;
        }

        public Criteria andAttribute2LessThanOrEqualTo(String value) {
            addCriterion("ATTRIBUTE2 <=", value, "attribute2");
            return (Criteria) this;
        }

        public Criteria andAttribute2Like(String value) {
            addCriterion("ATTRIBUTE2 like", value, "attribute2");
            return (Criteria) this;
        }

        public Criteria andAttribute2NotLike(String value) {
            addCriterion("ATTRIBUTE2 not like", value, "attribute2");
            return (Criteria) this;
        }

        public Criteria andAttribute2In(List<String> values) {
            addCriterion("ATTRIBUTE2 in", values, "attribute2");
            return (Criteria) this;
        }

        public Criteria andAttribute2NotIn(List<String> values) {
            addCriterion("ATTRIBUTE2 not in", values, "attribute2");
            return (Criteria) this;
        }

        public Criteria andAttribute2Between(String value1, String value2) {
            addCriterion("ATTRIBUTE2 between", value1, value2, "attribute2");
            return (Criteria) this;
        }

        public Criteria andAttribute2NotBetween(String value1, String value2) {
            addCriterion("ATTRIBUTE2 not between", value1, value2, "attribute2");
            return (Criteria) this;
        }

        public Criteria andAttribute3IsNull() {
            addCriterion("ATTRIBUTE3 is null");
            return (Criteria) this;
        }

        public Criteria andAttribute3IsNotNull() {
            addCriterion("ATTRIBUTE3 is not null");
            return (Criteria) this;
        }

        public Criteria andAttribute3EqualTo(String value) {
            addCriterion("ATTRIBUTE3 =", value, "attribute3");
            return (Criteria) this;
        }

        public Criteria andAttribute3NotEqualTo(String value) {
            addCriterion("ATTRIBUTE3 <>", value, "attribute3");
            return (Criteria) this;
        }

        public Criteria andAttribute3GreaterThan(String value) {
            addCriterion("ATTRIBUTE3 >", value, "attribute3");
            return (Criteria) this;
        }

        public Criteria andAttribute3GreaterThanOrEqualTo(String value) {
            addCriterion("ATTRIBUTE3 >=", value, "attribute3");
            return (Criteria) this;
        }

        public Criteria andAttribute3LessThan(String value) {
            addCriterion("ATTRIBUTE3 <", value, "attribute3");
            return (Criteria) this;
        }

        public Criteria andAttribute3LessThanOrEqualTo(String value) {
            addCriterion("ATTRIBUTE3 <=", value, "attribute3");
            return (Criteria) this;
        }

        public Criteria andAttribute3Like(String value) {
            addCriterion("ATTRIBUTE3 like", value, "attribute3");
            return (Criteria) this;
        }

        public Criteria andAttribute3NotLike(String value) {
            addCriterion("ATTRIBUTE3 not like", value, "attribute3");
            return (Criteria) this;
        }

        public Criteria andAttribute3In(List<String> values) {
            addCriterion("ATTRIBUTE3 in", values, "attribute3");
            return (Criteria) this;
        }

        public Criteria andAttribute3NotIn(List<String> values) {
            addCriterion("ATTRIBUTE3 not in", values, "attribute3");
            return (Criteria) this;
        }

        public Criteria andAttribute3Between(String value1, String value2) {
            addCriterion("ATTRIBUTE3 between", value1, value2, "attribute3");
            return (Criteria) this;
        }

        public Criteria andAttribute3NotBetween(String value1, String value2) {
            addCriterion("ATTRIBUTE3 not between", value1, value2, "attribute3");
            return (Criteria) this;
        }

        public Criteria andDayinIsNull() {
            addCriterion("dayin is null");
            return (Criteria) this;
        }

        public Criteria andDayinIsNotNull() {
            addCriterion("dayin is not null");
            return (Criteria) this;
        }

        public Criteria andDayinEqualTo(String value) {
            addCriterion("dayin =", value, "dayin");
            return (Criteria) this;
        }

        public Criteria andDayinNotEqualTo(String value) {
            addCriterion("dayin <>", value, "dayin");
            return (Criteria) this;
        }

        public Criteria andDayinGreaterThan(String value) {
            addCriterion("dayin >", value, "dayin");
            return (Criteria) this;
        }

        public Criteria andDayinGreaterThanOrEqualTo(String value) {
            addCriterion("dayin >=", value, "dayin");
            return (Criteria) this;
        }

        public Criteria andDayinLessThan(String value) {
            addCriterion("dayin <", value, "dayin");
            return (Criteria) this;
        }

        public Criteria andDayinLessThanOrEqualTo(String value) {
            addCriterion("dayin <=", value, "dayin");
            return (Criteria) this;
        }

        public Criteria andDayinLike(String value) {
            addCriterion("dayin like", value, "dayin");
            return (Criteria) this;
        }

        public Criteria andDayinNotLike(String value) {
            addCriterion("dayin not like", value, "dayin");
            return (Criteria) this;
        }

        public Criteria andDayinIn(List<String> values) {
            addCriterion("dayin in", values, "dayin");
            return (Criteria) this;
        }

        public Criteria andDayinNotIn(List<String> values) {
            addCriterion("dayin not in", values, "dayin");
            return (Criteria) this;
        }

        public Criteria andDayinBetween(String value1, String value2) {
            addCriterion("dayin between", value1, value2, "dayin");
            return (Criteria) this;
        }

        public Criteria andDayinNotBetween(String value1, String value2) {
            addCriterion("dayin not between", value1, value2, "dayin");
            return (Criteria) this;
        }

        public Criteria andJianyanIsNull() {
            addCriterion("JIANYAN is null");
            return (Criteria) this;
        }

        public Criteria andJianyanIsNotNull() {
            addCriterion("JIANYAN is not null");
            return (Criteria) this;
        }

        public Criteria andJianyanEqualTo(String value) {
            addCriterion("JIANYAN =", value, "jianyan");
            return (Criteria) this;
        }

        public Criteria andJianyanNotEqualTo(String value) {
            addCriterion("JIANYAN <>", value, "jianyan");
            return (Criteria) this;
        }

        public Criteria andJianyanGreaterThan(String value) {
            addCriterion("JIANYAN >", value, "jianyan");
            return (Criteria) this;
        }

        public Criteria andJianyanGreaterThanOrEqualTo(String value) {
            addCriterion("JIANYAN >=", value, "jianyan");
            return (Criteria) this;
        }

        public Criteria andJianyanLessThan(String value) {
            addCriterion("JIANYAN <", value, "jianyan");
            return (Criteria) this;
        }

        public Criteria andJianyanLessThanOrEqualTo(String value) {
            addCriterion("JIANYAN <=", value, "jianyan");
            return (Criteria) this;
        }

        public Criteria andJianyanLike(String value) {
            addCriterion("JIANYAN like", value, "jianyan");
            return (Criteria) this;
        }

        public Criteria andJianyanNotLike(String value) {
            addCriterion("JIANYAN not like", value, "jianyan");
            return (Criteria) this;
        }

        public Criteria andJianyanIn(List<String> values) {
            addCriterion("JIANYAN in", values, "jianyan");
            return (Criteria) this;
        }

        public Criteria andJianyanNotIn(List<String> values) {
            addCriterion("JIANYAN not in", values, "jianyan");
            return (Criteria) this;
        }

        public Criteria andJianyanBetween(String value1, String value2) {
            addCriterion("JIANYAN between", value1, value2, "jianyan");
            return (Criteria) this;
        }

        public Criteria andJianyanNotBetween(String value1, String value2) {
            addCriterion("JIANYAN not between", value1, value2, "jianyan");
            return (Criteria) this;
        }

        public Criteria andHsjeIsNull() {
            addCriterion("HSJE is null");
            return (Criteria) this;
        }

        public Criteria andHsjeIsNotNull() {
            addCriterion("HSJE is not null");
            return (Criteria) this;
        }

        public Criteria andHsjeEqualTo(String value) {
            addCriterion("HSJE =", value, "hsje");
            return (Criteria) this;
        }

        public Criteria andHsjeNotEqualTo(String value) {
            addCriterion("HSJE <>", value, "hsje");
            return (Criteria) this;
        }

        public Criteria andHsjeGreaterThan(String value) {
            addCriterion("HSJE >", value, "hsje");
            return (Criteria) this;
        }

        public Criteria andHsjeGreaterThanOrEqualTo(String value) {
            addCriterion("HSJE >=", value, "hsje");
            return (Criteria) this;
        }

        public Criteria andHsjeLessThan(String value) {
            addCriterion("HSJE <", value, "hsje");
            return (Criteria) this;
        }

        public Criteria andHsjeLessThanOrEqualTo(String value) {
            addCriterion("HSJE <=", value, "hsje");
            return (Criteria) this;
        }

        public Criteria andHsjeLike(String value) {
            addCriterion("HSJE like", value, "hsje");
            return (Criteria) this;
        }

        public Criteria andHsjeNotLike(String value) {
            addCriterion("HSJE not like", value, "hsje");
            return (Criteria) this;
        }

        public Criteria andHsjeIn(List<String> values) {
            addCriterion("HSJE in", values, "hsje");
            return (Criteria) this;
        }

        public Criteria andHsjeNotIn(List<String> values) {
            addCriterion("HSJE not in", values, "hsje");
            return (Criteria) this;
        }

        public Criteria andHsjeBetween(String value1, String value2) {
            addCriterion("HSJE between", value1, value2, "hsje");
            return (Criteria) this;
        }

        public Criteria andHsjeNotBetween(String value1, String value2) {
            addCriterion("HSJE not between", value1, value2, "hsje");
            return (Criteria) this;
        }

        public Criteria andWsjeIsNull() {
            addCriterion("WSJE is null");
            return (Criteria) this;
        }

        public Criteria andWsjeIsNotNull() {
            addCriterion("WSJE is not null");
            return (Criteria) this;
        }

        public Criteria andWsjeEqualTo(String value) {
            addCriterion("WSJE =", value, "wsje");
            return (Criteria) this;
        }

        public Criteria andWsjeNotEqualTo(String value) {
            addCriterion("WSJE <>", value, "wsje");
            return (Criteria) this;
        }

        public Criteria andWsjeGreaterThan(String value) {
            addCriterion("WSJE >", value, "wsje");
            return (Criteria) this;
        }

        public Criteria andWsjeGreaterThanOrEqualTo(String value) {
            addCriterion("WSJE >=", value, "wsje");
            return (Criteria) this;
        }

        public Criteria andWsjeLessThan(String value) {
            addCriterion("WSJE <", value, "wsje");
            return (Criteria) this;
        }

        public Criteria andWsjeLessThanOrEqualTo(String value) {
            addCriterion("WSJE <=", value, "wsje");
            return (Criteria) this;
        }

        public Criteria andWsjeLike(String value) {
            addCriterion("WSJE like", value, "wsje");
            return (Criteria) this;
        }

        public Criteria andWsjeNotLike(String value) {
            addCriterion("WSJE not like", value, "wsje");
            return (Criteria) this;
        }

        public Criteria andWsjeIn(List<String> values) {
            addCriterion("WSJE in", values, "wsje");
            return (Criteria) this;
        }

        public Criteria andWsjeNotIn(List<String> values) {
            addCriterion("WSJE not in", values, "wsje");
            return (Criteria) this;
        }

        public Criteria andWsjeBetween(String value1, String value2) {
            addCriterion("WSJE between", value1, value2, "wsje");
            return (Criteria) this;
        }

        public Criteria andWsjeNotBetween(String value1, String value2) {
            addCriterion("WSJE not between", value1, value2, "wsje");
            return (Criteria) this;
        }

        public Criteria andSeIsNull() {
            addCriterion("SE is null");
            return (Criteria) this;
        }

        public Criteria andSeIsNotNull() {
            addCriterion("SE is not null");
            return (Criteria) this;
        }

        public Criteria andSeEqualTo(String value) {
            addCriterion("SE =", value, "se");
            return (Criteria) this;
        }

        public Criteria andSeNotEqualTo(String value) {
            addCriterion("SE <>", value, "se");
            return (Criteria) this;
        }

        public Criteria andSeGreaterThan(String value) {
            addCriterion("SE >", value, "se");
            return (Criteria) this;
        }

        public Criteria andSeGreaterThanOrEqualTo(String value) {
            addCriterion("SE >=", value, "se");
            return (Criteria) this;
        }

        public Criteria andSeLessThan(String value) {
            addCriterion("SE <", value, "se");
            return (Criteria) this;
        }

        public Criteria andSeLessThanOrEqualTo(String value) {
            addCriterion("SE <=", value, "se");
            return (Criteria) this;
        }

        public Criteria andSeLike(String value) {
            addCriterion("SE like", value, "se");
            return (Criteria) this;
        }

        public Criteria andSeNotLike(String value) {
            addCriterion("SE not like", value, "se");
            return (Criteria) this;
        }

        public Criteria andSeIn(List<String> values) {
            addCriterion("SE in", values, "se");
            return (Criteria) this;
        }

        public Criteria andSeNotIn(List<String> values) {
            addCriterion("SE not in", values, "se");
            return (Criteria) this;
        }

        public Criteria andSeBetween(String value1, String value2) {
            addCriterion("SE between", value1, value2, "se");
            return (Criteria) this;
        }

        public Criteria andSeNotBetween(String value1, String value2) {
            addCriterion("SE not between", value1, value2, "se");
            return (Criteria) this;
        }

        public Criteria andHsjecIsNull() {
            addCriterion("HSJEC is null");
            return (Criteria) this;
        }

        public Criteria andHsjecIsNotNull() {
            addCriterion("HSJEC is not null");
            return (Criteria) this;
        }

        public Criteria andHsjecEqualTo(String value) {
            addCriterion("HSJEC =", value, "hsjec");
            return (Criteria) this;
        }

        public Criteria andHsjecNotEqualTo(String value) {
            addCriterion("HSJEC <>", value, "hsjec");
            return (Criteria) this;
        }

        public Criteria andHsjecGreaterThan(String value) {
            addCriterion("HSJEC >", value, "hsjec");
            return (Criteria) this;
        }

        public Criteria andHsjecGreaterThanOrEqualTo(String value) {
            addCriterion("HSJEC >=", value, "hsjec");
            return (Criteria) this;
        }

        public Criteria andHsjecLessThan(String value) {
            addCriterion("HSJEC <", value, "hsjec");
            return (Criteria) this;
        }

        public Criteria andHsjecLessThanOrEqualTo(String value) {
            addCriterion("HSJEC <=", value, "hsjec");
            return (Criteria) this;
        }

        public Criteria andHsjecLike(String value) {
            addCriterion("HSJEC like", value, "hsjec");
            return (Criteria) this;
        }

        public Criteria andHsjecNotLike(String value) {
            addCriterion("HSJEC not like", value, "hsjec");
            return (Criteria) this;
        }

        public Criteria andHsjecIn(List<String> values) {
            addCriterion("HSJEC in", values, "hsjec");
            return (Criteria) this;
        }

        public Criteria andHsjecNotIn(List<String> values) {
            addCriterion("HSJEC not in", values, "hsjec");
            return (Criteria) this;
        }

        public Criteria andHsjecBetween(String value1, String value2) {
            addCriterion("HSJEC between", value1, value2, "hsjec");
            return (Criteria) this;
        }

        public Criteria andHsjecNotBetween(String value1, String value2) {
            addCriterion("HSJEC not between", value1, value2, "hsjec");
            return (Criteria) this;
        }

        public Criteria andWsjecIsNull() {
            addCriterion("WSJEC is null");
            return (Criteria) this;
        }

        public Criteria andWsjecIsNotNull() {
            addCriterion("WSJEC is not null");
            return (Criteria) this;
        }

        public Criteria andWsjecEqualTo(String value) {
            addCriterion("WSJEC =", value, "wsjec");
            return (Criteria) this;
        }

        public Criteria andWsjecNotEqualTo(String value) {
            addCriterion("WSJEC <>", value, "wsjec");
            return (Criteria) this;
        }

        public Criteria andWsjecGreaterThan(String value) {
            addCriterion("WSJEC >", value, "wsjec");
            return (Criteria) this;
        }

        public Criteria andWsjecGreaterThanOrEqualTo(String value) {
            addCriterion("WSJEC >=", value, "wsjec");
            return (Criteria) this;
        }

        public Criteria andWsjecLessThan(String value) {
            addCriterion("WSJEC <", value, "wsjec");
            return (Criteria) this;
        }

        public Criteria andWsjecLessThanOrEqualTo(String value) {
            addCriterion("WSJEC <=", value, "wsjec");
            return (Criteria) this;
        }

        public Criteria andWsjecLike(String value) {
            addCriterion("WSJEC like", value, "wsjec");
            return (Criteria) this;
        }

        public Criteria andWsjecNotLike(String value) {
            addCriterion("WSJEC not like", value, "wsjec");
            return (Criteria) this;
        }

        public Criteria andWsjecIn(List<String> values) {
            addCriterion("WSJEC in", values, "wsjec");
            return (Criteria) this;
        }

        public Criteria andWsjecNotIn(List<String> values) {
            addCriterion("WSJEC not in", values, "wsjec");
            return (Criteria) this;
        }

        public Criteria andWsjecBetween(String value1, String value2) {
            addCriterion("WSJEC between", value1, value2, "wsjec");
            return (Criteria) this;
        }

        public Criteria andWsjecNotBetween(String value1, String value2) {
            addCriterion("WSJEC not between", value1, value2, "wsjec");
            return (Criteria) this;
        }

        public Criteria andSecIsNull() {
            addCriterion("SEC is null");
            return (Criteria) this;
        }

        public Criteria andSecIsNotNull() {
            addCriterion("SEC is not null");
            return (Criteria) this;
        }

        public Criteria andSecEqualTo(String value) {
            addCriterion("SEC =", value, "sec");
            return (Criteria) this;
        }

        public Criteria andSecNotEqualTo(String value) {
            addCriterion("SEC <>", value, "sec");
            return (Criteria) this;
        }

        public Criteria andSecGreaterThan(String value) {
            addCriterion("SEC >", value, "sec");
            return (Criteria) this;
        }

        public Criteria andSecGreaterThanOrEqualTo(String value) {
            addCriterion("SEC >=", value, "sec");
            return (Criteria) this;
        }

        public Criteria andSecLessThan(String value) {
            addCriterion("SEC <", value, "sec");
            return (Criteria) this;
        }

        public Criteria andSecLessThanOrEqualTo(String value) {
            addCriterion("SEC <=", value, "sec");
            return (Criteria) this;
        }

        public Criteria andSecLike(String value) {
            addCriterion("SEC like", value, "sec");
            return (Criteria) this;
        }

        public Criteria andSecNotLike(String value) {
            addCriterion("SEC not like", value, "sec");
            return (Criteria) this;
        }

        public Criteria andSecIn(List<String> values) {
            addCriterion("SEC in", values, "sec");
            return (Criteria) this;
        }

        public Criteria andSecNotIn(List<String> values) {
            addCriterion("SEC not in", values, "sec");
            return (Criteria) this;
        }

        public Criteria andSecBetween(String value1, String value2) {
            addCriterion("SEC between", value1, value2, "sec");
            return (Criteria) this;
        }

        public Criteria andSecNotBetween(String value1, String value2) {
            addCriterion("SEC not between", value1, value2, "sec");
            return (Criteria) this;
        }

        public Criteria andGroupidIsNull() {
            addCriterion("GROUPID is null");
            return (Criteria) this;
        }

        public Criteria andGroupidIsNotNull() {
            addCriterion("GROUPID is not null");
            return (Criteria) this;
        }

        public Criteria andGroupidEqualTo(String value) {
            addCriterion("GROUPID =", value, "groupid");
            return (Criteria) this;
        }

        public Criteria andGroupidNotEqualTo(String value) {
            addCriterion("GROUPID <>", value, "groupid");
            return (Criteria) this;
        }

        public Criteria andGroupidGreaterThan(String value) {
            addCriterion("GROUPID >", value, "groupid");
            return (Criteria) this;
        }

        public Criteria andGroupidGreaterThanOrEqualTo(String value) {
            addCriterion("GROUPID >=", value, "groupid");
            return (Criteria) this;
        }

        public Criteria andGroupidLessThan(String value) {
            addCriterion("GROUPID <", value, "groupid");
            return (Criteria) this;
        }

        public Criteria andGroupidLessThanOrEqualTo(String value) {
            addCriterion("GROUPID <=", value, "groupid");
            return (Criteria) this;
        }

        public Criteria andGroupidLike(String value) {
            addCriterion("GROUPID like", value, "groupid");
            return (Criteria) this;
        }

        public Criteria andGroupidNotLike(String value) {
            addCriterion("GROUPID not like", value, "groupid");
            return (Criteria) this;
        }

        public Criteria andGroupidIn(List<String> values) {
            addCriterion("GROUPID in", values, "groupid");
            return (Criteria) this;
        }

        public Criteria andGroupidNotIn(List<String> values) {
            addCriterion("GROUPID not in", values, "groupid");
            return (Criteria) this;
        }

        public Criteria andGroupidBetween(String value1, String value2) {
            addCriterion("GROUPID between", value1, value2, "groupid");
            return (Criteria) this;
        }

        public Criteria andGroupidNotBetween(String value1, String value2) {
            addCriterion("GROUPID not between", value1, value2, "groupid");
            return (Criteria) this;
        }

        public Criteria andInvoiceListIsNull() {
            addCriterion("INVOICE_LIST is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceListIsNotNull() {
            addCriterion("INVOICE_LIST is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceListEqualTo(String value) {
            addCriterion("INVOICE_LIST =", value, "invoiceList");
            return (Criteria) this;
        }

        public Criteria andInvoiceListNotEqualTo(String value) {
            addCriterion("INVOICE_LIST <>", value, "invoiceList");
            return (Criteria) this;
        }

        public Criteria andInvoiceListGreaterThan(String value) {
            addCriterion("INVOICE_LIST >", value, "invoiceList");
            return (Criteria) this;
        }

        public Criteria andInvoiceListGreaterThanOrEqualTo(String value) {
            addCriterion("INVOICE_LIST >=", value, "invoiceList");
            return (Criteria) this;
        }

        public Criteria andInvoiceListLessThan(String value) {
            addCriterion("INVOICE_LIST <", value, "invoiceList");
            return (Criteria) this;
        }

        public Criteria andInvoiceListLessThanOrEqualTo(String value) {
            addCriterion("INVOICE_LIST <=", value, "invoiceList");
            return (Criteria) this;
        }

        public Criteria andInvoiceListLike(String value) {
            addCriterion("INVOICE_LIST like", value, "invoiceList");
            return (Criteria) this;
        }

        public Criteria andInvoiceListNotLike(String value) {
            addCriterion("INVOICE_LIST not like", value, "invoiceList");
            return (Criteria) this;
        }

        public Criteria andInvoiceListIn(List<String> values) {
            addCriterion("INVOICE_LIST in", values, "invoiceList");
            return (Criteria) this;
        }

        public Criteria andInvoiceListNotIn(List<String> values) {
            addCriterion("INVOICE_LIST not in", values, "invoiceList");
            return (Criteria) this;
        }

        public Criteria andInvoiceListBetween(String value1, String value2) {
            addCriterion("INVOICE_LIST between", value1, value2, "invoiceList");
            return (Criteria) this;
        }

        public Criteria andInvoiceListNotBetween(String value1, String value2) {
            addCriterion("INVOICE_LIST not between", value1, value2, "invoiceList");
            return (Criteria) this;
        }

        public Criteria andEInvIdIsNull() {
            addCriterion("E_INV_ID is null");
            return (Criteria) this;
        }

        public Criteria andEInvIdIsNotNull() {
            addCriterion("E_INV_ID is not null");
            return (Criteria) this;
        }

        public Criteria andEInvIdEqualTo(String value) {
            addCriterion("E_INV_ID =", value, "eInvId");
            return (Criteria) this;
        }

        public Criteria andEInvIdNotEqualTo(String value) {
            addCriterion("E_INV_ID <>", value, "eInvId");
            return (Criteria) this;
        }

        public Criteria andEInvIdGreaterThan(String value) {
            addCriterion("E_INV_ID >", value, "eInvId");
            return (Criteria) this;
        }

        public Criteria andEInvIdGreaterThanOrEqualTo(String value) {
            addCriterion("E_INV_ID >=", value, "eInvId");
            return (Criteria) this;
        }

        public Criteria andEInvIdLessThan(String value) {
            addCriterion("E_INV_ID <", value, "eInvId");
            return (Criteria) this;
        }

        public Criteria andEInvIdLessThanOrEqualTo(String value) {
            addCriterion("E_INV_ID <=", value, "eInvId");
            return (Criteria) this;
        }

        public Criteria andEInvIdLike(String value) {
            addCriterion("E_INV_ID like", value, "eInvId");
            return (Criteria) this;
        }

        public Criteria andEInvIdNotLike(String value) {
            addCriterion("E_INV_ID not like", value, "eInvId");
            return (Criteria) this;
        }

        public Criteria andEInvIdIn(List<String> values) {
            addCriterion("E_INV_ID in", values, "eInvId");
            return (Criteria) this;
        }

        public Criteria andEInvIdNotIn(List<String> values) {
            addCriterion("E_INV_ID not in", values, "eInvId");
            return (Criteria) this;
        }

        public Criteria andEInvIdBetween(String value1, String value2) {
            addCriterion("E_INV_ID between", value1, value2, "eInvId");
            return (Criteria) this;
        }

        public Criteria andEInvIdNotBetween(String value1, String value2) {
            addCriterion("E_INV_ID not between", value1, value2, "eInvId");
            return (Criteria) this;
        }

        public Criteria andEInvMsgIsNull() {
            addCriterion("E_INV_MSG is null");
            return (Criteria) this;
        }

        public Criteria andEInvMsgIsNotNull() {
            addCriterion("E_INV_MSG is not null");
            return (Criteria) this;
        }

        public Criteria andEInvMsgEqualTo(String value) {
            addCriterion("E_INV_MSG =", value, "eInvMsg");
            return (Criteria) this;
        }

        public Criteria andEInvMsgNotEqualTo(String value) {
            addCriterion("E_INV_MSG <>", value, "eInvMsg");
            return (Criteria) this;
        }

        public Criteria andEInvMsgGreaterThan(String value) {
            addCriterion("E_INV_MSG >", value, "eInvMsg");
            return (Criteria) this;
        }

        public Criteria andEInvMsgGreaterThanOrEqualTo(String value) {
            addCriterion("E_INV_MSG >=", value, "eInvMsg");
            return (Criteria) this;
        }

        public Criteria andEInvMsgLessThan(String value) {
            addCriterion("E_INV_MSG <", value, "eInvMsg");
            return (Criteria) this;
        }

        public Criteria andEInvMsgLessThanOrEqualTo(String value) {
            addCriterion("E_INV_MSG <=", value, "eInvMsg");
            return (Criteria) this;
        }

        public Criteria andEInvMsgLike(String value) {
            addCriterion("E_INV_MSG like", value, "eInvMsg");
            return (Criteria) this;
        }

        public Criteria andEInvMsgNotLike(String value) {
            addCriterion("E_INV_MSG not like", value, "eInvMsg");
            return (Criteria) this;
        }

        public Criteria andEInvMsgIn(List<String> values) {
            addCriterion("E_INV_MSG in", values, "eInvMsg");
            return (Criteria) this;
        }

        public Criteria andEInvMsgNotIn(List<String> values) {
            addCriterion("E_INV_MSG not in", values, "eInvMsg");
            return (Criteria) this;
        }

        public Criteria andEInvMsgBetween(String value1, String value2) {
            addCriterion("E_INV_MSG between", value1, value2, "eInvMsg");
            return (Criteria) this;
        }

        public Criteria andEInvMsgNotBetween(String value1, String value2) {
            addCriterion("E_INV_MSG not between", value1, value2, "eInvMsg");
            return (Criteria) this;
        }

        public Criteria andEPdfUrlIsNull() {
            addCriterion("E_PDF_URL is null");
            return (Criteria) this;
        }

        public Criteria andEPdfUrlIsNotNull() {
            addCriterion("E_PDF_URL is not null");
            return (Criteria) this;
        }

        public Criteria andEPdfUrlEqualTo(String value) {
            addCriterion("E_PDF_URL =", value, "ePdfUrl");
            return (Criteria) this;
        }

        public Criteria andEPdfUrlNotEqualTo(String value) {
            addCriterion("E_PDF_URL <>", value, "ePdfUrl");
            return (Criteria) this;
        }

        public Criteria andEPdfUrlGreaterThan(String value) {
            addCriterion("E_PDF_URL >", value, "ePdfUrl");
            return (Criteria) this;
        }

        public Criteria andEPdfUrlGreaterThanOrEqualTo(String value) {
            addCriterion("E_PDF_URL >=", value, "ePdfUrl");
            return (Criteria) this;
        }

        public Criteria andEPdfUrlLessThan(String value) {
            addCriterion("E_PDF_URL <", value, "ePdfUrl");
            return (Criteria) this;
        }

        public Criteria andEPdfUrlLessThanOrEqualTo(String value) {
            addCriterion("E_PDF_URL <=", value, "ePdfUrl");
            return (Criteria) this;
        }

        public Criteria andEPdfUrlLike(String value) {
            addCriterion("E_PDF_URL like", value, "ePdfUrl");
            return (Criteria) this;
        }

        public Criteria andEPdfUrlNotLike(String value) {
            addCriterion("E_PDF_URL not like", value, "ePdfUrl");
            return (Criteria) this;
        }

        public Criteria andEPdfUrlIn(List<String> values) {
            addCriterion("E_PDF_URL in", values, "ePdfUrl");
            return (Criteria) this;
        }

        public Criteria andEPdfUrlNotIn(List<String> values) {
            addCriterion("E_PDF_URL not in", values, "ePdfUrl");
            return (Criteria) this;
        }

        public Criteria andEPdfUrlBetween(String value1, String value2) {
            addCriterion("E_PDF_URL between", value1, value2, "ePdfUrl");
            return (Criteria) this;
        }

        public Criteria andEPdfUrlNotBetween(String value1, String value2) {
            addCriterion("E_PDF_URL not between", value1, value2, "ePdfUrl");
            return (Criteria) this;
        }

        public Criteria andBillSdateIsNull() {
            addCriterion("BILL_SDATE is null");
            return (Criteria) this;
        }

        public Criteria andBillSdateIsNotNull() {
            addCriterion("BILL_SDATE is not null");
            return (Criteria) this;
        }

        public Criteria andBillSdateEqualTo(String value) {
            addCriterion("BILL_SDATE =", value, "billSdate");
            return (Criteria) this;
        }

        public Criteria andBillSdateNotEqualTo(String value) {
            addCriterion("BILL_SDATE <>", value, "billSdate");
            return (Criteria) this;
        }

        public Criteria andBillSdateGreaterThan(String value) {
            addCriterion("BILL_SDATE >", value, "billSdate");
            return (Criteria) this;
        }

        public Criteria andBillSdateGreaterThanOrEqualTo(String value) {
            addCriterion("BILL_SDATE >=", value, "billSdate");
            return (Criteria) this;
        }

        public Criteria andBillSdateLessThan(String value) {
            addCriterion("BILL_SDATE <", value, "billSdate");
            return (Criteria) this;
        }

        public Criteria andBillSdateLessThanOrEqualTo(String value) {
            addCriterion("BILL_SDATE <=", value, "billSdate");
            return (Criteria) this;
        }

        public Criteria andBillSdateLike(String value) {
            addCriterion("BILL_SDATE like", value, "billSdate");
            return (Criteria) this;
        }

        public Criteria andBillSdateNotLike(String value) {
            addCriterion("BILL_SDATE not like", value, "billSdate");
            return (Criteria) this;
        }

        public Criteria andBillSdateIn(List<String> values) {
            addCriterion("BILL_SDATE in", values, "billSdate");
            return (Criteria) this;
        }

        public Criteria andBillSdateNotIn(List<String> values) {
            addCriterion("BILL_SDATE not in", values, "billSdate");
            return (Criteria) this;
        }

        public Criteria andBillSdateBetween(String value1, String value2) {
            addCriterion("BILL_SDATE between", value1, value2, "billSdate");
            return (Criteria) this;
        }

        public Criteria andBillSdateNotBetween(String value1, String value2) {
            addCriterion("BILL_SDATE not between", value1, value2, "billSdate");
            return (Criteria) this;
        }

        public Criteria andInvoiceRedXxbmIsNull() {
            addCriterion("INVOICE_RED_XXBM is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceRedXxbmIsNotNull() {
            addCriterion("INVOICE_RED_XXBM is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceRedXxbmEqualTo(String value) {
            addCriterion("INVOICE_RED_XXBM =", value, "invoiceRedXxbm");
            return (Criteria) this;
        }

        public Criteria andInvoiceRedXxbmNotEqualTo(String value) {
            addCriterion("INVOICE_RED_XXBM <>", value, "invoiceRedXxbm");
            return (Criteria) this;
        }

        public Criteria andInvoiceRedXxbmGreaterThan(String value) {
            addCriterion("INVOICE_RED_XXBM >", value, "invoiceRedXxbm");
            return (Criteria) this;
        }

        public Criteria andInvoiceRedXxbmGreaterThanOrEqualTo(String value) {
            addCriterion("INVOICE_RED_XXBM >=", value, "invoiceRedXxbm");
            return (Criteria) this;
        }

        public Criteria andInvoiceRedXxbmLessThan(String value) {
            addCriterion("INVOICE_RED_XXBM <", value, "invoiceRedXxbm");
            return (Criteria) this;
        }

        public Criteria andInvoiceRedXxbmLessThanOrEqualTo(String value) {
            addCriterion("INVOICE_RED_XXBM <=", value, "invoiceRedXxbm");
            return (Criteria) this;
        }

        public Criteria andInvoiceRedXxbmLike(String value) {
            addCriterion("INVOICE_RED_XXBM like", value, "invoiceRedXxbm");
            return (Criteria) this;
        }

        public Criteria andInvoiceRedXxbmNotLike(String value) {
            addCriterion("INVOICE_RED_XXBM not like", value, "invoiceRedXxbm");
            return (Criteria) this;
        }

        public Criteria andInvoiceRedXxbmIn(List<String> values) {
            addCriterion("INVOICE_RED_XXBM in", values, "invoiceRedXxbm");
            return (Criteria) this;
        }

        public Criteria andInvoiceRedXxbmNotIn(List<String> values) {
            addCriterion("INVOICE_RED_XXBM not in", values, "invoiceRedXxbm");
            return (Criteria) this;
        }

        public Criteria andInvoiceRedXxbmBetween(String value1, String value2) {
            addCriterion("INVOICE_RED_XXBM between", value1, value2, "invoiceRedXxbm");
            return (Criteria) this;
        }

        public Criteria andInvoiceRedXxbmNotBetween(String value1, String value2) {
            addCriterion("INVOICE_RED_XXBM not between", value1, value2, "invoiceRedXxbm");
            return (Criteria) this;
        }

        public Criteria andInvoiceRedFpdmIsNull() {
            addCriterion("INVOICE_RED_FPDM is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceRedFpdmIsNotNull() {
            addCriterion("INVOICE_RED_FPDM is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceRedFpdmEqualTo(String value) {
            addCriterion("INVOICE_RED_FPDM =", value, "invoiceRedFpdm");
            return (Criteria) this;
        }

        public Criteria andInvoiceRedFpdmNotEqualTo(String value) {
            addCriterion("INVOICE_RED_FPDM <>", value, "invoiceRedFpdm");
            return (Criteria) this;
        }

        public Criteria andInvoiceRedFpdmGreaterThan(String value) {
            addCriterion("INVOICE_RED_FPDM >", value, "invoiceRedFpdm");
            return (Criteria) this;
        }

        public Criteria andInvoiceRedFpdmGreaterThanOrEqualTo(String value) {
            addCriterion("INVOICE_RED_FPDM >=", value, "invoiceRedFpdm");
            return (Criteria) this;
        }

        public Criteria andInvoiceRedFpdmLessThan(String value) {
            addCriterion("INVOICE_RED_FPDM <", value, "invoiceRedFpdm");
            return (Criteria) this;
        }

        public Criteria andInvoiceRedFpdmLessThanOrEqualTo(String value) {
            addCriterion("INVOICE_RED_FPDM <=", value, "invoiceRedFpdm");
            return (Criteria) this;
        }

        public Criteria andInvoiceRedFpdmLike(String value) {
            addCriterion("INVOICE_RED_FPDM like", value, "invoiceRedFpdm");
            return (Criteria) this;
        }

        public Criteria andInvoiceRedFpdmNotLike(String value) {
            addCriterion("INVOICE_RED_FPDM not like", value, "invoiceRedFpdm");
            return (Criteria) this;
        }

        public Criteria andInvoiceRedFpdmIn(List<String> values) {
            addCriterion("INVOICE_RED_FPDM in", values, "invoiceRedFpdm");
            return (Criteria) this;
        }

        public Criteria andInvoiceRedFpdmNotIn(List<String> values) {
            addCriterion("INVOICE_RED_FPDM not in", values, "invoiceRedFpdm");
            return (Criteria) this;
        }

        public Criteria andInvoiceRedFpdmBetween(String value1, String value2) {
            addCriterion("INVOICE_RED_FPDM between", value1, value2, "invoiceRedFpdm");
            return (Criteria) this;
        }

        public Criteria andInvoiceRedFpdmNotBetween(String value1, String value2) {
            addCriterion("INVOICE_RED_FPDM not between", value1, value2, "invoiceRedFpdm");
            return (Criteria) this;
        }

        public Criteria andInvoiceRedFphmIsNull() {
            addCriterion("INVOICE_RED_FPHM is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceRedFphmIsNotNull() {
            addCriterion("INVOICE_RED_FPHM is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceRedFphmEqualTo(String value) {
            addCriterion("INVOICE_RED_FPHM =", value, "invoiceRedFphm");
            return (Criteria) this;
        }

        public Criteria andInvoiceRedFphmNotEqualTo(String value) {
            addCriterion("INVOICE_RED_FPHM <>", value, "invoiceRedFphm");
            return (Criteria) this;
        }

        public Criteria andInvoiceRedFphmGreaterThan(String value) {
            addCriterion("INVOICE_RED_FPHM >", value, "invoiceRedFphm");
            return (Criteria) this;
        }

        public Criteria andInvoiceRedFphmGreaterThanOrEqualTo(String value) {
            addCriterion("INVOICE_RED_FPHM >=", value, "invoiceRedFphm");
            return (Criteria) this;
        }

        public Criteria andInvoiceRedFphmLessThan(String value) {
            addCriterion("INVOICE_RED_FPHM <", value, "invoiceRedFphm");
            return (Criteria) this;
        }

        public Criteria andInvoiceRedFphmLessThanOrEqualTo(String value) {
            addCriterion("INVOICE_RED_FPHM <=", value, "invoiceRedFphm");
            return (Criteria) this;
        }

        public Criteria andInvoiceRedFphmLike(String value) {
            addCriterion("INVOICE_RED_FPHM like", value, "invoiceRedFphm");
            return (Criteria) this;
        }

        public Criteria andInvoiceRedFphmNotLike(String value) {
            addCriterion("INVOICE_RED_FPHM not like", value, "invoiceRedFphm");
            return (Criteria) this;
        }

        public Criteria andInvoiceRedFphmIn(List<String> values) {
            addCriterion("INVOICE_RED_FPHM in", values, "invoiceRedFphm");
            return (Criteria) this;
        }

        public Criteria andInvoiceRedFphmNotIn(List<String> values) {
            addCriterion("INVOICE_RED_FPHM not in", values, "invoiceRedFphm");
            return (Criteria) this;
        }

        public Criteria andInvoiceRedFphmBetween(String value1, String value2) {
            addCriterion("INVOICE_RED_FPHM between", value1, value2, "invoiceRedFphm");
            return (Criteria) this;
        }

        public Criteria andInvoiceRedFphmNotBetween(String value1, String value2) {
            addCriterion("INVOICE_RED_FPHM not between", value1, value2, "invoiceRedFphm");
            return (Criteria) this;
        }

        public Criteria andDocNumlIsNull() {
            addCriterion("DOC_NUML is null");
            return (Criteria) this;
        }

        public Criteria andDocNumlIsNotNull() {
            addCriterion("DOC_NUML is not null");
            return (Criteria) this;
        }

        public Criteria andDocNumlEqualTo(String value) {
            addCriterion("DOC_NUML =", value, "docNuml");
            return (Criteria) this;
        }

        public Criteria andDocNumlNotEqualTo(String value) {
            addCriterion("DOC_NUML <>", value, "docNuml");
            return (Criteria) this;
        }

        public Criteria andDocNumlGreaterThan(String value) {
            addCriterion("DOC_NUML >", value, "docNuml");
            return (Criteria) this;
        }

        public Criteria andDocNumlGreaterThanOrEqualTo(String value) {
            addCriterion("DOC_NUML >=", value, "docNuml");
            return (Criteria) this;
        }

        public Criteria andDocNumlLessThan(String value) {
            addCriterion("DOC_NUML <", value, "docNuml");
            return (Criteria) this;
        }

        public Criteria andDocNumlLessThanOrEqualTo(String value) {
            addCriterion("DOC_NUML <=", value, "docNuml");
            return (Criteria) this;
        }

        public Criteria andDocNumlLike(String value) {
            addCriterion("DOC_NUML like", value, "docNuml");
            return (Criteria) this;
        }

        public Criteria andDocNumlNotLike(String value) {
            addCriterion("DOC_NUML not like", value, "docNuml");
            return (Criteria) this;
        }

        public Criteria andDocNumlIn(List<String> values) {
            addCriterion("DOC_NUML in", values, "docNuml");
            return (Criteria) this;
        }

        public Criteria andDocNumlNotIn(List<String> values) {
            addCriterion("DOC_NUML not in", values, "docNuml");
            return (Criteria) this;
        }

        public Criteria andDocNumlBetween(String value1, String value2) {
            addCriterion("DOC_NUML between", value1, value2, "docNuml");
            return (Criteria) this;
        }

        public Criteria andDocNumlNotBetween(String value1, String value2) {
            addCriterion("DOC_NUML not between", value1, value2, "docNuml");
            return (Criteria) this;
        }

        public Criteria andBillRemarkIsNull() {
            addCriterion("BILL_REMARK is null");
            return (Criteria) this;
        }

        public Criteria andBillRemarkIsNotNull() {
            addCriterion("BILL_REMARK is not null");
            return (Criteria) this;
        }

        public Criteria andBillRemarkEqualTo(String value) {
            addCriterion("BILL_REMARK =", value, "billRemark");
            return (Criteria) this;
        }

        public Criteria andBillRemarkNotEqualTo(String value) {
            addCriterion("BILL_REMARK <>", value, "billRemark");
            return (Criteria) this;
        }

        public Criteria andBillRemarkGreaterThan(String value) {
            addCriterion("BILL_REMARK >", value, "billRemark");
            return (Criteria) this;
        }

        public Criteria andBillRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("BILL_REMARK >=", value, "billRemark");
            return (Criteria) this;
        }

        public Criteria andBillRemarkLessThan(String value) {
            addCriterion("BILL_REMARK <", value, "billRemark");
            return (Criteria) this;
        }

        public Criteria andBillRemarkLessThanOrEqualTo(String value) {
            addCriterion("BILL_REMARK <=", value, "billRemark");
            return (Criteria) this;
        }

        public Criteria andBillRemarkLike(String value) {
            addCriterion("BILL_REMARK like", value, "billRemark");
            return (Criteria) this;
        }

        public Criteria andBillRemarkNotLike(String value) {
            addCriterion("BILL_REMARK not like", value, "billRemark");
            return (Criteria) this;
        }

        public Criteria andBillRemarkIn(List<String> values) {
            addCriterion("BILL_REMARK in", values, "billRemark");
            return (Criteria) this;
        }

        public Criteria andBillRemarkNotIn(List<String> values) {
            addCriterion("BILL_REMARK not in", values, "billRemark");
            return (Criteria) this;
        }

        public Criteria andBillRemarkBetween(String value1, String value2) {
            addCriterion("BILL_REMARK between", value1, value2, "billRemark");
            return (Criteria) this;
        }

        public Criteria andBillRemarkNotBetween(String value1, String value2) {
            addCriterion("BILL_REMARK not between", value1, value2, "billRemark");
            return (Criteria) this;
        }

        public Criteria andExnumIsNull() {
            addCriterion("EXNUM is null");
            return (Criteria) this;
        }

        public Criteria andExnumIsNotNull() {
            addCriterion("EXNUM is not null");
            return (Criteria) this;
        }

        public Criteria andExnumEqualTo(String value) {
            addCriterion("EXNUM =", value, "exnum");
            return (Criteria) this;
        }

        public Criteria andExnumNotEqualTo(String value) {
            addCriterion("EXNUM <>", value, "exnum");
            return (Criteria) this;
        }

        public Criteria andExnumGreaterThan(String value) {
            addCriterion("EXNUM >", value, "exnum");
            return (Criteria) this;
        }

        public Criteria andExnumGreaterThanOrEqualTo(String value) {
            addCriterion("EXNUM >=", value, "exnum");
            return (Criteria) this;
        }

        public Criteria andExnumLessThan(String value) {
            addCriterion("EXNUM <", value, "exnum");
            return (Criteria) this;
        }

        public Criteria andExnumLessThanOrEqualTo(String value) {
            addCriterion("EXNUM <=", value, "exnum");
            return (Criteria) this;
        }

        public Criteria andExnumLike(String value) {
            addCriterion("EXNUM like", value, "exnum");
            return (Criteria) this;
        }

        public Criteria andExnumNotLike(String value) {
            addCriterion("EXNUM not like", value, "exnum");
            return (Criteria) this;
        }

        public Criteria andExnumIn(List<String> values) {
            addCriterion("EXNUM in", values, "exnum");
            return (Criteria) this;
        }

        public Criteria andExnumNotIn(List<String> values) {
            addCriterion("EXNUM not in", values, "exnum");
            return (Criteria) this;
        }

        public Criteria andExnumBetween(String value1, String value2) {
            addCriterion("EXNUM between", value1, value2, "exnum");
            return (Criteria) this;
        }

        public Criteria andExnumNotBetween(String value1, String value2) {
            addCriterion("EXNUM not between", value1, value2, "exnum");
            return (Criteria) this;
        }

        public Criteria andAttributf1IsNull() {
            addCriterion("ATTRIBUTF1 is null");
            return (Criteria) this;
        }

        public Criteria andAttributf1IsNotNull() {
            addCriterion("ATTRIBUTF1 is not null");
            return (Criteria) this;
        }

        public Criteria andAttributf1EqualTo(String value) {
            addCriterion("ATTRIBUTF1 =", value, "attributf1");
            return (Criteria) this;
        }

        public Criteria andAttributf1NotEqualTo(String value) {
            addCriterion("ATTRIBUTF1 <>", value, "attributf1");
            return (Criteria) this;
        }

        public Criteria andAttributf1GreaterThan(String value) {
            addCriterion("ATTRIBUTF1 >", value, "attributf1");
            return (Criteria) this;
        }

        public Criteria andAttributf1GreaterThanOrEqualTo(String value) {
            addCriterion("ATTRIBUTF1 >=", value, "attributf1");
            return (Criteria) this;
        }

        public Criteria andAttributf1LessThan(String value) {
            addCriterion("ATTRIBUTF1 <", value, "attributf1");
            return (Criteria) this;
        }

        public Criteria andAttributf1LessThanOrEqualTo(String value) {
            addCriterion("ATTRIBUTF1 <=", value, "attributf1");
            return (Criteria) this;
        }

        public Criteria andAttributf1Like(String value) {
            addCriterion("ATTRIBUTF1 like", value, "attributf1");
            return (Criteria) this;
        }

        public Criteria andAttributf1NotLike(String value) {
            addCriterion("ATTRIBUTF1 not like", value, "attributf1");
            return (Criteria) this;
        }

        public Criteria andAttributf1In(List<String> values) {
            addCriterion("ATTRIBUTF1 in", values, "attributf1");
            return (Criteria) this;
        }

        public Criteria andAttributf1NotIn(List<String> values) {
            addCriterion("ATTRIBUTF1 not in", values, "attributf1");
            return (Criteria) this;
        }

        public Criteria andAttributf1Between(String value1, String value2) {
            addCriterion("ATTRIBUTF1 between", value1, value2, "attributf1");
            return (Criteria) this;
        }

        public Criteria andAttributf1NotBetween(String value1, String value2) {
            addCriterion("ATTRIBUTF1 not between", value1, value2, "attributf1");
            return (Criteria) this;
        }

        public Criteria andExidIsNull() {
            addCriterion("EXID is null");
            return (Criteria) this;
        }

        public Criteria andExidIsNotNull() {
            addCriterion("EXID is not null");
            return (Criteria) this;
        }

        public Criteria andExidEqualTo(String value) {
            addCriterion("EXID =", value, "exid");
            return (Criteria) this;
        }

        public Criteria andExidNotEqualTo(String value) {
            addCriterion("EXID <>", value, "exid");
            return (Criteria) this;
        }

        public Criteria andExidGreaterThan(String value) {
            addCriterion("EXID >", value, "exid");
            return (Criteria) this;
        }

        public Criteria andExidGreaterThanOrEqualTo(String value) {
            addCriterion("EXID >=", value, "exid");
            return (Criteria) this;
        }

        public Criteria andExidLessThan(String value) {
            addCriterion("EXID <", value, "exid");
            return (Criteria) this;
        }

        public Criteria andExidLessThanOrEqualTo(String value) {
            addCriterion("EXID <=", value, "exid");
            return (Criteria) this;
        }

        public Criteria andExidLike(String value) {
            addCriterion("EXID like", value, "exid");
            return (Criteria) this;
        }

        public Criteria andExidNotLike(String value) {
            addCriterion("EXID not like", value, "exid");
            return (Criteria) this;
        }

        public Criteria andExidIn(List<String> values) {
            addCriterion("EXID in", values, "exid");
            return (Criteria) this;
        }

        public Criteria andExidNotIn(List<String> values) {
            addCriterion("EXID not in", values, "exid");
            return (Criteria) this;
        }

        public Criteria andExidBetween(String value1, String value2) {
            addCriterion("EXID between", value1, value2, "exid");
            return (Criteria) this;
        }

        public Criteria andExidNotBetween(String value1, String value2) {
            addCriterion("EXID not between", value1, value2, "exid");
            return (Criteria) this;
        }

        public Criteria andExbzIsNull() {
            addCriterion("EXBZ is null");
            return (Criteria) this;
        }

        public Criteria andExbzIsNotNull() {
            addCriterion("EXBZ is not null");
            return (Criteria) this;
        }

        public Criteria andExbzEqualTo(String value) {
            addCriterion("EXBZ =", value, "exbz");
            return (Criteria) this;
        }

        public Criteria andExbzNotEqualTo(String value) {
            addCriterion("EXBZ <>", value, "exbz");
            return (Criteria) this;
        }

        public Criteria andExbzGreaterThan(String value) {
            addCriterion("EXBZ >", value, "exbz");
            return (Criteria) this;
        }

        public Criteria andExbzGreaterThanOrEqualTo(String value) {
            addCriterion("EXBZ >=", value, "exbz");
            return (Criteria) this;
        }

        public Criteria andExbzLessThan(String value) {
            addCriterion("EXBZ <", value, "exbz");
            return (Criteria) this;
        }

        public Criteria andExbzLessThanOrEqualTo(String value) {
            addCriterion("EXBZ <=", value, "exbz");
            return (Criteria) this;
        }

        public Criteria andExbzLike(String value) {
            addCriterion("EXBZ like", value, "exbz");
            return (Criteria) this;
        }

        public Criteria andExbzNotLike(String value) {
            addCriterion("EXBZ not like", value, "exbz");
            return (Criteria) this;
        }

        public Criteria andExbzIn(List<String> values) {
            addCriterion("EXBZ in", values, "exbz");
            return (Criteria) this;
        }

        public Criteria andExbzNotIn(List<String> values) {
            addCriterion("EXBZ not in", values, "exbz");
            return (Criteria) this;
        }

        public Criteria andExbzBetween(String value1, String value2) {
            addCriterion("EXBZ between", value1, value2, "exbz");
            return (Criteria) this;
        }

        public Criteria andExbzNotBetween(String value1, String value2) {
            addCriterion("EXBZ not between", value1, value2, "exbz");
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