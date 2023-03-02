package com.shorturl.toy02.entity;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@MappedSuperclass
public class BaseEntity {
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    @PrePersist
    public void create(){
        LocalDateTime now = LocalDateTime.now();
        this.createdDate = now;
        this.modifiedDate = now;

    }
    @PreUpdate
    public void update(){
        this.modifiedDate = LocalDateTime.now();
    }
}
