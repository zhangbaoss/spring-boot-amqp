package com.zhangbao.amqp.confirm.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.zhangbao.amqp.utils.ConnectionUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

/**
 * 生产者3,异步confirm模式
 *
 * Channel 对象提供的 ConfirmListener()回调方法只包含 deliveryTag（当前 Chanel 发出的消息序号），
 * 我们需要自己为 每一个 Channel 维护一个 unconfirm 的消息序号集合，每 publish 一条数据，集合中元素加 1，
 * 每回调一次 handleAck 方法，unconfirm 集合删掉相应的一条（multiple=false）或多条（multiple=true）记录。
 * 从程序运行效率上看，这个 unconfirm 集合最好采用有序集合 SortedSet 存储结构。
 * 实际上，SDK 中的 waitForConfirms()方法也是通过 SortedSet 维护消息序号的。
 *
 * @author zhangbao
 * @create 2019/2/20
 * @since 1.0.0
 */
public class Producer3 {

	private static final String EXCHANGE_NAME = "test_zhangbao_exchange_confirm";

	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		Connection connection = ConnectionUtils.getConnection();

		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

		//将channel设置为confirm模式
		channel.confirmSelect();

		SortedSet<Long> confirmSet = Collections.synchronizedSortedSet(new TreeSet<>());

		//添加监听通道
		channel.addConfirmListener(new ConfirmListener() {

			//没有问题的handleAck,成功调该方法
			@Override
			public void handleAck(long deliveryTag, boolean multiple) throws IOException {
				if (multiple) {
					System.out.println("----------handleAck----------multiple");
					confirmSet.headSet(deliveryTag + 1).clear();
				} else {
					System.out.println("----------handleAck----------multiple : false");
					confirmSet.remove(deliveryTag);
				}
			}

			@Override
			public void handleNack(long deliveryTag, boolean multiple) throws IOException {
				if (multiple) {
					System.out.println("----------handleNack----------multiple");
					confirmSet.headSet(deliveryTag + 1).clear();
				} else {
					System.out.println("----------handleNack----------multiple : false");
					confirmSet.remove(deliveryTag);
				}
			}
		});

		String msg = "Hello, I'm Asynchronization";

		while (true) {
			long seqNo = channel.getNextPublishSeqNo();
			channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());
			confirmSet.add(seqNo);
		}

//		channel.close();
//		connection.close();
	}
}
