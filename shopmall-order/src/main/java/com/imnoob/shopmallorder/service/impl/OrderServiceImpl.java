package com.imnoob.shopmallorder.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imnoob.shopmallcommon.exception.BizCodeEnume;
import com.imnoob.shopmallcommon.exception.CustomizeException;
import com.imnoob.shopmallcommon.utils.AjaxResult;
import com.imnoob.shopmallcommon.utils.R;
import com.imnoob.shopmallcommon.vo.rabbitTo.KillOrderTo;
import com.imnoob.shopmallcommon.vo.rabbitTo.OrderTo;
import com.imnoob.shopmallorder.fegin.ProductFeign;
import com.imnoob.shopmallorder.fegin.WareFeign;
import com.imnoob.shopmallorder.mapper.OrderItemMapper;
import com.imnoob.shopmallorder.model.Order;
import com.imnoob.shopmallorder.mapper.OrderMapper;
import com.imnoob.shopmallorder.model.OrderItem;
import com.imnoob.shopmallorder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imnoob.shopmallorder.vo.*;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * <p>
 * ¶©µ¥ 服务实现类
 * </p>
 *
 * @author chenkang
 * @since 2021-05-05
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Resource
    ProductFeign productFeign;

    @Resource
    OrderItemMapper orderItemMapper;

    @Resource
    WareFeign wareFeign;

    @Resource
    RabbitTemplate rabbitTemplate;

    @Resource
    OrderMapper orderMapper;

    //TODO 高并发下不适用分布式事务， 降低一致性要求 使用最终一致性的策略
//    @GlobalTransactional
    @Transactional
    @Override
    public Order createOrder(Long memberId, OrderConfirmVo orderinfo) {

        //构造订单
        Order order = new Order();
        order.setMemberId(memberId);
        order.setCreateTime(new Date(System.currentTimeMillis()));
        order.setStatus(1);
        order.setOrderSn(UUID.randomUUID().toString().replace("-",""));


        //TODO 锁定库存 分布式事务
        List<CartItem> orderItemVos = orderinfo.getOrderItemVos();
        List<LockWareVo> list = orderItemVos.stream().map(item -> {
            LockWareVo tmp = new LockWareVo();
            tmp.setSkuId(item.getSkuId());
            tmp.setNeedNum(item.getNum());
            tmp.setOrderSn(order.getOrderSn());
            return tmp;
        }).collect(Collectors.toList());
        R r = wareFeign.lockStock(list);
        if (r.getCode().equals(BizCodeEnume.WARE_SHORTAGE.getCode())){
            throw new CustomizeException(BizCodeEnume.CREATE_ORDER_ERROE);
        }


        MemberAddressVo memberAddressVo = orderinfo.getAddressVoList().get(0);
        BeanUtils.copyProperties(order, memberAddressVo);
        this.baseMapper.insert(order);


        //构造订单项
        buildorderItems(order,orderinfo);

        //发送消息
        OrderTo orderTo = new OrderTo();
        orderTo.setCreateTime(order.getCreateTime());
        orderTo.setId(order.getId());
        orderTo.setStatus(order.getStatus());
        orderTo.setOrderSn(order.getOrderSn());
        try {
            rabbitTemplate.convertAndSend("order-event-exchange","order.delay.route", orderTo);
        } catch (AmqpException e) {
            //消息发送失败 做好日志记录
            e.printStackTrace();
        }

        return order;

    }

    //为秒杀 商品构建订单
    @Transactional
    @Override
    public Order createOrder(KillOrderTo killOrderTo) {

        //构造订单
        Order order = new Order();
        order.setOrderSn(killOrderTo.getOrderSn());
        order.setMemberId(killOrderTo.getMemberId());
        order.setCreateTime(new java.util.Date(System.currentTimeMillis()));
        order.setTotalAmount(killOrderTo.getKillPrice().multiply(new BigDecimal(killOrderTo.getNum())));


        //....其他属性赋值 忽略
        orderMapper.insert(order);
        buildorderItems(killOrderTo);
        return order;

    }
    @Transactional
    public List<OrderItem> buildorderItems(KillOrderTo killOrderTo) {
        OrderItem orderItem = new OrderItem();
        orderItem.setSkuId(killOrderTo.getSkuId());
        orderItem.setSkuPrice(killOrderTo.getKillPrice());
        orderItem.setSkuQuantity(killOrderTo.getNum());
        orderItem.setSkuName(killOrderTo.getSkuName());

        ArrayList<OrderItem> res = new ArrayList<>();
        res.add(orderItem);
        return res;
    }

    //创建每一个商品的 明细
    @Transactional
    public List<OrderItem> buildorderItems(Order order, OrderConfirmVo orderinfo) {
        List<CartItem> orderItemVos = orderinfo.getOrderItemVos();
        ArrayList<OrderItem> orderItems = new ArrayList<>();
        for (CartItem item : orderItemVos) {
            OrderItem orderItem = new OrderItem();
            orderItem.setSkuId(item.getSkuId());
            orderItem.setOrderId(order.getId());
            orderItem.setOrderSn(order.getOrderSn());
            orderItem.setSkuQuantity(item.getNum());

            //查询spu的信息 和 sku信息
            AjaxResult<Map<String, Object>> mapAjaxResult = productFeign.spuInfo(item.getSkuId());
            Map<String, Object> data = mapAjaxResult.getData();
            Object skuinfo = data.get("skuinfo");
            Object spuinfo =  data.get("spuinfo");

            SkuInfoVo skuInfoVo = JSON.parseObject(JSON.toJSONString(skuinfo), new TypeReference<SkuInfoVo>() {});
            SpuInfoVo spuInfoVo = JSON.parseObject(JSON.toJSONString(spuinfo), new TypeReference<SpuInfoVo>() {});



            orderItem.setSkuId(skuInfoVo.getSkuId());
            orderItem.setSkuName(skuInfoVo.getSkuName());
            orderItem.setSkuPic(skuInfoVo.getSkuDefaultImg());
            orderItem.setSkuPrice(skuInfoVo.getPrice());

            orderItem.setSpuId(spuInfoVo.getId());
            orderItem.setSpuName(spuInfoVo.getSpuName());

            orderItemMapper.insert(orderItem);
            orderItems.add(orderItem);
        }

        return orderItems;
    }

    //根据订单号查询 订单信息
    public Order queryByOrderSn(String orderSn){
        return this.baseMapper.selectOne(new QueryWrapper<Order>().eq("order_sn", orderSn));
    }

    @Override
    public Integer payOrder(String orderSn) {
        Integer integer = orderMapper.payByOrderSn(orderSn);
        return integer;
    }

    public Integer expiredOrder(String orderSn){
        return orderMapper.expiredOrder(orderSn);
    }

}
