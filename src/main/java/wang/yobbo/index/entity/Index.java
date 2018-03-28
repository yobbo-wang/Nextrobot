package wang.yobbo.index.entity;

import wang.yobbo.common.appengine.entity.BaseEntity;
import javax.persistence.*;
/**
 * 此文件由应用引擎生成，严禁修改，如重复生成时，该文件将被覆盖。
 * Index 实体信息
 * 应用引擎第一张表
 * @author 应用引擎自动生成
 * @date 2018-03-28 14:50:20
 *
 */
@Entity
@Table(name = "NR_INDEX")
public class Index extends BaseEntity<String> {
	private static final long serialVersionUID = 1L;

    @Column(name = "INDEX_ONE",length = 12 )
    private String index_one;

    @Column(name = "INDEX_TWO",length = 60 )
    private String index_two;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "MAKEIFNO_ID")
    private wang.yobbo.makeifno.entity.Makeifno makeifno;

    public String getIndex_one(){
		return index_one;
	}

	public void setIndex_one(String index_one){
		this.index_one = index_one;
	}

    public String getIndex_two(){
		return index_two;
	}

	public void setIndex_two(String index_two){
		this.index_two = index_two;
	}

    public wang.yobbo.makeifno.entity.Makeifno getMakeifno(){
		return makeifno;
	}

	public void setMakeifno(wang.yobbo.makeifno.entity.Makeifno makeifno){
		this.makeifno = makeifno;
	}

}