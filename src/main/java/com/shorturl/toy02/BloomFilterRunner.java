package com.shorturl.toy02;

import com.shorturl.toy02.domain.BloomFilter;
import com.shorturl.toy02.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class BloomFilterRunner implements ApplicationRunner {

    @Value("${page-size}")
    private Integer PAGE_SIZE;
    private final UrlRepository urlRepository;
    private final BloomFilter bloomFilter;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        savePagedShortUrlsInBloomFilter(createFirstPage());
    }

    private Page<String> createFirstPage() {
        PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE);
        Page<String> firstPage = urlRepository.findPagedShortUrl(pageRequest);
        return Optional.of(firstPage).orElseGet(Page::empty);
    }

    protected void savePagedShortUrlsInBloomFilter(Page<String> page) {
        while(page.hasContent()){
            List<String> shortUrls = page.getContent();
            shortUrls.stream().forEach(shortUrl -> {
                log.info("Short URL : {}", shortUrl);
                bloomFilter.utilizeBloomFilter(shortUrl);
            } );

            if(page.hasNext()) page = urlRepository.findPagedShortUrl(page.nextPageable());
            else break;
        }
    }
}
