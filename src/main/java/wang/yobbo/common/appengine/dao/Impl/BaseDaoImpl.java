package wang.yobbo.common.appengine.dao.Impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.util.Assert;
import wang.yobbo.common.appengine.BaseDaoManager;
import wang.yobbo.common.appengine.dao.BaseDao;
import wang.yobbo.common.appengine.entity.AbstractEntity;
import wang.yobbo.common.appengine.entity.Searchable;
import wang.yobbo.common.exception.SearchException;

import javax.persistence.Query;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by xiaoyang on 2017/12/28.
 * 数据操作公共类
 * 如果父类没有指定是哪个实体对象，那DaoImpl里只能查询自定义sql。不能操作实体对象
 */
public class BaseDaoImpl<E extends AbstractEntity, ID extends Serializable> implements BaseDao<E, ID>{

    private BaseDaoManager baseDaoManager;

    private BaseDaoManager getBaseDaoManager(){
        if(null == this.baseDaoManager) {
            this.baseDaoManager = new BaseDaoManager(this.getClassForStatic());
        }
        return this.baseDaoManager;
    }

    /**
     * 如果没有指定超类，默认Entity是当前BaseDaoImpl。这样只能查询自定义sql
     * @return
     */
    private Class<E> getClassForStatic() {
        Type type = this.getClass().getGenericSuperclass();
        if(type instanceof ParameterizedType){
            ParameterizedType parameterizedType= (ParameterizedType) type;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            return (Class<E>)actualTypeArguments[0];
        }else{
            return (Class<E>) type;
        }
    }


    /**
     * 根据给定的条件查询结果集
     * @param searchable
     * @return
     */
    public Long count(Searchable searchable) {
        return this.getBaseDaoManager().count(searchable);
    }

    public long count() {
        return this.getBaseDaoManager().count();
    }

    /**
     * 根据searchable查询结果集
     * @param searchable
     * @return
     */
    public Page<E> find(Searchable searchable) {
        return this.getBaseDaoManager().findAll(searchable);
    }

    //查询分页，不带count
    public List<E> findPageWithoutCount(Searchable searchable) {
        return this.getBaseDaoManager().findPageWithoutCount(searchable);
    }

    //按条件查询后，过滤掉实体中的数据
    public Page<E> find(Searchable searchable, E entity) {
        Page<E> findAll = this.getBaseDaoManager().findAll(searchable);
        List<E> entities = findAll.getContent();
        List<E> dtos = new ArrayList();
        Iterator iterator = entities.iterator();

        while(iterator.hasNext()) {
            E next = (E)iterator.next();
            if(next != null){
                dtos.add(next);
            }
        }
        return new PageImpl(dtos, this.getBaseDaoManager().getPageable(searchable), findAll.getTotalElements());
    }

    public List<E> findAll() {
        return this.getBaseDaoManager().findAll();
    }

    public List<E> findAll(E var0) {
        return this.getBaseDaoManager().findAll(var0);
    }

    /**
     * 保存或更新
     * @param entity
     * @return
     */
    public E save(E entity) {
        return this.getBaseDaoManager().save(entity);
    }

    /**
     * 根据实体ID，批量删除
     * @param ids 主键ID数组
     * @return
     */
    public int delete(ID ... ids) {
        return this.getBaseDaoManager().delete(ids);
    }

    /**
     * 根据实体删除数据
     * @param entity
     * @return
     */
    public void delete(E entity){
        this.getBaseDaoManager().delete(entity);
    }

    public E get(Serializable id) {
        E entity = this.getBaseDaoManager().find(this.getClassForStatic(), id);
        return entity;
    }


    /**
     * 根据HQL语句查询
     * @param hql hql语句
     * @param params 参数数组
     * @return
     */
    public <T> List<T> queryByHQL(String hql,  Class<T> entityBean, Object ...params){
        Query query = this.getBaseDaoManager().getEntityManager().createQuery(hql, entityBean);
        this.getBaseDaoManager().setParameter(query, params);
        try{
            List<T> resultList = query.getResultList();
            return resultList;
        }catch (Exception e){
            e.printStackTrace(); //unable set result to bean
        }
        return null;
    }

    /**
     * 根据自定义sql执行
     * @param sql 自定义sql
     * @param params 参数数组
     * @return 返回影响行数
     */
    public int updateBysql(String sql, Object ...params){
        Assert.notNull(sql, "sql must not null.");
        Query query = this.getBaseDaoManager().getEntityManager().createNativeQuery(sql);
        this.getBaseDaoManager().setParameter(query, params);
        return query.executeUpdate();
    }

    /**
     * 自定义sql查询记录数
     * @param sql 自定义sql
     * @param params 参数数组
     * @return 记录数
     */
    public Long findBySqlCount(String sql, Object ...params){
        Assert.notNull(sql, "sql must not null.");
        sql = "select count(1) as COUNT from ( " + sql + ") a";
        Query query = this.getBaseDaoManager().getEntityManager().createNativeQuery(sql);
        this.getBaseDaoManager().setParameter(query, params);
        long total = new BigInteger(query.getSingleResult().toString()).longValue();
        return total;
    }

    /**
     * 自定义sql查询多个结果集。
     * @param sql 自定义sql
     * @param params 参数数组
     * @return 返回指定entity
     */
    public <T> List<T> fingAllBySql(String sql,Class<T> entityType, Object ...params){
        Assert.notNull(sql, "sql must not null.");
        Query query = this.getBaseDaoManager().getEntityManager().createNativeQuery(sql, entityType);
        this.getBaseDaoManager().setParameter(query, params);
        return query.getResultList();
    }

    /**
     * 自定义sql查询单条记录。这里按照分页原理，返回第一条数据
     * @param sql 自定义sql
     * @param params 参数数组
     * @return 返回Map结果集
     */
    public <T> T findBySqlOne(String sql, Class<T> entityType, Object... params) {
        Assert.notNull(sql, "sql must not null.");
        Query query = this.getBaseDaoManager().getEntityManager().createNativeQuery(sql, entityType);
        query.setFirstResult(0);
        query.setMaxResults(1);
        this.getBaseDaoManager().setParameter(query, params);
        try{
            return (T)query.getSingleResult();
        }catch (SearchException e){
            e.printStackTrace();
            return null;
        }
    }
}