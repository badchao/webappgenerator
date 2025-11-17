<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.service.mybatis_impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


import com.modo.cloud.beetl.service.BeetlService;
import org.springframework.stereotype.Service;

import ${basepackage}.model.${namespace}.${className};
import ${basepackage}.query.${namespace}.${className}Query;
// import com.github.rapid.common.util.holder.BeanValidatorHolder;

import ${basepackage}.model.${namespace}.*;
import ${basepackage}.query.${namespace}.*;
import ${basepackage}.mapper.${namespace}.*;
import ${basepackage}.service.${namespace}.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


import com.znyx.core.utils.ZnyxUtil;
import org.beetl.sql.core.engine.PageQuery;

/**
 * [${table.tableAlias}] 的Service接口实现
 * 
<#include "/java_description.include">
 */
@Service("${classNameLower}Service")
public class ${className}ServiceImpl extends BeetSQLIService<${className}Dao,${className}>{

	protected static final Logger logger = LoggerFactory.getLogger(${className}ServiceImpl.class);
	
	@Autowired
	${className}Dao ${classNameLower}Dao;
	
	<#list table.importedKeys.associatedTables?values as foreignKey>
	<#assign fkSqlTable = foreignKey.sqlTable>
	<#assign fkTable    = fkSqlTable.className>
	<#assign fkPojoClass = fkSqlTable.className>
	<#assign fkPojoClassVar = fkPojoClass?uncap_first>
	@Autowired
	${fkPojoClass}Service ${fkPojoClassVar}Service;
	</#list> 
	
	
    /** 检查到有错误请直接抛异常，不要使用 return errorCode的方式 */
    public void check(${className} ${classNameLower}) {
    	// Bean Validator检查,属性检查失败将抛异常
    	BeanValidatorHolder.validateWithException(${classNameLower});
    }
    
	/** 
	 * 创建${className}
	 **/
	@Override
	public void insert(${className} ${classNameLower}) {
	    Assert.notNull(${classNameLower},"'${classNameLower}' must be not null");
		Assert.notNull(${classNameLower}.getCreateUserId(),"createUserId must be not blank");

		${classNameLower}.setUpdateUserId(${classNameLower}.getCreateUserId());
		${classNameLower}.setCreateDate(new Date());
		${classNameLower}.setUpdateDate(new Date());
		
	    //init default value
	    
	    check(${classNameLower});
	    
	    super.insert(${classNameLower});
	}

	/** 
	 * 更新${className}
	 **/
	@Override
    public int updateById(${className} ${classNameLower}) {
        Assert.notNull(${classNameLower},"'${classNameLower}' must be not null");
        
        ${classNameLower}.setUpdateDate(new Date());
        Assert.notNull(${classNameLower}.getUpdateUserId(),"updateUserId must be not blank");
        
        check(${classNameLower});
        
		return super.updateById(${classNameLower});
    }
    

    /**
     *  join取回${className}的关联对象,如一对多，多对一等的关联对象,如: user找到关联的 地址列表 
     */
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ${className} join(${className} ${classNameLower}) {
    	if(${classNameLower} == null) return null;
    	
    	<#list table.importedKeys.associatedTables?values as foreignKey>
    	<#assign fkSqlTable = foreignKey.sqlTable>
    	<#assign fkTable    = fkSqlTable.className>
    	<#assign fkPojoClass = fkSqlTable.className>
    	<#assign fkPojoClassVar = fkPojoClass?uncap_first>
    	//${fkPojoClass} ${fkPojoClassVar} = ${fkPojoClassVar}Service.getone(new ${fkPojoClass}(${classNameLower}.get${fkPojoClass}Id()));
    	//${classNameLower}.set${fkPojoClass}(${fkPojoClassVar});
    	</#list>    	
    	return ${classNameLower};
    }
    
    public void joinList(Collection<${className}> list) {
        if(list == null) return;
        list.stream().forEach(item -> join(item));
    }
    
	
	/** 
	 * 分页查询
	 **/      
	@Transactional(readOnly=true)
	public PageQuery<${className}> query(${className}Query query) {
	    Assert.notNull(query,"'query' must be not null");
	    PageQuery<${className}> page = ${classNameLower}Dao.queryPage(query.getPageNo(), query.getPageSize(), query);
	    joinList(page.getList());
	    return page;
	}
	

	
<#list table.columns as column>
	<#if column.unique && !column.pk>
	@Transactional(readOnly=true)
	public ${className} getBy${column.columnName}(${column.primitiveJavaType} ${column.columnNameFirstLower}) {
		return ${classNameLower}Dao.getBy${column.columnName}(${column.columnNameFirstLower});
	}	
	
	</#if>
</#list>
    

}
