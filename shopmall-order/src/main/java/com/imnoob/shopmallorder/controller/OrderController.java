package com.imnoob.shopmallorder.controller;


import com.imnoob.shopmallcommon.utils.R;
import com.imnoob.shopmallorder.fegin.WareFeign;
import com.imnoob.shopmallorder.model.Order;
import com.imnoob.shopmallorder.service.OrderConfirmService;
import com.imnoob.shopmallorder.service.OrderService;
import com.imnoob.shopmallorder.vo.CartItem;
import com.imnoob.shopmallorder.vo.OrderConfirmVo;
import com.imnoob.shopmallorder.vo.SkuStockVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * ¶©µ¥ 前端控制器
 * </p>
 *
 * @author chenkang
 * @since 2021-05-05
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    OrderConfirmService orderConfirmService;

    @Resource
    OrderService orderService;

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @Resource
    WareFeign wareFeign;

    //TODO 常量抽取
    private final static String ORDER_TOKEN_PREFIX = "ORDER_USER::";

    //确认订单页面所需要的信息 和token
    @PostMapping("/confirorder")
    public R createOrder(Long memberId,Long[] skuids){
        OrderConfirmVo orderInfo = orderConfirmService.getOrderInfo(memberId, skuids);

//        TODO token解决幂等性
        String token = UUID.randomUUID().toString().replace("-", "");
        orderInfo.setOrderToken(token);
        redisTemplate.opsForValue().set(ORDER_TOKEN_PREFIX+memberId,token,30, TimeUnit.MINUTES);

        Object str = redisTemplate.opsForValue().get(ORDER_TOKEN_PREFIX + memberId);
        System.out.println("Key: " +ORDER_TOKEN_PREFIX + memberId+"token : " + str);

        //查询对应商品库存库存
        List<SkuStockVo> skuStockVos = wareFeign.gethasStock(Arrays.asList(skuids));
        Map<Long, Long> stockMap = skuStockVos.stream().collect(Collectors.toMap(SkuStockVo::getSkuId, SkuStockVo::getStorageNum));


        return R.ok().put("orderInfo", orderInfo).put("stockMap",stockMap);
    }

    @PostMapping("/createOrder")
    public R createOrder(Long memberId,@RequestBody OrderConfirmVo orderConfirmVo){
        String key = ORDER_TOKEN_PREFIX+memberId;
        String token = orderConfirmVo.getOrderToken();
        String luaScript = "if redis.call ('get',KEYS[1]) == ARGV[1] " +
                                "then return redis.call('del',KEYS[1]) " +
                            "else return 0 end";

        Long res = redisTemplate.execute(new DefaultRedisScript<Long>(luaScript, Long.class), Arrays.asList(key), token);
        Order order = null;
        if (res == 0){
            //验证失败 重复提交  获取长时间为确认提交订单
            return R.error("请勿重复提交");
        }else{
        //创建订单
            order = orderService.createOrder(memberId, orderConfirmVo);
        }

        return R.ok().put("orderInfo",order);
    }

    @GetMapping("/info/orderSn")
    public R queryOrderByOrderSn(@RequestParam("orderSn") String orderSn){
        Order order = orderService.queryByOrderSn(orderSn);
        return R.ok().put("orderInfo", order);
    }

}

