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

/**
 * tableName: ${table.sqlName}
 * [${table.tableAlias}] 的Dao操作
 * 
<#include "/java_description.include">
*/
public interface ${className}Mapper {
	
	@Insert({
        "insert into user_group (id, name, ",
        "parent_id, create_by, ",
        "update_by, create_time, ",
        "update_time, deleted, ",
        "enabled)",
        "values (#{id,jdbcType=BIGINT}, #{name,jdbcType=VARCHAR}, ",
        "#{parentId,jdbcType=BIGINT}, #{createBy,jdbcType=BIGINT}, ",
        "#{updateBy,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{deleted,jdbcType=BIT}, ",
        "#{enabled,jdbcType=BIT})"
    })
	public void insert(${className} entity);
	
	@Update({
        "update user_group",
        "set name = #{name,jdbcType=VARCHAR},",
          "parent_id = #{parentId,jdbcType=BIGINT},",
          "create_by = #{createBy,jdbcType=BIGINT},",
          "update_by = #{updateBy,jdbcType=BIGINT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "deleted = #{deleted,jdbcType=BIT},",
          "enabled = #{enabled,jdbcType=BIT}",
        "where id = #{id,jdbcType=BIGINT}"
    })
	public int update(${className} entity);

	@Delete({
        "delete from user_group",
        "where id = #{id,jdbcType=BIGINT}"
    })
	public int deleteById(${className} entity);
	
	@Select({
        "select",
        "id, name, parent_id, create_by, update_by, create_time, update_time, deleted, ",
        "from user_group",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("${basepackage}.UserGroupMapper.BaseResultMap")
	public ${className} getById(${className} entity);
	
	<#list table.columns as column>
	<#if column.unique && !column.pk>
	public ${className} getBy${column.columnName}(${column.primitiveJavaType} ${column.columnNameFirstLower});
	
	</#if>
	</#list>

	public Page<${className}> findPage(${className}Query query);	
	
	public List<${className}> findList(${className}Query query);
	
}
