package com.lagou.service.impl;

import com.lagou.service.IMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;


/**
 * 〈〉
 *
 * @author 商玉
 * @create 2022/1/20
 * @since 1.0.0
 */

/**
 * source.class里面就是对输出通道的定义（这是spring cloud Stream内置的通道封装）
 */
@EnableBinding(Source.class)
public class MessageProducerImpl implements IMessageProducer {


    @Autowired
    private Source source;

    @Override
    public void sendMessage(String content) {
        //想mq中发送消息（并不是直接操作mq，应该操作的是spring cloud stream）
        //使用通道向外发出消息（指的是source里面的output通道）
        source.output().send(MessageBuilder.withPayload(content).build());
    }

}
