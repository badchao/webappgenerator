<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 

package ${basepackage}.beetldao.${namespace};

import  ${basepackage}.query.${namespace}.${className}Query;
import  ${basepackage}.datafactory.${namespace}.${className}DataFactory;
import  ${basepackage}.entity.${namespace}.${className};

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import static org.junit.Assert.*;

import org.beetl.sql.core.engine.PageQuery;
import org.beetl.sql.core.page.DefaultPageRequest;
import org.beetl.sql.core.page.PageRequest;
import org.beetl.sql.core.page.PageResult;

import ${basepackage}.basetest.BaseDaoTest;




public class ${className}DaoTest extends BaseDaoTest {

    static final Logger log = LoggerFactory.getLogger(${className}DaoTest.class);

    @Rule public TestName testName = new TestName();
    
    ${className}Dao dao = getDao(${className}Dao.class);

    String tenantId = "SHOP01";
    String shopId = "SHOP01";
    
    ${className} item = ${className}DataFactory.new${className}();
    ${table.pkColumn.javaType} id = item.id();

    @Before
    public void before() throws Exception {
    	System.out.println("\n------------------ "+testName.getMethodName()+" ----------------------\n");
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void queryPage() {
        ${className}Query query = ${className}DataFactory.new${className}Query();
        query.setTenantId(tenantId);
        query.setKeyword("hello");
        
        PageRequest pr = DefaultPageRequest.of(1, 100);
        PageResult result = dao.queryPage(query,pr);
        System.out.println(result);
    }

    @Test
    public void insert() {
        dao.deleteById(id);

        item.setTenantId(tenantId);
        dao.insert(item);
    }

    @Test
    public void update() {
        item.setTenantId(tenantId);
        dao.updateById(item);
    }

    @Test
    public void deleteById() {
        dao.deleteById(id);
    }
    
    @Test
    public void single() {
        dao.single(id);
    }

}
