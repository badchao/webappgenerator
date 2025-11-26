<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.entity.${namespace};

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.BeanUtils;

import java.util.*;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.beetl.sql.core.annotatoin.AssignID;
import ${basepackage}.dto.${namespace}.*;

/**
 * tableName: ${table.sqlName} [${table.tableAlias}] 
 * 
<#include "/java_description.include">
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "${className}-${table.tableAlias}",description = "")
public class ${className}  implements java.io.Serializable,Cloneable{
	private static final long serialVersionUID = 1;
	
	//date formats
	<#list table.columns as column>
	<#if column.isDateTimeColumn>
	public static final String FORMAT_${column.constantName} = "yyyy-MM-dd";
	</#if>
	</#list>
	
	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	<#list table.columns as column>
    /**
     * ${column.columnAlias!}       db_column: ${column.sqlName} 
     */
	@ApiModelProperty(name = "${column.columnAlias!}${column.pk?string(' - 主键ID','')}",notes = "")
	${column.hibernateValidatorExprssion}
	<#if column.pk>
		<#if table.pkCount = 1>
	@AssignID
		<#else>
	@AssignID	
		</#if>
	</#if>
	private ${column.javaType} ${column.columnNameLower};
	
	</#list>
	//columns END

<@generateConstructor className/>
<@generateJavaOneToMany/>
<@generateJavaManyToOne/>

	public ${table.pkColumn.javaType} id() {
		return get${table.pkColumn.columnName}();
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	public ${className}Dto toDto() {
		${className}Dto dto = new ${className}Dto();
		BeanUtils.copyProperties(this, dto, null);
		return dto;
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
		<#list table.pkColumns as column>
			.append(get${column.columnName}())
		</#list>
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj instanceof ${className} == false) return false;
		${className} other = (${className})obj;
		return new EqualsBuilder()
			<#list table.pkColumns as column>
			.append(get${column.columnName}(),other.get${column.columnName}())
			</#list>
			.isEquals();
	}
	
	public ${className} clone()  {
		try {
			return (${className})super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}
	
}

<#macro generateJavaColumns>
	<#list table.columns as column>
	/**
     * ${column.columnAlias!}
     */ 
	<#if column.pk>
	@Id
	</#if>
	public ${column.javaType} get${column.columnName}() {
		return this.${column.columnNameLower};
	}
	
	public void set${column.columnName}(${column.javaType} ${column.columnNameLower}) {
		this.${column.columnNameLower} = ${column.columnNameLower};
	}
	
	<#-- 
	public ${column.javaType} ${column.columnNameLower}() {
		return get${column.columnName}();
	}

	public ${className} ${column.columnNameLower}(${column.javaType} ${column.columnNameLower}) {
		set${column.columnName}(${column.columnNameLower});
		return this;
	}
	-->
	</#list>
</#macro>


<#macro generateJavaOneToMany>
	<#list table.exportedKeys.associatedTables?values as foreignKey>
	<#assign fkSqlTable = foreignKey.sqlTable>
	<#assign fkTable    = fkSqlTable.className>
	<#assign fkPojoClass = fkSqlTable.className>
	<#assign fkPojoClassVar = fkPojoClass?uncap_first>
	
	@JsonIgnore
	@Schema(hidden = true)
	private Set ${fkPojoClassVar}s = new HashSet(0);
	public Set<${fkPojoClass}> get${fkPojoClass}s() {
		return ${fkPojoClassVar}s;
	}	
	public void set${fkPojoClass}s(Set<${fkPojoClass}> ${fkPojoClassVar}){
		this.${fkPojoClassVar}s = ${fkPojoClassVar};
	}
	</#list>
</#macro>

<#macro generateJavaManyToOne>
	<#list table.importedKeys.associatedTables?values as foreignKey>
	<#assign fkSqlTable = foreignKey.sqlTable>
	<#assign fkTable    = fkSqlTable.className>
	<#assign fkPojoClass = fkSqlTable.className>
	<#assign fkPojoClassVar = fkPojoClass?uncap_first>
	
	private ${fkPojoClass} ${fkPojoClassVar};
	public ${fkPojoClass} get${fkPojoClass}() {
		return ${fkPojoClassVar};
	}	
	public void set${fkPojoClass}(${fkPojoClass} ${fkPojoClassVar}){
		this.${fkPojoClassVar} = ${fkPojoClassVar};
	}
	</#list>
</#macro>
