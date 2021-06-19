package com.gjy.boke.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author GJY
 * @Date 2021/4/29 18:23
 * @Version 1.0
 */
@Configuration
public class DateFormatter implements Converter<String, Date> {
    String pattern = "yyyy/MM/dd";
    @Override
    public Date convert(String s) {
        try {
            return new SimpleDateFormat(pattern).parse(s);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
