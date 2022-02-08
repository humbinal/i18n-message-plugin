package com.example.demo.common.i18n;

/**
 * @author humbinal
 */
public interface CommonError {
    /**
     * @message 成功
     */
    public static final String SUCCESS = "0";

    /**
     * @message 接口不存在
     */
    public static final String NOT_FOUND = "0x00001";

    /**
     * @message 方法不被允许
     */
    public static final String METHOD_NOT_ALLOW = "0x00002";

    /**
     * @message 服务器内部错误
     */
    public static final String INTERNAL_SERVER_ERROR = "0x00003";

    /**
     * @message 参数不合法
     */
    public static final String INVALID_PARAMS = "0x00004";


    /**
     * @message 页码不能小于{value}
     */
    public static final String VALIDATION_PAGE_NO_MIN = "0x00101";

    /**
     * @message 每页数量不能小于{value}
     */
    public static final String VALIDATION_PAGE_SIZE_MIN = "0x00102";

}
