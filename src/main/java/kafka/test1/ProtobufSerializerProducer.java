package kafka.test1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import com.alibaba.dubbo.common.json.JSON;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

import kafka.StreamMessageList;
import kafka.YyDdmgeventOriginal;
import kafka.StreamMessageList.MessageList;
import kafka.producer.KeyedMessage;
import kafka.producer.Producer;

public class ProtobufSerializerProducer {
	public static final int threadNum = 6;
	public static final String TOPIC = "kafkaTest2"; 
	private final static CountDownLatch cdl = new CountDownLatch(10);
	private  static Properties props = new Properties();
	private static ExecutorService executor = Executors.newFixedThreadPool(threadNum, new CustomizableThreadFactory("Producer-thread-"){
		private static final long serialVersionUID = 2683331534571232746L;
		@Override
		public Thread createThread(Runnable runnable) {
			Thread t= super.createThread(runnable);
			return t;
		}
	});
	static{
		 props.put("bootstrap.servers", "localhost:9092");
		 props.put("acks", "all");
		 props.put("retries", 0);
		 props.put("batch.size", 100);
		 props.put("linger.ms", 1);
		 props.put("buffer.memory", 33554432);
		props.put("zookeeper.connect", "172.27.17.31:2181");
		 props.put("zookeeper.session.timeout.ms", "10000");
		 props.put("zookeeper.sync.time.ms", "500");
		 props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		 props.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
	}
	

	
	/**
	 * 单线程kafka写
	 * 多线程kafka写
	 * @param args
	 */
	public static void main(String[] args) {
//		KafkaProducer<String, byte[]> producer = new KafkaProducer<String, byte[]>(props);
		final List<KafkaProducer<String, byte[]>> producers = new ArrayList<KafkaProducer<String, byte[]>>();
		for(int i=0;i<threadNum;i++) {
			producers.add(new KafkaProducer<String, byte[]>(props));
		}
		int messageNo = 0;
		long begin = new Date().getTime();
        final int COUNT = 10;  
  
        int messageCount = 0;
        List<Future<Boolean>> fetures = new ArrayList<Future<Boolean>>();
        while (messageNo < COUNT) {
        	final int i = messageNo;
        	Future<Boolean> future = executor.submit(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
		        	YyDdmgeventOriginal.MessageUnit.Builder unitbuilder = YyDdmgeventOriginal.MessageUnit.newBuilder();
					YyDdmgeventOriginal.MessageUnit.yy_ddmgevent_original.Builder builder = YyDdmgeventOriginal.MessageUnit.yy_ddmgevent_original.newBuilder();
					builder.setAction("install");
//						builder.setGameid("data");
					builder.setIp("221.218.46.234");
					builder.setCountry("中国北京北京");
					builder.setSp("4");
					builder.setAct("ddmgevent");
					builder.setTime("1522080877");
					builder.setKey("96d808c4b1777e00cbcc05449421146b");
					builder.setUid("1140000000914840");
					builder.setImei("ef5c60eb525bd64c291732e02281cfac");
					builder.setMac("1C%3A77%3AF6%3A03%3AE8%3ADC");
					builder.setVer("50.3.0");
					builder.setSdkver("1");
					builder.setSys("0");
					builder.setSjp("OPPO+msm8952");
					builder.setMbos("android5.1.1");
					builder.setMbl("java");
					builder.setSr("1080x1920");
					builder.setNtm("460%3A01");
					builder.setNet("3");
					builder.setGameid("MSGDH");
//						builder.setGser("35002");
					builder.setRso("YY_JRTT");
					builder.setExtra("%7B%22idfa%22%3A%22589A714D-E491-47E8-BBBE-47FD193060FE%22%7D");
					builder.setActime("1522080877486");
					builder.setRoleid("12958299");
					builder.setRolename("%E6%97%A7%E6%A2%A6");
//						builder.setActionvalue("1");
//						builder.setAdvertid("42283");
//						builder.setFlashid(null);
//						builder
					YyDdmgeventOriginal.MessageUnit.yy_ddmgevent_original heartbeat = builder.build();
					unitbuilder.setMessageBody(heartbeat);
					YyDdmgeventOriginal.MessageUnit unit = unitbuilder.build();
					
//////						List<KafkaStream<byte[], byte[]>> param = new ArrayList<KafkaStream<byte[], byte[]>>();
////						List<KeyedMessage<String, YyDdmgeventOriginal.MessageUnit.yy_ddmgevent_original>> list = new ArrayList<KeyedMessage<String, YyDdmgeventOriginal.MessageUnit.yy_ddmgevent_original>>();
////						list.add(new KeyedMessage<String, YyDdmgeventOriginal.MessageUnit.yy_ddmgevent_original>(TOPIC, key ,heartbeat));
//						KeyedMessage<String, byte[]> keymsg = new KeyedMessage<String, byte[]>(TOPIC,heartbeat.toByteArray());
//			            producer.send(keymsg);
		            
		            StreamMessageList.MessageList.Builder msgListBuild = StreamMessageList.MessageList.newBuilder();
		            msgListBuild.setMessageNum(1);
		            msgListBuild.setParseType(3);
		            msgListBuild.addMessages(ByteString.copyFrom(unit.toByteArray()));

//			            KeyedMessage<String, byte[]> keymsg = new KeyedMessage<String, byte[]>(TOPIC,msgListBuild.build().toByteArray());
		            int ListPosi = i%threadNum;
		            System.out.println("-----------KafkaProducer number="+ListPosi);
		            producers.get(ListPosi).send(new ProducerRecord<String, byte[]>(TOPIC, msgListBuild.build().toByteArray()));
//		            try{
//		            	String oriStr = "install221.218.46.234中国北京北京4ddmgevent152208087796d808c4b1777e00cbcc05449421146b1140000000914840ef5c60eb525bd64c291732e02281cfac1C%3A77%3AF6%3A03%3AE8%3ADC50.3.010OPPO+msm8952android5.1.1java1080x1920460%3A013MSGDHYY_JRTT%7B%22idfa%22%3A%22589A714D-E491-47E8-BBBE-47FD193060FE%22%7D152208087748612958299%E6%97%A7%E6%A2%A6";
//		            	System.out.println("-----------json ByteArray size="+oriStr.getBytes().length);
//		            } catch(Throwable e) {
//		            	e.printStackTrace();
//		            }
//		            System.out.println("-----------protobuf ByteArray size="+msgListBuild.build().toByteArray().length+". dataNum="+i);
//		            cdl.countDown();
//		            System.out.println("-----------finished dataNum="+i+".leftNum="+cdl.getCount());
					return true;
				}
			}) ;
        	fetures.add(future);
            messageNo ++;  
            messageCount++;  
        }
        System.out.println("-----------cycleNum="+messageNo);
        //CountDownLatch判断方式
//        try {
//            cdl.await();
//        } catch (InterruptedException e) {
//        }
        boolean allFinish = false;
        while(!allFinish) {
        	System.out.println("-----------not finish");
        	try {
	        	for(Future<Boolean> future : fetures) {
	        		if(!future.get()) {
	        			allFinish = false;
	        			continue;
	        		}
	        	}
	        	allFinish = true;
        	} catch (Exception e) {
        	}
        }
        System.out.println("-----------finish thread done");
        executor.shutdown();
		for(int i=0;i<threadNum;i++) {
			producers.get(i).close();
		}
		long end = new Date().getTime();
		System.out.println("-----------begin="+begin+".end="+end);
	}
	
//	public static void main(String[] args) {
//		long begin, end;
//		//测试代码
//		TestObj testObj = new TestObj();
//		testObj.setAction("install");
//		testObj.setIp("221.218.46.234");
//		testObj.setCountry("中国北京北京");
//		testObj.setSp("4");
//		testObj.setAct("ddmgevent");
//		testObj.setTime("1522080877");
//		testObj.setKey("96d808c4b1777e00cbcc05449421146b");
//		testObj.setUid("1140000000914840");
//		testObj.setImei("ef5c60eb525bd64c291732e02281cfac");
//		testObj.setMac("1C%3A77%3AF6%3A03%3AE8%3ADC");
//		testObj.setVer("50.3.0");
//		testObj.setSdkver("1");
//		testObj.setSys("0");
//		testObj.setSjp("OPPO+msm8952");
//		testObj.setMbos("android5.1.1");
//		testObj.setMbl("java");
//		testObj.setSr("1080x1920");
//		testObj.setNtm("460%3A01");
//		testObj.setNet("3");
//		testObj.setGameid("MSGDH");
//		testObj.setRso("YY_JRTT");
//		testObj.setExtra("%7B%22idfa%22%3A%22589A714D-E491-47E8-BBBE-47FD193060FE%22%7D");
//		testObj.setActime("1522080877486");
//		testObj.setRoleid("12958299");
//		testObj.setRolename("%E6%97%A7%E6%A2%A6");
//		String jsonStr = null;
//		try {
//			System.out.println("-----------json ByteArray size="+JSON.json(testObj).getBytes().length);
//			jsonStr = JSON.json(testObj);
//			
//			//测试json解析10000次
//			begin = new Date().getTime();
//			TestObj parseObj = null;
//			for(int i=0;i<10000;i++) {
//				JSON.parse(jsonStr);
//			}
//			end = new Date().getTime();
//			System.out.println("----------- com.alibaba.dubbo.common.json.JSON。begin="+begin+".end="+end+". diffMillSeconds="+(end-begin));
//			
//			
//			//测试fastjson解析10000次
//			begin = new Date().getTime();
//			for(int i=0;i<10000;i++) {
//				com.alibaba.fastjson.JSON.parse(jsonStr);
//			}
//			end = new Date().getTime();
//			System.out.println("----------- com.alibaba.fastjson.JSON。begin="+begin+".end="+end+". diffMillSeconds="+(end-begin));
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
//
//		
//		
//    	YyDdmgeventOriginal.MessageUnit.Builder unitbuilder = YyDdmgeventOriginal.MessageUnit.newBuilder();
//		YyDdmgeventOriginal.MessageUnit.yy_ddmgevent_original.Builder builder = YyDdmgeventOriginal.MessageUnit.yy_ddmgevent_original.newBuilder();
//		builder.setAction("install");
//		builder.setIp("221.218.46.234");
//		builder.setCountry("中国北京北京");
//		builder.setSp("4");
//		builder.setAct("ddmgevent");
//		builder.setTime("1522080877");
//		builder.setKey("96d808c4b1777e00cbcc05449421146b");
//		builder.setUid("1140000000914840");
//		builder.setImei("ef5c60eb525bd64c291732e02281cfac");
//		builder.setMac("1C%3A77%3AF6%3A03%3AE8%3ADC");
//		builder.setVer("50.3.0");
//		builder.setSdkver("1");
//		builder.setSys("0");
//		builder.setSjp("OPPO+msm8952");
//		builder.setMbos("android5.1.1");
//		builder.setMbl("java");
//		builder.setSr("1080x1920");
//		builder.setNtm("460%3A01");
//		builder.setNet("3");
//		builder.setGameid("MSGDH");
//		builder.setRso("YY_JRTT");
//		builder.setExtra("%7B%22idfa%22%3A%22589A714D-E491-47E8-BBBE-47FD193060FE%22%7D");
//		builder.setActime("1522080877486");
//		builder.setRoleid("12958299");
//		builder.setRolename("%E6%97%A7%E6%A2%A6");
//		YyDdmgeventOriginal.MessageUnit.yy_ddmgevent_original heartbeat = builder.build();
//		unitbuilder.setMessageBody(heartbeat);
//		YyDdmgeventOriginal.MessageUnit unit = unitbuilder.build();
//		
//        
//        StreamMessageList.MessageList.Builder msgListBuild = StreamMessageList.MessageList.newBuilder();
//        msgListBuild.setMessageNum(1);
//        msgListBuild.setParseType(3);
//        msgListBuild.addMessages(ByteString.copyFrom(unit.toByteArray()));
//
//        byte[] binArr = msgListBuild.build().toByteArray();
//
//		try {
//			System.out.println("-----------protobuf ByteArray size="+binArr.length);			
//			//测试protobuf解析10000次
//			begin = new Date().getTime();
//			for(int i=0;i<10000;i++) {
//				MessageList.parseFrom(binArr);
//			}
//			end = new Date().getTime();
//			System.out.println("----------- protobuf。begin="+begin+".end="+end+". diffMillSeconds="+(end-begin));
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
//	}
	
	private static class TestObj {
		String action;
		String ip;
		String country;
		String sp;
		String act;
		String time;
		String key;
		String uid;
		String imei;
		String mac;
		String ver;
		String sdkver;
		String sys;
		String sjp;
		String mbos;
		String mbl;
		String sr;
		String ntm;
		String net;
		String gameid;
		String rso;
		String extra;
		String actime;
		String roleid;
		String rolename;
		
		public String getAction() {
			return action;
		}
		public void setAction(String action) {
			this.action = action;
		}
		public String getIp() {
			return ip;
		}
		public void setIp(String ip) {
			this.ip = ip;
		}
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public String getSp() {
			return sp;
		}
		public void setSp(String sp) {
			this.sp = sp;
		}
		public String getAct() {
			return act;
		}
		public void setAct(String act) {
			this.act = act;
		}
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getUid() {
			return uid;
		}
		public void setUid(String uid) {
			this.uid = uid;
		}
		public String getImei() {
			return imei;
		}
		public void setImei(String imei) {
			this.imei = imei;
		}
		public String getMac() {
			return mac;
		}
		public void setMac(String mac) {
			this.mac = mac;
		}
		public String getVer() {
			return ver;
		}
		public void setVer(String ver) {
			this.ver = ver;
		}
		public String getSdkver() {
			return sdkver;
		}
		public void setSdkver(String sdkver) {
			this.sdkver = sdkver;
		}
		public String getSys() {
			return sys;
		}
		public void setSys(String sys) {
			this.sys = sys;
		}
		public String getSjp() {
			return sjp;
		}
		public void setSjp(String sjp) {
			this.sjp = sjp;
		}
		public String getMbos() {
			return mbos;
		}
		public void setMbos(String mbos) {
			this.mbos = mbos;
		}
		public String getMbl() {
			return mbl;
		}
		public void setMbl(String mbl) {
			this.mbl = mbl;
		}
		public String getSr() {
			return sr;
		}
		public void setSr(String sr) {
			this.sr = sr;
		}
		public String getNtm() {
			return ntm;
		}
		public void setNtm(String ntm) {
			this.ntm = ntm;
		}
		public String getNet() {
			return net;
		}
		public void setNet(String net) {
			this.net = net;
		}
		public String getGameid() {
			return gameid;
		}
		public void setGameid(String gameid) {
			this.gameid = gameid;
		}
		public String getRso() {
			return rso;
		}
		public void setRso(String rso) {
			this.rso = rso;
		}
		public String getExtra() {
			return extra;
		}
		public void setExtra(String extra) {
			this.extra = extra;
		}
		public String getActime() {
			return actime;
		}
		public void setActime(String actime) {
			this.actime = actime;
		}
		public String getRoleid() {
			return roleid;
		}
		public void setRoleid(String roleid) {
			this.roleid = roleid;
		}
		public String getRolename() {
			return rolename;
		}
		public void setRolename(String rolename) {
			this.rolename = rolename;
		}
	}
}

