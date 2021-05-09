package com.imnoob.shopmallorder.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
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
        argsmap.put("x-dead-letter-routing-key", "checkOrder.route");
        return  new Queue("order.delay.queue",true,false,false,argsmap);
    }

    //处理队列
    @Bean
    public Queue stockReleaseQueue(){
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
