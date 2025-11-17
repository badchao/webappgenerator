<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>   

package ${basepackage}.dao.${namespace}.impl;

import ${basepackage}.entity.${namespace}.${className};
import ${basepackage}.query.${namespace}.${className}Query;

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
@SqlResource("${namespace}/${classNameLower}")
public interface ${className}Dao extends MyMapper<${className}>{
	
	  /**
	   * 标准的分页查询,根据query动态where条件
	   */
	  PageQuery<${className}> queryPage(
	          Integer pageNo,
	          Integer pageSize,
	          ${className}Query query);
	  
}