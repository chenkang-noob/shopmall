package com.imnoob.shopmallkill.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.redisson.Redisson;

import org.redisson.config.Config;

import java.io.IOException;
import java.net.UnknownHostException;

@Configuration
public class RedisConfig {

    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<String, Object> template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);

        // 定义Jackson2JsonRedisSerializer序列化对象
        Jackson2JsonRedisSerializer<Object> jacksonSeial = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jacksonSeial.setObjectMapper(om);

        StringRedisSerializer stringSerial = new StringRedisSerializer();

        // redis key 序列化方式使用stringSerial
        template.setKeySerializer(stringSerial);
        // redis value 序列化方式使用jackson
        template.setValueSerializer(jacksonSeial);

        // redis hash key 序列化方式使用stringSerial
        template.setHashKeySerializer(stringSerial);

        // redis hash value 序列化方式使用jackson
        template.setHashValueSerializer(jacksonSeial);

        return template;
    }

    @Bean
    public RedissonClient redisson() throws IOException {
        //1 创建配置
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.210.128:6379");
        //2.根据Config创建出RedissonClient
        return Redisson.create(config);

        /**
         * config.useClusterServers()
         *     .setScanInterval(2000) // 集群状态扫描间隔时间，单位是毫秒
         *     //可以用"rediss://"来启用SSL连接
         *     .addNodeAddress("redis://127.0.0.1:7000", "redis://127.0.0.1:7001")
         *     .addNodeAddress("redis://127.0.0.1:7002");
         */
    }

}
