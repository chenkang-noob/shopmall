package com.imnoob.shopmallorder.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.ClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {

    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }





    //声明主题发布交换机
    @Bean
    public Exchange wareRollbackExchange(){
        return  new TopicExchange("order-event-exchange",true,false);
    }

    //延迟队列
    @Bean
    public Queue delayQueue(){
        Map<String, Object> argsmap = new HashMap<>();
        argsmap.put("x-message-ttl", 60*1000);
        argsmap.put("x-dead-letter-exchange", "order-event-exchange");
        argsmap.put("x-dead-letter-routing-key", "order.deal.route");
        return  new Queue("order.delay.queue",true,false,false,argsmap);
    }

    //处理队列
    @Bean
    public Queue dealQueue(){
        return  new Queue("order.deal.queue",true,false,false,null);
    }


    @Bean
    public Binding BindingDelayQueue(){
        return new Binding("order.delay.queue", Binding.DestinationType.QUEUE,"order-event-exchange","order.delay.route",null);
    }

    @Bean
    public Binding BindingReleaseQueue(){
        return new Binding("order.deal.queue", Binding.DestinationType.QUEUE,"order-event-exchange","order.deal.route",null);
    }
}