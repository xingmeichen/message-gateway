package com.mabel.pojo.vo;

import lombok.Data;

/**
 * @project: message-gateway
 * @description:
 * @author: Mabel.Chen
 * @create: 2022-02-26
 **/
@Data
public class SmsVO {

    /***
     * 错误码，成功返回0
     */
    private String res_code;

    /***
     * 错误消息，成功返回Success
     */
    private String message;

    /***
     * 错误消息，成功返回Success
     */
    private String res_message;
}