package wang.yobbo.sys.entity;

import javax.persistence.Column;

/**
 * 主机信息
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "NR_HOSTS")
public class Hosts extends wang.yobbo.common.appengine.entity.BaseEntity<String> {
    private static final long serialVersionUID = 1L;
    @Column(name = "IP", length = 18, unique = true, nullable = false)
    private String ip;

    @Column(name = "USER_NAME", length = 30, nullable = false)
    private String userName;

    @Column(name = "PWD", length = 50)
    private String pwd;

    @Column(name = "NAME", length = 30)
    private String name;

    @Column(name = "REMARK", length = 50)
    private String remark;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
