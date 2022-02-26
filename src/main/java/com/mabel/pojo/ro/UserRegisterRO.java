package com.mabel.pojo.ro;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

/**
 * @project: message-gateway
 * @description:
 * @author: Mabel.Chen
 * @create: 2022-02-26
 **/
@Data
public class UserRegisterRO {

    @Pattern(regexp = "[a-zA-Z\\d]+", message = "用户名只能包含数字和大小写字母")
    @Length(min = 3, max = 32, message = "用户名长度必须是3到32个字符之间")
    private String userName;

    @Pattern(regexp = "[a-zA-Z\\d]+", message = "密码只能包含数字和大小写字母")
    @Length(min=8, max = 64, message = "密码长度必须是8到64个字符之间")
    private String password;
}