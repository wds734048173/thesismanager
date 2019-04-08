package org.lanqiao.thesismanager.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: WDS
 * @Date: 2019/1/10 16:40
 * @Description:
 */
@Configuration
public class JsonMessage {
    //消息json化
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

}
