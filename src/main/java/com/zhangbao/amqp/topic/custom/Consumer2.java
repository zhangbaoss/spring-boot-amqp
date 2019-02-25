package com.zhangbao.amqp.topic.custom;

import com.rabbitmq.client.*;
import com.zhangbao.amqp.utils.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者2
 *
 * @author zhangbao
 * @create 2019/2/19
 * @since 1.0.0
 */
public class Consumer2 {

	private static final String EXCHANGE_NAME = "test_zhangbao_exchange_topic";
	private static final String QUEUE_NAME = "test_zhangbao_queue_topic_2";

	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection = ConnectionUtils.getConnection();

		Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);

		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "goods.#");

		DefaultConsumer consumer = new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				String msg = new String(body, "utf-8");
				System.out.println("[消费者2获取的消息] : " + msg);
			}
		};

		channel.basicConsume(QUEUE_NAME, true, consumer);
	}
}
