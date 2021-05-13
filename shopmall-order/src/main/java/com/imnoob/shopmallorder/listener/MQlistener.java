package com.imnoob.shopmallorder.listener;

import com.imnoob.shopmallcommon.vo.rabbitTo.KillOrderTo;
import com.imnoob.shopmallcommon.vo.rabbitTo.OrderTo;
import com.imnoob.shopmallorder.mapper.OrderMapper;
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
import java.math.BigDecimal;
import java.util.Date;

@Component
@RabbitListener(queues = "order.deal.queue")
public class MQlistener {

    @Resource
    OrderService orderService;

    @Autowired
    RabbitTemplate rabbitTemplate;




    @RabbitHandler
    public void dealExpireOrder(Message message, OrderTo orderTo, Channel channel){
        //查询订单，如果订单支付成功 则查询库存 看库存的消息是否处理过 处理了则回滚，回滚则补偿

        Order order = orderService.queryByOrderSn(orderTo.getOrderSn());
        if (order != null){
            if  (order.getStatus() == 1){
                System.out.println("超时未支付");
                order.setStatus(3);
                //TODO 幂等修改状态
                Integer integer = orderService.expiredOrder(order.getOrderSn());
            }
            OrderTo tmp = new OrderTo();
            tmp.setCreateTime(order.getCreateTime());
            tmp.setId(order.getId());
            tmp.setStatus(order.getStatus());
            tmp.setOrderSn(order.getOrderSn());

            System.out.println("发送消息");
            rabbitTemplate.convertAndSend("stock-event-exchange","release.route",tmp);
            try {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            } catch (IOException e) {
                System.out.println("消息发送错误");
                e.printStackTrace();
            }
        }

        //如果订单未支付 则通知库存呢队列 释放库存
    }



}
