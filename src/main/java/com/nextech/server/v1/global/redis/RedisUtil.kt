package com.nextech.server.v1.global.redis

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class RedisUtil(
    private val redisTemplate: RedisTemplate<String, Any>,
    private val redisBlackListTemplate: RedisTemplate<String, Any>
) {

    fun set(key: String, value: Any, minutes: Int) {
        redisTemplate.opsForValue().set(key, value, minutes.toLong(), TimeUnit.MINUTES)
    }

    fun get(key: String): Any? {
        return redisTemplate.opsForValue().get(key)
    }

    fun delete(key: String): Boolean {
        return redisTemplate.delete(key)
    }

    fun hasKey(key: String): Boolean {
        return redisTemplate.hasKey(key) == true
    }

    fun setBlackList(key: String, value: Any, milliSeconds: Long) {
        redisBlackListTemplate.opsForValue().set(key, value, milliSeconds, TimeUnit.MILLISECONDS)
    }

    fun getBlackList(key: String): Any? {
        return redisBlackListTemplate.opsForValue().get(key)
    }

    fun deleteBlackList(key: String): Boolean {
        return redisBlackListTemplate.delete(key)
    }

    fun hasKeyBlackList(key: String): Boolean {
        return redisBlackListTemplate.hasKey(key) == true
    }
}