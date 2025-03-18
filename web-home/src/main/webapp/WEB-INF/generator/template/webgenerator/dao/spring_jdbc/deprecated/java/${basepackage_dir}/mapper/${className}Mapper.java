<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>   
package ${basepackage}.dao;

import ${basepackage}.model.*;
import ${basepackage}.query.*;

import java.util.*;
import com.github.rapid.common.util.page.Page;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

<#assign j='#'>
/**
 * tableName: ${table.sqlName}
 * [${table.tableAlias}] 的Dao操作
 * 
<${j}include "/java_description.include">
*/
public interface ${className}Mapper {
	
	@Insert({
        "insert into user_group (id, name, ",
        "parent_id, create_by, ",
        "update_by, create_time, ",
        "update_time, deleted, ",
        "enabled)",
        "values (${j}{id,jdbcType=BIGINT}, ${j}{name,jdbcType=VARCHAR}, ",
        "${j}{parentId,jdbcType=BIGINT}, ${j}{createBy,jdbcType=BIGINT}, ",
        "${j}{updateBy,jdbcType=BIGINT}, ${j}{createTime,jdbcType=TIMESTAMP}, ",
        "${j}{updateTime,jdbcType=TIMESTAMP}, ${j}{deleted,jdbcType=BIT}, ",
        "${j}{enabled,jdbcType=BIT})"
    })
	public void insert(${className} entity);
	
	@Update({
        "update user_group",
        "set name = ${j}{name,jdbcType=VARCHAR},",
          "parent_id = ${j}{parentId,jdbcType=BIGINT},",
          "create_by = ${j}{createBy,jdbcType=BIGINT},",
          "update_by = ${j}{updateBy,jdbcType=BIGINT},",
          "create_time = ${j}{createTime,jdbcType=TIMESTAMP},",
          "update_time = ${j}{updateTime,jdbcType=TIMESTAMP},",
          "deleted = ${j}{deleted,jdbcType=BIT},",
          "enabled = ${j}{enabled,jdbcType=BIT}",
        "where id = ${j}{id,jdbcType=BIGINT}"
    })
	public int update(${className} entity);

	@Delete({
        "delete from user_group",
        "where id = ${j}{id,jdbcType=BIGINT}"
    })
	public int deleteById(${className} entity);
	
	@Select({
        "select",
        "id, name, parent_id, create_by, update_by, create_time, update_time, deleted, ",
        "from user_group",
        "where id = ${j}{id,jdbcType=BIGINT}"
    })
    @ResultMap("${basepackage}.UserGroupMapper.BaseResultMap")
	public ${className} getById(${className} entity);
	
	<#list table.columns as column>
	<#if column.unique && !column.pk>
	public ${className} getBy${column.columnName}(${column.primitiveJavaType} ${column.columnNameFirstLower});
	
	</#if>
	</#list>

	public Page<${className}> query(${className}Query query);	
	
	public List<${className}> findList(${className}Query query);
	
}
