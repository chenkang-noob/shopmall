package com.imnoob.shopmallkill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imnoob.shopmallkill.mapper.KillSkuMapper;
import com.imnoob.shopmallkill.model.KillSku;
import com.imnoob.shopmallkill.model.KillTask;
import com.imnoob.shopmallkill.mapper.KillTaskMapper;
import com.imnoob.shopmallkill.service.KillTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imnoob.shopmallkill.to.SkuTo;
import com.imnoob.shopmallkill.vo.TaskDetail;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author chenkang
 * @since 2021-05-10
 */
@Service
public class KillTaskServiceImpl extends ServiceImpl<KillTaskMapper, KillTask> implements KillTaskService {

    @Resource
    KillTaskMapper killTaskMapper;

    @Resource
    KillSkuMapper killSkuMapper;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    private final static String SKU_INFO_KEY_PREFIX = "skuInfo:";

    @Override

    public List<KillTask> selectTask(long timestamp, long timestamp2) {
        List<KillTask> tasks = killTaskMapper.selectList(new QueryWrapper<KillTask>().between("startTime", timestamp, timestamp2));
        return tasks;
    }

    @Override
    public List<KillTask> getInfoByTime(Long start, Long end) {
        List<KillTask> tasks = killTaskMapper.selectList(new QueryWrapper<KillTask>().and(i -> i.le("endTime", end).ge("startTime", start)));
        return tasks;
    }

    @Override
    public TaskDetail getDetailInfo(Long id) {
        KillTask killTask = killTaskMapper.selectById(id);

        List<Object> range = redisTemplate.opsForList().range(SKU_INFO_KEY_PREFIX + id, 0, -1);
        List<SkuTo> skus = new ArrayList<>();
        for (Object o : range) {
            if (o instanceof SkuTo){
                SkuTo item = (SkuTo) o;
                if (killTask.getStarttime() > System.currentTimeMillis() || killTask.getEndtime() < System.currentTimeMillis()){
                    item.setRandKey("");  //按时间暴露 随机key
                }
                skus.add(item);
            }
        }
        TaskDetail taskDetail = new TaskDetail();
        BeanUtils.copyProperties(killTask, taskDetail);
        taskDetail.setSkus(skus);
        return taskDetail;
    }
}
