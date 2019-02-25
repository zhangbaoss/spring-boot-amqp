package com.zhangbao.amqp.simple.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.zhangbao.amqp.utils.ConnectionUtils;

import java.util.Map;

/**
 * 生产者
 *
 * @author zhangbao
 * @create 2019/2/18
 * @since 1.0.0
 */
public class Producer {

	private static final String QUEUE_NAME = "test_zhangbao_simple_queue";

	public static void main(String[] args) throws Exception {
		Connection connection = ConnectionUtils.getConnection();
		Channel channel = connection.createChannel();
		//是否持久化,队列默认是存在内存中,重启会失效.为true则持久化到Erlang自带的Mnesia数据库中
		boolean durable = false;
		//是否排外:1.当连接关闭时是否自动删除该队列;2.该队列是否只能被当前channel持有?
		boolean exclusive = false;
		//是否自动删除:当最后一个消费者断开连接后队列是否自动删除
		boolean autoDelete = false;
		//队列中的消息什么时候自动删除
		Map<String, Object> arguments = null;
		//声明一个队列
		channel.queueDeclare(QUEUE_NAME, durable, exclusive, autoDelete, arguments);
		String msg = "hello, 我是生产者!";
		String exchange = "";
		String routingKey = QUEUE_NAME;
		channel.basicPublish(exchange, routingKey, null, msg.getBytes());
		System.out.println("[生产者生产消息] : " + msg);
		channel.close();
		connection.close();
	}
}
