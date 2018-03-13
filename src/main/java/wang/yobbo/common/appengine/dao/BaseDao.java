package wang.yobbo.common.appengine.dao;

import org.springframework.data.domain.Page;
import wang.yobbo.common.appengine.entity.AbstractEntity;
import wang.yobbo.common.appengine.entity.Searchable;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaoyang on 2017/12/28.
 * 数据操作层公共封装接口
 */
public interface BaseDao<E extends AbstractEntity, ID extends Serializable> {

    /**
     * 获取记录数
     * @param searchable
     * @return
     */
    Long count(Searchable searchable);

    long count();

    /**
     * 根据查询条件结果集
     * @param searchable
     * @return
     */
    Page<E> findPage(Searchable searchable);

    /**
     * 查询分页
     * @param searchable
     * @return
     */
    List<E> findPageWithoutCount(Searchable searchable);

    Page<E> find(Searchable searchable, E entity);

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

    E save(E entity) throws Exception;

    int delete(ID... ids);

    void delete(E entity);

    E findById(Serializable id);

    /**
     * 根据hql语句查询记录
     * @param hql
     * @param entityType 实体类型
     * @param params
     * @param <T>
     * @return
     */
    <T> List<T> findByHQL(String hql, Map<String, Object> params, Class<T> entityType);

    /**
     * 根据hql语句查询单条记录
     * @param hql
     * @param entityType 实体类型
     * @param params
     * @param <T>
     * @return
     */
    <T> T findOneByHQL(String hql, Map<String, Object> params, Class<T> entityType);

    /**
     * 根据自定义sql执行
     * @param sql
     * @param params
     * @return
     */
    int updateBySql(String sql, Object ...params);

    /**
     * 根据自定义sql查询全部
     * @param sql
     * @param entityType
     * @param params
     * @param <T>
     * @return
     */
    <T> List<T> findAllBySql(String sql, Map<String, Object> params, Class<T> entityType);

    Long findBySqlCount(String sql, Object ...params);

    <T> T findOneBySql(String sql, Map<String, Object> params, Class<T> entityType);
}
