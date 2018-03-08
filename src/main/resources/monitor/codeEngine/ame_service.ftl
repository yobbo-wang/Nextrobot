package ${engine.packageName}.${engine.businessClassification}.service;

import org.springframework.data.domain.Page;
import ${engine.packageName}.common.appengine.entity.Searchable;
import ${engine.packageName}.${engine.businessClassification}.entity.${engine.entityName ? cap_first};
import java.util.List;

/**
* 此文件由应用引擎生成，严禁修改，如重复生成时，该文件将被覆盖。
* ${engine.entityName ? cap_first}Service
*<#if engine.remark ? exists>${engine.remark}</#if>服务接口类
* @author 应用引擎自动生成
* @date ${nowDate}
*
*/
public interface ${engine.entityName ? cap_first}Service {

    /**功能：插入对象或更新对象
    * @param ${engine.entityName?uncap_first}
    * @return ${engine.entityName ? cap_first} 返回 插入数据库后结果对象
    */
    ${engine.entityName ? cap_first} save(${engine.entityName ? cap_first} ${engine.entityName ? uncap_first});

    /**功能：根据主键id查询对象
    * @param id 主键id
    * @return ${engine.entityName ? cap_first} 返回 对象
    */
    ${engine.entityName ? cap_first} findById(String id);

    /**
    * 功能：根据实体id，批量删除
    * @param ids 主键ID数组
    * @return int 返回 删除记录数
    */
    int delete(String ...ids);

    /**
    * 功能：根据实体对象删除
    * @param ${engine.entityName ? uncap_first} 实体对象
    */
    void delete(${engine.entityName ? cap_first} ${engine.entityName ? uncap_first});

    /**
    * 功能：分页、排序、自定义条件查询结果集记录数
    * @param searchable 查询条件对象
    * @return Long 返回 结果集记录数
    */
    Long count(Searchable searchable);

    /**
    * 功能：查询对象所有记录数
    * @return Long 返回 结果集记录数
    */
    long count();

    /**
    * 功能：按分页、排序、自定义条件查询，返回不带分页信息的结果集
    * @param searchable 查询条件对象
    * @return List<${engine.entityName ? cap_first}> 返回 所有结果集
    */
    List<${engine.entityName ? cap_first}> findPageWithoutCount(Searchable searchable);

    /**
    * 功能：查询所有结果集
    * @return List<${engine.entityName ? cap_first}> 返回 所有结果集
    */
    List<${engine.entityName ? cap_first}> findAll();

    /**
    * 功能：查询所有结果集
    * @param ${engine.entityName ? uncap_first} 实体对象
    * @return List<${engine.entityName ? cap_first}> 返回 所有结果集
    */
    List<${engine.entityName ? cap_first}> findAll(${engine.entityName ? cap_first} ${engine.entityName ? uncap_first});

    /**功能：分页、排序、自定义条件查询
    * @param searchable 查询条件对象
    * @return Page<${engine.entityName ? cap_first}> 返回带有分页结果集
    */
    Page<${engine.entityName ? cap_first}> findPage(Searchable searchable);

}