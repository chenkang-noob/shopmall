package com.imnoob.shopmallkill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imnoob.shopmallkill.mapper.KillSkuMapper;
import com.imnoob.shopmallkill.mapper.KillTaskMapper;
import com.imnoob.shopmallkill.model.KillSku;
import com.imnoob.shopmallkill.model.KillTask;
import com.imnoob.shopmallkill.service.TaskUpService;
import com.imnoob.shopmallkill.to.SkuTo;
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

    private final static String SKU_KEY_PREFIX = "sku:";
    private final static String SKU_INFO_KEY_PREFIX = "skuInfo:";
    private final static String KILL_TASK_KEY_PREFIX = "skuInfo:";


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
            return skuTo;
        }).collect(Collectors.toList());

        //存储 参与商品的 信息
        redisTemplate.opsForList().leftPushAll(SKU_INFO_KEY_PREFIX + taskId, items);

        //存储 秒杀的基本信息
        redisTemplate.opsForValue().set(KILL_TASK_KEY_PREFIX+taskId,killTask);

        //存储 秒杀的信息的信号量
        for (SkuTo item : items) {
            redisTemplate.opsForValue().set(SKU_KEY_PREFIX+item.getRandKey(),item.getStock());
        }
    }
}
