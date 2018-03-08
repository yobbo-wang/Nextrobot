package ${engine.packageName}.${engine.businessClassification}.service.Impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import ${engine.packageName}.common.appengine.entity.Searchable;
import ${engine.packageName}.${engine.businessClassification}.dao.${engine.entityName ? cap_first}Dao;
import ${engine.packageName}.${engine.businessClassification}.entity.${engine.entityName ? cap_first};
import ${engine.packageName}.${engine.businessClassification}.service.${engine.entityName ? cap_first}Service;


import java.util.List;

/**
* 此文件由应用引擎生成，严禁修改，如重复生成时，该文件将被覆盖。
* ${engine.entityName ? cap_first}ServiceImpl
* <#if engine.remark ? exists>${engine.remark}</#if>服务实现类
* @author 应用引擎自动生成
* @date ${nowDate}
*
*/
@Service("${engine.entityName ? uncap_first}Service")
public class ${engine.entityName ? cap_first}ServiceImpl implements ${engine.entityName ? cap_first}Service{
    @Autowired
    private ${engine.entityName ? cap_first}Dao ${engine.entityName ? uncap_first}Dao;

    public ${engine.entityName ? cap_first} save(${engine.entityName ? cap_first} ${engine.entityName ? uncap_first}) {
        return this.${engine.entityName ? uncap_first}Dao.save(${engine.entityName ? uncap_first});
    }

    public ${engine.entityName ? cap_first} findById(String id) {
        return this.${engine.entityName ? uncap_first}Dao.findById(id);
    }

    public int delete(String... ids) {
        return this.${engine.entityName ? uncap_first}Dao.delete(ids);
    }

    public void delete(${engine.entityName ? cap_first} ${engine.entityName ? uncap_first}) {
        this.${engine.entityName ? uncap_first}Dao.delete(${engine.entityName ? uncap_first});
    }

    public Long count(Searchable searchable) {
        return this.${engine.entityName ? uncap_first}Dao.count(searchable);
    }

    public long count() {
        return this.${engine.entityName ? uncap_first}Dao.count();
    }

    public List<${engine.entityName ? cap_first}> findPageWithoutCount(Searchable searchable) {
        return this.${engine.entityName ? uncap_first}Dao.findPageWithoutCount(searchable);
    }

    public List<${engine.entityName ? cap_first}> findAll() {
        return this.${engine.entityName ? uncap_first}Dao.findAll();
    }

    public List<${engine.entityName ? cap_first}> findAll(${engine.entityName ? cap_first} ${engine.entityName ? uncap_first}) {
        return this.${engine.entityName ? uncap_first}Dao.findAll(${engine.entityName ? uncap_first});
    }

    public Page<${engine.entityName ? cap_first}> findPage(Searchable searchable) {
        return this.${engine.entityName ? uncap_first}Dao.findPage(searchable);
    }

}
