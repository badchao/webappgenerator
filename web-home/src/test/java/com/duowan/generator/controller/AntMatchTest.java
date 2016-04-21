package com.duowan.generator.controller;

import org.junit.Test;
import org.springframework.util.AntPathMatcher;

public class AntMatchTest {

	@Test
	public void test() {
//		String input = "/tmp/webgeneratoroutput/1444473257461/src/main/java/demo/abc/query/AppQuery.java";
		String input = "/main/java/demo/abc/query/AppQuery.java";
		String pattern = "/**/main/**/query/**";
		AntPathMatcher matcher = new AntPathMatcher();
		System.out.println(matcher.match(pattern, input));
	}
}
