package wang.yobbo.sys.entity;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import wang.yobbo.common.appengine.entity.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单配置
 */
@Entity
@Table(name = "NEXT_ROBOT_SYS_MENU")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE) //读写
public class NextRobotSysMenu extends BaseEntity<String> {
    private static final long serialVersionUID = 1L;

    /**
     * 菜单名称
     */
    @Column(name = "NAME", length = 30)
    private String text;

    @Column(name = "URL", length = 32)
    private String url;

    @Column(name = "TYPE", length = 32)
    private String type;

    @Column(name = "REMARK", length = 50)
    private String remark;

    @Column(name = "ORDER_NUMBER", length = 10)
    private Integer orderNumber;

    @Column(name = "PARENT_ID", length = 32)
    private String parentId;

    //下级菜单
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "PARENT_ID", insertable = false, updatable = false)
    private List<NextRobotSysMenu> children = new ArrayList<NextRobotSysMenu>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "MENU_ID")
    private List<NextRobotSysMenuEntity> tables = new ArrayList<NextRobotSysMenuEntity>();

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<NextRobotSysMenu> getChildren() {
        return children;
    }

    public void setChildren(List<NextRobotSysMenu> children) {
        this.children = children;
    }

    public List<NextRobotSysMenuEntity> getTables() {
        return tables;
    }

    public void setTables(List<NextRobotSysMenuEntity> tables) {
        this.tables = tables;
    }

    public boolean isNew() {
        //TODO 待实现
        return false;
    }
}
