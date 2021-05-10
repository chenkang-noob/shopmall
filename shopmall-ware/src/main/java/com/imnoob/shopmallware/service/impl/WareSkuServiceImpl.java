package com.imnoob.shopmallware.service.impl;

import com.imnoob.shopmallcommon.exception.BizCodeEnume;
import com.imnoob.shopmallcommon.exception.CustomizeException;
import com.imnoob.shopmallware.model.WareSku;
import com.imnoob.shopmallware.mapper.WareSkuMapper;
import com.imnoob.shopmallware.service.WareSkuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imnoob.shopmallware.vo.LockWareVo;
import com.imnoob.shopmallware.vo.SkuStockVo;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * ÉÌÆ·¿â´æ 服务实现类
 * </p>
 *
 * @author chenkang
 * @since 2021-04-22
 */
@Service
public class WareSkuServiceImpl extends ServiceImpl<WareSkuMapper, WareSku> implements WareSkuService {

    private static final String LOCK_PREFIX = "lockWare:";
    private static final String STATUS_PREFIX = "orderStatus:";

    @Resource
    WareSkuMapper wareSkuMapper;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @Override
    public List<SkuStockVo> gethasStock(List<Long> skuids) {

        List<SkuStockVo> res = skuids.stream().map(sku -> {
            SkuStockVo skuStockVo = new SkuStockVo();
            skuStockVo.setSkuId(sku);
            Long stock = wareSkuMapper.selectHasStcok(sku);
            skuStockVo.setStorageNum(stock);
            return skuStockVo;
        }).collect(Collectors.toList());

        return res;

    }

    @Transactional
    @Override
    public Boolean lockStock(List<LockWareVo> list) {

        for (LockWareVo item : list) {
            Integer integer = wareSkuMapper.lockStock(item.getSkuId(), item.getNeedNum());
            if (integer == 0) {
                //库存不足 回滚
                throw new CustomizeException(BizCodeEnume.WARE_SHORTAGE);
            }else {
                //锁库存成功 向MQ发送消息
                System.out.println("锁库存成功  发送MQ 消息");
                rabbitTemplate.convertAndSend("stock-event-exchange","delay.route",item);
            }
        }
        String orderSn = list.get(0).getOrderSn();
        for (LockWareVo item : list) {
            redisTemplate.opsForList().leftPush(LOCK_PREFIX + orderSn, item);
        }
        redisTemplate.expire(LOCK_PREFIX + orderSn, 1, TimeUnit.DAYS);

        return true;

    }

    @Override
    public void unlockWare(Long skuId, Integer needNum) {
        wareSkuMapper.unlockWare(skuId, needNum);
        return ;
    }

    @Transactional
    @Override
    public void unlockWare(ArrayList<LockWareVo> lockWareVos) {

        for (LockWareVo item : lockWareVos) {
             unlockWare(item.getSkuId(), item.getNeedNum());
        }

    }
}
