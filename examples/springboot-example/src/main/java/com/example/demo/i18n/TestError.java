package com.example.demo.i18n;

/**
 * @author humbinal
 */
public interface TestError {


    /**
     * @language zh_CN
     * @message 用户不存在
     * @solution 请检查用户名是否正确或进行用户注册
     */
    public static final String USER_NOT_EXIST = "0x00201";

    /**
     * @language zh_CN
     * @message 用户密码错误
     * @solution 请检查用户密码或重置密码
     */
    public static final String USER_PWD_INCORRECT = "0x00202";


    /**
     * @language zh_CN
     * @message 用户不能为空
     */
    public static final String USERNAME_NOT_BLANK = "0x00203";

    /**
     * @language zh_CN
     * @message 年龄不能小于{min}且不能大于{max}
     */
    public static final String USER_AGE_RANGE = "0x00204";

}
