package com.mabel.pojo.ro;

import lombok.Data;

import javax.validation.constraints.Pattern;

/**
 * @project: message-gateway
 * @description:
 * @author: Mabel.Chen
 * @create: 2022-02-26
 **/
@Data
public class UserLoginRO {

    @Pattern(regexp = "[a-zA-Z\\d]{3,32}", message = "请求参数错误")
    private String userName;

    @Pattern(regexp = "[a-zA-Z\\d]{8,64}", message = "请求参数错误")
    private String password;
}