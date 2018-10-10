package com.kindo.kaohe;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

import org.apache.commons.lang3.StringUtils;
/**
 * 
* @ClassName: CompareRunnable  
* @Description: 相识度比较线程  
* @author wangxiang  
* @date 2018年7月18日  
*
 */
public class CompareRunnable implements Runnable {
	private Map<String, List<String>> standardMap;
	private Map.Entry<String, List<String>> compare;
	private ArrayBlockingQueue<String> queue;
	private SimilarityService similarityService;
	
    public CompareRunnable(Map<String, List<String>> standardMap, Map.Entry<String, List<String>> compare, 
    		ArrayBlockingQueue<String> queue, SimilarityService similarityService) {
        this.standardMap = standardMap;
        this.compare = compare;
    	this.queue = queue;
    	this.similarityService = similarityService;
    }
	@Override
	public void run() {
		DecimalFormat df = new DecimalFormat("0.00%");
		double tempSim = 0;
		double sim = 0;
		String tempStandard = "";
		for (Map.Entry<String, List<String>> standard : standardMap.entrySet()) {
			sim = 0;
			// 完全相同，直接设置相识度为1
			if (compare.getKey().equals(standard.getKey())){
				tempSim = 1;
				tempStandard = standard.getKey();
				break;
			}else{
				sim = similarityService.sim(compare.getValue(), standard.getValue());
				// 取相识度最大的值
				if (sim > tempSim){
					tempStandard = standard.getKey();
					tempSim = sim;
				}
				
				tempStandard = StringUtils.isEmpty(tempStandard) ? standard.getKey() : tempStandard;
			}
		}
		try {
			// 队列满了等待
			while(queue.size() == 1024){
				Thread.sleep(10);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		queue.add(compare.getKey()+"  ~~~  "+tempStandard+" 相识度"+ df.format(new BigDecimal(tempSim).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue()));
	}

}
