<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>  
package ${basepackage}.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ${basepackage}.model.*;

/**
 * tableName: ${table.sqlName}
 * [${table.tableAlias}] 的mybatis Mapper操作
 * 
<#include "/java_description.include">
*/
@Mapper
public interface ${className}Mapper extends BaseMapper<${className}> {

}
