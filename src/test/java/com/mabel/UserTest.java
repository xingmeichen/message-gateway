package com.mabel;

import com.mabel.pojo.ro.UserRegisterRO;
import com.mabel.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @project: message-gateway
 * @description:
 * @author: Mabel.Chen
 * @create: 2022-02-26 13:16
 **/

@SpringBootTest
public class UserTest {

    @Autowired
    private UserService userServiceImpl;

    @Test
    public void registerTest() {
        UserRegisterRO registerRO = new UserRegisterRO();
        registerRO.setUserName("test123");
        registerRO.setPassword("psw654321");
        userServiceImpl.register(registerRO);
    }
}