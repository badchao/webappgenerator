package com.duowan.generator.tools;

import javax.servlet.jsp.JspFactory;

import org.apache.jasper.runtime.JspFactoryImpl;
import org.apache.jasper.security.SecurityClassLoad;
import org.apache.tomcat.SimpleInstanceManager;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.util.Assert;
/**
 * 开发调试使用的 Jetty Server
 * @author badqiu
 *
 */
public class GeneratorJettyServer {
    static {
        JspFactoryImpl factory = new JspFactoryImpl();
        SecurityClassLoad.securityClassLoad(factory.getClass().getClassLoader());
//        String basePackage = "org.apache.jasper.";
//        try {
//            factory.getClass().getClassLoader().loadClass(
//                basePackage
//                + "runtime.JspFactoryImpl$PrivilegedGetPageContext");
//            factory.getClass().getClassLoader().loadClass(
//                basePackage
//                + "runtime.JspFactoryImpl$PrivilegedReleasePageContext");
//            factory.getClass().getClassLoader().loadClass(
//                basePackage
//                + "runtime.JspRuntimeLibrary");
//            factory.getClass().getClassLoader().loadClass(
//                basePackage
//                + "runtime.JspRuntimeLibrary$PrivilegedIntrospectHelper");
//            factory.getClass().getClassLoader().loadClass(
//                basePackage
//                + "runtime.ServletResponseWrapperInclude");
//            factory.getClass().getClassLoader().loadClass(
//                basePackage
//                + "servlet.JspServletWrapper");
//        } catch (ClassNotFoundException ex) {
//            throw new RuntimeException(ex);
//        }

        JspFactory.setDefaultFactory(factory);
    }
    
	public static void main(String[] args) throws Exception {
		Server server = buildNormalServer(3030, "/");
		server.start();
	}
	
	/**
	 * 创建用于正常运行调试的Jetty Server, 以src/main/webapp为Web应用目录.
	 */
	public static Server buildNormalServer(int port, String contextPath) {
		Assert.notNull(JspFactory.getDefaultFactory());
		
		Server server = new Server(port);
		WebAppContext webContext = new WebAppContext("src/main/webapp", contextPath);
		webContext.setClassLoader(Thread.currentThread().getContextClassLoader());
//		webContext.setDefaultsDescriptor("src/test/resources/jetty-webdefault.xml"); // 避免windows lock,设置useFileMappedBuffer=false
//		webContext.setInitParameter("org.eclipse.jetty.servlet.Default.dirAllowed", "false");
//		webContext.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern", ".*/[^/]*servlet-api-[^/]*\\.jar$|.*/javax.servlet.jsp.jstl-.*\\.jar$|.*/[^/]*taglibs.*\\.jar$");
//		webContext.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern", ".*/[^/]*jstl.*\\.jar$");
		webContext.setAttribute("org.apache.tomcat.InstanceManager", new SimpleInstanceManager());
		 
		server.setHandler(webContext);
//		server.setStopAtShutdown(true);
		return server;
	}


}
