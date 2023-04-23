package com.tecwang.springdemo.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.lucene.util.ArrayUtil;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

import java.security.Provider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class KafkaTest {


    // 这个topic 有4个分区，每个分区有2个副本
    // recode->broker(follower or leader)
    //                    ↓ broker 返回 leader的地址
    //            recode -> leader Host
    final static String TOPIC = "my-replication-topic-3";
    final static String SERVER_CONFIG = "192.168.200.129:9092";

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 同步发送消息
        Properties kafkaProperties = getKafkaProperties();
        String message = "hello world";

        RecordMetadata recordMetadata;
        ProducerRecord<String, String> record;

        // 不指定分区发送消息，key决定了最终会到哪个分区
        record = new ProducerRecord<>(TOPIC, "key", message);
        recordMetadata = SendingMessagesSynchronously(kafkaProperties, record);

        // 指定分区
        record = new ProducerRecord<>(TOPIC, 4, "key", message);

        // 同步发送消息
        recordMetadata = SendingMessagesSynchronously(kafkaProperties, record);
        // 异步发送消息
        SendingMessagesAsynchronously(kafkaProperties, record);


//        System.out.println("topic: " + recordMetadata.topic());
//        System.out.println("partition: " + recordMetadata.partition());
//        System.out.println("offset: " + recordMetadata.offset());

    }

    private static void SendingMessagesAsynchronously(Properties kafkaProperties, ProducerRecord<String, String> record) throws InterruptedException {
        // 不用收到ACK就直接继续执行后面的代码。
        Producer<String, String> producer = new KafkaProducer<>(kafkaProperties);
        // 回调函数，收到ACK会调用。
        producer.send(record, (metadata, exception) -> {
            if (exception != null) {
                exception.printStackTrace();
            }
            System.out.println("topic: " + metadata.topic());
            System.out.println("partition: " + metadata.partition());
            System.out.println("offset: " + metadata.offset());
        });
        // 保证异步发送消息的线程执行完毕，不然不会执行回调函数，切换到主进程之后，线程执行完毕了，print信息
        Thread.sleep(10000);
    }

    private static Properties getKafkaProperties() {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, SERVER_CONFIG);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.setProperty(ProducerConfig.ACKS_CONFIG, "all");
        properties.setProperty(ProducerConfig.RETRIES_CONFIG, "0");
        properties.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, "16384");
        properties.setProperty(ProducerConfig.LINGER_MS_CONFIG, "1");
        properties.setProperty(ProducerConfig.BUFFER_MEMORY_CONFIG, "33554432");
        return properties;
    }


    /**
     * 同步发送消息，需要等待服务器返回ACK。
     * 3秒内没有收到ACK，重试3次
     * 3次重试后，还是没有收到ACK，抛出异常
     * 两个泛型参数 一个是key的类型，一个是value的类型
     * <p>
     * 参数
     * this.topic = topic;
     * this.partition = partition;
     * this.key = key;
     * this.value = value;
     * this.timestamp = timestamp;
     * this.headers = new RecordHeaders(headers);
     *
     * @param kafkaProperties producer的配置
     * @param record          消息
     */
    private static <K, V> RecordMetadata SendingMessagesSynchronously(Properties kafkaProperties, ProducerRecord<K, V> record) throws ExecutionException, InterruptedException {
        // 同步发送消息
        Producer<K, V> producer = new KafkaProducer<>(kafkaProperties);
        RecordMetadata recordMetadata = producer.send(record).get();
        producer.close();
        // 将ArrayList转换为数组
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        // 将ArrayList转换为数组
        int[] arr= integers.stream().mapToInt(Integer::valueOf).toArray();
        return recordMetadata;
    }

    // 生产者异步发消息
    // 不需要等待ACk，ACK是服务器产生的，不是消费者产生的。

}
