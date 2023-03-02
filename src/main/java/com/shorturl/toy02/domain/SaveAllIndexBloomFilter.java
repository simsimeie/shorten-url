package com.shorturl.toy02.domain;

import com.shorturl.toy02.constants.CommonConstants;
import com.shorturl.toy02.exception.BloomFilterDuplicateExcpetion;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
@Slf4j
public class SaveAllIndexBloomFilter implements BloomFilter {
    private BloomFilterNode[][] bloomFilterRepo;
    private final Converter converter;

    @Value("${short-url-digits}")
    private int shortUrlDigits;

    private final RedisTemplate redisTemplate;

    @PostConstruct
    public void init(){
        int base = converter.getBase();

        bloomFilterRepo = new BloomFilterNode[shortUrlDigits][base];

        for(int i=0; i<shortUrlDigits; i++){
            for(int j=0; j<base; j++){
                bloomFilterRepo[i][j] = new BloomFilterNode();
            }
        }
    }
    public void utilizeBloomFilter(String shortUrl){
        if(isNotExistInBloomFilterRepo(shortUrl)){
            saveShortUrlInBloomFilterRepo(shortUrl);
        }else{
            throw new BloomFilterDuplicateExcpetion();
        }
    }
    public boolean isNotExistInBloomFilterRepo(String shortUrl){
        for(int charOrder=0; charOrder < shortUrl.length(); charOrder++){
            int offset = bloomFilterHash(shortUrl, charOrder);

            if(isNotExistNode(charOrder, offset)){
                return true;
            }
            else if(isLastCharOrderOfShortUrl(shortUrl, charOrder)){
                return bloomFilterRepo[charOrder][offset].isSetAsLastChar() == false;
            }
        }
        return false;
    }

    protected int bloomFilterHash(String shortUrl, int charOrder){
        return converter.getOffset(shortUrl.charAt(charOrder));
    }
    private boolean isNotExistNode(int charOrder, int offset){
        return !bloomFilterRepo[charOrder][offset].isExist();
    }

    protected boolean isLastCharOrderOfShortUrl(String shortUrl, int charOrder){
        return charOrder == shortUrl.length()-1;
    }

    public void saveShortUrlInBloomFilterRepo(String shortUrl){
        for(int charOrder=0 ; charOrder < shortUrl.length() ; charOrder++) {
            int offset = bloomFilterHash(shortUrl, charOrder);
            bloomFilterRepo[charOrder][offset].setExist(true);

            if(isLastCharOrderOfShortUrl(shortUrl, charOrder)){
                bloomFilterRepo[charOrder][offset].setSetAsLastChar(true);
            }
        }
    }
    public void requestSyncronization(String shortUrl){
        redisTemplate.convertAndSend(CommonConstants.SYNC_BLOOMFILTER, shortUrl);
    }

    @Override
    public void sync(String shortUrl){
        if(isNotExistInBloomFilterRepo(shortUrl)){
            saveShortUrlInBloomFilterRepo(shortUrl);
        }
    }

    @Getter
    @Setter
    public static class BloomFilterNode{
        private boolean isExist;
        private boolean isSetAsLastChar;

    }
}
