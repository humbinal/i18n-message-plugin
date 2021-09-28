package io.github.humbinal.i18n.message.plugin.core.example;

/**
 * 订单模块异常消息类
 *
 * @author humbinal
 */
public interface OrderError {

    /**
     * @language zh_CN
     * @message 订单不存在
     * @solution 请更换订单号
     */
    String ORDER_NOT_EXIST = "0x0002001";

    /**
     * @language zh_CN
     * @message 订单已失效
     * @solution 超出规定支付时间订单将失效
     */
    String ORDER_EXPIRED = "0x0002002";
}
