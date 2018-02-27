package wang.yobbo.sys.entity;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import wang.yobbo.common.appengine.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 菜单表信息
 */
@Entity
@Table(name = "NEXT_ROBOT_SYS_MENU_TABLES")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE) //读写
public class NextRobotSysMenuTable extends BaseEntity<String> {
    private static final long serialVersionUID = 1L;

    @Column(name = "ENTITY_NAME", length = 40, unique = true)
    private String entityName;

    @Column(name = "TABLE_NAME", length = 40, unique = true)
    private String tableName;

    @Column(name = "REMARK", length = 150)
    private String remark;

    @Column(name = "MENU_ID", length = 32)
    private String menuId;

    @Column(name = "Business_Classification", length = 30)
    private String businessClassification;

    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MENU_ID")
    @JsonIgnore
    private NextRobotSysMenu nextRobotSysMenu;*/

    public boolean isNew() {
        return false;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getBusinessClassification() {
        return businessClassification;
    }

    public void setBusinessClassification(String businessClassification) {
        this.businessClassification = businessClassification;
    }
}
