package  team.CowsAndHorses.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
/**
 * @author Patrick_Star
 * @version 1.0
 */
@Configuration
public class RedisConfig {
    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {
        // 序列化方式
        Jackson2JsonRedisSerializer<Object> jsonRedisSerializer = getJsonRedisSerializer();
        RedisSerializationContext.SerializationPair<Object> serializationPair =
                RedisSerializationContext.SerializationPair.fromSerializer(jsonRedisSerializer);
        // 默认的缓存配置
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(serializationPair);

        return RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory))
                .cacheDefaults(redisCacheConfiguration)
                .transactionAware()
                .build();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        Jackson2JsonRedisSerializer<Object> jsonRedisSerializer = getJsonRedisSerializer();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson
        template.setValueSerializer(jsonRedisSerializer);
        // hash的value序列化方式采用jackson
        template.setHashValueSerializer(jsonRedisSerializer);
        // 支持事务
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 设置jackson的序列化方式
     */
    private Jackson2JsonRedisSerializer<Object> getJsonRedisSerializer() {
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        return new Jackson2JsonRedisSerializer<>(om, Object.class);
    }
}
