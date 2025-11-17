<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.query.${namespace};

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

import com.modo.cloud.query.BaseQuery;

/**
 * [${table.tableAlias}] 查询对象
 * 
<#include "/java_description.include">
 */
@ApiModel(value="${className}Query-${table.tableAlias}-查询对象", description = "")
@Data
public class ${className}Query extends BaseQuery implements Serializable {
	
    private static final long serialVersionUID = 1;
    
	<@generateFields/>
	
	public ${className}Query() {
		super();
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
	public boolean equals(Object other) {
		return EqualsBuilder.reflectionEquals(this, other);
	}
	
}

<#macro generateFields>

	<#list table.columns as column>
	<#if column.isDateTimeColumn && !column.contains("begin,start,end")>
	private ${column.javaType} ${column.columnNameLower}Begin;
	private ${column.javaType} ${column.columnNameLower}End;
	<#else>
	@ApiModelProperty(name = "${column.columnAlias!}${column.pk?string(' - 主键ID','')}",notes = "")
	private ${column.javaType} ${column.columnNameLower};
	</#if>
	
	</#list>

</#macro>

<#macro generateProperties>
	<#list table.columns as column>
	/** ${column.columnAlias} */
	<#if column.isDateTimeColumn && !column.contains("begin,start,end")>
	public ${column.javaType} get${column.columnName}Begin() {
		return this.${column.columnNameLower}Begin;
	}
	
	public void set${column.columnName}Begin(${column.javaType} value) {
		this.${column.columnNameLower}Begin = value;
	}	
	
	public ${column.javaType} get${column.columnName}End() {
		return this.${column.columnNameLower}End;
	}
	
	public void set${column.columnName}End(${column.javaType} value) {
		this.${column.columnNameLower}End = value;
	}
	
	<#else>
	public ${column.javaType} get${column.columnName}() {
		return this.${column.columnNameLower};
	}
	
	public void set${column.columnName}(${column.javaType} ${column.columnNameLower}) {
		this.${column.columnNameLower} = ${column.columnNameLower};
	}
	
	</#if>	
	</#list>
</#macro>



