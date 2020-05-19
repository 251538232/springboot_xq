package jp.co.xq.service.sys.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class SysArea implements Serializable {
    private Integer id;

    private Integer kenId;

    private Integer cityId;

    private Integer townId;

    private String zip;

    private Integer officeFlg;

    private Integer deleteFlg;

    private String kenName;

    private String kenFuri;

    private String cityName;

    private String cityFuri;

    private String townName;

    private String townFuri;

    private String townMemo;

    private String kyotoStreet;

    private String blockName;

    private String blockFuri;

    private String memo;

    private String officeName;

    private String officeFuri;

    private String officeAddress;

    private String newId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getKenId() {
        return kenId;
    }

    public void setKenId(Integer kenId) {
        this.kenId = kenId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getTownId() {
        return townId;
    }

    public void setTownId(Integer townId) {
        this.townId = townId;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Integer getOfficeFlg() {
        return officeFlg;
    }

    public void setOfficeFlg(Integer officeFlg) {
        this.officeFlg = officeFlg;
    }

    public Integer getDeleteFlg() {
        return deleteFlg;
    }

    public void setDeleteFlg(Integer deleteFlg) {
        this.deleteFlg = deleteFlg;
    }

    public String getKenName() {
        return kenName;
    }

    public void setKenName(String kenName) {
        this.kenName = kenName;
    }

    public String getKenFuri() {
        return kenFuri;
    }

    public void setKenFuri(String kenFuri) {
        this.kenFuri = kenFuri;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityFuri() {
        return cityFuri;
    }

    public void setCityFuri(String cityFuri) {
        this.cityFuri = cityFuri;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public String getTownFuri() {
        return townFuri;
    }

    public void setTownFuri(String townFuri) {
        this.townFuri = townFuri;
    }

    public String getTownMemo() {
        return townMemo;
    }

    public void setTownMemo(String townMemo) {
        this.townMemo = townMemo;
    }

    public String getKyotoStreet() {
        return kyotoStreet;
    }

    public void setKyotoStreet(String kyotoStreet) {
        this.kyotoStreet = kyotoStreet;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public String getBlockFuri() {
        return blockFuri;
    }

    public void setBlockFuri(String blockFuri) {
        this.blockFuri = blockFuri;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getOfficeFuri() {
        return officeFuri;
    }

    public void setOfficeFuri(String officeFuri) {
        this.officeFuri = officeFuri;
    }

    public String getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }

    public String getNewId() {
        return newId;
    }

    public void setNewId(String newId) {
        this.newId = newId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", kenId=").append(kenId);
        sb.append(", cityId=").append(cityId);
        sb.append(", townId=").append(townId);
        sb.append(", zip=").append(zip);
        sb.append(", officeFlg=").append(officeFlg);
        sb.append(", deleteFlg=").append(deleteFlg);
        sb.append(", kenName=").append(kenName);
        sb.append(", kenFuri=").append(kenFuri);
        sb.append(", cityName=").append(cityName);
        sb.append(", cityFuri=").append(cityFuri);
        sb.append(", townName=").append(townName);
        sb.append(", townFuri=").append(townFuri);
        sb.append(", townMemo=").append(townMemo);
        sb.append(", kyotoStreet=").append(kyotoStreet);
        sb.append(", blockName=").append(blockName);
        sb.append(", blockFuri=").append(blockFuri);
        sb.append(", memo=").append(memo);
        sb.append(", officeName=").append(officeName);
        sb.append(", officeFuri=").append(officeFuri);
        sb.append(", officeAddress=").append(officeAddress);
        sb.append(", newId=").append(newId);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        SysArea other = (SysArea) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getKenId() == null ? other.getKenId() == null : this.getKenId().equals(other.getKenId()))
            && (this.getCityId() == null ? other.getCityId() == null : this.getCityId().equals(other.getCityId()))
            && (this.getTownId() == null ? other.getTownId() == null : this.getTownId().equals(other.getTownId()))
            && (this.getZip() == null ? other.getZip() == null : this.getZip().equals(other.getZip()))
            && (this.getOfficeFlg() == null ? other.getOfficeFlg() == null : this.getOfficeFlg().equals(other.getOfficeFlg()))
            && (this.getDeleteFlg() == null ? other.getDeleteFlg() == null : this.getDeleteFlg().equals(other.getDeleteFlg()))
            && (this.getKenName() == null ? other.getKenName() == null : this.getKenName().equals(other.getKenName()))
            && (this.getKenFuri() == null ? other.getKenFuri() == null : this.getKenFuri().equals(other.getKenFuri()))
            && (this.getCityName() == null ? other.getCityName() == null : this.getCityName().equals(other.getCityName()))
            && (this.getCityFuri() == null ? other.getCityFuri() == null : this.getCityFuri().equals(other.getCityFuri()))
            && (this.getTownName() == null ? other.getTownName() == null : this.getTownName().equals(other.getTownName()))
            && (this.getTownFuri() == null ? other.getTownFuri() == null : this.getTownFuri().equals(other.getTownFuri()))
            && (this.getTownMemo() == null ? other.getTownMemo() == null : this.getTownMemo().equals(other.getTownMemo()))
            && (this.getKyotoStreet() == null ? other.getKyotoStreet() == null : this.getKyotoStreet().equals(other.getKyotoStreet()))
            && (this.getBlockName() == null ? other.getBlockName() == null : this.getBlockName().equals(other.getBlockName()))
            && (this.getBlockFuri() == null ? other.getBlockFuri() == null : this.getBlockFuri().equals(other.getBlockFuri()))
            && (this.getMemo() == null ? other.getMemo() == null : this.getMemo().equals(other.getMemo()))
            && (this.getOfficeName() == null ? other.getOfficeName() == null : this.getOfficeName().equals(other.getOfficeName()))
            && (this.getOfficeFuri() == null ? other.getOfficeFuri() == null : this.getOfficeFuri().equals(other.getOfficeFuri()))
            && (this.getOfficeAddress() == null ? other.getOfficeAddress() == null : this.getOfficeAddress().equals(other.getOfficeAddress()))
            && (this.getNewId() == null ? other.getNewId() == null : this.getNewId().equals(other.getNewId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getKenId() == null) ? 0 : getKenId().hashCode());
        result = prime * result + ((getCityId() == null) ? 0 : getCityId().hashCode());
        result = prime * result + ((getTownId() == null) ? 0 : getTownId().hashCode());
        result = prime * result + ((getZip() == null) ? 0 : getZip().hashCode());
        result = prime * result + ((getOfficeFlg() == null) ? 0 : getOfficeFlg().hashCode());
        result = prime * result + ((getDeleteFlg() == null) ? 0 : getDeleteFlg().hashCode());
        result = prime * result + ((getKenName() == null) ? 0 : getKenName().hashCode());
        result = prime * result + ((getKenFuri() == null) ? 0 : getKenFuri().hashCode());
        result = prime * result + ((getCityName() == null) ? 0 : getCityName().hashCode());
        result = prime * result + ((getCityFuri() == null) ? 0 : getCityFuri().hashCode());
        result = prime * result + ((getTownName() == null) ? 0 : getTownName().hashCode());
        result = prime * result + ((getTownFuri() == null) ? 0 : getTownFuri().hashCode());
        result = prime * result + ((getTownMemo() == null) ? 0 : getTownMemo().hashCode());
        result = prime * result + ((getKyotoStreet() == null) ? 0 : getKyotoStreet().hashCode());
        result = prime * result + ((getBlockName() == null) ? 0 : getBlockName().hashCode());
        result = prime * result + ((getBlockFuri() == null) ? 0 : getBlockFuri().hashCode());
        result = prime * result + ((getMemo() == null) ? 0 : getMemo().hashCode());
        result = prime * result + ((getOfficeName() == null) ? 0 : getOfficeName().hashCode());
        result = prime * result + ((getOfficeFuri() == null) ? 0 : getOfficeFuri().hashCode());
        result = prime * result + ((getOfficeAddress() == null) ? 0 : getOfficeAddress().hashCode());
        result = prime * result + ((getNewId() == null) ? 0 : getNewId().hashCode());
        return result;
    }

    /**
     * This enum was generated by MyBatis Generator.
     * This enum corresponds to the database table sys_area
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public enum Column {
        id("id", "id", "INTEGER", false),
        kenId("ken_id", "kenId", "INTEGER", false),
        cityId("city_id", "cityId", "INTEGER", false),
        townId("town_id", "townId", "INTEGER", false),
        zip("zip", "zip", "VARCHAR", false),
        officeFlg("office_flg", "officeFlg", "INTEGER", false),
        deleteFlg("delete_flg", "deleteFlg", "INTEGER", false),
        kenName("ken_name", "kenName", "VARCHAR", false),
        kenFuri("ken_furi", "kenFuri", "VARCHAR", false),
        cityName("city_name", "cityName", "VARCHAR", false),
        cityFuri("city_furi", "cityFuri", "VARCHAR", false),
        townName("town_name", "townName", "VARCHAR", false),
        townFuri("town_furi", "townFuri", "VARCHAR", false),
        townMemo("town_memo", "townMemo", "VARCHAR", false),
        kyotoStreet("kyoto_street", "kyotoStreet", "VARCHAR", false),
        blockName("block_name", "blockName", "VARCHAR", false),
        blockFuri("block_furi", "blockFuri", "VARCHAR", false),
        memo("memo", "memo", "VARCHAR", false),
        officeName("office_name", "officeName", "VARCHAR", false),
        officeFuri("office_furi", "officeFuri", "VARCHAR", false),
        officeAddress("office_address", "officeAddress", "VARCHAR", false),
        newId("new_id", "newId", "VARCHAR", false);

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table sys_area
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private static final String BEGINNING_DELIMITER = "`";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table sys_area
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private static final String ENDING_DELIMITER = "`";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table sys_area
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String column;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table sys_area
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final boolean isColumnNameDelimited;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table sys_area
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String javaProperty;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table sys_area
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String jdbcType;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table sys_area
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String value() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table sys_area
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getValue() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table sys_area
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getJavaProperty() {
            return this.javaProperty;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table sys_area
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getJdbcType() {
            return this.jdbcType;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table sys_area
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        Column(String column, String javaProperty, String jdbcType, boolean isColumnNameDelimited) {
            this.column = column;
            this.javaProperty = javaProperty;
            this.jdbcType = jdbcType;
            this.isColumnNameDelimited = isColumnNameDelimited;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table sys_area
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String desc() {
            return this.getEscapedColumnName() + " DESC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table sys_area
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String asc() {
            return this.getEscapedColumnName() + " ASC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table sys_area
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public static Column[] excludes(Column ... excludes) {
            ArrayList<Column> columns = new ArrayList<>(Arrays.asList(Column.values()));
            if (excludes != null && excludes.length > 0) {
                columns.removeAll(new ArrayList<>(Arrays.asList(excludes)));
            }
            return columns.toArray(new Column[]{});
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table sys_area
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getEscapedColumnName() {
            if (this.isColumnNameDelimited) {
                return new StringBuilder().append(BEGINNING_DELIMITER).append(this.column).append(ENDING_DELIMITER).toString();
            } else {
                return this.column;
            }
        }
    }
}