package com.shorturl.toy02.dto;

import com.shorturl.toy02.entity.Url;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
public class UrlDto {
    private String originUrl;
    private String shortUrl;

}
