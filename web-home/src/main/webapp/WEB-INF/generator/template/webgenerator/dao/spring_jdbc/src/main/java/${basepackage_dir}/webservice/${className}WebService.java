<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.webservice;


import ${basepackage}.model.*;
import ${basepackage}.query.*;

import com.github.rapid.common.util.page.Page;
import java.util.Date;

/**
 * [${className}] 的WebService接口
 * 
 * @author ${author}
 * @version 1.0
 * @since 1.0
 */
public interface ${className}WebService {

	/** 
	 * 创建${className}
	 **/
	public void create(${className} ${classNameLower});
	
	/** 
	 * 更新${className}
	 **/	
    public void update(${className} ${classNameLower});
    
	/** 
	 * 删除${className}
	 **/
    public void removeById(<@generateArguments table.pkColumns/>);
    
	/** 
	 * 根据ID得到${className}
	 **/    
    public ${className} getById(<@generateArguments table.pkColumns/>);
    
	/** 
	 * 分页查询: ${className}
	 **/      
	public Page<${className}> findPage(${className}Query query);
	
    
}
