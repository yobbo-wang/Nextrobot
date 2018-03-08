package wang.yobbo.sys.entity;

import wang.yobbo.common.appengine.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "NEXT_ROBOT_MENU_ROLE")
public class NextRobotMenuRole extends BaseEntity<String> {
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "ROLE_ID")
    private NextRobotSysRole nextRobotSysRole;

    @ManyToOne
    @JoinColumn(name = "MENU_ID")
    private NextRobotSysMenu nextRobotSysMenu;

    public NextRobotSysRole getNextRobotSysRole() {
        return nextRobotSysRole;
    }

    public void setNextRobotSysRole(NextRobotSysRole nextRobotSysRole) {
        this.nextRobotSysRole = nextRobotSysRole;
    }

    public NextRobotSysMenu getNextRobotSysMenu() {
        return nextRobotSysMenu;
    }

    public void setNextRobotSysMenu(NextRobotSysMenu nextRobotSysMenu) {
        this.nextRobotSysMenu = nextRobotSysMenu;
    }
}
