package com.shorturl.toy02.entity;

import com.shorturl.toy02.dto.UrlDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(name="UNIQUE_ORIGIN_URL", columnNames = {"originUrl"})
       ,@UniqueConstraint(name="UNIQUE_SHORT_URL", columnNames = {"shortUrl"})
})
public class Url extends BaseEntity{
    public Url(String originUrl, String shortUrl) {
        this.originUrl = originUrl;
        this.shortUrl = shortUrl;
    }
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String originUrl;
    @Column(nullable = false)
    private String shortUrl;

    public UrlDto makeUrlDto(){
       return UrlDto.of(this.originUrl, this.shortUrl);
    }
}
