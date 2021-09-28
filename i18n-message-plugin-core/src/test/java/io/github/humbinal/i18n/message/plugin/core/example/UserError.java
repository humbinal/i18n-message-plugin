package io.github.humbinal.i18n.message.plugin.core.example;

/**
 * 用户模块异常消息类
 *
 * @author humbinal
 */
public enum UserError {

    /**
     * @language zh_CN
     * @message 用户不存在
     * @solution 请检查用户名是否正确或进行用户注册
     */
    USER_NOT_EXIT("0x0001001", "user not exist"),

    /**
     * @language zh_CN
     * @message 用户密码错误
     * @solution 请检查用户密码或重置密码
     */
    USER_PWD_INCORRECT("0x0001002", "user password incorrect");

    UserError(String code, String msg) {
    }
}
