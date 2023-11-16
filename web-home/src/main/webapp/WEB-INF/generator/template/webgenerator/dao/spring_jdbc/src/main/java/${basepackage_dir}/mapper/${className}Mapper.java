<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>  
package ${basepackage}.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;
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
	
}
