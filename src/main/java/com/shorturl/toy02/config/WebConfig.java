package com.shorturl.toy02.config;

import com.shorturl.toy02.domain.Threshold;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class WebConfig {
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Threshold threshold(){
        return new Threshold(0,0);
    }
}
