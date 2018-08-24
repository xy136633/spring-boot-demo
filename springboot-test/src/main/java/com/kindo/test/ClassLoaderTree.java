package com.kindo.test;
public class ClassLoaderTree { 
 
    public static void main(String[] args) { 
        ClassLoader loader = ClassLoaderTree.class.getClassLoader(); 
        while (loader != null) { 
            System.out.println(loader.getClass().getName()); 
            loader = loader.getParent(); 
        } 
        System.out.println(loader);//最后当loader=null的时候。这个时候loader代表的是引导类加载器BootStrap
    } 
 }