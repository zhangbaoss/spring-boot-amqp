package com.zhangbao.amqp.utils;

/**
 * @author zhangbao
 * @date 2019-02-21
 */
public interface Constants {

	String DIRECT_QUEUE_NAME = "test_zhangbao_direct_queue";

	String TOPIC_QUEUE_NAME_1 = "test_zhangbao_topic_queue_1";
	String TOPIC_QUEUE_NAME_2 = "test_zhangbao_topic_queue_2";
	String TOPIC_EXCHANGE_NAME = "test_zhangbao_topic_exchange";
	String TOPIC_ROUTING_KEY_1 = "topic.key";
	String TOPIC_ROUTING_KEY_2 = "topic.*";

	String FANOUT_QUEUE_NAME_1 = "test_zhangbao_fanout_queue_1";
	String FANOUT_QUEUE_NAME_2 = "test_zhangbao_fanout_queue_2";
	String FANOUT_EXCHANGE_NAME = "test_zhangbao_fanout_exchange";

	String HEADERS_QUEUE_NAME = "test_zhangbao_headers_queue";
	String HEADERS_EXCHANGE_NAME = "test_zhangbao_headers_exchange";

}
