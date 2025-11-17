<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 

package ${basepackage}.beetldao.${namespace};

import  ${basepackage}.query.${namespace}.${className}Query;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import org.beetl.sql.core.engine.PageQuery;
import com.znyx.core.basetest.BaseDaoTest;

public class ${className}DaoTest extends BaseDaoTest {

    ${className}Dao dao = getZnyxDao(${className}Dao.class);

    String rootShopId = "SHOP01";
    String shopId = "SHOP01";
    
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void queryPage() {
        ${className}Query query = new ${className}Query();
        query.setRootShopId(rootShopId);
        PageQuery result = dao.queryPage(1,100,query);

    }
}