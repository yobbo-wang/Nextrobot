package wang.yobbo.sys.entity;

import wang.yobbo.common.appengine.entity.BaseEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "NEXT_ROBOT_SYS_ROLE")
public class NextRobotSysRole extends BaseEntity<String> {
    private static final long serialVersionUID = 1L;

    //业务角色名称
    @Column(name = "ROLE_NAME", length = 100, nullable = false)
    private String roleName;

    @OneToMany
    @JoinColumn(name = "USER_ID")
    private transient Set<NextRobotSysUser> user_roleSet;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<NextRobotSysUser> getUser_roleSet() {
        return user_roleSet;
    }

    public void setUser_roleSet(Set<NextRobotSysUser> user_roleSet) {
        this.user_roleSet = user_roleSet;
    }
}
