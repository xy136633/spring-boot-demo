package com.kindo.kaohe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 
* @ClassName: FileExcludeStopWordService  
* @Description: 停用词过滤服务 
* @author xiayong  
* @date 2018年7月18日  
*
 */
@Service
public class FileExcludeStopWordService { 

	@Autowired
	ThulacLiteSplitService thulacLiteSplitService;
	
	//停用词词表 
	public static final String stopWordTable = "." + File.separator + "src_file" + File.separator + "stop_word_table.txt";
	
	public static void main(String[] args) { 
		//源文件和目的文件 
		String srcFile = "." + File.separator + "srcFile" + File.separator + "如何正确的使用化妆品效.txt"; 
		String destFile = "." + File.separator + "destFile" + File.separator + "如何正确的使用化妆品效.txt"; 
		new FileExcludeStopWordService().fileExcludeStopWord(srcFile); 
	}
	
	public Map<String, List<String>> fileExcludeStopWord(String srcFile){
		// key:原数据字符串  value:分词列表
		Map<String, List<String>> filterResultMap = new HashMap<String, List<String>>();
		try{
			//读取原文件和停用词表
			BufferedReader srcFileBr = new BufferedReader(new InputStreamReader(new FileInputStream(new File(srcFile))));
			BufferedReader stopWordFileBr = new BufferedReader(new InputStreamReader(new FileInputStream(new File(stopWordTable))));
			//用来存放停用词的集合 
			Set<String> stopWordSet = new HashSet<String>(); 
			//初始化停用词集 
			String stopWord = null; 
			for(; (stopWord = stopWordFileBr.readLine()) != null;){ 
				stopWordSet.add(stopWord);
			} 
			
			thulacLiteSplitService.initSeg();
			
			String paragraph = null;
			for(; (paragraph = srcFileBr.readLine()) != null;){
				// 一样的数据串，只保留一份分词结果
				if (filterResultMap.containsKey(paragraph)){
					continue;
				}
				
				filterResultMap.put(paragraph, new ArrayList<String>());
				
				//得到分词后的词汇数组，以便后续比较
				List<String> splitResultList = thulacLiteSplitService.liteSplit(paragraph);
				//过滤停用词
				for (String words : splitResultList) {
					if(!stopWordSet.contains(words)){
						filterResultMap.get(paragraph).add(words);
					}
				}
			} 
			stopWordFileBr.close(); 
			srcFileBr.close();
	     } catch (FileNotFoundException e) { 
	    	 e.printStackTrace();  
	     } catch(Exception e){
	    	 e.printStackTrace(); 
	     }
		return filterResultMap;
	}
}