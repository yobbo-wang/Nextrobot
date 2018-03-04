package wang.yobbo.sys.entity;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import wang.yobbo.common.appengine.entity.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单表信息
 */
@Entity
@Table(name = "NEXT_ROBOT_SYS_MENU_TABLES")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE) //读写
public class NextRobotSysMenuEntity extends BaseEntity<String> {
    private static final long serialVersionUID = 1L;

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

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "ENTITY_ID")
    private List<NextRobotEntityProperty> entityProperties = new ArrayList<NextRobotEntityProperty>();

    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MENU_ID")
    @JsonIgnore
    private NextRobotSysMenu nextRobotSysMenu;*/

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

    public List<NextRobotEntityProperty> getEntityProperties() {
        return entityProperties;
    }

    public void setEntityProperties(List<NextRobotEntityProperty> entityProperties) {
        this.entityProperties = entityProperties;
    }

    public void setBusinessClassification(String businessClassification) {
        this.businessClassification = businessClassification;
    }
}
