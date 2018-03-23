package com.jst.reward.service.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jst.reward.rabbit.DemoQueue;
import com.jst.reward.service.test.interfaces.TestMqService;

@Service
public class TestMqServiceImpl implements TestMqService {
	private final static Logger log = LoggerFactory.getLogger(TestMqServiceImpl.class);
	 
	@Override
	public void testMq(String str) {
		DemoQueue.send(str);
	}
}
