package com.kindo.kaohe;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import io.github.yizhiru.thulac4j.SegOnly;
/**
 * 
* @ClassName: ThulacLiteSplitService  
* @Description: 分词服务  
* @author xiayong  
* @date 2018年7月18日  
*
 */
@Service
public class ThulacLiteSplitService {
	
	private SegOnly seg;

	public static void main(String[] args) {
		String sentence = "滔滔的流水，向着波士顿湾无声逝去";
		SegOnly seg;
		try {
			seg = new SegOnly("models/cws_model.bin", "models/cws_dat.bin");
			seg.setUserWordsPath("userwords/userwords.txt");
			System.out.println(seg.segment(sentence));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	public ThulacLiteSplitService(){
//		try {
//			seg = new SegOnly("models/cws_model.bin", "models/cws_dat.bin");
//			seg.setUserWordsPath("userwords/userwords.txt");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	public void initSeg(){
		try {
			seg = new SegOnly("models/cws_model.bin", "models/cws_dat.bin");
			seg.setUserWordsPath("userwords/userwords.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<String> liteSplit(String line){
		return seg.segment(line);
	}
}
