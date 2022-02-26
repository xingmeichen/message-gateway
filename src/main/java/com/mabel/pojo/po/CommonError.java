package com.mabel.pojo.po;

import lombok.Getter;

import java.util.Arrays;

import static com.mabel.pojo.po.CommonResponse.*;

/**
 * @project: message-gateway
 * @description: 通用错误类
 * 错误码为0表示成功，小于0表示出错
 * @author: Mabel.Chen
 * @create: 2022-02-26
 **/
@Getter
public enum CommonError {

    SUCCESS(0, "success", CommonResponse.SUCCESS),
    SYSTEM_ERROR(-1, "系统错误", INTERNAL_SYSTEM_ERROR),
    DUPLICATE_USER_NAME(-2, "用户已经存在", WRONG_PARAMETER),
    NO_USER(-3, "用户不存在", WRONG_PARAMETER),
    WRONG_PASSWORD(-4, "密码错误", WRONG_PARAMETER),
    NO_SESSION(-5, "Session ID不存在", WRONG_PARAMETER),
    WRONG_SESSION(-6, "Session ID错误", WRONG_PARAMETER),
    NO_AUTH(-7, "禁止未经授权访问", CommonResponse.NO_AUTH);

    private Integer errorCode;
    private String errorMsg;
    private CommonResponse response;


    CommonError(Integer code, String errorMsg, CommonResponse response) {
        this.errorCode = code;
        this.errorMsg = errorMsg;
        this.response = response;
    }

    public static CommonError getEnumByCode(Integer code) {
        return Arrays.asList(CommonError.values()).stream()
                .filter(item -> null != code && code.equals(item.getErrorCode())).findFirst().orElse(SUCCESS);
    }
}