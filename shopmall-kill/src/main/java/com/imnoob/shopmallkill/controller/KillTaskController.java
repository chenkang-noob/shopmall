package com.imnoob.shopmallkill.controller;


import com.imnoob.shopmallcommon.utils.R;
import com.imnoob.shopmallkill.model.KillTask;
import com.imnoob.shopmallkill.service.KillTaskService;
import com.imnoob.shopmallkill.vo.TaskDetail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

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

}

