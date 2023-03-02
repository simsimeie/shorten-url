package com.shorturl.toy02.controller;

import com.shorturl.toy02.domain.Threshold;
import com.shorturl.toy02.dto.UrlDto;
import com.shorturl.toy02.entity.Url;
import com.shorturl.toy02.exception.TooManyRetryExcpetion;
import com.shorturl.toy02.service.UrlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UrlController {
    private final UrlService urlService;
    private final ApplicationContext applicationContext;


    @PostMapping("short-url")
    @ResponseBody
    public ResponseEntity<UrlDto> saveShortUrl(@RequestBody UrlDto urlDto){
        //TODO: originUrl에 프로토콜 있을 때, 없을 때 처리
        //TODO: originUrl에 인코딩되어 들어올 때 안되어 들어올 때
        //TODO: originUrl이 유효한지 확인
        try {
            Optional<Url> url = Optional.ofNullable(urlService.findShortUrlByOriginUrl(urlDto.getOriginUrl()));
            if (!url.isEmpty()) {
                return ResponseEntity.ok().body(url.get().makeUrlDto());
            }

            Url savedUrl = urlService.createShortUrlAndSaveUrl(urlDto.getOriginUrl(), applicationContext.getBean(Threshold.class));
            return ResponseEntity.ok().body(savedUrl.makeUrlDto());
        }catch (TooManyRetryExcpetion e){
            return ResponseEntity.internalServerError().body(UrlDto.of(urlDto.getOriginUrl(), null));
        }
    }

    @GetMapping("/{url}")
    public String redirectToOriginUrl(@PathVariable("url") String shortUrl){
        Url url = urlService.findUrlByShortUrl(shortUrl);
        return "redirect:https://"+url.getOriginUrl();
    }
}
