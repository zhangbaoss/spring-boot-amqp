package com.zhangbao.amqp.routing.custom;

import com.rabbitmq.client.*;
import com.zhangbao.amqp.utils.ConnectionUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeoutException;

/**
 * 消费者1
 *
 * @author zhangbao
 * @create 2019/2/19
 * @since 1.0.0
 */
public class Consumer1 {

	private static final String EXCHANGE_NAME = "test_zhangbao_exchange_routing";
	private static final String QUEUE_NAME = "test_zhangbao_queue_routing_1";

	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection = ConnectionUtils.getConnection();

		Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);

		//绑定交换机并设置路由为error
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "error");

		DefaultConsumer consumer = new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				String msg = new String(body, Charset.defaultCharset());
				System.out.println("[消费者1接收到的消息] : " + msg);
			}
		};

		channel.basicConsume(QUEUE_NAME, true, consumer);

	}
}
