package kafka.test1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.datatype.Duration;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

import common.util.LogHelper;
import kafka.StreamMessageList.MessageList;
import kafka.StreamMessageList;
import kafka.YyDdmgeventOriginal;
import kafka.YyDdmgeventOriginal.MessageUnit;
import kafka.message.MessageAndMetadata;

/**
 * 消费者-使用Protobuf反序列化
 */
public class ProtobufDeserializerConsumer {
	private  static Properties props = new Properties();
	static String topic = "kafkaTest2";
	public static final int threadNum = 6;
	private static ExecutorService executor = Executors.newFixedThreadPool(threadNum, new CustomizableThreadFactory("Producer-thread-"){
		private static final long serialVersionUID = 2683331534571232746L;
		@Override
		public Thread createThread(Runnable runnable) {
			Thread t= super.createThread(runnable);
			return t;
		}
	});
	
	private static boolean isClose = false;
	
	static{
		props.put("bootstrap.servers", "localhost:9092");
//		props.put("zookeeper.connect", "172.27.17.31:2181");
//		 props.put("zookeeper.session.timeout.ms", "10000");
//		 props.put("zookeeper.sync.time.ms", "500");
	     props.put("group.id", "test2");
//	     props.put("auto.offset.reset", "earliest");
	     props.put("enable.auto.commit", "true");
	     props.put("auto.commit.interval.ms", "1000");
	     props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     props.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
	}
	
	public  static void main(String args[]){
//	     KafkaConsumer<String, byte[]> consumer = new KafkaConsumer<>(props);
//	     consumer.subscribe(Arrays.asList(topic));
		List<KafkaConsumer<String, byte[]>> consumerList = new ArrayList<KafkaConsumer<String, byte[]>>();
		long begin = new Date().getTime();
		for(int i=0;i<threadNum;i++) {
		     KafkaConsumer<String, byte[]> consumer = new KafkaConsumer<>(props);
//		     consumer.subscribe(Arrays.asList(topic));	//自动负载均衡
//		     consumer.seekToBeginning(Arrays.asList(new TopicPartition(topic, 0)));
		     //kafka自动负载方式
//		     consumer.poll(0);	//首次，拉取一次元数据
//		     for(int pNum = 0;pNum<6;pNum++) {
//		    	 consumer.seek(new TopicPartition(topic, pNum), 0);
//		     }
		     //手动指定消费位置，无法自动负载均衡，所以要手动注册，才能消费
		     consumer.assign(Arrays.asList(new TopicPartition(topic, i)));
		     consumer.seek(new TopicPartition(topic, i), 0);
		     consumerList.add(consumer);
		}
		long end = new Date().getTime();
		LogHelper.log("c:\\ProtobufDeserializerConsumer.txt", "初始化KafkaConsumer-----------begin="+begin+".end="+end);
		System.out.println("初始化KafkaConsumer-----------begin="+begin+".end="+end);
		for(final KafkaConsumer<String, byte[]> consumerTmp : consumerList) {
			executor.submit(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					try {
						while(true) {
							ConsumerRecords<String, byte[]> records = consumerTmp.poll(100);
							Iterable<ConsumerRecord<String, byte[]>> all = records.records(topic);
							int i = 0;
							for (ConsumerRecord<String, byte[]> record : records) {
								String logStr = "---------------------:"+(i++);
								System.out.println(logStr);
								LogHelper.log("c:\\ProtobufDeserializerConsumer.txt", logStr);
								int msgSize = 0;
								try{
									MessageList messageList;
									messageList = MessageList.parseFrom(record.value());
									List<ByteString> list = messageList.getMessagesList();
									msgSize = CollectionUtils.isNotEmpty(list) ? list.size() : 0;
									for (ByteString bs : list) {

										MessageUnit msgUnit = MessageUnit.parseFrom(bs);
										YyDdmgeventOriginal.MessageUnit.yy_ddmgevent_original msgBody = msgUnit.getMessageBody();
										YyDdmgeventOriginal.MessageUnit.UnitHeader msgHeader = msgUnit.getHeader();
										System.out.println("key = "+record.key()+", value = " + msgBody.getAction());
										LogHelper.log("c:\\ProtobufDeserializerConsumer.txt", "key = "+record.key()+", value = " + msgBody.getAction());
										record.offset();
									}
								} catch (Exception e) {
									continue;
								} finally {
									LogHelper.log("c:\\ProtobufDeserializerConsumer.txt", "ProtobufDeserializerConsumer result。msgSize="+msgSize+", offset= "+record.offset()+", partition= " + record.partition());
								}
							}
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					} finally {	     
						consumerTmp.close();
					}
					return true;
				}
			}) ;
		}
	}
}
