package com.shorturl.toy02.domain;

import org.springframework.stereotype.Component;

@Component
public class Base62Converter implements Converter {
    private static final String ALPHANUMERIC = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int BASE = ALPHANUMERIC.length();

    @Override
    public String convert(long value) {
        StringBuilder result = new StringBuilder();
        do {
            int remainder = (int) (value % BASE);
            result.insert(0, ALPHANUMERIC.charAt(remainder));
            value /= BASE;
        } while (value > 0);
        return result.toString();
    }

    @Override
    public int getOffset(char ch) {
        return ALPHANUMERIC.indexOf(ch);
    }

    @Override
    public int getBase() {
        return BASE;
    }
}
