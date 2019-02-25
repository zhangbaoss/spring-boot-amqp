package com.zhangbao.amqp.pubsub.producer;

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
 * @create 2019/2/18
 * @since 1.0.0
 */
public class Producer {

	private static final String EXCHANGE_NAME = "test_zhangbao_pubsub_exchange";

	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection = ConnectionUtils.getConnection();

		Channel channel = connection.createChannel();

		//声明交换机
		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT.getType());

		String msg = "hello, Pub AND Sub";

		channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());

		System.out.println("[生产者生产的消息] : " + msg);

		channel.close();
		connection.close();
	}
}
