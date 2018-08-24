package com.kindo.kaohe.configuration;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ProcessOne extends AbstractProcess<String> {

	@Override
	boolean vaild(List<String> list) {
		System.err.println("processOne valid..."+list);
		return false;
	}

	@Override
	void failAfter() {
		System.err.println("processOne valid fail...");
	}

	@Override
	void successAfter() {
		System.err.println("processOne valid success...");
	}

}
