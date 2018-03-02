package ${engine.packageName}.${engine.businessClassification}.entity;

import ${engine.packageName}.common.appengine.entity.BaseEntity;
import javax.persistence.Column;
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
public class ${engine.entityName ? cap_first} extends BaseEntity<String> {
	private static final long serialVersionUID = 1L;

<#list fieldList as field>    <#if field.REMARKS ? exists>//${field.REMARKS}</#if>
    @Column(name = "${field.COLUMN_NAME ? upper_case}"<#if field.COLUMN_SIZE ? exists>,length = ${field.COLUMN_SIZE} </#if><#if field.PRIMARYKEYS ? exists && field.PRIMARYKEYS == "YES">,unique = true</#if><#if field.IS_NULLABLE ? exists && field.PRIMARYKEYS == "NO">,nullable = false</#if>)
    private ${field.TYPE_NAME} ${field.COLUMN_NAME};
</#list>

<#list fieldList as field>
    public ${field.TYPE_NAME} get${field.COLUMN_NAME ? cap_first}(){
		return ${field.COLUMN_NAME};
	}
	public void set${field.COLUMN_NAME ? cap_first}(${field.TYPE_NAME} ${field.COLUMN_NAME}){
		this.${field.COLUMN_NAME} = ${field.COLUMN_NAME};
	}
</#list>

    public boolean isNew() {
       return false;
    }
}