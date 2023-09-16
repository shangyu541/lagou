package com.lagou.service.impl;

import com.lagou.LagouCloudStreamProducerApplication;
import com.lagou.service.IMessageProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest(classes = {LagouCloudStreamProducerApplication.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class MessageProducerImplTest {

    @Autowired
    private IMessageProducer iMessageProducer;

    @Test
    public void testSendMessage() {
        iMessageProducer.sendMessage("hello world-lagou101");
    }

}