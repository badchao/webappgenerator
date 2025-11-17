<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 

package ${basepackage}.dao.${namespace};

import com.znyx.core.basetest.BaseDaoTest;
import  ${basepackage}.query.${namespace}.${className}Query;

import org.beetl.sql.core.engine.PageQuery;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ${className}DaoTest extends BaseDaoTest {

    ${className}Dao dao = getZnyxDao(${className}Dao.class);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void queryPage() {
        ${className}Query query = new ${className}Query();
        query.setRootShopId("SHOP01");
        PageQuery result = dao.queryPage(1,100,query);

    }
}