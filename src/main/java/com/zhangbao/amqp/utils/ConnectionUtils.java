package com.zhangbao.amqp.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * RabbitMQ配置类
 *
 * @author zhangbao
 * @create 2019/2/18
 * @since 1.0.0
 */
//@RabbitMqConfiguration
public class ConnectionUtils {

//	@Bean
//	RabbitTemplate rabbitTemplate() {
//		return new RabbitTemplate();
//	}

	public static Connection getConnection() throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		factory.setPort(5672);
		factory.setUsername("zhangbao");
		factory.setPassword("zhangbao");
		factory.setVirtualHost("/zhangbao");
		return factory.newConnection();
	}

}
