package wang.yobbo.common.appengine;

import com.google.common.collect.Sets;
import net.sf.ehcache.config.Searchable;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.orm.jpa.SharedEntityManagerCreator;
import org.springframework.util.Assert;
import wang.yobbo.common.appengine.dao.Impl.BaseDaoImpl;
import wang.yobbo.common.appengine.entity.AbstractEntity;
import wang.yobbo.common.appengine.plugin.EntityUtils;
import wang.yobbo.common.appengine.plugin.LogicDeleteable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by xiaoyang on 2017/12/28.
 * 数据操作Manager
 * 该类提供一些基础数据操方法
 */
public final class BaseDaoManager {
    public static final String LOGIC_DELETE_ALL_QUERY_STRING = "update %s x set x.deleted=true where x in (?1)";
    private static final String COUNT_QUERY_STRING = "select count(x) from %s x where 1=1 ";
    private static final String FIND_QUERY_STRING = "from %s x where 1=1 ";
    private static final String DELETE_QUERY_STRING = "delete from %s x where x in (?1)";
    private static EntityManager entityManager;
    private JpaEntityInformation jpaEntityInformation;
    private String entityName;
    private String IDName;
    private Class<?> entityClass;
    private String countAllJPQLL;
    private String findAllJPQL;
    /**
     * 实例化是，假如clazz是BaseDaoImpl，则不给entityClass赋值
     * @param clazz
     */
    public BaseDaoManager(Class<?> clazz) {
        if(!StringUtils.equals(clazz.getName(), BaseDaoImpl.class.getName())){
            this.entityClass = clazz;
            //新版Spring jpa可能把JpaEntityInformationSupport.getEntityInformation替换成JpaEntityInformationSupport.getMetadata了。
            this.jpaEntityInformation = JpaEntityInformationSupport.getMetadata(entityClass, entityManager);
            this.entityName = this.jpaEntityInformation.getEntityName(); //获取实体名称
            this.IDName = (String)this.jpaEntityInformation.getIdAttributeNames().iterator().next(); //获取主键ID名称
            this.countAllJPQLL = String.format(COUNT_QUERY_STRING, this.entityName);
            this.findAllJPQL = String.format(FIND_QUERY_STRING , this.entityName);
        }
    }
    //spring set static bean
    public static void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        entityManager = SharedEntityManagerCreator.createSharedEntityManager(entityManagerFactory);
    }

    @org.jetbrains.annotations.Contract(pure = true)
    public EntityManager getEntityManager() {
        Assert.notNull(entityManager, "entityManager must not null, please see " +
                "[wang.yobbo.common.appengine.BaseDaoManager#setEntityManagerFactory]");
        return entityManager;
    }

    @Nullable
    @Contract(pure = true)
    public <T>List<T> findAll(T t){
        return null;
    }

    /**
     * 保存
     * @param entity
     * @param <T>
     * @return
     */
    @Contract("null -> null")
    public <T extends AbstractEntity> T save(T entity) {
        if (entity == null) {
            return null;
        } else {
            this.dataCleaning(entity); //清空主键ID中有空格情况
            if (entity.getId() == null) {
                entityManager.persist(entity); // 新增
            } else {
                entity = (T) entityManager.merge(entity); //更新
            }
            return entity;
        }
    }

    public <T extends AbstractEntity> T find(Class<T> clazz, Serializable id) {
        if (id == null) {
            return null;
        } else {
            return id instanceof Number && ((Number)id).intValue() == 0 ? null : this.entityManager.find(clazz, id);
        }
    }

    private <T extends AbstractEntity> void dataCleaning(T entity) {
        //不为null，是空字符串情况
        if (entity.getId() != null && entity.getId() instanceof String && StringUtils.isBlank((String)entity.getId())) {
            entity.setId(null);
        }
        Field[] fields = EntityUtils.getClassFields(entity.getClass(), false);
        if (fields != null && fields.length > 0) {
            Field[] var3 = fields;
            int var4 = fields.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Field field = var3[var5];
                Class<?> fieldClazz = field.getType();
                if (AbstractEntity.class.isAssignableFrom(fieldClazz)) {
                    AbstractEntity result = EntityUtils.getSimpleFieldVavlue(entity, field);
                    if (result != null) {
                        if (result.getId() != null) {
                            if (StringUtils.isBlank(result.getId().toString())) {
                                result = null;
                                EntityUtils.invokeSetMethod(entity, field.getName(), new Class[]{fieldClazz}, new Object[]{result});
                            }
                        } else {
                            result = null;
                            EntityUtils.invokeSetMethod(entity, field.getName(), new Class[]{fieldClazz}, new Object[]{result});
                        }
                    }
                }
            }
        }

    }

    /**
     * 根据实体删除数据
     * @param entity
     * @param <T>
     */
    public <T extends AbstractEntity> void delete(T entity) {
        if (entity != null) {
            try{
                entityManager.remove(entityManager.merge(entity));
            }catch (RuntimeException  e){
                throw e;
            }
        }
    }

    /**
     * 根据主键ID数组批量删除
     * @param ids
     * @param <T>
     * @return
     */
    public <T extends AbstractEntity> int delete(Serializable[] ids){
        if (ArrayUtils.isEmpty(ids)) {
            return 0;
        }
        List<AbstractEntity> models = new ArrayList();
        Serializable[] var3 = ids;
        int var4 = ids.length;
        for(int var5 = 0; var5 < var4; ++var5) {
            Serializable id = var3[var5];
            AbstractEntity model = null;
            try {
                model = (AbstractEntity)this.entityClass.newInstance();
            } catch (Exception var10) {
                throw new RuntimeException("batch delete " + this.entityClass + " error", var10);
            }
            try {
                BeanUtils.setProperty(model, this.IDName, id);
            } catch (Exception var9) {
                throw new RuntimeException("batch delete " + this.entityClass + " error, can not set id", var9);
            }
            models.add(model);
        }
        return this.deleteInBatch(models);
    }

    private <T extends AbstractEntity> int deleteInBatch(Iterable<T> entities) {
        Iterator<T> iter = entities.iterator();
        int count = 0;
        if (entities != null && iter.hasNext()) {
            Set models = Sets.newHashSet(iter);
            boolean logicDeleteableEntity = LogicDeleteable.class.isAssignableFrom(this.entityClass);
            String xql;
            if (logicDeleteableEntity) {
                xql = String.format(LOGIC_DELETE_ALL_QUERY_STRING, this.entityName);
                count += this.batchUpdate(xql, models);
            } else {
                xql = String.format(DELETE_QUERY_STRING, this.entityName);
                count += this.batchUpdate(xql, models);
            }
        }
        return count;
    }

    /**
     * 执行更新
     * @param xql
     * @param params
     * @return
     */
    private int batchUpdate(String xql, Object... params) {
        Query query = entityManager.createQuery(xql);
        this.setParameter(query, params);
        return query.executeUpdate();
    }

    /**
     * 查询所有结果集
     * @param <T> 实体
     * @return 结果集
     */
    public <T>List<T> findAll(){
        Assert.notNull(this.entityClass, "Entity must not null, please set Entity.");
        return this.findAll(this.findAllJPQL, new Object[0]);
    }

    private <T> List<T> findAll(String hql, Object... params) {
        return this.findAll(hql, (Pageable)null, params);
    }

    private  <T> List<T> findAll(String hql, Pageable pageable, Object... params) {
        Query query = entityManager.createQuery(hql + this.prepareOrder(pageable != null ? pageable.getSort() : null));
        this.setParameter(query, params);
        if(pageable != null) {
            query.setFirstResult(pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());
        }
        return query.getResultList();
    }

    @NotNull
    private String prepareOrder(Sort sort) {
        if(sort != null && sort.iterator().hasNext()) {
            StringBuilder orderBy = new StringBuilder("");
            orderBy.append(" order by ");
            orderBy.append(sort.toString().replace(":", " "));
            return orderBy.toString();
        } else {
            return "";
        }
    }

    /**
     * 获取实体结果集
     * @param searchable
     * @return
     */
    public long count(Searchable searchable) {
        return this.count(this.countAllJPQLL, searchable);
    }

    private long count(String nxql, Searchable searchable) {
//        this.assertConverted(searchable);
        StringBuilder s = new StringBuilder(nxql);
//        searchCallback.prepareNXQL(s, searchable);
        Query query = this.getEntityManager().createQuery(s.toString());
//        searchCallback.setValues(query, searchable);
        return (Long) query.getSingleResult();
    }

    /**
     * 设置sql参数公共处理方法
     * @param query
     * @param var0
     */
    public void setParameter(Query query, Object ... var0){
        if(query.getParameters().size() != var0.length) {
            throw new RuntimeException("参数个数与设值个数不相等。");
        }
        for(int i=0;i<var0.length;i++){
            int index = i + 1;
            Object param = var0[i];
            if(param instanceof Date){
                query.setParameter(index, (Date) param, TemporalType.DATE); //转换成日期格式Date.from(Instant.parse(param.toString()))
            }
            else if(param instanceof Calendar){
                query.setParameter(index, (Calendar)param, TemporalType.DATE);
            }
            else{
                query.setParameter(index, param);
            }
        }
    }

}
