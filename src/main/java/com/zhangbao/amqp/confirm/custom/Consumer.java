package com.zhangbao.amqp.confirm.custom;

import com.rabbitmq.client.*;
import com.zhangbao.amqp.utils.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者
 *
 * @author zhangbao
 * @create 2019/2/20
 * @since 1.0.0
 */
public class Consumer {

	private static final String QUEUE_NAME = "test_zhangbao_queue_confirm";

	private static final String EXCHANGE_NAME = "test_zhangbao_exchange_confirm";

	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection = ConnectionUtils.getConnection();

		Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);

		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");

		channel.basicConsume(QUEUE_NAME, new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				System.out.println("消费者接收消息: " + new String(body, "utf-8"));
			}
		});

	}
}
