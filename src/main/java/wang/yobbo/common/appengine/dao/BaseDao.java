package wang.yobbo.common.appengine.dao;

import net.sf.ehcache.config.Searchable;
import org.springframework.data.domain.Page;
import wang.yobbo.common.appengine.entity.AbstractEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaoyang on 2017/12/28.
 * 数据操作层公共封装接口
 */
public interface BaseDao<E extends AbstractEntity, ID extends Serializable> {

    /**
     * 获取记录数
     * @param v0
     * @return
     */
    Long count(Searchable v0);

    long count();

    Page<E> find(Searchable v0);

    /**
     * 查询分页
     * @param v0
     * @return
     */
    List<E> findPageWithoutCount(Searchable v0);

    Page<E> find(Searchable var0, E var1);

    /**
     * 查询所有
     */
    List<E> findAll();

    /**
     * 查询所有
     * @param entity
     * @return
     */
    List<E> findAll(E entity);

    E createOfEntity(E entity);

    E saveOfEntity(E entity);

    int deleteById(ID... ids);

    void deleteOfEntity(E entity);

    E get(Serializable id);

    Map findBySqlOne(String sql, Object...params);
}
