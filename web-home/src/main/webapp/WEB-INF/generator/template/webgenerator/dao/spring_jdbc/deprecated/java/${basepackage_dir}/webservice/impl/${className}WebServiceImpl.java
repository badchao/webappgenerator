<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.webservice.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import ${basepackage}.model.*;
import ${basepackage}.query.*;

import com.github.rapid.common.util.page.Page;
import com.github.rapid.common.util.page.PageQuery;

import java.util.Date;
import java.util.List;

import ${basepackage}.service.${className}Service;
import ${basepackage}.webservice.${className}WebService;

import com.github.rapid.common.util.page.Page;
import com.github.rapid.common.util.page.PageQuery;

/**
 * [${className}] 的WebService接口实现
 * 
 * @author ${author}
 * @version 1.0
 * @since 1.0
 */
public class ${className}WebServiceImpl implements ${className}WebService {

	@Autowired
	private ${className}Service ${classNameLower}Service;

	public void set${className}Service(${className}Service ${classNameLower}Service) {
		this.${classNameLower}Service = ${classNameLower}Service;
	}

	@Override
	public void create(${className} ${classNameLower}) {
		${classNameLower}Service.create(${classNameLower});
	}

	@Override
	public void update(${className} ${classNameLower}) {
		${classNameLower}Service.updateByManual(${classNameLower});
	}

	@Override
	public void removeById(<@generateArguments table.pkColumns/>) {
		${classNameLower}Service.removeById(new ${className}(<@generatePassingParameters table.pkColumns/>));
	}

	@Override
	public ${className} getById(boolean join,<@generateArguments table.pkColumns/>) {
		${className} result = ${classNameLower}Service.getById(new ${className}(<@generatePassingParameters table.pkColumns/>));
		if(join) ${classNameLower}Service.join(result);
		return result;
	}

	@Override
	public Page<${className}> query(boolean join,${className}Query query) {
		Assert.isTrue(query.getPageSize() <= 1000,"query.pageSize too large");
		Page<${className}> result = ${classNameLower}Service.query(query);
		if(join) result.forEach(${classNameLower}Service::join);
		return result;
	}
	
	@Override
	public List<${className}> search(boolean join,String query,PageQuery pageQuery){
		throw new RuntimeException("not yet impl");
	}
    
}
