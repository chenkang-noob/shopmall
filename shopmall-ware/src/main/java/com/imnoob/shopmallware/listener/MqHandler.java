package com.imnoob.shopmallware.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.imnoob.shopmallcommon.utils.R;
import com.imnoob.shopmallware.feign.OrderFeign;
import com.imnoob.shopmallware.service.WareSkuService;
import com.imnoob.shopmallware.vo.LockWareVo;
import com.imnoob.shopmallware.vo.OrderVo;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RabbitListener(queues = "stock.release.queue")
@Component
public class MqHandler {

    private static final String LOCK_PREFIX = "lockWare:";
    private static final String STATUS_PREFIX = "orderStatus:";

    @Resource
    OrderFeign orderFeign;

    @Resource
    WareSkuService wareSkuService;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;



    @RabbitHandler

    //因为未使用seata做分布式事务，所以本监听主要处理 库存锁定 订单创建失败情况下的 库存回滚
    //如果订单创建了 则交给下面的监听处理
    public void releaseStock(Message message, LockWareVo vo, Channel channel) {
        /**
         * 情况1：当 订单不存在的时候 解锁库存
         * 情况2： 当订单的状态为取消 或 回退时 解锁库存
         */
        System.out.println("回滚情况的消息处理");
        R r = orderFeign.queryOrderByOrderSn(vo.getOrderSn());
        if (r.getCode() == 0){
            Object orderInfo = r.get("orderInfo");
            Integer status = (Integer) redisTemplate.opsForValue().get(STATUS_PREFIX + vo.getOrderSn());
            if (orderInfo == null) {
                wareSkuService.unlockWare(vo.getSkuId(), vo.getNeedNum());
                try {
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else {
            //远程调用失败 不能确认消息 并且重新入队
            try {
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
            } catch (IOException e) {
                System.out.println("消息拒绝异常");
                e.printStackTrace();
            }
        }


    }

    //下面的监听 用于处理 订单已经创建 但是用户取消支付 或则超时取消订单时  库存锁定的回滚
    @RabbitHandler
    public void releaseStock(Message message, OrderVo vo, Channel channel) {

        R r = orderFeign.queryOrderByOrderSn(vo.getOrderSn());
        Object orderInfo = r.get("orderInfo");
        OrderVo orderVo = JSON.parseObject(JSON.toJSONString(orderInfo), new TypeReference<OrderVo>() {
        });
        Integer status = (Integer) redisTemplate.opsForValue().get(STATUS_PREFIX + orderVo.getOrderSn());
        if (r.getCode() == 0) {
            if (orderVo.getStatus() != 2) {
                if (status != null && !(status == 0 || status == 2)){
                    //未支付  回滚
                    List<Object> items = redisTemplate.opsForList().range(LOCK_PREFIX + orderVo.getOrderSn(), 1, -1);
                    ArrayList<LockWareVo> lockWareVos = new ArrayList<>();
                    for (Object item : items) {
                        if (item instanceof LockWareVo) lockWareVos.add((LockWareVo) item);
                    }
                    wareSkuService.unlockWare(lockWareVos);
                }
            } else {
                //TODO 支付成功 扣除库存

            }
            try {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //远程调用失败 不能确认消息 并且重新入队
            try {
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
            } catch (IOException e) {
                System.out.println("消息拒绝异常");
                e.printStackTrace();
            }
        }


    }

}
