package com.shorturl.toy02.domain;

import com.shorturl.toy02.exception.TooManyRetryExcpetion;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

@Getter
public class Threshold {
    @Value("${threshold.retry.bloomfilter}")
    private int BLOOM_FILTER_DUPLICATE_THRESHOLD;
    @Value("${threshold.retry.database}")
    private int DATABASE_DUPLICATE_THRESHOLD;

    private int bloomfilterRetryCount;
    private int databaseRetryCount;

    public Threshold(int bloomfilterRetryCount, int databaseRetryCount) {
        this.bloomfilterRetryCount = bloomfilterRetryCount;
        this.databaseRetryCount = databaseRetryCount;
    }

    public void bloomfilterRetryCountUp(){
        if(++bloomfilterRetryCount >= BLOOM_FILTER_DUPLICATE_THRESHOLD){
            throw new TooManyRetryExcpetion();
        }
    }

    public void databaseRetryCountUp(){
        this.bloomfilterRetryCount = 0;
        if(++databaseRetryCount >= DATABASE_DUPLICATE_THRESHOLD){
            throw new TooManyRetryExcpetion();
        }
    }
}
