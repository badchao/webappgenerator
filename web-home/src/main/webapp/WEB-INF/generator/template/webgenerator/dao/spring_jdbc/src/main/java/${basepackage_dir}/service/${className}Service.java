<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.service;

import com.github.rapid.common.util.page.Page;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import ${basepackage}.model.*;
import ${basepackage}.dao.*;
import ${basepackage}.query.*;
import java.util.Date;

/**
 * [${table.tableAlias}] 的Service接口
 * 
<#include "/java_description.include">
 */
public interface ${className}Service {

	/** 
	 * 创建${className}
	 **/
	public ${className} create(${className} ${classNameLower});
	
	/** 
	 * 更新${className}
	 **/	
    public ${className} update(${className} ${classNameLower});
    
    /**
     *  join取回${className}的关联信息,如一对多，多对一等的关联信息
     */
    public ${className} join(${className} ${classNameLower});
    
	/** 
	 * 删除${className}
	 **/
    public void removeById(<@generateArguments table.pkColumns/>);
    
	/** 
	 * 根据ID得到${className}
	 **/    
    public ${className} getById(<@generateArguments table.pkColumns/>);
    
    /** 
	 * 根据ID得到${className},找不到抛异常
	 **/ 
    public ${className} getRequiredById(<@generateArguments table.pkColumns/>);
    
	/** 
	 * 分页查询: ${className}
	 **/      
	public Page<${className}> findPage(${className}Query query);
	
<#list table.columns as column>
	<#if column.unique && !column.pk>
	/** 
	 * 根据${column.columnName}(${column.columnAlias}) 查找 ${className}
	 **/ 	
	public ${className} getBy${column.columnName}(${column.primitiveJavaType} ${column.columnNameFirstLower});
	
	</#if>
</#list>
    
}
