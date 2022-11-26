package com.numble.backend.common.config.cache;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.numble.backend.common.config.redis.CacheKey;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {

	// redisConnectionFactory bean이 autowired됨
	@Bean
	@Primary
	public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
		RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
			.disableCachingNullValues()
			// 60초 ttl 존재
			.entryTtl(Duration.ofSeconds(CacheKey.DEFAULT_EXPIRE_SEC))
			.computePrefixWith(CacheKeyPrefix.simple())
			.serializeKeysWith(
				RedisSerializationContext.SerializationPair
					.fromSerializer(new StringRedisSerializer()))
			.serializeValuesWith(RedisSerializationContext
				.SerializationPair
				.fromSerializer(new GenericJackson2JsonRedisSerializer()));

		return RedisCacheManager.RedisCacheManagerBuilder
			.fromConnectionFactory(redisConnectionFactory)
			.cacheDefaults(configuration)
			.build();

	}

	@Bean
	public CacheManager caffeineCacheManager() {
		List<CaffeineCache> caches = Arrays.stream(CacheType.values())
			.map(cache -> new CaffeineCache(cache.getName(),
				Caffeine.newBuilder()
					.recordStats()
					.expireAfterWrite(
						cache.getExpireAfterWrite(),
						TimeUnit.SECONDS)
					.maximumSize(
						cache.getMaxSize())
					.build()))
			.collect(Collectors.toList());

		SimpleCacheManager cacheManager = new SimpleCacheManager();
		cacheManager.setCaches(caches);

		return cacheManager;
	}
}
