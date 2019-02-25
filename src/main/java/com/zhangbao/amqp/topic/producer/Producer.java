package com.zhangbao.amqp.topic.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.zhangbao.amqp.utils.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 生产者
 *
 * @author zhangbao
 * @create 2019/2/19
 * @since 1.0.0
 */
public class Producer {

	private static final String EXCHANGE_NAME = "test_zhangbao_exchange_topic";

	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection = ConnectionUtils.getConnection();

		Channel channel = connection.createChannel();

		//声明路由器
		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

		String msg = "Hello, Topic!";

		//什么routingKey为goods.add
		String routingKey = "goods.update";
		channel.basicPublish(EXCHANGE_NAME, routingKey, null, msg.getBytes());

		System.out.println("生产者生产消息: " + msg);

		channel.close();
		connection.close();
	}
}
