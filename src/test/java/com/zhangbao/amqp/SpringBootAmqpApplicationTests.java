package com.zhangbao.amqp;

import com.zhangbao.amqp.spring.producer.Producer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootAmqpApplicationTests {

	@Autowired
	private Producer producer;

	@Test
	public void contextLoads() {
		producer.sendDirect();
		producer.sendTopic();
		producer.sendFanout();
		producer.sendHeaders();
	}

}
