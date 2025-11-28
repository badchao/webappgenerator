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

import ${basepackage}.service.${className}Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import ${basepackage}.model.${className};
import ${basepackage}.query.${className}Query;
import com.github.rapid.common.util.holder.BeanValidatorHolder;
import com.github.rapid.common.util.page.Page;

import ${basepackage}.model.*;
import ${basepackage}.query.*;
import ${basepackage}.mapper.*;
import ${basepackage}.service.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * [${table.tableAlias}] 的Service接口实现
 * 
<#include "/java_description.include">
 */
@Service("${classNameLower}Service")
public class ${className}ServiceImpl extends BaseService implements ${className}Service {

	protected static final Logger logger = LoggerFactory.getLogger(${className}ServiceImpl.class);
	private static final int MAX_PAGE_SIZE = 10000;
	/*
	* 请删除无用的方法，本代码生成器的理念是: 1. 一次生成，后期手工修改代码 2. 删除代码比手写重复代码快捷，所以请删除无用代码
	*/
	@Autowired
	${className}Mapper ${classNameLower}Mapper;
	
	<#list table.importedKeys.associatedTables?values as foreignKey>
	<#assign fkSqlTable = foreignKey.sqlTable>
	<#assign fkTable    = fkSqlTable.className>
	<#assign fkPojoClass = fkSqlTable.className>
	<#assign fkPojoClassVar = fkPojoClass?uncap_first>
	@Autowired
	${fkPojoClass}Service ${fkPojoClassVar}Service;
	</#list> 
	
	public void set${className}Mapper(${className}Mapper mapper) {
		this.${classNameLower}Mapper = mapper;
	}
	
    /** 检查到有错误请直接抛异常，不要使用 return errorCode的方式 */
    public void check(${className} ${classNameLower}) {
    	// Bean Validator检查,属性检查失败将抛异常
    	BeanValidatorHolder.validateWithException(${classNameLower});
    	${classNameLower}.check();
    }
    
	public void initCommonDefault(${className} item) {
		//join(item);
		//item.setSearchStr(buildSearchStr(item));
		item.initDefault();
	}
	
	/** 
	 * 创建${className}
	 **/
	@Override
	public ${className} create(${className} ${classNameLower}) {
	    Assert.notNull(${classNameLower},"'${classNameLower}' must be not null");

	    //init default value
	    
	    check(${classNameLower});
	    
	    ${classNameLower}Mapper.insert(${classNameLower});
	    return ${classNameLower};
	}

	/** 
	 * 更新${className}
	 **/
	@Override
    public ${className} update(${className} ${classNameLower}) {
        Assert.notNull(${classNameLower},"'${classNameLower}' must be not null");
        check(${classNameLower});
        
		${classNameLower}Mapper.updateBy<@mybatisJavaIdMethod/>(${classNameLower});
		
        return ${classNameLower};
    }
    
	/** 
	 * 人工手动更新${className}
	 **/
	@Override
    public ${className} updateByManual(${className} ${classNameLower}) {
        Assert.notNull(${classNameLower},"'${classNameLower}' must be not null");

		//不可以让客户端可以更新所有属性
		${className} target = getone(${classNameLower});
		BeanUtils.copyProperties(${classNameLower}, target,"createTime","creator"); //ignore some copy property
		
		return update(target);
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
	 * 删除${className}
	 **/
    @Override
    public void remove(${className} ${classNameLower}) {
        ${classNameLower}Mapper.deleteBy<@mybatisJavaIdMethod/>(${classNameLower});
    }
    
	/** 
	 * 根据ID得到${className}
	 **/
    @Override
    public ${className} getone(${className} ${classNameLower}) {
        return ${classNameLower}Mapper.selectBy<@mybatisJavaIdMethod/>(${classNameLower});
    }
    
    /** 
	 * 根据ID得到${className},找不到抛异常
	 **/
    @Override
    public ${className} getRequiredById(${className} ${classNameLower}) {
    	${className} r = getone(${classNameLower});
    	if(r == null) {
    		throw new IllegalArgumentException("required ${className} not found by id:"+${classNameLower});
    	}
    	return r;
    }
    
	/** 
	 * 分页查询
	 **/      
	@Transactional(readOnly=true)
	public Page<${className}> query(${className}Query query) {
	    Assert.notNull(query,"'query' must be not null");
	    Assert.isTrue(query.getPageSize() <= MAX_PAGE_SIZE,"query.getPageSize() too large");
	    IPage<${className}> r = ${classNameLower}Mapper.queryPage(toMybatisPage(query),query);
	    return toRapidPage(r);
	}
	
	@Transactional(readOnly=true)
	public List<${className}> findList(${className}Query query) {
		Assert.notNull(query,"'query' must be not null");
		Assert.isTrue(query.getPageSize() <= MAX_PAGE_SIZE,"query.getPageSize() too large");
		IPage<${className}> r = ${classNameLower}Mapper.queryPage(toMybatisPage(query),query);
		return r.getRecords();
	}
	
<#list table.columns as column>
	<#if column.unique && !column.pk>
	@Transactional(readOnly=true)
	public ${className} getBy${column.columnName}(${column.primitiveJavaType} ${column.columnNameFirstLower}) {
		return ${classNameLower}Mapper.getBy${column.columnName}(${column.columnNameFirstLower});
	}	
	
	</#if>
</#list>


    

}
