package com.shorturl.toy02.exception;

public class DataBaseDuplicateExcpetion extends RuntimeException{
    public DataBaseDuplicateExcpetion(){
        super("Duplicated Short Url");
    }
}
