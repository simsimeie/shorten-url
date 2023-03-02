package com.shorturl.toy02.exception;

public class BloomFilterDuplicateExcpetion extends RuntimeException{
    public BloomFilterDuplicateExcpetion(){
        super("Duplicated Short Url");
    }
}
