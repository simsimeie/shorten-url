package com.shorturl.toy02.domain;

import com.shorturl.toy02.exception.BloomFilterDuplicateExcpetion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class BloomFilterTest {

    private SaveAllIndexBloomFilter bloomFilter;

    public BloomFilterTest(@Autowired SaveAllIndexBloomFilter bloomFilter){
        this.bloomFilter = bloomFilter;
    }

    @Test
    @DisplayName("동일한 short url 저장 시도시 exception 발생하는지 test")
    public void duplicateExceptionTest01(){
        Throwable throwable = catchThrowable(()->{
            bloomFilter.utilizeBloomFilter("abc12");
            bloomFilter.utilizeBloomFilter("abc12");
        });
        assertThat(throwable).isInstanceOf(BloomFilterDuplicateExcpetion.class);
    }



}