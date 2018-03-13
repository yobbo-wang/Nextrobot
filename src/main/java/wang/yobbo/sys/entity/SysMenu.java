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
@Table(name = "NR_SYS_MENU")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE) //读写
public class SysMenu extends BaseEntity<String> {
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
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "PARENT_ID", insertable = false, updatable = false)
    private List<SysMenu> children = new ArrayList<SysMenu>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "MENU_ID")
    private List<SysMenuEntity> tables = new ArrayList<SysMenuEntity>();

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

    public List<SysMenu> getChildren() {
        return children;
    }

    public void setChildren(List<SysMenu> children) {
        this.children = children;
    }

    public List<SysMenuEntity> getTables() {
        return tables;
    }

    public void setTables(List<SysMenuEntity> tables) {
        this.tables = tables;
    }
}
