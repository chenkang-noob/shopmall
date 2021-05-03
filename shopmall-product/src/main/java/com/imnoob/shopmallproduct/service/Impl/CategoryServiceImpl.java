package com.imnoob.shopmallproduct.service.Impl;

import com.imnoob.shopmallproduct.mapper.CategoryMapper;
import com.imnoob.shopmallproduct.model.Category;
import com.imnoob.shopmallproduct.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final static String categeoryKey = "categeoryKey";
    private final static String categeoryLockKey = "categeoryLock";

    @Resource
    CategoryMapper categoryMapper;

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @Cacheable(value = {"category"},key = "'categeoryKey'")
    public List<Category> listWithTree(){

        //TODO 缓存击穿 解决方法：redis为空时加锁（分布式）查询数据库并且更新redis
        //TODO 分布式锁 重点加锁 与 删除锁的操作的原子性；考虑死锁的情况。比如断电（设置过期时间）；考虑到删除别人的锁（将value设为UUID删除前先比较）


        String token = UUID.randomUUID().toString();
        List<Category> level1 = null;
        while (true){
            Object s = redisTemplate.opsForValue().get(categeoryKey);
            if (s != null ) return  (List<Category>) s;
            //竞争成功
            if (redisTemplate.opsForValue().setIfAbsent(categeoryLockKey,token,1,TimeUnit.MINUTES)){
                //双重检验
                Object tmp = redisTemplate.opsForValue().get(categeoryKey);
                if (s != null ) return  (List<Category>) s;
                System.out.println("锁竞争成功UUID : "+token);
               try{
                   List<Category> all = categoryMapper.selectList(null);
                   level1 = all.stream().filter(category -> category.getParentCid() == 0).collect(Collectors.toList());
                   for (Category item : level1) {
                       List<Category> leve2 = all.stream().filter(category -> category.getParentCid() == item.getCatId()).collect(Collectors.toList());
                       item.setList(leve2);
                       for (Category item2 : leve2) {
                           List<Category> leve3 = all.stream().filter(category -> category.getParentCid() == item2.getCatId()).collect(Collectors.toList());
                           item2.setList(leve3);
                       }

                   }
                   System.out.println("方法执行完毕 : "+token);
                   redisTemplate.opsForValue().set(categeoryKey,level1,24, TimeUnit.HOURS);
               }finally {
                   System.out.println("锁删除成功UUID : "+token);
                   //TODO lua脚本 原子性释放锁
                    String script = "if redis.call(\"get\",KEYS[1]) == ARGV[1] then return redis.call(\"del\",KEYS[1]) else return 0 end";
                   redisTemplate.execute(new DefaultRedisScript<Long>(script, Long.class), Arrays.asList(categeoryLockKey), token);


                   //TODO 代解决问题 redis集群中，master节点挂掉则会造成多个实例获取锁
                   break;
               }
            }

        }

        return level1;
    }

    public void removeMenuByIds(List<Long> asList) {
        //TODO  1、检查当前删除的菜单，是否被别的地方引用

        //逻辑删除
        categoryMapper.deleteBatchIds(asList);
    }

    public void save(Category category) {
        categoryMapper.insert(category);
    }

    public void updateBatchById(List<Category> asList) {
        //categoryMapper.updateBatchById();
    }
}
