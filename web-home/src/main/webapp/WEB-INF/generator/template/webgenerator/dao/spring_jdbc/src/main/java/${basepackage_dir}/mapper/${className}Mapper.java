<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>  
package ${basepackage}.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import ${basepackage}.query.*;
import ${basepackage}.model.*;

/**
 * tableName: ${table.sqlName}
 * [${table.tableAlias}] 的mybatis Mapper操作
 * 
<#include "/java_description.include">
*/
@Mapper
public interface ${className}Mapper extends MppBaseMapper<${className}> {

	IPage<${className}> queryPage(IPage<?> page, @Param("query") ${className}Query query);
	

	<#list table.columns as column>
	<#if column.unique && !column.pk>
	public ${className} getBy${column.columnName}(${column.primitiveJavaType} ${column.columnNameFirstLower}) {
		LambdaQueryWrapper<${className}> query = new LambdaQueryWrapper<${className}>();
		query.eq(${className}::get${column.columnName}, ${column.columnNameFirstLower});
		return selectOne(query);	
	}	
	</#if>
	</#list>	
}
