package com.shorturl.toy02;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BloomFilterRunnerTest {
    @Autowired
    private BloomFilterRunner bloomFilterRunner;
    @Test
    @DisplayName("Empty page가 input으로 들어갔을 때, Exception 발생 없이 수행되는지 테스트")
    public void givenEmptyPage_whenCallSavePagedShortUrlsInBloomfilter_thenNoIssue(){
        //given
        Page<String> empty = Page.empty();
        //when
        bloomFilterRunner.savePagedShortUrlsInBloomFilter(Page.empty());
        //then
    }
}