package jp.co.xq.service.sys.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SysAreaExample implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private static final long serialVersionUID = 1L;

    private Integer limit;

    private Integer offset;

    public SysAreaExample() {
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

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getOffset() {
        return offset;
    }

    protected abstract static class GeneratedCriteria implements Serializable {
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

        public Criteria andKenIdIsNull() {
            addCriterion("ken_id is null");
            return (Criteria) this;
        }

        public Criteria andKenIdIsNotNull() {
            addCriterion("ken_id is not null");
            return (Criteria) this;
        }

        public Criteria andKenIdEqualTo(Integer value) {
            addCriterion("ken_id =", value, "kenId");
            return (Criteria) this;
        }

        public Criteria andKenIdNotEqualTo(Integer value) {
            addCriterion("ken_id <>", value, "kenId");
            return (Criteria) this;
        }

        public Criteria andKenIdGreaterThan(Integer value) {
            addCriterion("ken_id >", value, "kenId");
            return (Criteria) this;
        }

        public Criteria andKenIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("ken_id >=", value, "kenId");
            return (Criteria) this;
        }

        public Criteria andKenIdLessThan(Integer value) {
            addCriterion("ken_id <", value, "kenId");
            return (Criteria) this;
        }

        public Criteria andKenIdLessThanOrEqualTo(Integer value) {
            addCriterion("ken_id <=", value, "kenId");
            return (Criteria) this;
        }

        public Criteria andKenIdIn(List<Integer> values) {
            addCriterion("ken_id in", values, "kenId");
            return (Criteria) this;
        }

        public Criteria andKenIdNotIn(List<Integer> values) {
            addCriterion("ken_id not in", values, "kenId");
            return (Criteria) this;
        }

        public Criteria andKenIdBetween(Integer value1, Integer value2) {
            addCriterion("ken_id between", value1, value2, "kenId");
            return (Criteria) this;
        }

        public Criteria andKenIdNotBetween(Integer value1, Integer value2) {
            addCriterion("ken_id not between", value1, value2, "kenId");
            return (Criteria) this;
        }

        public Criteria andCityIdIsNull() {
            addCriterion("city_id is null");
            return (Criteria) this;
        }

        public Criteria andCityIdIsNotNull() {
            addCriterion("city_id is not null");
            return (Criteria) this;
        }

        public Criteria andCityIdEqualTo(Integer value) {
            addCriterion("city_id =", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdNotEqualTo(Integer value) {
            addCriterion("city_id <>", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdGreaterThan(Integer value) {
            addCriterion("city_id >", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("city_id >=", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdLessThan(Integer value) {
            addCriterion("city_id <", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdLessThanOrEqualTo(Integer value) {
            addCriterion("city_id <=", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdIn(List<Integer> values) {
            addCriterion("city_id in", values, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdNotIn(List<Integer> values) {
            addCriterion("city_id not in", values, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdBetween(Integer value1, Integer value2) {
            addCriterion("city_id between", value1, value2, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdNotBetween(Integer value1, Integer value2) {
            addCriterion("city_id not between", value1, value2, "cityId");
            return (Criteria) this;
        }

        public Criteria andTownIdIsNull() {
            addCriterion("town_id is null");
            return (Criteria) this;
        }

        public Criteria andTownIdIsNotNull() {
            addCriterion("town_id is not null");
            return (Criteria) this;
        }

        public Criteria andTownIdEqualTo(Integer value) {
            addCriterion("town_id =", value, "townId");
            return (Criteria) this;
        }

        public Criteria andTownIdNotEqualTo(Integer value) {
            addCriterion("town_id <>", value, "townId");
            return (Criteria) this;
        }

        public Criteria andTownIdGreaterThan(Integer value) {
            addCriterion("town_id >", value, "townId");
            return (Criteria) this;
        }

        public Criteria andTownIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("town_id >=", value, "townId");
            return (Criteria) this;
        }

        public Criteria andTownIdLessThan(Integer value) {
            addCriterion("town_id <", value, "townId");
            return (Criteria) this;
        }

        public Criteria andTownIdLessThanOrEqualTo(Integer value) {
            addCriterion("town_id <=", value, "townId");
            return (Criteria) this;
        }

        public Criteria andTownIdIn(List<Integer> values) {
            addCriterion("town_id in", values, "townId");
            return (Criteria) this;
        }

        public Criteria andTownIdNotIn(List<Integer> values) {
            addCriterion("town_id not in", values, "townId");
            return (Criteria) this;
        }

        public Criteria andTownIdBetween(Integer value1, Integer value2) {
            addCriterion("town_id between", value1, value2, "townId");
            return (Criteria) this;
        }

        public Criteria andTownIdNotBetween(Integer value1, Integer value2) {
            addCriterion("town_id not between", value1, value2, "townId");
            return (Criteria) this;
        }

        public Criteria andZipIsNull() {
            addCriterion("zip is null");
            return (Criteria) this;
        }

        public Criteria andZipIsNotNull() {
            addCriterion("zip is not null");
            return (Criteria) this;
        }

        public Criteria andZipEqualTo(String value) {
            addCriterion("zip =", value, "zip");
            return (Criteria) this;
        }

        public Criteria andZipNotEqualTo(String value) {
            addCriterion("zip <>", value, "zip");
            return (Criteria) this;
        }

        public Criteria andZipGreaterThan(String value) {
            addCriterion("zip >", value, "zip");
            return (Criteria) this;
        }

        public Criteria andZipGreaterThanOrEqualTo(String value) {
            addCriterion("zip >=", value, "zip");
            return (Criteria) this;
        }

        public Criteria andZipLessThan(String value) {
            addCriterion("zip <", value, "zip");
            return (Criteria) this;
        }

        public Criteria andZipLessThanOrEqualTo(String value) {
            addCriterion("zip <=", value, "zip");
            return (Criteria) this;
        }

        public Criteria andZipLike(String value) {
            addCriterion("zip like", value, "zip");
            return (Criteria) this;
        }

        public Criteria andZipNotLike(String value) {
            addCriterion("zip not like", value, "zip");
            return (Criteria) this;
        }

        public Criteria andZipIn(List<String> values) {
            addCriterion("zip in", values, "zip");
            return (Criteria) this;
        }

        public Criteria andZipNotIn(List<String> values) {
            addCriterion("zip not in", values, "zip");
            return (Criteria) this;
        }

        public Criteria andZipBetween(String value1, String value2) {
            addCriterion("zip between", value1, value2, "zip");
            return (Criteria) this;
        }

        public Criteria andZipNotBetween(String value1, String value2) {
            addCriterion("zip not between", value1, value2, "zip");
            return (Criteria) this;
        }

        public Criteria andOfficeFlgIsNull() {
            addCriterion("office_flg is null");
            return (Criteria) this;
        }

        public Criteria andOfficeFlgIsNotNull() {
            addCriterion("office_flg is not null");
            return (Criteria) this;
        }

        public Criteria andOfficeFlgEqualTo(Integer value) {
            addCriterion("office_flg =", value, "officeFlg");
            return (Criteria) this;
        }

        public Criteria andOfficeFlgNotEqualTo(Integer value) {
            addCriterion("office_flg <>", value, "officeFlg");
            return (Criteria) this;
        }

        public Criteria andOfficeFlgGreaterThan(Integer value) {
            addCriterion("office_flg >", value, "officeFlg");
            return (Criteria) this;
        }

        public Criteria andOfficeFlgGreaterThanOrEqualTo(Integer value) {
            addCriterion("office_flg >=", value, "officeFlg");
            return (Criteria) this;
        }

        public Criteria andOfficeFlgLessThan(Integer value) {
            addCriterion("office_flg <", value, "officeFlg");
            return (Criteria) this;
        }

        public Criteria andOfficeFlgLessThanOrEqualTo(Integer value) {
            addCriterion("office_flg <=", value, "officeFlg");
            return (Criteria) this;
        }

        public Criteria andOfficeFlgIn(List<Integer> values) {
            addCriterion("office_flg in", values, "officeFlg");
            return (Criteria) this;
        }

        public Criteria andOfficeFlgNotIn(List<Integer> values) {
            addCriterion("office_flg not in", values, "officeFlg");
            return (Criteria) this;
        }

        public Criteria andOfficeFlgBetween(Integer value1, Integer value2) {
            addCriterion("office_flg between", value1, value2, "officeFlg");
            return (Criteria) this;
        }

        public Criteria andOfficeFlgNotBetween(Integer value1, Integer value2) {
            addCriterion("office_flg not between", value1, value2, "officeFlg");
            return (Criteria) this;
        }

        public Criteria andDeleteFlgIsNull() {
            addCriterion("delete_flg is null");
            return (Criteria) this;
        }

        public Criteria andDeleteFlgIsNotNull() {
            addCriterion("delete_flg is not null");
            return (Criteria) this;
        }

        public Criteria andDeleteFlgEqualTo(Integer value) {
            addCriterion("delete_flg =", value, "deleteFlg");
            return (Criteria) this;
        }

        public Criteria andDeleteFlgNotEqualTo(Integer value) {
            addCriterion("delete_flg <>", value, "deleteFlg");
            return (Criteria) this;
        }

        public Criteria andDeleteFlgGreaterThan(Integer value) {
            addCriterion("delete_flg >", value, "deleteFlg");
            return (Criteria) this;
        }

        public Criteria andDeleteFlgGreaterThanOrEqualTo(Integer value) {
            addCriterion("delete_flg >=", value, "deleteFlg");
            return (Criteria) this;
        }

        public Criteria andDeleteFlgLessThan(Integer value) {
            addCriterion("delete_flg <", value, "deleteFlg");
            return (Criteria) this;
        }

        public Criteria andDeleteFlgLessThanOrEqualTo(Integer value) {
            addCriterion("delete_flg <=", value, "deleteFlg");
            return (Criteria) this;
        }

        public Criteria andDeleteFlgIn(List<Integer> values) {
            addCriterion("delete_flg in", values, "deleteFlg");
            return (Criteria) this;
        }

        public Criteria andDeleteFlgNotIn(List<Integer> values) {
            addCriterion("delete_flg not in", values, "deleteFlg");
            return (Criteria) this;
        }

        public Criteria andDeleteFlgBetween(Integer value1, Integer value2) {
            addCriterion("delete_flg between", value1, value2, "deleteFlg");
            return (Criteria) this;
        }

        public Criteria andDeleteFlgNotBetween(Integer value1, Integer value2) {
            addCriterion("delete_flg not between", value1, value2, "deleteFlg");
            return (Criteria) this;
        }

        public Criteria andKenNameIsNull() {
            addCriterion("ken_name is null");
            return (Criteria) this;
        }

        public Criteria andKenNameIsNotNull() {
            addCriterion("ken_name is not null");
            return (Criteria) this;
        }

        public Criteria andKenNameEqualTo(String value) {
            addCriterion("ken_name =", value, "kenName");
            return (Criteria) this;
        }

        public Criteria andKenNameNotEqualTo(String value) {
            addCriterion("ken_name <>", value, "kenName");
            return (Criteria) this;
        }

        public Criteria andKenNameGreaterThan(String value) {
            addCriterion("ken_name >", value, "kenName");
            return (Criteria) this;
        }

        public Criteria andKenNameGreaterThanOrEqualTo(String value) {
            addCriterion("ken_name >=", value, "kenName");
            return (Criteria) this;
        }

        public Criteria andKenNameLessThan(String value) {
            addCriterion("ken_name <", value, "kenName");
            return (Criteria) this;
        }

        public Criteria andKenNameLessThanOrEqualTo(String value) {
            addCriterion("ken_name <=", value, "kenName");
            return (Criteria) this;
        }

        public Criteria andKenNameLike(String value) {
            addCriterion("ken_name like", value, "kenName");
            return (Criteria) this;
        }

        public Criteria andKenNameNotLike(String value) {
            addCriterion("ken_name not like", value, "kenName");
            return (Criteria) this;
        }

        public Criteria andKenNameIn(List<String> values) {
            addCriterion("ken_name in", values, "kenName");
            return (Criteria) this;
        }

        public Criteria andKenNameNotIn(List<String> values) {
            addCriterion("ken_name not in", values, "kenName");
            return (Criteria) this;
        }

        public Criteria andKenNameBetween(String value1, String value2) {
            addCriterion("ken_name between", value1, value2, "kenName");
            return (Criteria) this;
        }

        public Criteria andKenNameNotBetween(String value1, String value2) {
            addCriterion("ken_name not between", value1, value2, "kenName");
            return (Criteria) this;
        }

        public Criteria andKenFuriIsNull() {
            addCriterion("ken_furi is null");
            return (Criteria) this;
        }

        public Criteria andKenFuriIsNotNull() {
            addCriterion("ken_furi is not null");
            return (Criteria) this;
        }

        public Criteria andKenFuriEqualTo(String value) {
            addCriterion("ken_furi =", value, "kenFuri");
            return (Criteria) this;
        }

        public Criteria andKenFuriNotEqualTo(String value) {
            addCriterion("ken_furi <>", value, "kenFuri");
            return (Criteria) this;
        }

        public Criteria andKenFuriGreaterThan(String value) {
            addCriterion("ken_furi >", value, "kenFuri");
            return (Criteria) this;
        }

        public Criteria andKenFuriGreaterThanOrEqualTo(String value) {
            addCriterion("ken_furi >=", value, "kenFuri");
            return (Criteria) this;
        }

        public Criteria andKenFuriLessThan(String value) {
            addCriterion("ken_furi <", value, "kenFuri");
            return (Criteria) this;
        }

        public Criteria andKenFuriLessThanOrEqualTo(String value) {
            addCriterion("ken_furi <=", value, "kenFuri");
            return (Criteria) this;
        }

        public Criteria andKenFuriLike(String value) {
            addCriterion("ken_furi like", value, "kenFuri");
            return (Criteria) this;
        }

        public Criteria andKenFuriNotLike(String value) {
            addCriterion("ken_furi not like", value, "kenFuri");
            return (Criteria) this;
        }

        public Criteria andKenFuriIn(List<String> values) {
            addCriterion("ken_furi in", values, "kenFuri");
            return (Criteria) this;
        }

        public Criteria andKenFuriNotIn(List<String> values) {
            addCriterion("ken_furi not in", values, "kenFuri");
            return (Criteria) this;
        }

        public Criteria andKenFuriBetween(String value1, String value2) {
            addCriterion("ken_furi between", value1, value2, "kenFuri");
            return (Criteria) this;
        }

        public Criteria andKenFuriNotBetween(String value1, String value2) {
            addCriterion("ken_furi not between", value1, value2, "kenFuri");
            return (Criteria) this;
        }

        public Criteria andCityNameIsNull() {
            addCriterion("city_name is null");
            return (Criteria) this;
        }

        public Criteria andCityNameIsNotNull() {
            addCriterion("city_name is not null");
            return (Criteria) this;
        }

        public Criteria andCityNameEqualTo(String value) {
            addCriterion("city_name =", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameNotEqualTo(String value) {
            addCriterion("city_name <>", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameGreaterThan(String value) {
            addCriterion("city_name >", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameGreaterThanOrEqualTo(String value) {
            addCriterion("city_name >=", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameLessThan(String value) {
            addCriterion("city_name <", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameLessThanOrEqualTo(String value) {
            addCriterion("city_name <=", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameLike(String value) {
            addCriterion("city_name like", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameNotLike(String value) {
            addCriterion("city_name not like", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameIn(List<String> values) {
            addCriterion("city_name in", values, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameNotIn(List<String> values) {
            addCriterion("city_name not in", values, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameBetween(String value1, String value2) {
            addCriterion("city_name between", value1, value2, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameNotBetween(String value1, String value2) {
            addCriterion("city_name not between", value1, value2, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityFuriIsNull() {
            addCriterion("city_furi is null");
            return (Criteria) this;
        }

        public Criteria andCityFuriIsNotNull() {
            addCriterion("city_furi is not null");
            return (Criteria) this;
        }

        public Criteria andCityFuriEqualTo(String value) {
            addCriterion("city_furi =", value, "cityFuri");
            return (Criteria) this;
        }

        public Criteria andCityFuriNotEqualTo(String value) {
            addCriterion("city_furi <>", value, "cityFuri");
            return (Criteria) this;
        }

        public Criteria andCityFuriGreaterThan(String value) {
            addCriterion("city_furi >", value, "cityFuri");
            return (Criteria) this;
        }

        public Criteria andCityFuriGreaterThanOrEqualTo(String value) {
            addCriterion("city_furi >=", value, "cityFuri");
            return (Criteria) this;
        }

        public Criteria andCityFuriLessThan(String value) {
            addCriterion("city_furi <", value, "cityFuri");
            return (Criteria) this;
        }

        public Criteria andCityFuriLessThanOrEqualTo(String value) {
            addCriterion("city_furi <=", value, "cityFuri");
            return (Criteria) this;
        }

        public Criteria andCityFuriLike(String value) {
            addCriterion("city_furi like", value, "cityFuri");
            return (Criteria) this;
        }

        public Criteria andCityFuriNotLike(String value) {
            addCriterion("city_furi not like", value, "cityFuri");
            return (Criteria) this;
        }

        public Criteria andCityFuriIn(List<String> values) {
            addCriterion("city_furi in", values, "cityFuri");
            return (Criteria) this;
        }

        public Criteria andCityFuriNotIn(List<String> values) {
            addCriterion("city_furi not in", values, "cityFuri");
            return (Criteria) this;
        }

        public Criteria andCityFuriBetween(String value1, String value2) {
            addCriterion("city_furi between", value1, value2, "cityFuri");
            return (Criteria) this;
        }

        public Criteria andCityFuriNotBetween(String value1, String value2) {
            addCriterion("city_furi not between", value1, value2, "cityFuri");
            return (Criteria) this;
        }

        public Criteria andTownNameIsNull() {
            addCriterion("town_name is null");
            return (Criteria) this;
        }

        public Criteria andTownNameIsNotNull() {
            addCriterion("town_name is not null");
            return (Criteria) this;
        }

        public Criteria andTownNameEqualTo(String value) {
            addCriterion("town_name =", value, "townName");
            return (Criteria) this;
        }

        public Criteria andTownNameNotEqualTo(String value) {
            addCriterion("town_name <>", value, "townName");
            return (Criteria) this;
        }

        public Criteria andTownNameGreaterThan(String value) {
            addCriterion("town_name >", value, "townName");
            return (Criteria) this;
        }

        public Criteria andTownNameGreaterThanOrEqualTo(String value) {
            addCriterion("town_name >=", value, "townName");
            return (Criteria) this;
        }

        public Criteria andTownNameLessThan(String value) {
            addCriterion("town_name <", value, "townName");
            return (Criteria) this;
        }

        public Criteria andTownNameLessThanOrEqualTo(String value) {
            addCriterion("town_name <=", value, "townName");
            return (Criteria) this;
        }

        public Criteria andTownNameLike(String value) {
            addCriterion("town_name like", value, "townName");
            return (Criteria) this;
        }

        public Criteria andTownNameNotLike(String value) {
            addCriterion("town_name not like", value, "townName");
            return (Criteria) this;
        }

        public Criteria andTownNameIn(List<String> values) {
            addCriterion("town_name in", values, "townName");
            return (Criteria) this;
        }

        public Criteria andTownNameNotIn(List<String> values) {
            addCriterion("town_name not in", values, "townName");
            return (Criteria) this;
        }

        public Criteria andTownNameBetween(String value1, String value2) {
            addCriterion("town_name between", value1, value2, "townName");
            return (Criteria) this;
        }

        public Criteria andTownNameNotBetween(String value1, String value2) {
            addCriterion("town_name not between", value1, value2, "townName");
            return (Criteria) this;
        }

        public Criteria andTownFuriIsNull() {
            addCriterion("town_furi is null");
            return (Criteria) this;
        }

        public Criteria andTownFuriIsNotNull() {
            addCriterion("town_furi is not null");
            return (Criteria) this;
        }

        public Criteria andTownFuriEqualTo(String value) {
            addCriterion("town_furi =", value, "townFuri");
            return (Criteria) this;
        }

        public Criteria andTownFuriNotEqualTo(String value) {
            addCriterion("town_furi <>", value, "townFuri");
            return (Criteria) this;
        }

        public Criteria andTownFuriGreaterThan(String value) {
            addCriterion("town_furi >", value, "townFuri");
            return (Criteria) this;
        }

        public Criteria andTownFuriGreaterThanOrEqualTo(String value) {
            addCriterion("town_furi >=", value, "townFuri");
            return (Criteria) this;
        }

        public Criteria andTownFuriLessThan(String value) {
            addCriterion("town_furi <", value, "townFuri");
            return (Criteria) this;
        }

        public Criteria andTownFuriLessThanOrEqualTo(String value) {
            addCriterion("town_furi <=", value, "townFuri");
            return (Criteria) this;
        }

        public Criteria andTownFuriLike(String value) {
            addCriterion("town_furi like", value, "townFuri");
            return (Criteria) this;
        }

        public Criteria andTownFuriNotLike(String value) {
            addCriterion("town_furi not like", value, "townFuri");
            return (Criteria) this;
        }

        public Criteria andTownFuriIn(List<String> values) {
            addCriterion("town_furi in", values, "townFuri");
            return (Criteria) this;
        }

        public Criteria andTownFuriNotIn(List<String> values) {
            addCriterion("town_furi not in", values, "townFuri");
            return (Criteria) this;
        }

        public Criteria andTownFuriBetween(String value1, String value2) {
            addCriterion("town_furi between", value1, value2, "townFuri");
            return (Criteria) this;
        }

        public Criteria andTownFuriNotBetween(String value1, String value2) {
            addCriterion("town_furi not between", value1, value2, "townFuri");
            return (Criteria) this;
        }

        public Criteria andTownMemoIsNull() {
            addCriterion("town_memo is null");
            return (Criteria) this;
        }

        public Criteria andTownMemoIsNotNull() {
            addCriterion("town_memo is not null");
            return (Criteria) this;
        }

        public Criteria andTownMemoEqualTo(String value) {
            addCriterion("town_memo =", value, "townMemo");
            return (Criteria) this;
        }

        public Criteria andTownMemoNotEqualTo(String value) {
            addCriterion("town_memo <>", value, "townMemo");
            return (Criteria) this;
        }

        public Criteria andTownMemoGreaterThan(String value) {
            addCriterion("town_memo >", value, "townMemo");
            return (Criteria) this;
        }

        public Criteria andTownMemoGreaterThanOrEqualTo(String value) {
            addCriterion("town_memo >=", value, "townMemo");
            return (Criteria) this;
        }

        public Criteria andTownMemoLessThan(String value) {
            addCriterion("town_memo <", value, "townMemo");
            return (Criteria) this;
        }

        public Criteria andTownMemoLessThanOrEqualTo(String value) {
            addCriterion("town_memo <=", value, "townMemo");
            return (Criteria) this;
        }

        public Criteria andTownMemoLike(String value) {
            addCriterion("town_memo like", value, "townMemo");
            return (Criteria) this;
        }

        public Criteria andTownMemoNotLike(String value) {
            addCriterion("town_memo not like", value, "townMemo");
            return (Criteria) this;
        }

        public Criteria andTownMemoIn(List<String> values) {
            addCriterion("town_memo in", values, "townMemo");
            return (Criteria) this;
        }

        public Criteria andTownMemoNotIn(List<String> values) {
            addCriterion("town_memo not in", values, "townMemo");
            return (Criteria) this;
        }

        public Criteria andTownMemoBetween(String value1, String value2) {
            addCriterion("town_memo between", value1, value2, "townMemo");
            return (Criteria) this;
        }

        public Criteria andTownMemoNotBetween(String value1, String value2) {
            addCriterion("town_memo not between", value1, value2, "townMemo");
            return (Criteria) this;
        }

        public Criteria andKyotoStreetIsNull() {
            addCriterion("kyoto_street is null");
            return (Criteria) this;
        }

        public Criteria andKyotoStreetIsNotNull() {
            addCriterion("kyoto_street is not null");
            return (Criteria) this;
        }

        public Criteria andKyotoStreetEqualTo(String value) {
            addCriterion("kyoto_street =", value, "kyotoStreet");
            return (Criteria) this;
        }

        public Criteria andKyotoStreetNotEqualTo(String value) {
            addCriterion("kyoto_street <>", value, "kyotoStreet");
            return (Criteria) this;
        }

        public Criteria andKyotoStreetGreaterThan(String value) {
            addCriterion("kyoto_street >", value, "kyotoStreet");
            return (Criteria) this;
        }

        public Criteria andKyotoStreetGreaterThanOrEqualTo(String value) {
            addCriterion("kyoto_street >=", value, "kyotoStreet");
            return (Criteria) this;
        }

        public Criteria andKyotoStreetLessThan(String value) {
            addCriterion("kyoto_street <", value, "kyotoStreet");
            return (Criteria) this;
        }

        public Criteria andKyotoStreetLessThanOrEqualTo(String value) {
            addCriterion("kyoto_street <=", value, "kyotoStreet");
            return (Criteria) this;
        }

        public Criteria andKyotoStreetLike(String value) {
            addCriterion("kyoto_street like", value, "kyotoStreet");
            return (Criteria) this;
        }

        public Criteria andKyotoStreetNotLike(String value) {
            addCriterion("kyoto_street not like", value, "kyotoStreet");
            return (Criteria) this;
        }

        public Criteria andKyotoStreetIn(List<String> values) {
            addCriterion("kyoto_street in", values, "kyotoStreet");
            return (Criteria) this;
        }

        public Criteria andKyotoStreetNotIn(List<String> values) {
            addCriterion("kyoto_street not in", values, "kyotoStreet");
            return (Criteria) this;
        }

        public Criteria andKyotoStreetBetween(String value1, String value2) {
            addCriterion("kyoto_street between", value1, value2, "kyotoStreet");
            return (Criteria) this;
        }

        public Criteria andKyotoStreetNotBetween(String value1, String value2) {
            addCriterion("kyoto_street not between", value1, value2, "kyotoStreet");
            return (Criteria) this;
        }

        public Criteria andBlockNameIsNull() {
            addCriterion("block_name is null");
            return (Criteria) this;
        }

        public Criteria andBlockNameIsNotNull() {
            addCriterion("block_name is not null");
            return (Criteria) this;
        }

        public Criteria andBlockNameEqualTo(String value) {
            addCriterion("block_name =", value, "blockName");
            return (Criteria) this;
        }

        public Criteria andBlockNameNotEqualTo(String value) {
            addCriterion("block_name <>", value, "blockName");
            return (Criteria) this;
        }

        public Criteria andBlockNameGreaterThan(String value) {
            addCriterion("block_name >", value, "blockName");
            return (Criteria) this;
        }

        public Criteria andBlockNameGreaterThanOrEqualTo(String value) {
            addCriterion("block_name >=", value, "blockName");
            return (Criteria) this;
        }

        public Criteria andBlockNameLessThan(String value) {
            addCriterion("block_name <", value, "blockName");
            return (Criteria) this;
        }

        public Criteria andBlockNameLessThanOrEqualTo(String value) {
            addCriterion("block_name <=", value, "blockName");
            return (Criteria) this;
        }

        public Criteria andBlockNameLike(String value) {
            addCriterion("block_name like", value, "blockName");
            return (Criteria) this;
        }

        public Criteria andBlockNameNotLike(String value) {
            addCriterion("block_name not like", value, "blockName");
            return (Criteria) this;
        }

        public Criteria andBlockNameIn(List<String> values) {
            addCriterion("block_name in", values, "blockName");
            return (Criteria) this;
        }

        public Criteria andBlockNameNotIn(List<String> values) {
            addCriterion("block_name not in", values, "blockName");
            return (Criteria) this;
        }

        public Criteria andBlockNameBetween(String value1, String value2) {
            addCriterion("block_name between", value1, value2, "blockName");
            return (Criteria) this;
        }

        public Criteria andBlockNameNotBetween(String value1, String value2) {
            addCriterion("block_name not between", value1, value2, "blockName");
            return (Criteria) this;
        }

        public Criteria andBlockFuriIsNull() {
            addCriterion("block_furi is null");
            return (Criteria) this;
        }

        public Criteria andBlockFuriIsNotNull() {
            addCriterion("block_furi is not null");
            return (Criteria) this;
        }

        public Criteria andBlockFuriEqualTo(String value) {
            addCriterion("block_furi =", value, "blockFuri");
            return (Criteria) this;
        }

        public Criteria andBlockFuriNotEqualTo(String value) {
            addCriterion("block_furi <>", value, "blockFuri");
            return (Criteria) this;
        }

        public Criteria andBlockFuriGreaterThan(String value) {
            addCriterion("block_furi >", value, "blockFuri");
            return (Criteria) this;
        }

        public Criteria andBlockFuriGreaterThanOrEqualTo(String value) {
            addCriterion("block_furi >=", value, "blockFuri");
            return (Criteria) this;
        }

        public Criteria andBlockFuriLessThan(String value) {
            addCriterion("block_furi <", value, "blockFuri");
            return (Criteria) this;
        }

        public Criteria andBlockFuriLessThanOrEqualTo(String value) {
            addCriterion("block_furi <=", value, "blockFuri");
            return (Criteria) this;
        }

        public Criteria andBlockFuriLike(String value) {
            addCriterion("block_furi like", value, "blockFuri");
            return (Criteria) this;
        }

        public Criteria andBlockFuriNotLike(String value) {
            addCriterion("block_furi not like", value, "blockFuri");
            return (Criteria) this;
        }

        public Criteria andBlockFuriIn(List<String> values) {
            addCriterion("block_furi in", values, "blockFuri");
            return (Criteria) this;
        }

        public Criteria andBlockFuriNotIn(List<String> values) {
            addCriterion("block_furi not in", values, "blockFuri");
            return (Criteria) this;
        }

        public Criteria andBlockFuriBetween(String value1, String value2) {
            addCriterion("block_furi between", value1, value2, "blockFuri");
            return (Criteria) this;
        }

        public Criteria andBlockFuriNotBetween(String value1, String value2) {
            addCriterion("block_furi not between", value1, value2, "blockFuri");
            return (Criteria) this;
        }

        public Criteria andMemoIsNull() {
            addCriterion("memo is null");
            return (Criteria) this;
        }

        public Criteria andMemoIsNotNull() {
            addCriterion("memo is not null");
            return (Criteria) this;
        }

        public Criteria andMemoEqualTo(String value) {
            addCriterion("memo =", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoNotEqualTo(String value) {
            addCriterion("memo <>", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoGreaterThan(String value) {
            addCriterion("memo >", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoGreaterThanOrEqualTo(String value) {
            addCriterion("memo >=", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoLessThan(String value) {
            addCriterion("memo <", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoLessThanOrEqualTo(String value) {
            addCriterion("memo <=", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoLike(String value) {
            addCriterion("memo like", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoNotLike(String value) {
            addCriterion("memo not like", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoIn(List<String> values) {
            addCriterion("memo in", values, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoNotIn(List<String> values) {
            addCriterion("memo not in", values, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoBetween(String value1, String value2) {
            addCriterion("memo between", value1, value2, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoNotBetween(String value1, String value2) {
            addCriterion("memo not between", value1, value2, "memo");
            return (Criteria) this;
        }

        public Criteria andOfficeNameIsNull() {
            addCriterion("office_name is null");
            return (Criteria) this;
        }

        public Criteria andOfficeNameIsNotNull() {
            addCriterion("office_name is not null");
            return (Criteria) this;
        }

        public Criteria andOfficeNameEqualTo(String value) {
            addCriterion("office_name =", value, "officeName");
            return (Criteria) this;
        }

        public Criteria andOfficeNameNotEqualTo(String value) {
            addCriterion("office_name <>", value, "officeName");
            return (Criteria) this;
        }

        public Criteria andOfficeNameGreaterThan(String value) {
            addCriterion("office_name >", value, "officeName");
            return (Criteria) this;
        }

        public Criteria andOfficeNameGreaterThanOrEqualTo(String value) {
            addCriterion("office_name >=", value, "officeName");
            return (Criteria) this;
        }

        public Criteria andOfficeNameLessThan(String value) {
            addCriterion("office_name <", value, "officeName");
            return (Criteria) this;
        }

        public Criteria andOfficeNameLessThanOrEqualTo(String value) {
            addCriterion("office_name <=", value, "officeName");
            return (Criteria) this;
        }

        public Criteria andOfficeNameLike(String value) {
            addCriterion("office_name like", value, "officeName");
            return (Criteria) this;
        }

        public Criteria andOfficeNameNotLike(String value) {
            addCriterion("office_name not like", value, "officeName");
            return (Criteria) this;
        }

        public Criteria andOfficeNameIn(List<String> values) {
            addCriterion("office_name in", values, "officeName");
            return (Criteria) this;
        }

        public Criteria andOfficeNameNotIn(List<String> values) {
            addCriterion("office_name not in", values, "officeName");
            return (Criteria) this;
        }

        public Criteria andOfficeNameBetween(String value1, String value2) {
            addCriterion("office_name between", value1, value2, "officeName");
            return (Criteria) this;
        }

        public Criteria andOfficeNameNotBetween(String value1, String value2) {
            addCriterion("office_name not between", value1, value2, "officeName");
            return (Criteria) this;
        }

        public Criteria andOfficeFuriIsNull() {
            addCriterion("office_furi is null");
            return (Criteria) this;
        }

        public Criteria andOfficeFuriIsNotNull() {
            addCriterion("office_furi is not null");
            return (Criteria) this;
        }

        public Criteria andOfficeFuriEqualTo(String value) {
            addCriterion("office_furi =", value, "officeFuri");
            return (Criteria) this;
        }

        public Criteria andOfficeFuriNotEqualTo(String value) {
            addCriterion("office_furi <>", value, "officeFuri");
            return (Criteria) this;
        }

        public Criteria andOfficeFuriGreaterThan(String value) {
            addCriterion("office_furi >", value, "officeFuri");
            return (Criteria) this;
        }

        public Criteria andOfficeFuriGreaterThanOrEqualTo(String value) {
            addCriterion("office_furi >=", value, "officeFuri");
            return (Criteria) this;
        }

        public Criteria andOfficeFuriLessThan(String value) {
            addCriterion("office_furi <", value, "officeFuri");
            return (Criteria) this;
        }

        public Criteria andOfficeFuriLessThanOrEqualTo(String value) {
            addCriterion("office_furi <=", value, "officeFuri");
            return (Criteria) this;
        }

        public Criteria andOfficeFuriLike(String value) {
            addCriterion("office_furi like", value, "officeFuri");
            return (Criteria) this;
        }

        public Criteria andOfficeFuriNotLike(String value) {
            addCriterion("office_furi not like", value, "officeFuri");
            return (Criteria) this;
        }

        public Criteria andOfficeFuriIn(List<String> values) {
            addCriterion("office_furi in", values, "officeFuri");
            return (Criteria) this;
        }

        public Criteria andOfficeFuriNotIn(List<String> values) {
            addCriterion("office_furi not in", values, "officeFuri");
            return (Criteria) this;
        }

        public Criteria andOfficeFuriBetween(String value1, String value2) {
            addCriterion("office_furi between", value1, value2, "officeFuri");
            return (Criteria) this;
        }

        public Criteria andOfficeFuriNotBetween(String value1, String value2) {
            addCriterion("office_furi not between", value1, value2, "officeFuri");
            return (Criteria) this;
        }

        public Criteria andOfficeAddressIsNull() {
            addCriterion("office_address is null");
            return (Criteria) this;
        }

        public Criteria andOfficeAddressIsNotNull() {
            addCriterion("office_address is not null");
            return (Criteria) this;
        }

        public Criteria andOfficeAddressEqualTo(String value) {
            addCriterion("office_address =", value, "officeAddress");
            return (Criteria) this;
        }

        public Criteria andOfficeAddressNotEqualTo(String value) {
            addCriterion("office_address <>", value, "officeAddress");
            return (Criteria) this;
        }

        public Criteria andOfficeAddressGreaterThan(String value) {
            addCriterion("office_address >", value, "officeAddress");
            return (Criteria) this;
        }

        public Criteria andOfficeAddressGreaterThanOrEqualTo(String value) {
            addCriterion("office_address >=", value, "officeAddress");
            return (Criteria) this;
        }

        public Criteria andOfficeAddressLessThan(String value) {
            addCriterion("office_address <", value, "officeAddress");
            return (Criteria) this;
        }

        public Criteria andOfficeAddressLessThanOrEqualTo(String value) {
            addCriterion("office_address <=", value, "officeAddress");
            return (Criteria) this;
        }

        public Criteria andOfficeAddressLike(String value) {
            addCriterion("office_address like", value, "officeAddress");
            return (Criteria) this;
        }

        public Criteria andOfficeAddressNotLike(String value) {
            addCriterion("office_address not like", value, "officeAddress");
            return (Criteria) this;
        }

        public Criteria andOfficeAddressIn(List<String> values) {
            addCriterion("office_address in", values, "officeAddress");
            return (Criteria) this;
        }

        public Criteria andOfficeAddressNotIn(List<String> values) {
            addCriterion("office_address not in", values, "officeAddress");
            return (Criteria) this;
        }

        public Criteria andOfficeAddressBetween(String value1, String value2) {
            addCriterion("office_address between", value1, value2, "officeAddress");
            return (Criteria) this;
        }

        public Criteria andOfficeAddressNotBetween(String value1, String value2) {
            addCriterion("office_address not between", value1, value2, "officeAddress");
            return (Criteria) this;
        }

        public Criteria andNewIdIsNull() {
            addCriterion("new_id is null");
            return (Criteria) this;
        }

        public Criteria andNewIdIsNotNull() {
            addCriterion("new_id is not null");
            return (Criteria) this;
        }

        public Criteria andNewIdEqualTo(String value) {
            addCriterion("new_id =", value, "newId");
            return (Criteria) this;
        }

        public Criteria andNewIdNotEqualTo(String value) {
            addCriterion("new_id <>", value, "newId");
            return (Criteria) this;
        }

        public Criteria andNewIdGreaterThan(String value) {
            addCriterion("new_id >", value, "newId");
            return (Criteria) this;
        }

        public Criteria andNewIdGreaterThanOrEqualTo(String value) {
            addCriterion("new_id >=", value, "newId");
            return (Criteria) this;
        }

        public Criteria andNewIdLessThan(String value) {
            addCriterion("new_id <", value, "newId");
            return (Criteria) this;
        }

        public Criteria andNewIdLessThanOrEqualTo(String value) {
            addCriterion("new_id <=", value, "newId");
            return (Criteria) this;
        }

        public Criteria andNewIdLike(String value) {
            addCriterion("new_id like", value, "newId");
            return (Criteria) this;
        }

        public Criteria andNewIdNotLike(String value) {
            addCriterion("new_id not like", value, "newId");
            return (Criteria) this;
        }

        public Criteria andNewIdIn(List<String> values) {
            addCriterion("new_id in", values, "newId");
            return (Criteria) this;
        }

        public Criteria andNewIdNotIn(List<String> values) {
            addCriterion("new_id not in", values, "newId");
            return (Criteria) this;
        }

        public Criteria andNewIdBetween(String value1, String value2) {
            addCriterion("new_id between", value1, value2, "newId");
            return (Criteria) this;
        }

        public Criteria andNewIdNotBetween(String value1, String value2) {
            addCriterion("new_id not between", value1, value2, "newId");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria implements Serializable {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion implements Serializable {
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