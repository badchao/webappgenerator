package com.duowan.generator.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlHelper {

	static Pattern TABLE_NAME_FROM_CREATE_VIEW_SQL_REGEX = Pattern.compile("(?i)create\\s+view\\s+`?(\\w+)");
	static Pattern TABLE_NAME_FROM_CREATE_TABLE_SQL_REGEX = Pattern.compile("(?i)create\\s+table\\s+`?(\\w+)");
	
	/**
	 * 根据create table,create view SQL找到表名
	 * @param sqls
	 * @return
	 */
	public static List<String> findTableOrViewNames(String sqls) {
		List<String> result = new ArrayList<String>();
		//find tableNames and generate
		Matcher m = TABLE_NAME_FROM_CREATE_TABLE_SQL_REGEX.matcher(sqls);
		while(m.find()) {
			result.add(m.group(1));
		}
		m = TABLE_NAME_FROM_CREATE_VIEW_SQL_REGEX.matcher(sqls);
		while(m.find()) {
			result.add(m.group(1));
		}
		
		return result;
	}
	
}
