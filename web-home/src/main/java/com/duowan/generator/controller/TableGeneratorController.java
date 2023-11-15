package com.duowan.generator.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.output.TeeOutputStream;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.org.rapid_framework.generator.GeneratorConstants;
import cn.org.rapid_framework.generator.GeneratorContext;
import cn.org.rapid_framework.generator.GeneratorFacade;
import cn.org.rapid_framework.generator.util.FileHelper;

import com.duowan.generator.common.util.SqlHelper;
import com.duowan.generator.common.util.ZipHelper;
import com.github.rapid.common.util.holder.PropertiesHolder;

/**
 * 基于表的生成器
 * 
 * @author badqiu
 *
 */
@Controller
@RequestMapping("/tablegenerator")
public class TableGeneratorController {

	static ByteArrayOutputStream memoryConsole = new ByteArrayOutputStream();
	static {
		System.setOut(new PrintStream(new TeeOutputStream(System.out,(memoryConsole))));
	}
	
	static Lock lock = new ReentrantLock();
	@RequestMapping
	public void gen(GenCmdExecutor cmd,HttpServletRequest request,HttpServletResponse response) throws Exception {
		lock.lock();
		try {
			memoryConsole.reset();
			cmd.response = response;
			cmd.request = request;
			cmd.execute();
		}finally {
			lock.unlock();
		}
	}
	
//	static String dataSourceJndiName = "java:comp/env/jdbc/mydatasource";
//	public void initDataSource() throws IllegalStateException, NamingException {
//		DataSource dataSource = (DataSource)ApplicationContextHolder.getBean("dataSource");
//		SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
//		builder.bind(dataSourceJndiName, dataSource);
//	    builder.activate();
//	}
	
//	public static DataSource getDataSource() {
//		DataSource dataSource = (DataSource)ApplicationContextHolder.getBean("dataSource");
//		return dataSource;
//	}

	public static class GenCmdExecutor {
		public String sqls;
		public String basepackage;
		public String tableRemovePrefixes = "";
		public String namespace = "admin";
		public String author = "badqiu";
		public String projectId;
		
		public ServletContext context;
		public HttpServletRequest request;
		public HttpServletResponse response;
		
		private long cmdId = System.currentTimeMillis();
		private String outRoot = System.getProperty("java.io.tmpdir")+"/webgeneratoroutput/"+cmdId;
		
		// H2 database
//		private String jdbcUsername = "sa";
//		private String jdbcPassword = "sa";
//		private String jdbcDriver = "org.h2.Driver";
//		private String jdbcUrl = "jdbc:h2:mem:tmp_generator"+cmdId+";DB_CLOSE_DELAY=5";

		// mysql database: 
		// GRANT ALL PRIVILEGES ON *.* TO 'generator'@'%' IDENTIFIED BY '!2dF9~ZPo8*w' WITH GRANT OPTION;
		// CREATE DATABASE IF NOT EXISTS tmp_generator default charset=utf8;
		private String jdbcUsername = PropertiesHolder.getProperty("mysql_webgenerator.jdbc.username");
		private String jdbcPassword = PropertiesHolder.getProperty("mysql_webgenerator.jdbc.password");
		private String jdbcDriver = "com.mysql.jdbc.Driver";
		private String jdbcUrl = PropertiesHolder.getProperty("mysql_webgenerator.jdbc.url");
		
		public void setSqls(String sqls) {
			this.sqls = sqls;
		}

		public void setBasepackage(String basepackage) {
			this.basepackage = basepackage;
		}

		public void setTableRemovePrefixes(String tableRemovePrefixes) {
			this.tableRemovePrefixes = tableRemovePrefixes;
		}
		
		public void setNamespace(String namespace) {
			this.namespace = namespace;
		}

		public void setAuthor(String author) {
			this.author = author;
		}
		
		public void setProjectId(String projectId) {
			this.projectId = projectId;
		}
		
		//每一条string组成: new String[include,dir,exclude]
		String[][] multi_project_dir_layout_mappings = new String[][]{
				//model
				new String[]{"/**/main/**/query/**","model"},
				new String[]{"/**/main/**/model/**","model"},
				
				//dao
				new String[]{"/**/main/**/dao/**","dao"},
				new String[]{"/**/main/**/mapper/**","service"},
				new String[]{"/**/main/**/util/**","dao"},
				new String[]{"/**/main/resources/freemarker_sql/**","dao"},
				new String[]{"/**/test/resources/testdata/**","dao"},
				new String[]{"/**/test/**/dao/**","dao"},
				new String[]{"/**/test/**/*DataFactory.java","dao"},
				
				//service
				new String[]{"/**/test/**/service/**","service"},
				new String[]{"/**/test/**/*DataFactory.java","service"},
				new String[]{"/**/main/**/service/**","service"},
				
				//web-admin
				new String[]{"/**/main/**/controller/**","web-admin"},
				new String[]{"/**/main/**/webapp/pages/**","web-admin"},
				new String[]{"/**/main/**/webapp/js/**","web-admin"},
				new String[]{"/**/main/**/webapp/vue/**","web-admin"},
				new String[]{"/**/main/**/webapp/wx_miniprogram/**","web-admin"},
				
				//web-service
				new String[]{"/**/main/**/webservice/**/*WebService.java","webservice-api"},
				new String[]{"/**/main/**/webservice/**/impl/**","webservice-server"},
				new String[]{"/**/main/resources/webservice/**/*WebService-rpc-servlet.xml","webservice-server"},
			};
		
		//每一条string组成: new String[include,dir,exclude]
		String[][] vue_arechetype_layout = new String[][]{
				//service
				new String[]{"/**/main/**/query/**","service"},
				new String[]{"/**/main/**/model/**","service"},
				new String[]{"/**/main/**/dao/**","service"},
				new String[]{"/**/main/**/util/**","service"},
				new String[]{"/**/main/resources/freemarker_sql/**","service"},
				new String[]{"/**/test/resources/testdata/**","service"},
				new String[]{"/**/test/**/dao/**","service"},
				new String[]{"/**/test/**/*DataFactory.java","service"},
				new String[]{"/**/test/**/service/**","service"},
				new String[]{"/**/test/**/*DataFactory.java","service"},
				new String[]{"/**/main/**/service/**","service"},
				
				//admin-front
				new String[]{"/**/main/**/webapp/nodejs_vue/**","admin-front"},
				new String[]{"/**/main/**/webapp/wx_miniprogram/**","admin-front"},
				
				//admin-server
//				new String[]{"/**/main/**/webservice/**/*WebService.java","admin-server"},
//				new String[]{"/**/main/**/webservice/**/impl/**","admin-server"},
//				new String[]{"/**/main/resources/webservice/**/*WebService-rpc-servlet.xml","admin-server"},
				new String[]{"/**/main/**/admin/controller/Admin*Controller.java","admin-server"},
				
				//user-server
				//new String[]{"/**/main/**/controller/*Controller.java","user-server","/**/main/**/admin/controller/Admin*Controller.java"},
			};

			//每一条string组成: new String[include,dir,exclude]
			String[][] controler_service_diy_layout = new String[][]{
					//service
					new String[]{"/**/main/**/query/**","service"},
					new String[]{"/**/main/**/model/**","service"},
					new String[]{"/**/main/**/dao/**","service"},
					new String[]{"/**/main/**/mapper/**","service"},
					new String[]{"/**/main/**/util/**","service"},
					new String[]{"/**/main/resources/freemarker_sql/**","service"},
					new String[]{"/**/test/resources/testdata/**","service"},
					new String[]{"/**/test/**/dao/**","service"},
					new String[]{"/**/test/**/mapper/**","service"},
					new String[]{"/**/test/**/*DataFactory.java","service"},
					new String[]{"/**/test/**/service/**","service"},
					new String[]{"/**/test/**/*DataFactory.java","service"},
					new String[]{"/**/main/**/service/**","service"},
					
					//admin-front
					new String[]{"/**/main/**/webapp/nodejs_vue/**","admin-front"},
					new String[]{"/**/main/**/webapp/wx_miniprogram/**","admin-front"},
					
					//admin-server
					new String[]{"/**/main/**/admin/controller/*Controller.java","admin-server"},
					
					//user-server
					//new String[]{"/**/main/**/controller/*Controller.java","user-server","/**/main/**/admin/controller/Admin*Controller.java"},
				};
				
		private Map<String,String[][]> layoutDirMappings = new HashMap();
		{
			layoutDirMappings.put("multi_project_dir_layout",multi_project_dir_layout_mappings);
			layoutDirMappings.put("vue_arechetype_layout",vue_arechetype_layout);
			layoutDirMappings.put("controler_service_diy_layout",controler_service_diy_layout);
		}
		
		public void execute() throws Exception {
			Assert.hasText(basepackage,"'basepackage' must be not blank");
			Assert.hasText(sqls,"'sqls' must be not blank");
			
			GeneratorFacade g = null;
			try {
				
				executeSqls(sqls);
				setGeneratorProperties();
				
				g = new GeneratorFacade();
//				g.getGenerator().setTemplateRootDirs(
//						"classpath:generator/template/webgenerator/dao/spring_jdbc",
//						"classpath:generator/template/webgenerator/share/custom");
				context = request.getSession().getServletContext();
				g.getGenerator().setTemplateRootDirs(
						context.getRealPath("/WEB-INF/generator/template/webgenerator/dao/spring_jdbc"),
						context.getRealPath("/WEB-INF/generator/template/webgenerator/share/custom"));
				
				for(String tableName : SqlHelper.findTableOrViewNames(sqls)) {
					g.generateByTable(tableName);
				}
				
				
				for(Map.Entry<String, String[][]> mapping : layoutDirMappings.entrySet()) {
					copy2MutiProjectDirLayout(outRoot,mapping.getKey(),mapping.getValue());
				}
//				copy2MutiProjectDirLayout(outRoot,"multi_project_dir_layout",multi_project_dir_layout_mappings);
//				copy2MutiProjectDirLayout(outRoot,"vue_arechetype_layout",vue_arechetype_layout);
				
				
				FileUtils.writeStringToFile(new File(outRoot,"generator.log"), memoryConsole.toString());
				
				response.setHeader("Content-Disposition", "attachment; filename=\"" + "generator_table_output.zip" + "\"");
				ZipHelper.zip(outRoot,response.getOutputStream());
				
//				FileUtils.copyDirectory(new File(outRoot), new File("E:/scm/xsj/dataanalyse/web_app_report"));
			}finally {
				if(g != null) 
					g.deleteOutRootDir();
				executeSqlsNoError("DROP DATABASE IF EXISTS tmp_generator; " +
						"CREATE DATABASE IF NOT EXISTS tmp_generator default charset=utf8;");
				
				memoryConsole.reset();
			}
		}
		
		/**
		 * 将生成的文件，另外拷贝成别一套目录结构的数据
		 **/
		public void copy2MutiProjectDirLayout(String outRoot, String newDirName,String[][] layout_mappings) throws IOException {
			AntPathMatcher pathMatcher = new AntPathMatcher();
			Collection<File> files = FileUtils.listFiles(new File(outRoot), null, true);
			for(File f : files) {
				Set<String> keySet = layoutDirMappings.keySet();
				if(isContains(f, keySet)) {
					continue;
				}
				
				System.out.println("copy2MutiProjectDirLayout,file:"+f.getPath());
				
				for(String[] mapping : layout_mappings) {
					String includePattern = mapping[0];
					String targetDir = mapping[1];
					String excludePattern = mapping.length > 2 ? mapping[2] : null;
					
					boolean exclude = isAntPathMatch(pathMatcher, f,excludePattern);
					if(exclude){
						continue;
					}
					
					boolean match = isAntPathMatch(pathMatcher, f,includePattern);
					if(match) {
						System.out.println("match,file:"+f+" pattern:"+includePattern+" result:"+match);
						
						String relativePath = FileHelper.getRelativePath(new File(outRoot),f);
						FileUtils.copyFile(f, new File(outRoot,newDirName+"/"+targetDir+"/"+relativePath));
					}
				}
			}
			
		}

		private boolean isAntPathMatch(AntPathMatcher pathMatcher, File f,
				String pattern) {
			if(StringUtils.isBlank(pattern)) return false;
			
			String toMatchPath = f.getPath().replace('\\', '/').replaceFirst("[\\w]:", "");
			boolean match = pathMatcher.match(pattern, toMatchPath);
			return match;
		}

		private boolean isContains(File f, Set<String> keySet) {
			for(String dir : keySet) {
				if(f.toString().contains(dir)) {
					return true;
				}
			}
			return false;
		}

		private void executeSqlsNoError(String sqls)  {
			try {
				executeSqls(sqls);
				System.out.println("executed sqls:"+sqls);
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		private void executeSqls(String sqls) throws SQLException {
			DriverManagerDataSource ds = new DriverManagerDataSource();
			ds.setDriverClassName(jdbcDriver);
			ds.setUrl(jdbcUrl);
			ds.setUsername(jdbcUsername);
			ds.setPassword(jdbcPassword);
			
			Connection conn = ds.getConnection();
			try {
				Statement stat = conn.createStatement();
				for(String sql : sqls.split(";")) {
					if(StringUtils.isNotBlank(sql)) {
						stat.execute(sql);
					}
				}
			}finally {
				conn.close();
			}
		}

		private void setGeneratorProperties() {
			Properties props = new Properties();
			
//			props.setProperty(GeneratorConstants.DATA_SOURCE_JNDI_NAME.code, dataSourceJndiName);
			
			props.setProperty(GeneratorConstants.JDBC_DRIVER.code, jdbcDriver);
			props.setProperty(GeneratorConstants.JDBC_USERNAME.code, jdbcUsername);
			props.setProperty(GeneratorConstants.JDBC_PASSWORD.code, jdbcPassword);
			props.setProperty(GeneratorConstants.JDBC_URL.code, jdbcUrl);
			
//			props.setProperty(GeneratorConstants.JDBC_URL.code, "jdbc:h2:mem:tmp_generator;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
			
			props.setProperty("java_typemapping.java.sql.Timestamp", "java.util.Date");
			props.setProperty("java_typemapping.java.sql.Date", "java.util.Date");
			props.setProperty("java_typemapping.java.sql.Time", "java.util.Date");
			props.setProperty("java_typemapping.java.lang.Byte", "Integer");
			props.setProperty("java_typemapping.java.lang.Short", "Integer");
			props.setProperty("java_typemapping.java.math.BigDecimal", "Double");
			props.setProperty("java_typemapping.java.sql.Clob", "String");
			
			props.setProperty("java_typemapping.java.lang.Integer", "Integer");
			props.setProperty("java_typemapping.java.lang.Long", "Long");
			props.setProperty("java_typemapping.java.lang.String", "String");
			
			props.setProperty("outRoot", outRoot);
			props.setProperty("namespace", namespace);
			props.setProperty("author", author);
			props.setProperty("projectId",projectId);
			props.setProperty("basepackage",basepackage);
			props.setProperty("tableRemovePrefixes",tableRemovePrefixes);
			props.setProperty("generator_tools_class","cn.org.rapid_framework.generator.util.StringHelper,org.apache.commons.lang.StringUtils,com.duowan.generator.common.util.GeneratorColumnUtil,");
			
			GeneratorContext.setGeneratorProperties(props);
		}		
	}

	

	
}
