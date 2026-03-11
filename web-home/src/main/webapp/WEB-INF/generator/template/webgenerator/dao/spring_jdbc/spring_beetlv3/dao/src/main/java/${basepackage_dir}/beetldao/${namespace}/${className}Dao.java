<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>   

package ${basepackage}.beetldao.${namespace};

import ${basepackage}.entity.${namespace}.${className};
import ${basepackage}.query.${namespace}.${className}Query;

import org.springframework.stereotype.Repository;


import org.beetl.sql.core.engine.PageQuery; // beetl v2.0
import org.beetl.sql.core.page.PageResult; // beetl v3.0
import org.beetl.sql.core.page.PageRequest;
import org.beetl.sql.mapper.annotation.Param;
import org.beetl.sql.mapper.annotation.SqlResource;
import com.modo.cloud.beetl.mapper.MyMapper;


/**
 * tableName: ${table.sqlName}
 * [${table.tableAlias}] 的Dao操作 
 *  
<#include "/java_description.include">
*/
@Repository
@SqlResource("${namespace}.${classNameLower}")
public interface ${className}Dao extends MyMapper<${className}>{
	
	  /**
	   * 标准的分页查询,根据query动态where条件
	   */
	PageResult<${className}> queryPage(@Param("query")  ${className}Query query,PageRequest pageRequest);
	  
}