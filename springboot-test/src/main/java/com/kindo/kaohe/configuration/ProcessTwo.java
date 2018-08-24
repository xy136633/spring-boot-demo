package com.kindo.kaohe.configuration;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ProcessTwo extends AbstractProcess<String> {

	@Override
	boolean vaild(List<String> list) {
		System.err.println("processTwo valid..."+list);
		return true;
	}

	@Override
	void failAfter() {
		System.err.println("processTwo valid fail...");
	}

	@Override
	void successAfter() {
		System.err.println("processTwo valid success...");
	}

}
