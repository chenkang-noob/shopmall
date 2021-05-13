package com.imnoob.shopmallkill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imnoob.shopmallkill.mapper.KillSkuMapper;
import com.imnoob.shopmallkill.mapper.KillTaskMapper;
import com.imnoob.shopmallkill.model.KillSku;
import com.imnoob.shopmallkill.model.KillTask;
import com.imnoob.shopmallkill.service.TaskUpService;
import com.imnoob.shopmallkill.to.SkuTo;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskUpServiceImpl implements TaskUpService {

    @Resource
    KillSkuMapper killSkuMapper;

    @Resource
    KillTaskMapper killTaskMapper;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    RedissonClient redissonClient;

//    TODO 常量的抽取
    private final static String SKU_KEY_PREFIX = "sku:";                   //信号量
    private final static String SKU_INFO_KEY_PREFIX = "skuInfo:";         //hashmap 信息
    private final static String KILL_TASK_KEY_PREFIX = "KillTask:";       //秒杀任务 信息


    @Override
    public void upTask(Long taskId) {
        //TODO 将 秒杀任务 添加到redis 中
        //TODO 将 参与的商品加入redis
        //TODO 为每一件商品设置信号量

        HashMap<String, Object> eqal = new HashMap<>();
        eqal.put("id", taskId);
        eqal.put("status", 0);

        KillTask killTask = killTaskMapper.selectOne(new QueryWrapper<KillTask>().allEq(eqal));
        if (killTask == null) {
            //任务已经上架
            return;
        }
        killTask.setStatus(1);
        killTaskMapper.updateById(killTask);

        List<KillSku> skus = killSkuMapper.selectList(new QueryWrapper<KillSku>().eq("task_id", taskId));
        List<SkuTo> items = skus.stream().map(item -> {
            SkuTo skuTo = new SkuTo();
            BeanUtils.copyProperties(item, skuTo);
            String rondomKey = UUID.randomUUID().toString().replace("-", "");
            skuTo.setRandKey(rondomKey);
            redisTemplate.opsForHash().put(SKU_INFO_KEY_PREFIX + taskId,skuTo.getId()+"",skuTo);
            return skuTo;
        }).collect(Collectors.toList());

        //存储 参与商品的 信息

        //存储 秒杀的基本信息
        redisTemplate.opsForValue().set(KILL_TASK_KEY_PREFIX+taskId,killTask);

        //存储 秒杀的信息的信号量
        for (SkuTo item : items) {
            String key = item.getRandKey();
            RSemaphore semaphore = redissonClient.getSemaphore(SKU_KEY_PREFIX + key);
            semaphore.trySetPermits(item.getStock());
        }
    }
}
