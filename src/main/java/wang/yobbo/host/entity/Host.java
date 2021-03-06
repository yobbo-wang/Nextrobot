package wang.yobbo.host.entity;

import wang.yobbo.common.appengine.entity.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
/**
 * 此文件由应用引擎生成，严禁修改，如重复生成时，该文件将被覆盖。
 * NextRobotHost 实体信息
 * 主机信息
 * @author 应用引擎自动生成
 * @date 2018-03-03 02:25:55
 *
 */
@Entity
@Table(name = "NR_HOST_TEST")
public class Host extends BaseEntity<String> {
	private static final long serialVersionUID = 1L;

    
    @Column(name = "NAME")
    private String name;
    
    @Column(name = "IP")
    private String ip;

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
}