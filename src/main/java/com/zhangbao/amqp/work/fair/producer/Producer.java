package com.zhangbao.amqp.work.fair.producer;

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

	private static final String QUEUE_NAME = "test_zhangbao_work_poll_queue";

	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection = ConnectionUtils.getConnection();

		Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);

		//每次只发送一个消息给每个消费者,消费者处理完消息后发送一个手动回执给队列,队列才会再次发送一个消息给消费者
		int prefetchCount = 1;
		channel.basicQos(prefetchCount);

		for (int i = 0; i < 50; i++) {
			String msg = "hello, " + i;
			channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
			System.out.println("[生产者生产消息] : " + msg);
		}

		channel.close();
		connection.close();

	}
}
