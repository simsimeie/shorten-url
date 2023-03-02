package com.shorturl.toy02.repository;

import com.shorturl.toy02.entity.Url;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UrlRepository extends JpaRepository<Url, Long> {
    Url findUrlByOriginUrl(String originUrl);

    Url findUrlByShortUrl(String shortUrl);

    @Query("select u.shortUrl from Url u")
    Page<String> findPagedShortUrl(Pageable pageable);

}
