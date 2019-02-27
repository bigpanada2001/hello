package kafka;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class KafkaTest {

    private static String topic;
    private static String group;
    public static void main(String[] args) {
        Properties props = new Properties();
        topic=args[0];
        group=args[1]; // auto-offset-commit
        props.put("bootstrap.servers", "XXX:9092,XXX:9092");
        props.put("group.id", group);
        props.put("auto.offset.reset", "earliest");
        props.put("enable.auto.commit", "true"); // �Զ�commit
        props.put("auto.commit.interval.ms", "1000"); // �Զ�commit�ļ��
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        KafkaConsumer<String, byte[]> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(topic)); // �����Ѷ��topic,���һ��list
        while (true) {
            ConsumerRecords<String, byte[]> records = consumer.poll(100);
            for (ConsumerRecord<String, byte[]> record : records) {
                System.out.printf("offset = %d, key = %s, value = %s \n", record.offset(), record.key(), record.value());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
