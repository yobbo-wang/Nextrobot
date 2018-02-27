package wang.yobbo.common.appengine.entity;

import org.springframework.data.domain.Persistable;
import wang.yobbo.common.appengine.BaseDaoManager;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Created by xiaoyang on 2017/12/25.
 * 实体公共封装类
 * 实现主键实现方式
 */
@MappedSuperclass
public abstract class AbstractEntity<ID extends Serializable> implements Persistable<ID> {
    public abstract ID getId();
    public abstract void setId(ID var1);
}
