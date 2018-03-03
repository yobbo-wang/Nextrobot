package wang.yobbo.iptables.entity;

import wang.yobbo.common.appengine.entity.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
/**
 * 此文件由应用引擎生成，严禁修改，如重复生成时，该文件将被覆盖。
 * NextRobotIptables 实体信息
 * 仪表破傲顶顶顶
 * @author 应用引擎自动生成
 * @date 2018-03-03 23:14:28
 *
 */
@Entity
@Table(name = "NEXT_ROBOT_IPTABLES")
public class NextRobotIptables extends BaseEntity<String> {
	private static final long serialVersionUID = 1L;

    
    @Column(name = "NAME")
    private java.lang.String name;
    
    @Column(name = "IP")
    private java.lang.String ip;

    public java.lang.String getName(){
		return name;
	}
	public void setName(java.lang.String name){
		this.name = name;
	}
    public java.lang.String getIp(){
		return ip;
	}
	public void setIp(java.lang.String ip){
		this.ip = ip;
	}

    public boolean isNew() {
       return false;
    }
}