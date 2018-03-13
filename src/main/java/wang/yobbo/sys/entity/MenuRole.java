package wang.yobbo.sys.entity;

import wang.yobbo.common.appengine.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "NR_MENU_ROLE")
public class MenuRole extends BaseEntity<String> {
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "ROLE_ID")
    private SysRole nextRobotSysRole;

    @ManyToOne
    @JoinColumn(name = "MENU_ID")
    private SysMenu nextRobotSysMenu;

    public SysRole getNextRobotSysRole() {
        return nextRobotSysRole;
    }

    public void setNextRobotSysRole(SysRole nextRobotSysRole) {
        this.nextRobotSysRole = nextRobotSysRole;
    }

    public SysMenu getNextRobotSysMenu() {
        return nextRobotSysMenu;
    }

    public void setNextRobotSysMenu(SysMenu nextRobotSysMenu) {
        this.nextRobotSysMenu = nextRobotSysMenu;
    }
}
