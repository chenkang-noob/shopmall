package com.imnoob.shopmallorder.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.imnoob.shopmallcommon.utils.R;
import com.imnoob.shopmallorder.model.Order;
import com.imnoob.shopmallorder.service.OrderService;
import com.imnoob.shopmallorder.vo.OrderVo;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

@Component
@RabbitListener(queues = "order.deal.queue")
public class MQlistener {

    @Resource
    OrderService orderService;

    @Autowired
    RabbitTemplate rabbitTemplate;



    @RabbitHandler
    public void releaseStock(Message message, OrderVo orderVo ,Channel channel){
        //查询订单，如果订单支付成功 则查询库存 看库存的消息是否处理过 处理了则回滚，回滚则补偿

        Order order = orderService.queryByOrderSn(orderVo.getOrderSn());
        if (order != null){
            if  (order.getStatus() == 1){
                order.setStatus(3);
                orderService.updateById(order);
            }
            rabbitTemplate.convertAndSend("stock-event-exchange","release.route",order);
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
