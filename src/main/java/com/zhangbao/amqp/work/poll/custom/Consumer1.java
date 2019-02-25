package com.zhangbao.amqp.work.poll.custom;

import com.rabbitmq.client.*;
import com.zhangbao.amqp.utils.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者1
 *
 * @author zhangbao
 * @create 2019/2/18
 * @since 1.0.0
 */
public class Consumer1 {

	private static final String QUEUE_NAME = "test_zhangbao_work_poll_queue";

	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection = ConnectionUtils.getConnection();

		Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);

		DefaultConsumer consumer = new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				String msg = new String(body, "utf-8");
				try {
					Thread.sleep(1000);
					System.out.println("[消费者1接收的消息] : " + msg);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		channel.basicConsume(QUEUE_NAME, true, consumer);
	}
}
