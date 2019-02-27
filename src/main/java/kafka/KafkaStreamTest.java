package kafka;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.google.protobuf.ByteString;

import kafka.StreamMessageList.MessageList;
import kafka.YyDdmgeventOriginal.MessageUnit;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

public class KafkaStreamTest {

    private static String ip;
    private static String groupId;
    private static String topic;
    public static void main(String[] args) {
    	 
		Properties props = new Properties();
		//zookeeper 配置
		props.put("zookeeper.connect", ip);

		//group 代表一个消费组
		props.put("group.id", groupId);

		//zk连接超时
		props.put("zookeeper.session.timeout.ms", "10000");
		props.put("zookeeper.sync.time.ms", "500");
		props.put("auto.commit.interval.ms", "10000");
		props.put("fetch.message.max.bytes", "10485760");
		props.put("auto.offset.reset", "largest");
		//序列化类
		props.put("serializer.class", "kafka.serializer.StringEncoder");
		props.put("consumer.timeout.ms", "1000");
		ConsumerConfig config = new ConsumerConfig(props);
		ConsumerConnector connector = kafka.consumer.Consumer.createJavaConsumerConnector(config);
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put(topic, new Integer(1));
		Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = connector.createMessageStreams(topicCountMap);
		List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);
		for (final KafkaStream<byte[], byte[]> stream : streams) {
			ConsumerIterator<byte[], byte[]> it = stream.iterator();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String sec = sdf.format(new Date());
			while (it.hasNext()) {
				MessageAndMetadata<byte[], byte[]> next = it.next();
				MessageList messageList = null;
				try {
					byte[] message = next.message();
					messageList = MessageList.parseFrom(message);
				} catch (Exception e) {
					continue;
				} 
				
				List<ByteString> list = messageList.getMessagesList();
				int msgSize = CollectionUtils.isNotEmpty(list) ? list.size() : 0;
	//			Clock dealClock = Clock.start();
				for (ByteString bs : list) {
					try{
						MessageUnit msgUnit = MessageUnit.parseFrom(bs);
						YyDdmgeventOriginal.MessageUnit.yy_ddmgevent_original msgBody = msgUnit.getMessageBody();
						YyDdmgeventOriginal.MessageUnit.UnitHeader msgHeader = msgUnit.getHeader();
						
					} catch (Exception e) {
						continue;
					}
				}
			}
		}
    }

}
