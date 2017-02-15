<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.webservice.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ${basepackage}.model.*;
import ${basepackage}.query.*;

import com.github.rapid.common.util.page.Page;
import java.util.Date;

import ${basepackage}.service.${className}Service;
import ${basepackage}.webservice.${className}WebService;
import com.github.rapid.common.util.page.Page;

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

	public ${className} create(${className} ${classNameLower}) {
		return ${classNameLower}Service.create(${classNameLower});
	}

	public ${className} update(${className} ${classNameLower}) {
		return ${classNameLower}Service.update(${classNameLower});
	}

	public void removeById(<@generateArguments table.pkColumns/>) {
		${classNameLower}Service.removeById(<@generatePassingParameters table.pkColumns/>);
	}

	public ${className} getById(<@generateArguments table.pkColumns/>) {
		return ${classNameLower}Service.getById(<@generatePassingParameters table.pkColumns/>);
	}

	public Page<${className}> findPage(${className}Query query) {
		return ${classNameLower}Service.findPage(query);
	}
    
}
