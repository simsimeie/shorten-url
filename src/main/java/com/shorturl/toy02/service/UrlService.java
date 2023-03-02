package com.shorturl.toy02.service;

import com.shorturl.toy02.domain.*;
import com.shorturl.toy02.entity.Url;
import com.shorturl.toy02.exception.BloomFilterDuplicateExcpetion;
import com.shorturl.toy02.exception.DataBaseDuplicateExcpetion;
import com.shorturl.toy02.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UrlService {

    private final UrlRepository urlRepository;
    private final ShortUrl shortUrl;
    private final BloomFilter bloomFilter;

    public Url findShortUrlByOriginUrl(String originUrl){
        Url shortUrl = urlRepository.findUrlByOriginUrl(originUrl);
        return shortUrl;
    }

    public Url findUrlByShortUrl(String shortUrl){
        Url url = urlRepository.findUrlByShortUrl(shortUrl);
        return url;
    }

    public Url createShortUrlAndSaveUrl(String originUrl, Threshold threshold){
        try {
            synchronized (this) {
                String l_shortUrl = shortUrl.createShortUrl();
                bloomFilter.utilizeBloomFilter(l_shortUrl);
                Url url = saveUrl(new Url(originUrl, l_shortUrl));
                bloomFilter.requestSyncronization(l_shortUrl);
                return url;
            }
        }
        catch (BloomFilterDuplicateExcpetion e){
            threshold.bloomfilterRetryCountUp();
            return createShortUrlAndSaveUrl(originUrl, threshold);
        }
        catch (DataBaseDuplicateExcpetion e){
            threshold.databaseRetryCountUp();
            return createShortUrlAndSaveUrl(originUrl, threshold);
        }
    }

    public Url saveUrl(Url url){
        try{
            return urlRepository.save(url);
        }
        catch (DataIntegrityViolationException e){
            if(e.getCause().getCause().getMessage().contains("UNIQUE_SHORT_URL")){
                throw new DataBaseDuplicateExcpetion();
            }
            throw e;
        }
    }
}
