package wang.yobbo.hostInfo.entity;

import wang.yobbo.common.appengine.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
/**
 * 此文件由应用引擎生成，严禁修改，如重复生成时，该文件将被覆盖。
 * NextRobotHostInfo 实体信息
 * 主机信息
 * @author 应用引擎自动生成
 * @date 2018-03-02 16:51:36
 *
 */
@Entity
@Table(name = "NEXT_ROBOT_HostInfo")
public class NextRobotHostInfo extends BaseEntity<String> {
    private static final long serialVersionUID = 1L;

    //rewrwe
    @Column(name = "AGE",length = 23 ,unique = true)
    private java.lang.Integer age;
    //erewrewr
    @Column(name = "NAME",length = 2324 ,unique = true)
    private java.lang.String name;

    public java.lang.Integer getAge(){
        return age;
    }
    public void setAge(java.lang.Integer age){
        this.age = age;
    }
    public java.lang.String getName(){
        return name;
    }
    public void setName(java.lang.String name){
        this.name = name;
    }

    public boolean isNew() {
        return false;
    }
}