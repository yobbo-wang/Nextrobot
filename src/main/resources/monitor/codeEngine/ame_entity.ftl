package wang.yobbo.${engine.businessClassification}.entity;

import wang.yobbo.common.appengine.entity.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.Table;
/**
 * 此文件由应用引擎生成，严禁修改，如重复生成时，该文件将被覆盖。
 * ${engine.entityName?cap_first} 实体信息
 * <#if engine.remark ??>${engine.remark}<#else>实体</#if>
 * @author 应用引擎自动生成
 * @date ${nowDate}
 *
 */
@Entity
@Table(name = "${engine.tableName}")
public class ${engine.entityName?cap_first} extends BaseEntity<String> {
	private static final long serialVersionUID = 1L;

<#list fieldList as field>

</#list>

    public boolean isNew() {
       return false;
    }
}