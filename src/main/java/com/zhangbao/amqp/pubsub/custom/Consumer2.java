package com.zhangbao.amqp.pubsub.custom;

import com.rabbitmq.client.*;
import com.zhangbao.amqp.utils.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者
 *
 * @author zhangbao
 * @create 2019/2/18
 * @since 1.0.0
 */
public class Consumer2 {

	private static final String QUEUE_NAME = "test_zhangbao_pubsub_queue2";

	private static final String EXCHANGE_NAME = "test_zhangbao_pubsub_exchange";

	public static void main(String[] args) throws IOException, TimeoutException {

		Connection connection = ConnectionUtils.getConnection();

		Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);

		//将队列和交换机绑定起来
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");

		DefaultConsumer consumer = new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				String msg = new String(body, "UTF-8");
				System.out.println("[消费者2消费信息] : " + msg);
			}
		};

		channel.basicConsume(QUEUE_NAME, true, consumer);

	}
}
