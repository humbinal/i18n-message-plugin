package com.example.demo.common.exception;

import java.util.Map;

/**
 * @author humbinal
 */
public class CommonException extends Exception {

    private final String code;

    /**
     * 自定义错误模板信息
     * 模板变量使用 “{{ key }}” 语法
     */
    private Map<String, String> template;

    public CommonException(String code) {
        super();
        this.code = code;
    }

    public CommonException(String message, String code) {
        super(message);
        this.code = code;
    }

    public CommonException(String code, Map<String, String> template) {
        super();
        this.code = code;
        this.template = template;
    }

    public CommonException(String message, String code, Map<String, String> template) {
        super(message);
        this.code = code;
        this.template = template;
    }

    public CommonException(String message, Throwable cause, String code, Map<String, String> template) {
        super(message, cause);
        this.code = code;
        this.template = template;
    }

    public String getCode() {
        return code;
    }

    public Map<String, String> getTemplate() {
        return template;
    }
}
