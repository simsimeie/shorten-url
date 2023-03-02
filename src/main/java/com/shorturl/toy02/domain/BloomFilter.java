package com.shorturl.toy02.domain;

public interface BloomFilter {
    void utilizeBloomFilter(String shortUrl);
    void requestSyncronization(String shortUrl);
    void sync(String shortUrl);
}
