package wang.yobbo.index.entity;

import wang.yobbo.common.appengine.entity.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
/**
 * 此文件由应用引擎生成，严禁修改，如重复生成时，该文件将被覆盖。
 * Index 实体信息
 * 应用引擎第一张表
 * @author 应用引擎自动生成
 * @date 2018-03-13 18:03:05
 *
 */
@Entity
@Table(name = "NR_INDEX")
public class Index extends BaseEntity<String> {
	private static final long serialVersionUID = 1L;

    //a1-1
    @Column(name = "A1",length = 20 ,unique = true,nullable = false)
    private java.lang.String a1;
    //a2-2
    @Column(name = "A2",length = 30 ,unique = true,nullable = false)
    private java.lang.String a2;

    public java.lang.String getA1(){
		return a1;
	}

	public void setA1(java.lang.String a1){
		this.a1 = a1;
	}
    public java.lang.String getA2(){
		return a2;
	}

	public void setA2(java.lang.String a2){
		this.a2 = a2;
	}
}