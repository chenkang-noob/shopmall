package com.imnoob.shopmallkill.service;

import com.imnoob.shopmallkill.model.KillTask;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imnoob.shopmallkill.vo.KillOrderVo;
import com.imnoob.shopmallkill.vo.TaskDetail;

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

    List<KillTask> getInfoByTime(Long start, Long end);

    TaskDetail getDetailInfo(Long id);

    KillOrderVo killOrder(Long id, Long taskid, Long skuid, Integer num, String key);
}
