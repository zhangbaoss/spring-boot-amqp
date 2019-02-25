package com.zhangbao.amqp.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static com.zhangbao.amqp.utils.Constants.*;

/**
 * RabbitMq配置类
 *
 * @author zhangbao
 * @create 2019/2/21
 * @since 1.0.0
 */
@Configuration
public class RabbitMqConfiguration {


	/*****************Direct模式 START*******************/
	@Bean
	public Queue directQueue() {
		return new Queue(DIRECT_QUEUE_NAME, true);
	}
	/*****************Direct模式 END*********************/

	/*****************Topic模式 START********************/
	@Bean
	public Queue topicQueue1() {
		return new Queue(TOPIC_QUEUE_NAME_1, true);
	}

	@Bean
	public Queue topicQueue2() {
		return new Queue(TOPIC_QUEUE_NAME_2, true);
	}

	@Bean
	public TopicExchange topicExchange() {
		return new TopicExchange(TOPIC_EXCHANGE_NAME);
	}

	@Bean
	public Binding topicBinding1() {
		return BindingBuilder
				.bind(topicQueue1())
				.to(topicExchange())
				.with(TOPIC_ROUTING_KEY_1);
	}

	@Bean
	public Binding topicBinding2() {
		return BindingBuilder
				.bind(topicQueue2())
				.to(topicExchange())
				.with(TOPIC_ROUTING_KEY_2);
	}
	/*****************Topic模式 END**********************/


	/*****************Fanout模式 START*******************/
	@Bean
	public Queue fanoutQueue1() {
		return new Queue(FANOUT_QUEUE_NAME_1, true);
	}

	@Bean
	public Queue fanoutQueue2() {
		return new Queue(FANOUT_QUEUE_NAME_2, true);
	}

	@Bean
	public FanoutExchange fanoutExchange() {
		return new FanoutExchange(FANOUT_EXCHANGE_NAME);
	}

	@Bean
	public Binding fanoutBinding1() {
		return BindingBuilder
				.bind(fanoutQueue1())
				.to(fanoutExchange());
	}

	@Bean
	public Binding fanoutBinding2() {
		return BindingBuilder
				.bind(fanoutQueue2())
				.to(fanoutExchange());
	}
	/*****************Fanout模式 END*********************/

	/*****************Headers模式 START******************/
	@Bean
	public Queue headersQueue() {
		return new Queue(HEADERS_QUEUE_NAME, true);
	}

	@Bean
	public HeadersExchange headersExchange() {
		return new HeadersExchange(HEADERS_EXCHANGE_NAME);
	}

	@Bean
	public Binding headersBinding() {
		Map<String, Object> map = new HashMap<>();
		map.put("h1", "v1");
		map.put("h2", "v2");
		return BindingBuilder
				.bind(headersQueue())
				.to(headersExchange())
				.whereAll(map)
				.match();
	}
	/*****************Headers模式 END********************/
}
