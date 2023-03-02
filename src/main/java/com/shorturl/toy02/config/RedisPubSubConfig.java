package com.shorturl.toy02.config;

import com.shorturl.toy02.constants.CommonConstants;
import com.shorturl.toy02.domain.Subscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@Configuration
@RequiredArgsConstructor
public class RedisPubSubConfig {
    private final Subscriber subscriber;

    @Bean
    public RedisMessageListenerContainer RedisMessageListener(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(subscriber, new ChannelTopic(CommonConstants.SYNC_BLOOMFILTER));
        return container;
    }

}
