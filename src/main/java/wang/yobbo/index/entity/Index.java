package wang.yobbo.index.entity;

import wang.yobbo.common.appengine.entity.BaseEntity;
import javax.persistence.*;
/**
 * 此文件由应用引擎生成，严禁修改，如重复生成时，该文件将被覆盖。
 * Index 实体信息
 * dd
 * @author 应用引擎自动生成
 * @date 2018-03-16 15:55:56
 *
 */
@Entity
@Table(name = "NR_INDEX")
public class Index extends BaseEntity<String> {
	private static final long serialVersionUID = 1L;

    @Column(name = "A1")
    private String a1;

    @Column(name = "A2")
    private String a2;

    @Column(name = "A3")
    private String a3;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "MESSAGE_MANY_ID") //通过主键关联
    private java.util.List<wang.yobbo.message.entity.Message> Message;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "INDEX_ONE_ID") //通过主键关联
    private wang.yobbo.index.entity.Index Index;

    public String getA1(){
		return a1;
	}

	public void setA1(String a1){
		this.a1 = a1;
	}

    public String getA2(){
		return a2;
	}

	public void setA2(String a2){
		this.a2 = a2;
	}

    public String getA3(){
		return a3;
	}

	public void setA3(String a3){
		this.a3 = a3;
	}

    public java.util.List<wang.yobbo.message.entity.Message> getMessage(){
		return Message;
	}

	public void setMessage(java.util.List<wang.yobbo.message.entity.Message> Message){
		this.Message = Message;
	}

    public wang.yobbo.index.entity.Index getIndex(){
		return Index;
	}

	public void setIndex(wang.yobbo.index.entity.Index Index){
		this.Index = Index;
	}

}