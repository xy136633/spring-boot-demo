package com.kindo.kaohe.configuration;

import java.util.List;

public abstract class AbstractProcess<T> {

	public boolean beforeValid(){
		System.err.println("default beforeValid...");
		return true;
	}
	abstract boolean vaild(List<T> list);
	
	abstract void failAfter();
	
	abstract void successAfter();
}
