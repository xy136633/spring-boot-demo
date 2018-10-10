package com.kindo.kaohe;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
* @ClassName: CompareApi  
* @Description: 比较接口  
* @author xiayong  
* @date 2018年7月18日  
*
 */
@RestController
@RequestMapping("/compareApi")
public class CompareApi {

	@Autowired
	private FileExcludeStopWordService fileExcludeStopWordService;
	@Autowired
	private SimilarityService similarityService;
	
	@RequestMapping(value = "/compareDatas", method = RequestMethod.GET)
	public String compareDatas(){
		// 标准数据分词、停用词处理
		Map<String, List<String>> standardMap = fileExcludeStopWordService.fileExcludeStopWord("src_file/standard_datas.txt");
		// 待比对数据分词、停用词处理
		Map<String, List<String>> needCompareMap = fileExcludeStopWordService.fileExcludeStopWord("src_file/need_compare_datas.txt");
        
		DecimalFormat df = new DecimalFormat("0.00%");
		
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("src_file/compare_result_date.txt"))));
			String tempStandard;
			double tempSim = 0;
			double sim = 0;
			for (Map.Entry<String, List<String>> compare : needCompareMap.entrySet()) {
				tempStandard = "";
				tempSim = 0;
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
				bw.write(compare.getKey()+"  ~~~  "+tempStandard+" 相识度"+ df.format(new BigDecimal(tempSim).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue()));
				bw.write("\r\n");
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (null != bw){
				try {
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return "==================compare sucess==============";
	}
	
	@RequestMapping(value = "/execCompareDatas", method = RequestMethod.GET)
	public String execCompareDatas(){
		// 标准数据分词、停用词处理
		final Map<String, List<String>> standardMap = fileExcludeStopWordService.fileExcludeStopWord("src_file/standard_datas.txt");
		// 待比对数据分词、停用词处理
		Map<String, List<String>> needCompareMap = fileExcludeStopWordService.fileExcludeStopWord("src_file/need_compare_datas.txt");
        
		BufferedWriter bw = null;
		// 创建一个线程池
		ExecutorService pool = Executors.newFixedThreadPool(5);
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("src_file/compare_result_date.txt"))));
			// 创建多个有返回值的任务
			List<Future<String>> list = new ArrayList<Future<String>>();
			long beginTime = System.currentTimeMillis();
			for (final Map.Entry<String, List<String>> compare : needCompareMap.entrySet()) {
				Future<String> f = pool.submit(new Callable<String>() {
					DecimalFormat df = new DecimalFormat("0.00%");

					@Override
					public String call() throws Exception {
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
						return compare.getKey()+"  ~~~  "+tempStandard+" 相识度"+ df.format(new BigDecimal(tempSim).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue());
					}
				});
				list.add(f);
			}
//			System.err.println(System.currentTimeMillis() - beginTime);
			
			for (Future<String> future : list) {
				bw.write(future.get());
				bw.write("\r\n");
			}
//			System.err.println(System.currentTimeMillis() - beginTime);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			pool.shutdown();
			if (null != bw){
				try {
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return "==================compare sucess==============";
	}
	
	@RequestMapping(value = "/queueCompareDatas", method = RequestMethod.GET)
	public String queueCompareDatas(){
		// 标准数据分词、停用词处理
		Map<String, List<String>> standardMap = fileExcludeStopWordService.fileExcludeStopWord("src_file/standard_datas.txt");
		// 待比对数据分词、停用词处理
		Map<String, List<String>> needCompareMap = fileExcludeStopWordService.fileExcludeStopWord("src_file/need_compare_datas.txt");
		
		ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(1024);
		// 创建一个线程池
		ExecutorService pool = Executors.newFixedThreadPool(5);

		for (Map.Entry<String, List<String>> compare : needCompareMap.entrySet()) {
			pool.execute(new CompareRunnable(standardMap, compare, queue, similarityService));
		}
		
		// 结果输出	
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("src_file/compare_result_date.txt"))));
			new Thread(new ResultOperateRunnable(bw, queue, needCompareMap.size(), pool)).start();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "==================compare sucess==============";
	}
}
