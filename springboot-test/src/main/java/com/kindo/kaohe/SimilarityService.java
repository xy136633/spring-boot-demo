package com.kindo.kaohe;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
 
/**
 * 
* @ClassName: SimilarityService  
* @Description: 字符串相似性匹配算法服务  
* @author xiayong  
* @date 2018年7月18日  
*
 */
@Service
public class SimilarityService {
 
    // 求余弦相似度
    public double sim(List<String> string1, List<String> string2) {
    	Map<String, int[]> vectorMap = new HashMap<String, int[]>();
    	 
        int[] tempArray = null;
        
    	for (String key : string1) {
            if (vectorMap.containsKey(key)) {
                vectorMap.get(key)[0]++;
            } else {
                tempArray = new int[2];
                tempArray[0] = 1;
                tempArray[1] = 0;
                vectorMap.put(key, tempArray);
            }
        }
        for (String key : string2) {
            if (vectorMap.containsKey(key)) {
                vectorMap.get(key)[1]++;
            } else {
                tempArray = new int[2];
                tempArray[0] = 0;
                tempArray[1] = 1;
                vectorMap.put(key, tempArray);
            }
        }
        
        double result = 0;
        result = pointMulti(vectorMap) / sqrtMulti(vectorMap);
        return result;
    }
 
    private double sqrtMulti(Map<String, int[]> paramMap) {
        double result = 0;
        result = squares(paramMap);
        result = Math.sqrt(result);
        return result;
    }
 
    // 求平方和
    private double squares(Map<String, int[]> paramMap) {
        double result1 = 0;
        double result2 = 0;
        Set<String> keySet = paramMap.keySet();
        for (String character : keySet) {
            int temp[] = paramMap.get(character);
            result1 += (temp[0] * temp[0]);
            result2 += (temp[1] * temp[1]);
        }
        return result1 * result2;
    }
 
    // 点乘法
    private double pointMulti(Map<String, int[]> paramMap) {
        double result = 0;
        Set<String> keySet = paramMap.keySet();
        for (String character : keySet) {
            int temp[] = paramMap.get(character);
            result += (temp[0] * temp[1]);
        }
        return result;
    }
 
    public static void main(String[] args) {
        List<String> s1 = new ArrayList<String>();
        s1.add("我");
        s1.add("是");
        s1.add("一个");
        s1.add("帅哥");
        List<String> s2 = new ArrayList<String>();
        s2.add("帅哥");
        s2.add("是");
        s2.add("我");
        SimilarityService similarity = new SimilarityService();
        System.out.println(similarity.sim(s1, s2));
    }
 
}