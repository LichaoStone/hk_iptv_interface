package com.hicon.utils;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * kafka发送工具类
 * @作者 栗超
 * @时间 2018年5月22日 下午2:58:06
 * @说明
 */
public class Productor {
	private static final Logger logger = LoggerFactory.getLogger(Productor.class);
	private static Properties props;
	private static ProducerConfig config;
	private static Producer<String, Object> producer;

	static {
		props = new Properties();
		props.put("zookeeper.connect", PropertiesUtil.getValue("zkconnect"));
		props.put("metadata.broker.list", PropertiesUtil.getValue("kafkaconnect"));
		props.put("serializer.class", "kafka.serializer.StringEncoder");
		props.put("key.serializer.class", "kafka.serializer.StringEncoder");
		props.put("request.required.acks", "1");

		// 决定消息是否应在一个后台线程异步发送,合法的值为async，表示异步发送；sync表示同步发送，默认值sync
		props.put("producer.type", "async");
		// 当使用异步模式时，缓冲数据的最大时间 默认值：5000
		props.put("queue.buffering.max.ms", "100");
		// 在异步模式下，producer端允许buffer的最大消息数量 默认值：10000
		props.put("queue.buffering.max.messages", "10000");
		// 值为-1 则 无阻塞超时限制，消息不会被抛弃；如果值为0 则立即清空队列，消息被抛弃 默认值：-1
		props.put("queue.enqueue.timeout.ms", "-1");
		// 在异步模式下，一个batch发送的消息数量 默认值：200
		props.put("batch.num.messages", "200");
		config = new ProducerConfig(props);
		producer = new Producer<String, Object>(config);
	}

	/**
	 * 发送方法
	 * @param str
	 * @param udid
	 * @param bolt
	 */
	public static void send(String str, String udid, String bolt) {
		try {
			KeyedMessage<String, Object> data = new KeyedMessage<String, Object>(bolt,udid,str);
			producer.send(data);
		} catch (Exception e) {
			logger.error("【KAFKA-Productor】发送数据失败:",e);
		}
	}

	public static void main(String[] args) {
		send("[{\"createTime\":\"2018-11-02 15:05:24.0\",\"nick\":\"用户26830201655\",\"did\":\"184724dcf1514c24a3b87b1c242c1290\",\"avatar\":\"11\",\"comment\":\"666666\"}]", PkCreat.getTablePk(),"1");
	}
}
