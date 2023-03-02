package com.shorturl.toy02.domain;


import com.shorturl.toy02.exception.TooManyRetryExcpetion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

@SpringBootTest(properties = {"threshold.retry.bloomfilter=1","threshold.retry.database=1"})
class ThresholdTest {
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    @DisplayName("prototype scope로 생성된 threshold bean의 초기설정 값이 정상인지 확인")
    public void thresholdTest01(){
        Threshold threshold = applicationContext.getBean(Threshold.class);
        assertEquals(0, threshold.getBloomfilterRetryCount());
        assertEquals(0, threshold.getDatabaseRetryCount());
        assertEquals(1, threshold.getBLOOM_FILTER_DUPLICATE_THRESHOLD());
        assertEquals(1, threshold.getDATABASE_DUPLICATE_THRESHOLD());
    }

    @Test
    @DisplayName("bloomfilter retry count가 threshold에 도달하면 TooManyRetryExcpetion 발생하는지 테스트")
    public void thresholdTest02(){
        Throwable throwable = catchThrowable(()->{
            Threshold threshold = applicationContext.getBean(Threshold.class);
            threshold.bloomfilterRetryCountUp();
        });
        assertThat(throwable).isInstanceOf(TooManyRetryExcpetion.class);
    }

    @Test
    @DisplayName("database retry count가 threshold에 도달하면 TooManyRetryExcpetion 발생하는지 테스트")
    public void thresholdTest03(){
        Throwable throwable = catchThrowable(()->{
            Threshold threshold = applicationContext.getBean(Threshold.class);
            threshold.databaseRetryCountUp();
        });
        assertThat(throwable).isInstanceOf(TooManyRetryExcpetion.class);
    }
}