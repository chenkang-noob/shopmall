package com.imnoob.shopmallorder.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.ClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class RabbitConfig implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnCallback {

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


    /**
     * 秒杀相关队列
     */
    //一个队列接收消息 生成订单
    // 一个队列作为延迟队列
    // 一个队列支付检查 回滚操作

    //声明主题发布交换机
    @Bean
    public Exchange killRollbackExchange(){
        return  new TopicExchange("killorder-event-exchange",true,false);
    }


    //处理队列
    @Bean
    public Queue killdealQueue(){

        return  new Queue("killorder.deal.queue",true,false,false,null);
    }


//    绑定订单 处理队列
    @Bean
    public Binding BindingkilldealQueue(){
        return new Binding("killorder.deal.queue", Binding.DestinationType.QUEUE,"killorder-event-exchange","killorder.deal.route",null);
    }


    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        log.info("消息发送失败原因：" + s);
        log.info("消息唯一标识：{}",correlationData);
    }

    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        log.info("消息主体: {}", message);
        log.info("回复编码: {}", i);
        log.info("回复内容: {}", s);
        log.info("交换器: {}", s1);
        log.info("路由键: {}", s2);
    }
}
