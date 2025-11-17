<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>   
package ${basepackage}.dao.impl;

import ${basepackage}.entity.${className};
import ${basepackage}.query.${className}Query;
import com.modo.cloud.beetl.mapper.MyMapper;
import org.beetl.sql.mapper.annotation.Param;
import org.beetl.sql.mapper.annotation.SqlResource;
import org.springframework.stereotype.Repository;

import com.znyx.core.beetl.MyMapper;
import org.beetl.sql.core.annotatoin.SqlResource;
import org.beetl.sql.core.engine.PageQuery;


/**
 * tableName: ${table.sqlName}
 * [${table.tableAlias}] 的Dao操作 
 *  
<#include "/java_description.include">
*/
@Repository
@SqlResource("${classNameLower}")
public interface ${className}Dao extends MyMapper<${className}>{
	
	  /**
	   * 标准的分页查询,根据query动态where条件
	   */
	  PageQuery<${className}> queryPage(
	          Integer pageNo,
	          Integer pageSize,
	          ${className}Query query);
	  
}