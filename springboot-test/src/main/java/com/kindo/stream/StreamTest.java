package com.kindo.stream;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.alibaba.fastjson.JSONObject;

public class StreamTest {
	public static void main(String[] args) {
		List<Person> persons =
			    Arrays.asList(
			        new Person("Max", 18),
			        new Person("Peter", 23),
			        new Person("Pamela", 23),
			        new Person("David", 12));
		Map<Integer, List<Person>> personsByAge = persons
			    .stream()
			    .collect(Collectors.groupingBy(p -> p.age));

		personsByAge
		    .forEach((age, p) -> System.out.format("age %s: %s\n", age, p));
		
		String phrase = persons
			    .stream()
			    .filter(p -> p.age >= 18)
			    .map(p -> p.name)
			    .collect(Collectors.joining(" and ", "In Germany ", " are of legal age."));

		System.out.println(phrase);
		
		Collector<Person, StringJoiner, String> personNameCollector =
			    Collector.of(
			        () -> new StringJoiner(" | "),          // supplier
			        (j, p) -> j.add(p.name.toUpperCase()),  // accumulator
			        (j1, j2) -> j1.merge(j2),               // combiner
			        StringJoiner::toString);                // finisher

			String names = persons
			    .stream()
			    .collect(personNameCollector);

			System.out.println(names);
			
//		average();
		
//		buildXml();
		
//		learnStream();
	}

	private static void average() {
		Arrays.stream(new int[] {4, 1, 2, 3})
	    .map(n -> 2 * n + 1)
	    .average()
	    .ifPresent(System.out::println);
	}

	private static void buildXml() {
		JSONObject jsonObj = JSONObject.parseObject("{\"UID\":255268,\"ID\":\"45541197-3$1$571500$1\",\"MEMBER_CODE\":\"45541197-3\",\"MEMBER_NAME\":\"广州番禺中心医院\",\"DISTRICT_CODE\":\"\",\"ZYH\":\"2005506\",\"ZYCS\":1,\"YLFKFS\":\"7\",\"JKKH\":\"\",\"BAH\":\"571500\",\"XM\":\"李江涵\",\"XB\":\"1\",\"CSRQ\":\"1997-2-23 00:00:00\",\"NL\":21,\"GJ\":\"中国\",\"BZYZSNL\":\"\",\"XSECSTZ_1\":\"\",\"XSECSTZ_2\":\"\",\"XSECSTZ_3\":\"\",\"XSECSTZ_4\":\"\",\"XSECSTZ_5\":\"\",\"XSERYTZ\":\"\",\"CSD\":\"湖南隆回县\",\"JG\":\"湖南隆回县\",\"MZ\":\"汉族\",\"SFZH\":\"430524199702230000\",\"HY\":\"1\",\"RYTJ\":2,\"RYSJ\":\"2017-10-17 23:20:00\",\"RYKB\":\"重症医学科病区\",\"RYKBBM\":\"501200\",\"ZKKB\":\"\",\"ZKKBBM1\":\"300100\",\"ZKKBBM2\":\"\",\"ZKKBBM3\":\"\",\"CYSJ\":\"2018-1-1 10:07:00\",\"CYKB\":\"神经内科病区\",\"CYKBBM\":\"300100\",\"SJZYTS\":77,\"MZZD\":\"CPR术后\",\"JBBM\":\"I46.000\",\"RYZDMC\":\"心跳骤停，心肺复苏术后\",\"RYZDBM\":\"I46.000\",\"RYQZRQ\":\"2017-10-18 00:00:00\",\"ZYZD\":\"缺血缺氧性脑病\",\"JBDM\":\"G93.102\",\"RYBQ\":\"1\",\"QTZD1\":\"糖尿病酮症酸中毒\",\"JBDM1\":\"E10.101\",\"RYBQ1\":\"1\",\"QTZD2\":\"肺部感染\",\"JBDM2\":\"J98.414\",\"RYBQ2\":\"1\",\"QTZD3\":\"感染性休克\",\"JBDM3\":\"A41.901\",\"RYBQ3\":\"1\",\"QTZD4\":\"急性肾衰竭\",\"JBDM4\":\"N17.900\",\"RYBQ4\":\"1\",\"QTZD5\":\"1型糖尿病\",\"JBDM5\":\"E10.600\",\"RYBQ5\":\"1\",\"QTZD6\":\"高钠血症\",\"JBDM6\":\"E87.001\",\"RYBQ6\":\"1\",\"QTZD7\":\"\",\"JBDM7\":\"\",\"RYBQ7\":\"\",\"QTZD8\":\"\",\"JBDM8\":\"\",\"RYBQ8\":\"\",\"QTZD9\":\"\",\"JBDM9\":\"\",\"RYBQ9\":\"\",\"QTZD10\":\"\",\"JBDM10\":\"\",\"RYBQ10\":\"\",\"QTZD11\":\"\",\"JBDM11\":\"\",\"RYBQ11\":\"\",\"QTZD12\":\"\",\"JBDM12\":\"\",\"RYBQ12\":\"\",\"QTZD13\":\"\",\"JBDM13\":\"\",\"RYBQ13\":\"\",\"QTZD14\":\"\",\"JBDM14\":\"\",\"RYBQ14\":\"\",\"QTZD15\":\"\",\"JBDM15\":\"\",\"RYBQ15\":\"\",\"WBYY\":\"\",\"H23\":\"\",\"KZR\":\"吴多斌\",\"KZR_CODE\":\"1453\",\"ZRYS\":\"吴多斌\",\"ZRYS_CODE\":\"1453\",\"ZZYS\":\"吴多斌\",\"ZZYS_CODE\":\"1453\",\"ZYYS\":\"李敏子\",\"ZYYS_CODE\":\"4334\",\"ZRHS\":\"陈娜\",\"JXYS\":\"\",\"SXYS\":\"\",\"BMY\":\"伍坤林\",\"BAZL\":\"甲\",\"ZKYS\":\"吴多斌\",\"ZKHS\":\"杨晓华\",\"ZKRQ\":\"2018-4-22 21:18:00\",\"SSJCZBM1\":\"\",\"SSJCZRQ1\":\"\",\"SSJB1\":\"\",\"SSJCZMC1\":\"\",\"SZ1\":\"\",\"YZ1\":\"\",\"EZ1\":\"\",\"QKYHDJ1\":\"\",\"MZFS1\":\"\",\"MZYS1\":\"\",\"SSJCZBM2\":\"\",\"SSJCZRQ2\":\"\",\"SSJB2\":\"\",\"SSJCZMC2\":\"\",\"SZ2\":\"\",\"YZ2\":\"\",\"EZ2\":\"\",\"QKYHDJ2\":\"\",\"MZFS2\":\"\",\"MZYS2\":\"\",\"SSJCZBM3\":\"\",\"SSJCZRQ3\":\"\",\"SSJB3\":\"\",\"SSJCZMC3\":\"\",\"SZ3\":\"\",\"YZ3\":\"\",\"EZ3\":\"\",\"QKYHDJ3\":\"\",\"MZFS3\":\"\",\"MZYS3\":\"\",\"SSJCZBM4\":\"\",\"SSJCZRQ4\":\"\",\"SSJB4\":\"\",\"SSJCZMC4\":\"\",\"SZ4\":\"\",\"YZ4\":\"\",\"EZ4\":\"\",\"QKYHDJ4\":\"\",\"MZFS4\":\"\",\"MZYS4\":\"\",\"SSJCZBM5\":\"\",\"SSJCZRQ5\":\"\",\"SSJB5\":\"\",\"SSJCZMC5\":\"\",\"SZ5\":\"\",\"YZ5\":\"\",\"EZ5\":\"\",\"QKYHDJ5\":\"\",\"MZFS5\":\"\",\"MZYS5\":\"\",\"SSJCZBM6\":\"\",\"SSJCZRQ6\":\"\",\"SSJB6\":\"\",\"SSJCZMC6\":\"\",\"SZ6\":\"\",\"YZ6\":\"\",\"EZ6\":\"\",\"QKYHDJ6\":\"\",\"MZFS6\":\"\",\"MZYS6\":\"\",\"SSJCZBM7\":\"\",\"SSJCZRQ7\":\"\",\"SSJB7\":\"\",\"SSJCZMC7\":\"\",\"SZ7\":\"\",\"YZ7\":\"\",\"EZ7\":\"\",\"QKYHDJ7\":\"\",\"MZFS7\":\"\",\"MZYS7\":\"\",\"SSJCZBM8\":\"\",\"SSJCZRQ8\":\"\",\"SSJB8\":\"\",\"SSJCZMC8\":\"\",\"SZ8\":\"\",\"YZ8\":\"\",\"EZ8\":\"\",\"QKYHDJ8\":\"\",\"MZFS8\":\"\",\"MZYS8\":\"\",\"QJCS\":1,\"CGCS\":1,\"LYFS\":\"1\",\"YZZY_YLJG\":\"\",\"SFZZYJH\":\"1\",\"MD\":\"\",\"ZFY\":254853.80,\"ZFJE\":254853.80,\"YLFUF\":6998.11,\"ZLCZF\":35138.40,\"HLF\":19348.42,\"QTFY\":881.05,\"BLZDF\":\"\",\"SYSZDF\":19932.96,\"YXXZDF\":5007.60,\"LCZDXMF\":11919.04,\"FSSZLXMF\":28071.76,\"WLZLF\":18645.60,\"SSZLF\":758.10,\"MAF\":100.30,\"SSF\":657.80,\"KFF\":4110.60,\"ZYZLF\":220.00,\"XYF\":88775.69,\"KJYWF\":26565.16,\"ZCYF\":695.54,\"ZCYF1\":\"\",\"XF\":565.00,\"BDBLZPF\":\"\",\"QDBLZPF\":\"\",\"NXYZLZPF\":\"\",\"XBYZLZPF\":\"\",\"HCYYCLF\":2657.33,\"YYCLF\":17856.12,\"YCXYYCLF\":11918.08,\"QTF\":\"\",\"GDRQ\":\"\",\"CHECK_FLAG\":1,\"SYNDATE\":\"2018-8-29 10:49:01\",\"SYNFLAG\":\"\"}");
		try {
			BufferedReader srcFileBr = new BufferedReader(new InputStreamReader(new FileInputStream(new File("src_file/mq.txt"))));
			String line = null;
			StringBuffer sbf = new StringBuffer("<ESBEntry><AccessControl><SysFlag>1</SysFlag><UserName>EMR</UserName><Password>123456</Password><Fid>PS90001</Fid></AccessControl><MessageHeader><Fid>PS90001</Fid><SourceSysCode>S02</SourceSysCode><ReturnFlag>0</ReturnFlag><TargetSysCode>S36</TargetSysCode><MsgDate>2018-09-12 14:28:10</MsgDate></MessageHeader><MsgInfo><Msg id=\"1001\" lastUpdate =\"2018-09-18 09:28:10\" action=\"insert\">");
			for(; (line = srcFileBr.readLine()) != null;){
				sbf.append("<").append(line.trim()).append(">");
				sbf.append(jsonObj.get(BaInfoEnum.valueOf(line.trim()).getCdrgField()));
				sbf.append("</").append(line.trim()).append(">");
			}
			sbf.append("</Msg></MsgInfo></ESBEntry>");
			System.err.println(sbf.toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void learnStream() {
		// 首先,创建一个1-6乱序的List
		List<Integer> lists = new ArrayList<>();
		lists.add(4);
		lists.add(3);
		lists.add(6);
		lists.add(1);
		lists.add(5);
		lists.add(2);

		// 看看List里面的数据是什么样子的先
		System.out.print("List里面的数据:");

		for (Integer elem : lists) System.out.print(elem + " ");// 4 3 6 1 5 2
		System.out.println();

		// 最小值
		System.out.print("List中最小的值为:");
		Stream<Integer> stream = lists.stream();
		Optional<Integer> min = stream.min(Integer::compareTo);
		if (min.isPresent()) {
			System.out.println(min.get());// 1
		}
		
//		System.out.print("stream重复使用List中最大的值为:");
//		stream.max(Integer::compareTo).ifPresent(System.out::println);
		// 最大值
		System.out.print("List中最大的值为:");
		lists.stream().max(Integer::compareTo).ifPresent(System.out::println);// 6

		// 排序
		System.out.print("将List流进行排序:");
		Stream<Integer> sorted = lists.stream().sorted();
		sorted.forEach(elem -> System.out.print(elem + " "));// 1 2 3 4 5 6
		System.out.println();

		// 过滤
		System.out.print("过滤List流,只剩下那些大于3的元素:");
		lists.stream().filter(elem -> elem > 3).forEach(elem -> System.out.print(elem + " "));// 4 5 6
		System.out.println();

		// 过滤
		System.out.println("过滤List流,只剩下那些大于0并且小于4的元素:\n=====begin=====");
		lists.stream().filter(elem -> elem > 0).filter(elem -> elem < 4).sorted(Integer::compareTo)
				.forEach(System.out::println);// 1 2 3

		System.out.println("=====end=====");
		// 经过了前面的这么多流操作,我们再来看看List里面的值有没有发生什么改变

		System.out.print("原List里面的数据:");
		for (Integer elem : lists)
			System.out.print(elem + " ");// 4 3 6 1 5 2
	}
}
