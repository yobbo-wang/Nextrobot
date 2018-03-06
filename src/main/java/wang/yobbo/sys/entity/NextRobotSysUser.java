package wang.yobbo.sys.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import wang.yobbo.common.appengine.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 实体类可以继承BaseEntity，BaseEntity类已经加了ID、CREATE_DATE、UPDATE_DATE字段，主键生成策略是32位UUID
 * @Cache(usage = CacheConcurrencyStrategy.READ_ONLY) 如果在实体上加了这个注解，只能新增,删除，不能修改
 */
@Entity
@Table(name = "NEXT_ROBOT_SYS_USER")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class NextRobotSysUser extends BaseEntity<String>{
    private static final long serialVersionUID = 1L;

    //用户名
    @Column(name = "USER_NAME", length = 100, nullable = false, unique = true)
    private String userName;

    //姓名
    @Column(name = "NAME", length = 50, nullable = false)
    private String name;

    //密码
    @Column(name = "PASSWORD", length = 64, nullable = false)
    private String password;

    //邮箱
    @Column(name = "EMAIL", length = 64, nullable = false)
    private String email;

    //手机
    @Column(name = "MOBILE_PHONE_NUMBER", length = 30)
    private String mobilePhoneNumber;

    //用户状态
    @Column(name = "STATUS", length = 1, nullable = false)
    private String status;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
