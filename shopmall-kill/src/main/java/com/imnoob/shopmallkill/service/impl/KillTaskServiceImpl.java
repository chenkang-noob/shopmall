package com.imnoob.shopmallkill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imnoob.shopmallkill.model.KillTask;
import com.imnoob.shopmallkill.mapper.KillTaskMapper;
import com.imnoob.shopmallkill.service.KillTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenkang
 * @since 2021-05-10
 */
@Service
public class KillTaskServiceImpl extends ServiceImpl<KillTaskMapper, KillTask> implements KillTaskService {

    @Resource
    KillTaskMapper killTaskMapper;

    @Override
    public List<KillTask> selectTask(long timestamp, long timestamp2) {
        List<KillTask> tasks = killTaskMapper.selectList(new QueryWrapper<KillTask>().between("createTime", timestamp, timestamp2));
        return tasks;
    }
}
