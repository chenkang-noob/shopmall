# shopmall
>这是一个微服务电商项目
g
>

关注点：
接口幂等性
限流策略
redis序列化策略
数据一致性
redis实现分布式锁
缓存击穿、缓存穿透、缓存雪崩

### 数据一致性
>常规数据使用spring-cache进行redis操作，特殊数据则需要考虑数据一致性，并发等问题则需要自定义逻辑


解决方案：
<ol>
<li>对于经常修改的数据则不设置缓存</li>
<li>对于并发量小的数据，且允许短时间缓存不一致采用最终一致性策略。</li>
<li>使用读写锁，只允许一个线程进行数据、缓存的修改</li>
</ol>

### redis实现分布式锁
方案一：
使用redis的setnx命令和lua脚本实现 redis单机的分布式锁。（集群情况下可能会出错）
重点：
<ol>
<li>加锁与解锁都要是原子性操作</li>
<li>避免各种情况下的死锁（比如断电）</li>
<li>避免错误的解锁（使用类似版本号的操作）</li>
<li>使用双重检验操作只需要访问一次数据库，以及最小的锁竞争</li>
</ol>

~~~java
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
~~~

方案二：使用redission框架，操作方式和JUC相差不多


### 高并发下的选择 强一致性 弱一致性 最终一致性 (柔性事务)

### CAP raft理论

### 提高响应速度---异步编程

### MQ消息 消息丢失 消息挤压 重复消费
消息丢失： 
1、消息未抵达服务器 ：try catch 做好日志记录
2、消息抵达服务器broker未持久化 ：利用rabbitmq的确认机制
3、自动ack的情况下，消费者未消费完就ack

重复消费
1、消费成功时重复消费； 做好业务的幂等性
2、消费失败时：允许重复消费

消费积压
1、上线更多消费者
2、消费消息进入数据库，离线处理


### 秒杀设计
1、提前上架商品到缓存中