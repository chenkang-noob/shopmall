package com.imnoob.shopmallorder.controller;


import com.imnoob.shopmallcommon.exception.BizCodeEnume;
import com.imnoob.shopmallcommon.exception.CustomizeException;
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

        String token = UUID.randomUUID().toString().replace("-", "");
        orderInfo.setOrderToken(token);
        redisTemplate.opsForValue().set(ORDER_TOKEN_PREFIX+token,memberId,30, TimeUnit.MINUTES);


        //查询对应商品库存库存
        List<SkuStockVo> skuStockVos = wareFeign.gethasStock(Arrays.asList(skuids));
        Map<Long, Long> stockMap = skuStockVos.stream().collect(Collectors.toMap(SkuStockVo::getSkuId, SkuStockVo::getStorageNum));


        return R.ok().put("orderInfo", orderInfo).put("stockMap",stockMap);
    }

    @PostMapping("/createOrder")
    public R createOrder(Long memberId,@RequestBody OrderConfirmVo orderConfirmVo){

        String token = orderConfirmVo.getOrderToken();
        String key = ORDER_TOKEN_PREFIX+token;
        String luaScript = "if redis.call ('get',KEYS[1]) == ARGV[1] " +
                                "then return redis.call('del',KEYS[1]) " +
                            "else return 0 end";
        Long res = redisTemplate.execute(new DefaultRedisScript<Long>(luaScript, Long.class), Arrays.asList(key), memberId);
        Order order = null;
        if (res == 0){
            return R.error("请勿重复订单提交");
        }else{
            order = orderService.createOrder(memberId, orderConfirmVo);
        }

        return R.ok().put("orderInfo",order);
    }

    @PostMapping("/payOrder")
    public R payOrder(String orderSn){
        Integer res = orderService.payOrder(orderSn);
        if (res == 0){
            throw new CustomizeException(BizCodeEnume.PAY_ERROE);
        }else{
            return R.ok("支付成功");
        }

    }

    @GetMapping("/info/orderSn")
    public R queryOrderByOrderSn(@RequestParam("orderSn")String orderSn){
        Order order = orderService.queryByOrderSn(orderSn);
        return R.ok().put("orderInfo", order);
    }

}

