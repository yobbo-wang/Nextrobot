package wang.yobbo.sys.entity;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import wang.yobbo.common.appengine.entity.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 菜单表信息
 */
@Entity
@Table(name = "NR_SYS_MENU_ENTITY")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE) //读写
public class SysMenuEntity extends BaseEntity<String> {
    private static final long serialVersionUID = 1L;

    public SysMenuEntity() {
    }

    public SysMenuEntity(String entityName, String tableName, String businessClassification, String id) {
        this.entityName = entityName;
        this.tableName = tableName;
        this.businessClassification = businessClassification;
        super.setId(id);
    }

    @Column(name = "ENTITY_NAME", length = 40, unique = true)
    private String entityName;

    @Column(name = "TABLE_NAME", length = 40, unique = true)
    private String tableName;

    @Column(name = "REMARK", length = 150)
    private String remark;

    @Column(name = "MENU_ID", length = 32)
    private String menuId;

    @Column(name = "BUSINESS_CLASSIFICATION", length = 30)
    private String businessClassification;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ENTITY_ID", updatable = false)
    private List<EntityProperty> entityProperties = new ArrayList<EntityProperty>();

    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MENU_ID")
    private transient  NextRobotSysMenu nextRobotSysMenu;*/

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

    public List<EntityProperty> getEntityProperties() {
        return entityProperties;
    }

    public void setEntityProperties(List<EntityProperty> entityProperties) {
        this.entityProperties = entityProperties;
    }

    public void setBusinessClassification(String businessClassification) {
        this.businessClassification = businessClassification;
    }
}
