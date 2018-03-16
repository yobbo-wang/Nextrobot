package ${engine.packageName}.${engine.businessClassification}.entity;

import ${engine.packageName}.common.appengine.entity.BaseEntity;
import javax.persistence.*;
/**
 * 此文件由应用引擎生成，严禁修改，如重复生成时，该文件将被覆盖。
 * ${engine.entityName? cap_first} 实体信息
 * <#if engine.remark ? exists>${engine.remark}<#else>实体</#if>
 * @author 应用引擎自动生成
 * @date ${nowDate}
 *
 */
@Entity
@Table(name = "${engine.tableName ? upper_case}")
public class ${engine.entityName ? cap_first} extends BaseEntity<String> {
	private static final long serialVersionUID = 1L;
<#list fieldList as field><#if field.remarks ? exists>//${field.remarks}</#if>
    <#if field.type_name == 'Boolean' || field.type_name == 'boolean'>
    @Column(name = "${field.column_name ? upper_case}"<#if field.column_size ? exists>,length = ${field.column_size} </#if><#if field.is_null_able ? exists && field.is_null_able == "NO">,nullable = false</#if>)
    @org.hibernate.annotations.Type(type = "yes_no")
    private ${field.type_name} ${field.column_name} = false;
    <#elseif field.masterSlaveType ? exists>
    @${field.masterSlaveType ? split('_')[0]}(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "${field.column_name ? upper_case}_${field.masterSlaveType ? split('_')[2] ? upper_case}_ID") //通过主键关联
    private ${field.type_name} ${field.column_name};
    <#else>
    @Column(name = "${field.column_name ? upper_case}"<#if field.column_size ? exists>,length = ${field.column_size} </#if><#if field.primary_key ? exists && field.primary_key == "YES">,unique = true</#if><#if field.is_null_able ? exists && field.is_null_able == "NO">,nullable = false</#if>)
    private ${field.type_name} ${field.column_name};
        </#if>
</#list>

<#list fieldList as field>
    public ${field.type_name} get${field.column_name ? cap_first}(){
		return ${field.column_name};
	}

	public void set${field.column_name ? cap_first}(${field.type_name} ${field.column_name}){
		this.${field.column_name} = ${field.column_name};
	}

</#list>
}