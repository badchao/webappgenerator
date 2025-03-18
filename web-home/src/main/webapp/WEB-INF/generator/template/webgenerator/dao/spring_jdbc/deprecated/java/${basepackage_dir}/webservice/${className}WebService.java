<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.webservice;


import ${basepackage}.model.*;
import ${basepackage}.query.*;

import com.github.rapid.common.util.page.Page;
import com.github.rapid.common.util.page.PageQuery;
import java.util.Date;
import java.util.List;

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
    public void remove(<@generateArguments table.pkColumns/>);
    
	/** 
	 * 根据ID得到${className}
	 **/    
    public ${className} getById(boolean join,<@generateArguments table.pkColumns/>);
    
	/** 
	 * 分页查询: ${className}
	 **/      
	public Page<${className}> query(boolean join,${className}Query query);
	
	/** 
	 * 搜索: ${className}
	 **/ 
	public List<${className}> search(boolean join,String query,PageQuery pageQuery);
}
