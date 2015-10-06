<#include "/custom.include">
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameFirstLower = className?uncap_first>   
<#assign classNameLowerCase = className?lower_case>   

package ${basepackage}.controller;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.github.rapid.common.beanutils.BeanUtils;
import com.github.rapid.common.exception.MessageException;
import com.github.rapid.common.web.scope.Flash;
import com.github.rapid.common.util.CsvFileUtil;
import com.github.rapid.common.util.ValidationErrorsUtil;
import com.github.rapid.common.util.page.Page;

<#include "/java_imports.include">

/**
 * [${table.tableAlias}] 的业务操作
 * 
<#include "/java_description.include">
 *
 */
@Controller
@RequestMapping("/${classNameLowerCase}")
public class ${className}Controller {
	
	private static Logger logger = LoggerFactory.getLogger(${className}Controller.class);
	
	private ${className}Service ${classNameFirstLower}Service;
	
	private final String LIST_ACTION = "redirect:/${classNameLowerCase}/index.do";
	
	private static String CREATED_SUCCESS = "创建成功";
	private static String UPDATE_SUCCESS = "更新成功";
	private static String DELETE_SUCCESS = "删除成功";
	
	/** 
	 * 增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,注意大小写
	 **/
	public void set${className}Service(${className}Service service) {
		this.${classNameFirstLower}Service = service;
	}
	
	/** binder用于bean属性的设置 */
	@InitBinder  
	public void initBinder(WebDataBinder binder) {  
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));  
	}
	   
	/**
	 * 增加了@ModelAttribute的方法可以在本controller方法调用前执行,可以存放一些共享变量,如枚举值,或是一些初始化操作
	 */
	@ModelAttribute
	public void init(ModelMap model) {
	}
	
	/** 列表 */
	@RequestMapping
	public String index(ModelMap model,${className}Query query,HttpServletRequest request) {
		Page<${className}> page = this.${classNameFirstLower}Service.findPage(query);
		
		model.addAttribute("page",page);
		model.addAttribute("query",query);
		return "pages/${classNameLowerCase}/index";
	}
	
	/** 显示 */
	@RequestMapping
	public String show(ModelMap model,<@generateRequestParamArguments table.pkColumns/>) throws Exception {
		${className} ${classNameFirstLower} = (${className})${classNameFirstLower}Service.getById(<@generatePassingParameters table.pkColumns/>);
		model.addAttribute("${classNameFirstLower}",${classNameFirstLower});
		return "pages/${classNameLowerCase}/show";
	}

	/** 进入新增 */
	@RequestMapping
	public String add(ModelMap model,${className} ${classNameFirstLower}) throws Exception {
		model.addAttribute("${classNameFirstLower}",${classNameFirstLower});
		return "pages/${classNameLowerCase}/add";
	}
	
	/** 保存新增,@Valid标注spirng在绑定对象时自动为我们验证对象属性并存放errors在BindingResult  */
	@RequestMapping
	public String create(ModelMap model,${className} ${classNameFirstLower},BindingResult errors) throws Exception {
		try {
			${classNameFirstLower}Service.create(${classNameFirstLower});
		}catch(ConstraintViolationException e) {
			ValidationErrorsUtil.convert(e, errors);
			return  "pages/${classNameLowerCase}/add";
		}catch(MessageException e) {
			Flash.current().error(e.getMessage());
			return  "pages/${classNameLowerCase}/add";
		}
		Flash.current().success(CREATED_SUCCESS); //存放在Flash中的数据,在下一次http请求中仍然可以读取数据,error()用于显示错误消息
		return LIST_ACTION;
	}
	
	/** 编辑 */
	@RequestMapping
	public String edit(ModelMap model,<@generateRequestParamArguments table.pkColumns/>) throws Exception {
		${className} ${classNameFirstLower} = (${className})${classNameFirstLower}Service.getById(<@generatePassingParameters table.pkColumns/>);
		model.addAttribute("${classNameFirstLower}",${classNameFirstLower});
		return "pages/${classNameLowerCase}/edit";
	}
	
	/** 保存更新,@Valid标注spirng在绑定对象时自动为我们验证对象属性并存放errors在BindingResult  */
	@RequestMapping
	public String update(ModelMap model,<@generateRequestParamArguments table.pkColumns/>,${className} ${classNameFirstLower},BindingResult errors) throws Exception {
		try {
			${classNameFirstLower}Service.update(${classNameFirstLower});
		}catch(ConstraintViolationException e) {
			ValidationErrorsUtil.convert(e, errors);
			return  "pages/${classNameLowerCase}/edit";
		}catch(MessageException e) {
			Flash.current().error(e.getMessage());
			return  "pages/${classNameLowerCase}/edit";
		}
		Flash.current().success(UPDATE_SUCCESS);
		return LIST_ACTION;
	}
	
	/** 删除 */
	@RequestMapping
	public String delete(ModelMap model,<@generateRequestParamArguments table.pkColumns/>) {
		${classNameFirstLower}Service.removeById(<@generatePassingParameters table.pkColumns/>);
		Flash.current().success(DELETE_SUCCESS);
		return LIST_ACTION;
	}
	
	
	/** 上传csv文件保存  */
	@RequestMapping
	public String upload(@RequestParam("file")  CommonsMultipartFile file)  throws Exception {
		Assert.isTrue(file.getOriginalFilename().endsWith(".csv"),"只能上传.csv文件");
		
		int skipLines = 1;
		List<Map> maps = CsvFileUtil.readCsv2Maps(file.getInputStream(),"UTF-8","<#list table.notPkColumns as c>${c.columnNameFirstLower}<#if c_has_next>,</#if></#list>",skipLines);
		List<${className}> items = ${className}Util.to${className}List(maps);
		
		int successCount = 0;
		int errorCount = 0;
		for(${className} item : items) {
			try {
				${classNameFirstLower}Service.create(item);
				successCount++;
			}catch(Exception e) {
				errorCount++;
				logger.info("create_${className}_error",e);
			}
		}
		
		Flash.current().success("上传成功,创建成功条数:"+successCount+",失败条数:"+errorCount);
		return LIST_ACTION;
	}

}

<#macro generateRequestParamArguments columns>
<#compress>
<#list table.pkColumns as column> ${column.primitiveJavaType} ${column.columnNameFirstLower}<#if column_has_next>,</#if></#list>
</#compress>
</#macro>
