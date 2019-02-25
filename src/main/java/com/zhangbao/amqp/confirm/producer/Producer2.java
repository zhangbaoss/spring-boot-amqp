package com.zhangbao.amqp.confirm.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.zhangbao.amqp.utils.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 生产者2,批量confirm模式
 *
 * 批量 confirm 模式稍微复杂一点，客户端程序需要定期（每隔多少秒）或者定量（达到多少条）
 * 或者两则结合起来 publish 消息，然后等待服务器端 confirm, 相比普通 confirm 模式，
 * 批量极大提升 confirm 效率，但是问题在于一旦 出现 confirm 返回 false 或者超时的情况时，
 * 客户端需要将这一批次的消息全部重发，这会带来明显的重复消息数量，并且，当消息经常丢失时，
 * 批量 confirm 性能应该是不升反降的
 *
 * @author zhangbao
 * @create 2019/2/20
 * @since 1.0.0
 */
public class Producer2 {

	private static final String EXCHANGE_NAME = "test_zhangbao_exchange_confirm";

	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		Connection connection = ConnectionUtils.getConnection();

		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

		//将channel设置为confirm模式
		channel.confirmSelect();

		for (int i = 0; i < 10; i++) {
			String msg = "Hello, I'm Simple Confirm" + i;
			channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());
		}

		//确认是否回复
		if (channel.waitForConfirms()) {
			System.out.println("Send msg is success");
		} else {
			System.out.println("Send msg is failed");
		}

		channel.close();
		connection.close();
	}
}
