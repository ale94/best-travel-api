package ar.com.alejandro.best_travel_api.config;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Map;

@Configuration
@EnableCaching
@EnableScheduling
@Slf4j
public class RedisConfig {

    @Value(value = "${cache.redis.address}")
    private String serverAddress; //Server address to redis
    @Value(value = "${cache.redis.password}")
    private String serverPassword; //Server password

    /*
     * Con esta configuración cargamos el cliente de redis al contenedor de spring
     */
    @Bean
    public RedissonClient redissonClient() {
        var config = new Config();
        config.useSingleServer()
                .setAddress(serverAddress)
                .setPassword(serverPassword);
        return Redisson.create(config);
    }

    /*
     * Con esta configuracion podemos habilitar las anotaciones de spring cache @Cacheable
     */
    @Bean
    @Autowired
    public CacheManager cacheManager(RedissonClient redissonClient) {
        Object CacheConstants;
        var configs = Map.of(
                "hotels", new CacheConfig(),
                "flights", new CacheConfig()
        );
        return new RedissonSpringCacheManager(redissonClient, configs);
    }
}