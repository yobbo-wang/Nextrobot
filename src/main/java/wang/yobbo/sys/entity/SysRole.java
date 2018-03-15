package wang.yobbo.sys.entity;

import wang.yobbo.common.appengine.entity.BaseEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "NR_SYS_ROLE")
public class SysRole extends BaseEntity<String> {
    private static final long serialVersionUID = 1L;

    //业务角色名称
    @Column(name = "ROLE_NAME", length = 100, nullable = false)
    private String roleName;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    private transient Set<SysUser> user_roleSet;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<SysUser> getUser_roleSet() {
        return user_roleSet;
    }

    public void setUser_roleSet(Set<SysUser> user_roleSet) {
        this.user_roleSet = user_roleSet;
    }
}
