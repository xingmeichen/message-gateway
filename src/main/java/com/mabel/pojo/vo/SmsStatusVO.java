package com.mabel.pojo.vo;

import lombok.Data;

import java.util.Map;

/**
 * @project: message-gateway
 * @description:
 * @author: Mabel.Chen
 * @create: 2022-02-26
 **/
@Data
public class SmsStatusVO extends SmsVO {

    /***
     * 错误码，成功返回0
     */
    private String res_code;

    /***
     * 优先级消息发送成功统计图，keyName = qos级别，string类型；value long类型。
     */
    private Map<String, Long> qosSuccessMap;

    /***
     * 优先级消息发送失败统计图，keyName = qos级别，string类型；value long类型。
     */
    private Map<String, Long> qosRejectMap;

    /***
     * 已接收的总请求数量，不包括参数校验失败的请求
     */
    private Integer totalReceived;
}