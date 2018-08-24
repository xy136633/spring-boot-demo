package com.kindo.kaohe;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
/**
 * 
* @ClassName: ResultOperateRunnable  
* @Description: 比较结果处理 
* @author xiayong  
* @date 2018年7月17日  
*
 */
public class ResultOperateRunnable implements Runnable {

	private BufferedWriter bw;
	private ArrayBlockingQueue<String> queue;
	private int total;
	private ExecutorService pool;
	
	public ResultOperateRunnable(BufferedWriter bw, ArrayBlockingQueue<String> queue, int total, ExecutorService pool){
		this.bw = bw;
		this.queue = queue;
		this.total = total;
		this.pool = pool;
	}
	
	@Override
	public void run() {
		boolean flag = true;
		int count = 0;
		try {
			while (flag){
				if (queue.isEmpty()){
					Thread.sleep(10);
				}
				
				if (!queue.isEmpty()){
					count++;
					bw.write(queue.take());
					bw.write("\r\n");
				}
				// 全部数据处理完成，退出循环
				if (count >= total){
					flag = false;
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if (null != bw){
					bw.close();
				}
				if (null != pool){
					pool.shutdown();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
