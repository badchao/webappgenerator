package com.duowan.generator.common.util;

import cn.org.rapid_framework.generator.provider.db.table.model.Column;
import cn.org.rapid_framework.generator.util.typemapping.DatabaseDataTypesUtils;
import freemarker.template.TemplateModel;

public class GeneratorColumnUtil{

	/** 得到rapid validation的验证表达式 */
	public static String getJqueryValidation(Column c) {
		
		String result = "";
		if(!c.isNullable()) {
			result += "required='true' ";
		}
		if(c.getSqlName().indexOf("mail") >= 0) {
			result += "email='true' ";
		}
		if(c.getSqlName().indexOf("url") >= 0) {
			result += "url='true' ";
		}
		if(DatabaseDataTypesUtils.isFloatNumber(c.getJavaType())) {
			result += "number='true' ";
			result += "min='0' ";
		}
		if(DatabaseDataTypesUtils.isIntegerNumber(c.getJavaType())) {
			result += "digits='true' min='0' ";
			if(c.getJavaType().toLowerCase().indexOf("short") >= 0) {
				result += "max='"+Short.MAX_VALUE+"' ";
			}else if(c.getJavaType().toLowerCase().indexOf("byte") >= 0) {
				result += "max='"+Byte.MAX_VALUE+"' ";
			}
		}
		return result;
	}
	
}
