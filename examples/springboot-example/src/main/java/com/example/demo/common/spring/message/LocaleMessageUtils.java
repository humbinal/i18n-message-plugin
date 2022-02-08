package com.example.demo.common.spring.message;

import com.example.demo.common.spring.SpringContextUtils;

/**
 * @author humbinal
 */
public class LocaleMessageUtils {

    private final static String SOLUTION_LABEL_SUFFIX = ".solution";

    public static String getMessage(String code) {
        return SpringContextUtils.getBean(LocaleMessageSourceService.class).getMessage(code);
    }

    public static String getSolution(String code) {
        return SpringContextUtils.getBean(LocaleMessageSourceService.class).getMessage(code + SOLUTION_LABEL_SUFFIX);
    }
}
