package wang.yobbo.message.entity;

import wang.yobbo.common.appengine.entity.BaseEntity;
import javax.persistence.*;
/**
 * 此文件由应用引擎生成，严禁修改，如重复生成时，该文件将被覆盖。
 * Message 实体信息
 * 应用引擎消息管理
 * @author 应用引擎自动生成
 * @date 2018-03-23 13:41:10
 *
 */
@Entity
@Table(name = "NR_MESSAGE")
public class Message extends BaseEntity<String> {
	private static final long serialVersionUID = 1L;

    @Column(name = "NAME",length = 30 )
    private String name;

    @Column(name = "MASSAGE")
    private java.sql.Clob massage;

    @Column(name = "STATUS",length = 2 )
    private String status;

    public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

    public java.sql.Clob getMassage(){
		return massage;
	}

	public void setMassage(java.sql.Clob massage){
		this.massage = massage;
	}

    public String getStatus(){
		return status;
	}

	public void setStatus(String status){
		this.status = status;
	}

}