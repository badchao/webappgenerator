<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>   
package ${basepackage}.dao.impl;

import $
import lightspeed.model.User;
import ${basepackage}.query.*;
import ${basepackage}.dao.${className}Dao;















import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Date;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.github.rapid.common.util.page.Page;
import com.github.rapid.common.util.MapUtil;
import com.github.rapid.common.util.ObjectUtil;
import com.github.rapid.common.jdbc.sqlgenerator.CacheSqlGenerator;
import com.github.rapid.common.jdbc.sqlgenerator.SpringNamedSqlGenerator;
import com.github.rapid.common.jdbc.sqlgenerator.metadata.MetadataCreateUtils;
import com.github.rapid.common.jdbc.sqlgenerator.metadata.Table;

/**
 * tableName: ${table.sqlName}
 * [${table.tableAlias}] 的Dao操作 
 *  
<#include "/java_description.include">
*/
@Repository("${classNameLower}Dao")
@CacheConfig(cacheNames="${className?lower_case}")
public class ${className}DaoImpl extends BaseDao implements ${className}Dao{

	private static final Logger logger = LoggerFactory.getLogger(${className}DaoImpl.class);
	private static final String CACHEKEY_PK = "<@generateCacheArguments 'entity.' table.pkColumns/>";
	
	@Resource(name="${projectId}DataSource")
	private DataSource dataSource;
	
	/*
	* 请删除无用的方法，本代码生成器的理念是: 1. 一次生成，后期手工修改代码 2. 删除代码比手写重复代码快捷，所以请删除无用代码
	*/
	
	private RowMapper<${className}> entityRowMapper = new BeanPropertyRowMapper<${className}>(getEntityClass());
	
	private CacheSqlGenerator sqlGenerator = null; //增删改查sql生成工具
	private Table table;
	protected String selectFromSql = null; // SQL: select age,sex from demo_table  
	
	@Override
	protected void checkDaoConfig() {
		setDataSource(dataSource);
		super.checkDaoConfig();
		
		table = MetadataCreateUtils.createTable(getEntityClass());
		sqlGenerator = new CacheSqlGenerator(new SpringNamedSqlGenerator(table));
		selectFromSql = "SELECT " + sqlGenerator.getColumnsSql() + " FROM " + table.getTableName() + " ";
	}
	
	@Override
	public Class<${className}> getEntityClass() {
		return ${className}.class;
	}
	
	@Override
	public String getIdentifierPropertyName() {
		<#if (table.pkCount >= 1)>
		return "${table.pkColumn.columnNameLower}";
		<#else>
		return null;
		</#if>
	}
	
	public RowMapper<${className}> getEntityRowMapper() {
		return entityRowMapper;
	}
	
	public void insert(${className} entity) {
		String sql = sqlGenerator.getInsertSql();
		insertWithGeneratedKey(entity,sql); //for sqlserver:identity and mysql:auto_increment
		
		//其它主键生成策略
		//insertWithOracleSequence(entity,"sequenceName",sql); //oracle sequence: 
		//insertWithDB2Sequence(entity,"sequenceName",sql); //db2 sequence:
		//insertWithUUID(entity,sql); //uuid
		//insertWithAssigned(entity,sql); //手工分配
	}
	
	@CacheEvict(key = CACHEKEY_PK)
	public int update(${className} entity) {
		String sql = sqlGenerator.getUpdateByPkSql();
		return getExtNamedJdbcTemplate().update(sql, entity);
	}
	
	@CacheEvict(key = CACHEKEY_PK)
	public int deleteById(${className} entity) {
		String sql = sqlGenerator.getDeleteByPkSql();
		return  getExtNamedJdbcTemplate().update(sql,entity);
	}

	@Cacheable(key = CACHEKEY_PK)
	public ${className} getById(${className} entity) {
		String sql = sqlGenerator.getSelectByPkSql();
		return getExtNamedJdbcTemplate().queryOne(sql, entity,getEntityRowMapper());
	}
	
	<#list table.columns as column>
	<#if column.unique && !column.pk>
	public ${className} getBy${column.columnName}(${column.primitiveJavaType} ${column.columnNameFirstLower}) {
		String sql =  selectFromSql + " where ${column.sqlName}=?";
		return (${className})DataAccessUtils.singleResult(getJdbcTemplate().query(sql, getEntityRowMapper(), ${column.columnNameFirstLower}));
	}	
	
	</#if>
	</#list>

	public Page<${className}> findPage(${className}Query query) {
		
		StringBuilder sql = getQuerySql(query);
		
        //sql.append(" order by :sortColumns ");
		
		return pageQuery(sql.toString(),query,getEntityRowMapper());				
	}
	
	public List<${className}> findList(${className}Query query) {
		StringBuilder sql = getQuerySql(query);
		return getExtNamedJdbcTemplate().query(sql.toString(),query,getEntityRowMapper());
	}
	
	public StringBuilder getQuerySql(${className}Query query) {
		StringBuilder sql = new StringBuilder(selectFromSql + " where 1=1 ");
		
		//大表容易有性能问题，小表才能使用动态查询
		
		/*
		<#list table.columns as column>
		<#if column.isDateTimeColumn>
		if(ObjectUtil.isNotEmpty(query.get${column.columnName}Begin())) {
			sql.append(" and ${column.sqlName} >= :${column.columnNameLower}Begin ");
		}
		if(ObjectUtil.isNotEmpty(query.get${column.columnName}End())) {
			sql.append(" and ${column.sqlName} <= :${column.columnNameLower}End ");
		}
		<#else>
		if(ObjectUtil.isNotEmpty(query.get${column.columnName}())) {
			sql.append(" and ${column.sqlName} = :${column.columnNameLower} ");
		}
		</#if>
		</#list>
		*/
		
		return sql;
	}
	
	private ${className} queryOneByWhereEq(String column,String columnValue) {
		if(StringUtils.isBlank(columnValue)) return null;
		
		String sql = selectFromSql + " where  " + column + " = :" + column;
		Map paramMap = MapUtil.newMap(column,columnValue);
		return getExtNamedJdbcTemplate().queryOne(sql, paramMap ,getEntityRowMapper());
	}
	
}


<#macro generateCacheArguments prefix columns>
<#compress>
<#list columns as column>#${prefix}${column.columnNameFirstLower}<#if column_has_next>+'/'+</#if></#list>
</#compress>
</#macro>
