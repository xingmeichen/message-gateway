package com.mabel.service;

import com.mabel.pojo.ro.UserLoginRO;
import com.mabel.pojo.ro.UserLogoutRO;
import com.mabel.pojo.ro.UserRegisterRO;

/**
 * @project: message-gateway
 * @description: 用户管理Service接口
 * 具体实现：
 * @see com.mabel.service.impl.UserServiceImpl
 * @author: Mabel.Chen
 * @create: 2022-02-26
 **/
public interface UserService {

    Integer register(UserRegisterRO registerRO);

    Integer login(UserLoginRO loginRO, String sessionId);

    Integer logout(UserLogoutRO logoutRO);
}