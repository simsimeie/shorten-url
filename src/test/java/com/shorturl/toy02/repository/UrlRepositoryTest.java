package com.shorturl.toy02.repository;


import com.shorturl.toy02.entity.Url;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
@SpringBootTest
class UrlRepositoryTest {
    @Autowired
    private UrlRepository urlRepository;

    @Test
    @DisplayName("Short URL 동일한 값 넣었을 때 Unique constraint 의해 Exception 발생하는지 확인")
    public void uniqueConstraintExceptionTest01(){
        Url url1 = new Url("naver.com","abc12");
        Url url2 = new Url("kakao.com","abc12");

        Throwable throwable = catchThrowable(()->{
            urlRepository.save(url1);
            urlRepository.save(url2);
        });
        assertThat(throwable).isInstanceOf(DataIntegrityViolationException.class);
        assertThat(throwable).getCause().getCause().hasMessageContaining("UNIQUE_SHORT_URL");
    }

}