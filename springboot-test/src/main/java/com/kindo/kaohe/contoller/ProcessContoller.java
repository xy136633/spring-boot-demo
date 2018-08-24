package com.kindo.kaohe.contoller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kindo.kaohe.configuration.DispatcherProcess;

@RestController
@RequestMapping("/processContoller")
public class ProcessContoller {
	@Autowired
	private DispatcherProcess dispatcherProcess;
	
	@RequestMapping(value = "/executeProcess", method = RequestMethod.GET)
	public String executeProcess(){
		List<String> dataList = new ArrayList<String>();
		dataList.add("1");
		dataList.add("11");
		dataList.add("111");
		dispatcherProcess.execute(dataList);
		return "--------success-------";
	}
}
