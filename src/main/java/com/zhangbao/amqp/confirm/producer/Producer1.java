package com.zhangbao.amqp.confirm.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.zhangbao.amqp.utils.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 生产者1,普通confirm模式
 *
 * @author zhangbao
 * @create 2019/2/20
 * @since 1.0.0
 */
public class Producer1 {

	private static final String EXCHANGE_NAME = "test_zhangbao_exchange_confirm";

	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		Connection connection = ConnectionUtils.getConnection();

		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

		//将channel设置为confirm模式
		channel.confirmSelect();

		String msg = "Hello, I'm Simple Confirm";

		channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());

		//确认是否回复
		if (channel.waitForConfirms()) {
			System.out.println("Send msg is success");
		} else {
			System.out.println("Send msg is failed");
		}

		channel.close();
		connection.close();
	}
}
