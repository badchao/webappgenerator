package com.duowan.generator.controller;

import org.junit.Test;

public class TmpTest {

	@Test
	public void test() {
		double sum = 200000;
		for(int i = 0; i < 10; i++) {
			sum = sum + sum * 0.2;
		}
		System.out.println("sum:"+sum);
	}
}
