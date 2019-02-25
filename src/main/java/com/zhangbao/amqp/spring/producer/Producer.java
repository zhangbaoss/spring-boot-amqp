package com.zhangbao.amqp.spring.producer;

import com.zhangbao.amqp.config.RabbitMqConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.zhangbao.amqp.utils.Constants.*;

/**
 * 生产者
 *
 * @author zhangbao
 * @create 2019/2/20
 * @since 1.0.0
 */
@Component
public class Producer {

	private static Logger logger = LoggerFactory.getLogger(Producer.class);

	@Autowired
	private AmqpTemplate amqpTemplate;

	public void sendDirect() {
		String msg = "This is Direct Msg";
		logger.info("send direct msg : {}", msg);
		amqpTemplate.convertAndSend(DIRECT_QUEUE_NAME, msg);
	}

	public void sendTopic() {
		String msg = "This is Topic Msg";
		logger.info("send topic msg : {}", msg);
		amqpTemplate.convertAndSend(TOPIC_EXCHANGE_NAME, "topic.key", msg);
		amqpTemplate.convertAndSend(TOPIC_EXCHANGE_NAME, "topic.value", msg);
	}

	public void sendFanout() {
		String msg = "This is Fanout Msg";
		logger.info("send fanout msg : {}", msg);
		amqpTemplate.convertAndSend(FANOUT_EXCHANGE_NAME, "", msg);
	}

	public void sendHeaders() {
		String msg = "This is Headers Msg";
		logger.info("send headers msg : {}", msg);
		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setHeader("h1", "v1");
		messageProperties.setHeader("h2", "v2");
		Message message = new Message(msg.getBytes(), messageProperties);
		amqpTemplate.convertAndSend(HEADERS_EXCHANGE_NAME, "", message);
	}
}
