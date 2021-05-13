package com.imnoob.shopmallkill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imnoob.shopmallcommon.vo.rabbitTo.KillOrderTo;
import com.imnoob.shopmallkill.mapper.KillSkuMapper;
import com.imnoob.shopmallkill.model.KillSku;
import com.imnoob.shopmallkill.model.KillTask;
import com.imnoob.shopmallkill.mapper.KillTaskMapper;
import com.imnoob.shopmallkill.service.KillTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imnoob.shopmallkill.to.SkuTo;
import com.imnoob.shopmallkill.vo.KillOrderVo;
import com.imnoob.shopmallkill.vo.TaskDetail;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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


    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    RabbitTemplate rabbitTemplate;

    private final static String SKU_KEY_PREFIX = "sku:";
    private final static String SKU_INFO_KEY_PREFIX = "skuInfo:";
    private final static String KILL_TASK_KEY_PREFIX = "KillTask:";
    private final static String LIMIT_KEY_PREFIX = "limit:";

    @Override

    public List<KillTask> selectTask(long timestamp, long timestamp2) {
        List<KillTask> tasks = killTaskMapper.selectList(new QueryWrapper<KillTask>().between("startTime", timestamp, timestamp2).eq("status",0));
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

        List<Object> range = redisTemplate.opsForHash().values(SKU_INFO_KEY_PREFIX + id);
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

    /**
     * 流程：下单 -》 校验时间、随机码、幂等、限购 -》 扣减信号量 -》发送消息 -》创建订单
     */
    @Override
    public KillOrderVo killOrder(Long id, Long taskid, Long skuid, Integer num, String key) {

        KillTask o = (KillTask) redisTemplate.opsForValue().get(KILL_TASK_KEY_PREFIX + taskid);

        //校验时间
        if (o == null || o.getStarttime() > System.currentTimeMillis() || o.getEndtime()<System.currentTimeMillis()){
            return null;
        }

        //同一款商品只准参与一次秒杀  幂等
        String limitKey = LIMIT_KEY_PREFIX + id + "_" + skuid;
        Boolean isReOrder = redisTemplate.opsForValue().setIfAbsent(limitKey, 1, o.getEndtime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        if (!isReOrder){
            return null;
        }

        //TODO 限购

        //凭借随机码扣减
        RSemaphore semaphore = redissonClient.getSemaphore(SKU_KEY_PREFIX + key);

        boolean b = semaphore.tryAcquire(num);
        if (b){
            SkuTo o1 = (SkuTo) redisTemplate.opsForHash().get(SKU_INFO_KEY_PREFIX + taskid, skuid+"");
            if (o1 == null ) return null;
            KillOrderVo killOrderVo = new KillOrderVo();
            killOrderVo.setTaskId(taskid);
            killOrderVo.setKillName(o.getKillName());
            killOrderVo.setKillPrice(o1.getKillPrice());
            killOrderVo.setNum(num);
            killOrderVo.setSkuName(o1.getSkuName());
            killOrderVo.setOrderSn(UUID.randomUUID().toString().replace("-",""));
            killOrderVo.setMemberId(id);

            //TODO 消息队列给订单服务 创建订单
            KillOrderTo killOrderTo = new KillOrderTo();
            BeanUtils.copyProperties(killOrderVo, killOrderTo);
            rabbitTemplate.convertAndSend("killorder-event-exchange","killorder.deal.route",killOrderTo);
            return killOrderVo;
        }
        return null;



    }
}
