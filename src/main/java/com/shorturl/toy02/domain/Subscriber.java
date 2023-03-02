package com.shorturl.toy02.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Subscriber implements MessageListener {
    private final RedisTemplate redisTemplate;
    private final BloomFilter bloomFilter;
    @Override
    public void onMessage(Message message, byte[] pattern) {
        synchronized (this) {
            String shortUrl = (String) redisTemplate.getValueSerializer().deserialize(message.getBody());
            log.info("Short URL from other instance : {}", shortUrl);
            bloomFilter.sync(shortUrl);
        }
    }
}
