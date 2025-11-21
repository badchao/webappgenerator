<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 

package ${basepackage}.beetldao.${namespace};

import  ${basepackage}.query.${namespace}.${className}Query;
import  ${basepackage}.datafactory.${namespace}.${className}DataFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import org.beetl.sql.core.engine.PageQuery;
import com.znyx.core.basetest.BaseDaoTest;



public class ${className}DaoTest extends BaseDaoTest {

    static final Logger log = LoggerFactory.getLogger(${className}DaoTest.class);

    ${className}Dao dao = getZnyxDao(${className}Dao.class);

    String rootShopId = "SHOP01";
    String shopId = "SHOP01";
    
    ${className} item = {className}DataFactory.new${className}();
    ${table.pkColumn.javaType} id = item.id();

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void queryPage() {
        ${className}Query query = {className}DataFactory.new${className}Query();
        query.setRootShopId(rootShopId);
        query.setKeyword("hello");
        PageQuery result = dao.queryPage(1, 100, query);
    }

    @Test
    public void insert() {
        dao.deleteById(id);

        item.setRootShopId(rootShopId);
        dao.insert(item);
    }

    @Test
    public void update() {
        item.setRootShopId(rootShopId);
        dao.updateById(item);
    }

    @Test
    public void deleteById() {
        dao.deleteById(id);
    }

}
