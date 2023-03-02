package com.shorturl.toy02.domain;

public interface Converter {
    String convert(long value);
    int getOffset(char ch);
    int getBase();
}
