package com.zhangbao.amqp.routing.producer;

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

	private static final String EXCHANGE_NAME = "test_zhangbao_exchange_routing";

	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection = ConnectionUtils.getConnection();

		Channel channel = connection.createChannel();

		//声明转发器,设置类型为direct
		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

		String msg = "Hello, Routing!";

		//发送消息到路由,并设置路由为error
		String routingKey = "error";
		channel.basicPublish(EXCHANGE_NAME, routingKey, null, msg.getBytes());

		System.out.println("生产者生产消息: " + msg);

		channel.close();
		connection.close();
	}
}
