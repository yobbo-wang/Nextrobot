package ${engine.packageName}.${engine.businessClassification}.dao.Impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ${engine.packageName}.common.appengine.entity.Searchable;
import ${engine.packageName}.common.appengine.dao.Impl.BaseDaoImpl;
import ${engine.packageName}.${engine.businessClassification}.dao.${engine.entityName ? cap_first}Dao;
import ${engine.packageName}.${engine.businessClassification}.entity.${engine.entityName ? cap_first};

import java.util.List;
/**
* 此文件由应用引擎生成，严禁修改，如重复生成时，该文件将被覆盖。
* ${engine.entityName ? cap_first}DaoImpl
* <#if engine.remark ? exists>${engine.remark}</#if>数据访问实现类
* @author 应用引擎自动生成
* @date ${nowDate}
*
*/
@Component
public class ${engine.entityName ? cap_first}DaoImpl extends BaseDaoImpl<${engine.entityName ? cap_first}, String> implements ${engine.entityName ? cap_first}Dao{

    public ${engine.entityName ? cap_first} save(${engine.entityName ? cap_first} ${engine.entityName ? uncap_first}) throws Exception{
        return super.save(${engine.entityName ? uncap_first});
    }

    public ${engine.entityName ? cap_first} findById(String id) {
        return super.get(id);
    }

    public int delete(String... ids) {
        return super.delete(ids);
    }

    public void delete(${engine.entityName ? cap_first} ${engine.entityName ? uncap_first}) {
        super.delete(${engine.entityName ? uncap_first});
    }

    public Long count(Searchable searchable) {
        return super.count(searchable);
    }

    public long count() {
        return super.count();
    }

    public List<${engine.entityName ? cap_first}> findPageWithoutCount(Searchable searchable) {
        return super.findPageWithoutCount(searchable);
    }

    public List<${engine.entityName ? cap_first}> findAll() {
        return super.findAll();
    }

    public List<${engine.entityName ? cap_first}> findAll(${engine.entityName ? cap_first} ${engine.entityName ? uncap_first}) {
        return super.findAll(${engine.entityName ? uncap_first});
    }

    public Page<${engine.entityName ? cap_first}> findPage(Searchable searchable) {
        return super.findPage(searchable);
    }

}