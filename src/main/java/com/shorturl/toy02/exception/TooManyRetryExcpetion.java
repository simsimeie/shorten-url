package com.shorturl.toy02.exception;

public class TooManyRetryExcpetion extends RuntimeException{
    public TooManyRetryExcpetion(){
        super("Too Many Retry Exception");
    }
}
