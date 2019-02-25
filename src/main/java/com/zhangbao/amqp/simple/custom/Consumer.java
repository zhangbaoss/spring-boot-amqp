package com.zhangbao.amqp.simple.custom;

import com.rabbitmq.client.*;
import com.zhangbao.amqp.utils.ConnectionUtils;

import java.io.IOException;

/**
 * 消费者
 *
 * @author zhangbao
 * @create 2019/2/18
 * @since 1.0.0
 */
public class Consumer {

	private static final String QUEUE_NAME = "test_zhangbao_simple_queue";

	public static void main(String[] args) throws Exception {
		//获取一个连接
		Connection connection = ConnectionUtils.getConnection();
		//声明一个通道
		Channel channel = connection.createChannel();
		//声明队列,如果确定队列存在,则不会重新创建
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);

		DefaultConsumer consumer = new DefaultConsumer(channel){

			//获取到达的消息
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				String msg = new String(body, "utf-8");
				System.out.println("[消费者接收消息] : " + msg);
			}
		};

		//监听队列
		channel.basicConsume(QUEUE_NAME, true, consumer);
	}
}
