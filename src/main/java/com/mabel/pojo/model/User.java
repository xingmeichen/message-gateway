package com.mabel.pojo.model;

import lombok.Data;

/**
 * @project: message-gateway
 * @description:
 * @author: Mabel.Chen
 * @create: 2022-02-26
 **/
@Data
public class User {

    private String userName;
    private String password;

    public User() {}

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}