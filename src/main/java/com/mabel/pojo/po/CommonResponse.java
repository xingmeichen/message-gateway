package com.mabel.pojo.po;

import lombok.Getter;

/**
 * @project: message-gateway
 * @description:
 * @author: Mabel.Chen
 * @create: 2022-02-26
 **/
@Getter
public enum CommonResponse {

    SUCCESS(200, "success"),
    WRONG_PARAMETER(400, "请求参数错误"),
    NO_AUTH(403, "禁止未经授权访问"),
    INTERNAL_SYSTEM_ERROR(500, "内部服务器错误");

    private Integer code;
    private String message;

    CommonResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}