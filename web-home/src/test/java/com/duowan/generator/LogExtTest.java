package com.duowan.generator;

import java.io.File;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.github.rapid.common.util.RegexUtil;

public class LogExtTest {

	@Test
	public void test() throws Exception {
		
		File file = new File("src/test/resources/test_log.log");
		System.out.println(file.getAbsolutePath());
		String content = FileUtils.readFileToString(file);
//		System.out.println(content);
		
		String[] regexList = new String[] {"(lz[_\\-\\w]+)","(web[_\\-\\w]+)","(ty[_\\-\\w]+)"};
		Set all = new LinkedHashSet();
		for(String regex : regexList) {
			List<String> list = RegexUtil.findAllByRegexGroup(content, regex, 1);
			all.addAll(list);
		}
		
		printList(all);
	}

	private void printList(Collection<String> all) {
		all.forEach((item) -> {
			System.out.println(item);
		});
	}
}
