package com.imnoob.shopmallorder.listener;

import com.imnoob.shopmallcommon.vo.rabbitTo.KillOrderTo;
import com.imnoob.shopmallcommon.vo.rabbitTo.OrderTo;
import com.imnoob.shopmallorder.model.Order;
import com.imnoob.shopmallorder.service.OrderService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

@Component
@RabbitListener(queues = {"killorder.deal.queue"})
public class KillOrderListener {

    @Resource
    OrderService orderService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @RabbitHandler
    public void dealKillOrder(Message message, KillOrderTo killOrderTo, Channel channel){

        Order order = orderService.createOrder(killOrderTo);
        if (order != null){


            try {
                OrderTo orderTo = new OrderTo();
                orderTo.setCreateTime(order.getCreateTime());
                orderTo.setId(order.getId());
                orderTo.setStatus(order.getStatus());
                orderTo.setOrderSn(order.getOrderSn());

                rabbitTemplate.convertAndSend("order-event-exchange","order.delay.route", orderTo);
                channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            } catch (IOException e) {
                System.out.println("消息发送错误");
                e.printStackTrace();
            }
        }


    }
}
