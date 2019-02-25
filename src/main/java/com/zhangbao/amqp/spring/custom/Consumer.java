package com.zhangbao.amqp.spring.custom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

import static com.zhangbao.amqp.utils.Constants.*;

/**
 * 消费者
 *
 * @author zhangbao
 * @create 2019/2/20
 * @since 1.0.0
 */
@Component
public class Consumer {

	private static Logger logger = LoggerFactory.getLogger(Consumer.class);

	@RabbitListener(queues = DIRECT_QUEUE_NAME)
	public void receiveDirect(String msg) {
		logger.info("Direct Receive Msg : {}", msg);
	}

	@RabbitListener(queues = TOPIC_QUEUE_NAME_1)
	public void receiveTopic1(String msg) {
		logger.info("Topic1 Receive Msg : {}", msg);
	}

	@RabbitListener(queues = TOPIC_QUEUE_NAME_2)
	public void receiveTopic2(String msg) {
		logger.info("Topic2 Receive Msg : {}", msg);
	}

	@RabbitListener(queues = FANOUT_QUEUE_NAME_1)
	public void receiveFanout1(String msg) {
		logger.info("Fanout1 Receive Msg : {}", msg);
	}

	@RabbitListener(queues = FANOUT_QUEUE_NAME_2)
	public void receiveFanout2(String msg) {
		logger.info("Fanout2 Receive Msg : {}", msg);
	}

	@RabbitListener(queues = HEADERS_QUEUE_NAME)
	public void receiveHeaders(byte[] msg) {
		logger.info("Headers Receive Msg : {}", new String(msg, Charset.defaultCharset()));
	}
}
