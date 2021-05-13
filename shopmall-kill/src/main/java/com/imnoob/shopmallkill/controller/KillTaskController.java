package com.imnoob.shopmallkill.controller;


import com.imnoob.shopmallcommon.utils.R;
import com.imnoob.shopmallkill.model.KillTask;
import com.imnoob.shopmallkill.service.KillTaskService;
import com.imnoob.shopmallkill.vo.KillOrderVo;
import com.imnoob.shopmallkill.vo.TaskDetail;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chenkang
 * @since 2021-05-10
 */
@RestController
@RequestMapping("/killTask")
public class KillTaskController {

    @Resource
    KillTaskService killTaskService;

    @GetMapping("/taskInfo")
    public R getKillTask(Long start,Long end){
        List<KillTask> list = killTaskService.getInfoByTime(start, end);
        return R.ok().put("tasks", list);
    }

    @GetMapping("/taskInfo/{id}")
    public R detailInfo(@PathVariable("id") Long id){
        TaskDetail detailInfo = killTaskService.getDetailInfo(id);
        return R.ok().put("detailInfo", detailInfo);
    }

    /**
     * 流程：下单 -》 校验时间、随机码、幂等、限购 -》 扣减信号量 -》发送消息 -》创建订单
     */

    @PostMapping("/killOrder")
    public R killOrder(@RequestParam("memberId") Long id,
                       @RequestParam("taskId") Long taskid,
                       @RequestParam("skuId") Long skuid,
                       @RequestParam("num") Integer num,
                       @RequestParam("key") String key){

        KillOrderVo killOrderVo = killTaskService.killOrder(id, taskid, skuid, num,key);
        if (killOrderVo == null) return R.error("秒杀失败");
        return R.ok().put("killOrder", killOrderVo);
    }

}

