package com.imnoob.shopmallkill.task;

import com.imnoob.shopmallkill.model.KillTask;
import com.imnoob.shopmallkill.service.KillTaskService;
import com.imnoob.shopmallkill.service.TaskUpService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class UpSkuTask {

    @Resource
    TaskUpService taskUpService;

    @Resource
    KillTaskService killTaskService;

    @Resource
    RedissonClient redissonClient;

//    * * 3 * * ? *
// 每天凌晨三点进行扫描 同步秒杀数据进入redis
    @Async
    @Scheduled(cron = "1 * * * * * ")
    public void upKillTask(){

        LocalDate localDate = LocalDate.now();
        LocalDate bigin = localDate.plusDays(1);
        LocalDate end = localDate.plusDays(3);
        long timestamp = bigin.atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli();
        long timestamp2 = end.atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli();

        List<KillTask> task = killTaskService.selectTask(timestamp, timestamp2);

        for (KillTask killTask : task) {
            //加锁 锁的粒度？
            RLock lock = redissonClient.getLock("taskLock:" + killTask.getId());
            try {
                //设置超时让更多的 定时任务同时运行
                boolean b = lock.tryLock(2, TimeUnit.SECONDS);
                if (b){
                    taskUpService.upTask(killTask.getId());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }

        }

    }
}
