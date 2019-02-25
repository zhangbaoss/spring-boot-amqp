package com.zhangbao.amqp.tx.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.zhangbao.amqp.utils.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 生产者
 *
 * @author zhangbao
 * @create 2019/2/19
 * @since 1.0.0
 */
public class Producer {

	private static final String EXCHANGE_NAME = "test_zhangbao_exchange_tx";

	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection = ConnectionUtils.getConnection();

		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

		String msg = "Hello, TX!";


		try {
			//开启事务
			channel.txSelect();

			channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());

			System.out.println("生产者生产消息 : " + msg);

			//提交事务
			channel.txCommit();
			System.out.println("生产者提交消息!");
		} catch (Exception e) {
			//回滚事务
			channel.txRollback();
			System.out.println("生产者回滚消息!");
		} finally {
			channel.close();
			connection.close();
		}
	}
}
