package wang.yobbo.message.entity;

import wang.yobbo.common.appengine.entity.BaseEntity;
import javax.persistence.*;
/**
 * 此文件由应用引擎生成，严禁修改，如重复生成时，该文件将被覆盖。
 * Message 实体信息
 * 应用引擎消息管理
 * @author 应用引擎自动生成
 * @date 2018-03-16 15:03:55
 *
 */
@Entity
@Table(name = "NR_MESSAGE")
public class Message extends BaseEntity<String> {
	private static final long serialVersionUID = 1L;

    @Column(name = "NAME")
    private String name;

    @Column(name = "MASSAGE")
    private String massage;

    public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

    public String getMassage(){
		return massage;
	}

	public void setMassage(String massage){
		this.massage = massage;
	}

}