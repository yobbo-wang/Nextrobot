package wang.yobbo.sys.entity;

import wang.yobbo.common.appengine.entity.BaseEntity;

import javax.persistence.*;

/**
 * 实体属性
 */
@Entity
@Table(name = "NR_ENTITY_PROPERTY")
public class EntityProperty extends BaseEntity<String> {
    private static final long serialVersionUID = 1L;

    @Column(name = "COLUMN_NAME", length = 50, nullable = false)
    private String column_name;

    @Column(name = "TYPE_NAME", length = 60, nullable = false)
    private String type_name;

    @Column(name = "PRIMARY_KEY", length = 3)
    private String primary_key;

    @Column(name = "IS_NULL_ABLE", length = 3)
    private String is_null_able;

    @Column(name = "IS_AUTOINCREMENT", length = 3)
    private String is_autoincrement;

    @Column(name = "COLUMN_SIZE", length = 10)
    private Integer column_size;

    @Column(name = "ORDINAL_POSITION", length = 5)
    private Integer ordinal_position;

    @Column(name = "REMARKS", length = 200)
    private String remarks;

    @Column(name = "ENTITY_ID", length = 32, updatable = false)
    private String entity_id;

    public String getColumn_name() {
        return column_name;
    }

    public void setColumn_name(String column_name) {
        this.column_name = column_name;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getPrimary_key() {
        return primary_key;
    }

    public void setPrimary_key(String primary_key) {
        this.primary_key = primary_key;
    }

    public String getIs_null_able() {
        return is_null_able;
    }

    public void setIs_null_able(String is_null_able) {
        this.is_null_able = is_null_able;
    }

    public String getIs_autoincrement() {
        return is_autoincrement;
    }

    public void setIs_autoincrement(String is_autoincrement) {
        this.is_autoincrement = is_autoincrement;
    }

    public Integer getColumn_size() {
        return column_size;
    }

    public void setColumn_size(Integer column_size) {
        this.column_size = column_size;
    }

    public Integer getOrdinal_position() {
        return ordinal_position;
    }

    public void setOrdinal_position(Integer ordinal_position) {
        this.ordinal_position = ordinal_position;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getEntity_id() {
        return entity_id;
    }

    public void setEntity_id(String entity_id) {
        this.entity_id = entity_id;
    }
}
