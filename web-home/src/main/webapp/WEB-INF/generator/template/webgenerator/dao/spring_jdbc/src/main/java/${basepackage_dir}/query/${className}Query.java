<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.query;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * [${table.tableAlias}] 查询对象
 * 
<#include "/java_description.include">
 */
@Schema(title="${className}Query-${table.tableAlias}-查询对象", description = "")
public class ${className}Query extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 1;
    
    //搜索关键字,不区分列，模糊搜索使用
    private String keyword; 
    
	<@generateFields/>
	
	public ${className}Query() {
		super();
	}

	public ${className}Query(int page, int pageSize) {
		super(page, pageSize);
	}

	public ${className}Query(int pageSize) {
		super(pageSize);
	}
	
	<@generateProperties/>
	
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
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
	@Schema(title="${column.columnAlias!}${column.pk?string(' - 主键ID','')}", description = "", example = "")
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



