package common;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;
import java.util.concurrent.locks.LockSupport;

import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

public class BaseTest {
	public static void transBinStr(int number) {
		int sum;
		String result = "";
		for (int i = number; i >= 1; i = i / 2) {
			if (i % 2 == 0) {
				sum = 0;
			} else {
				sum = 1;
			}
			result = sum + result;
		}
		System.out.print(result);
	}
	
	public static void testPipeLineAndNormal(Jedis jedis)
			throws InterruptedException {
		Logger logger = Logger.getLogger("javasoft");
		long start = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			jedis.set(String.valueOf(i), String.valueOf(i));
		}
		long end = System.currentTimeMillis();
		logger.info("the jedis total time is:" + (end - start));

		Pipeline pipe = jedis.pipelined(); // 先创建一个pipeline的链接对象
		long start_pipe = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			pipe.set(String.valueOf(i), String.valueOf(i));
		}
		pipe.sync(); // 获取所有的response
		long end_pipe = System.currentTimeMillis();
		logger.info("the pipe total time is:" + (end_pipe - start_pipe));
		
		BlockingQueue<String> logQueue = new LinkedBlockingQueue<String>();
		long begin = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			logQueue.put("i=" + i);
		}
		long stop = System.currentTimeMillis();
		logger.info("the BlockingQueue total time is:" + (stop - begin));
	}

	public static void main(String[] args) {
		System.out.println(0b101);
		
//		System.out.println(0144);
//		System.out.println("~~~~~~~~~~~~~~");
//		System.out.println(""+(1 << 15));
//		
//		System.out.println(Integer.toBinaryString(32768));
//		System.out.println(Integer.MAX_VALUE);
//		System.out.println(Integer.toBinaryString(Integer.MAX_VALUE));
//		System.out.println(Integer.MIN_VALUE);
//		System.out.println(Integer.toBinaryString(-32767));
//		
//		System.out.println("---------------");
//		// TODO Auto-generated method stub
//		System.out.println(""+(-8 >>> 1));
//		System.out.println(""+(-8 >> 1));
////		System.out.println(""+(1 <<< 1));
//		System.out.println(""+(1 << 2));
//		System.out.println(""+(1 << 31));
//		System.out.println(""+(1 << 32));
//		System.out.println(""+(1 << 33));
//		
//		int num = 0xFFFFFFE;
//		System.out.println("---------------");
//		System.out.println(""+(-8 >>> 1));
//		System.out.println(""+(-8 >> 1));
//		
//		System.out.println("++++++++++++++++");
//		int s=Integer.parseInt("01111111111111111111111111111111",2);
//        System.out.println(s);
//		s=Integer.parseInt("11111111111111111111111111111101",2);
//        System.out.println(s);
		
		LinkedHashMap tm = new LinkedHashMap();
		tm.put("1", "1");
		tm.put("4", "4");
		tm.put("3", "3");
		tm.put("8", "8");
		tm.put("7", "7");
		tm.put("9", "9");
		tm.put("6", "6");
		tm.put("2", "2");
		tm.put("10", "10");
		tm.put("5", "5");
		for(Object tmp : tm.keySet()) {
			System.out.println((String)tmp);
		}
		
		LockSupport.park();
		ThreadLocal a = new ThreadLocal();
		
		redis.clients.jedis.Jedis jedis = null;
		Pipeline pl = jedis.pipelined();
		Response<String> r = pl.set("", "");
		pl.sync();
//		pl.
	}

}
