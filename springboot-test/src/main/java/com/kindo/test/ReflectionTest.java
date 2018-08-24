package com.kindo.test;

import java.util.Date;

public class ReflectionTest {

	public static void main(String[] args) {
		Date date1 = new Date();
		Date date2 = new Date();
		Class c1 = date1.getClass();
		
		ClassLoader cl = c1.getClassLoader();
		try {
			cl.loadClass("com.kindo.test.Foo");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			Class f2 = Class.forName("com.kindo.test.Foo");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Foo f = new Foo();
		Class f0 = f.getClass();
		Class f1 = Foo.class;
	}
}
