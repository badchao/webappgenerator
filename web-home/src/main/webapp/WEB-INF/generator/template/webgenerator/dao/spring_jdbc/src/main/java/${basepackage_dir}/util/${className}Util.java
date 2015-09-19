<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>   
package ${basepackage}.util;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.github.rapid.common.beanutils.BeanUtils;

<#include "/java_imports.include">


/**
 * 
<#include "/java_description.include">
*/
public class ${className}Util {
	
	public static List<${className}> to${className}List(List<Map> rows) {
		List<${className}> result = new ArrayList<${className}>();
		for(Map row : rows) {
			if(MapUtils.isEmpty(row))
				continue;
			
			${className} item = new ${className}();
			BeanUtils.copyProperties(item, row);
			result.add(item);
		}
		return result;
	}
	
}
