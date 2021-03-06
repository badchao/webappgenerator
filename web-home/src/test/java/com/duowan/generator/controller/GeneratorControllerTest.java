package com.duowan.generator.controller;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.duowan.generator.controller.TableGeneratorController.GenCmdExecutor;


public class GeneratorControllerTest {

	@Test
	public void test() throws Exception {
		GenCmdExecutor cmd = new GenCmdExecutor();
		cmd.sqls = "create table user_info( username varchar(200) primary key);";
		cmd.basepackage = "com.duowan.qq";
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockHttpServletRequest request = new MockHttpServletRequest();
		new TableGeneratorController().gen(cmd,request,response);
	}
	
}
