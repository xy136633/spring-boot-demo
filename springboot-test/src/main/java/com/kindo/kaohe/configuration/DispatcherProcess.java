package com.kindo.kaohe.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.kindo.kaohe.aspect.LogAnnotation;
@Component
@ConfigurationProperties(prefix="process-application")
public class DispatcherProcess {

	@Autowired
	private List<AbstractProcess> processList;
	@LogAnnotation(name="kkdkdkd")
	public void execute(List<String> dataList){
		for (AbstractProcess abstractProcess : processList) {
			abstractProcess.beforeValid();
			if (abstractProcess.vaild(dataList)){
				abstractProcess.successAfter();
			}else{
				abstractProcess.failAfter();
			}
		}
	}
}
