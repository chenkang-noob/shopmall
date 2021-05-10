package com.imnoob.shopmallkill.service;

import com.imnoob.shopmallkill.model.KillTask;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenkang
 * @since 2021-05-10
 */
public interface KillTaskService extends IService<KillTask> {

    List<KillTask> selectTask(long timestamp, long timestamp2);
}
