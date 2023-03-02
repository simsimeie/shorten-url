package com.shorturl.toy02.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class ShortUrlImpl implements ShortUrl{
    private final Converter converter;
    @Value("${short-url-digits}")
    private int SHORT_URL_DIGITS;

    @Override
    public String createShortUrl() {
        long randomNumberForShortUrl = createRandomNumberForShortUrl();
        return createShortUrl(randomNumberForShortUrl);
    }

    public String createShortUrl(long index){
        return converter.convert(index);
    }
    private long createRandomNumberForShortUrl(){
        SecureRandom secureRandom = new SecureRandom();
        return secureRandom.nextLong(getBound());
    }
    private long getBound(){
        return (long) Math.pow(converter.getBase(), SHORT_URL_DIGITS);
    }
}
