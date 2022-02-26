package com.mabel.service.impl;

import com.mabel.dao.UserDao;
import com.mabel.pojo.model.User;
import com.mabel.pojo.po.CommonError;
import com.mabel.pojo.po.CommonResponse;
import com.mabel.pojo.ro.UserLoginRO;
import com.mabel.pojo.ro.UserLogoutRO;
import com.mabel.pojo.ro.UserRegisterRO;
import com.mabel.service.UserService;
import com.mabel.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @project: message-gateway
 * @description: 用户管理Service 实现,
 * 接口的定义
 * @see com.mabel.service.UserService
 * @author: Mabel.Chen
 * @create: 2022-02-26
 **/
@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisTemplate redisTemplate;

    /***
     * 用户注册，
     * 如果注册成功，则返回用户ID
     * 如果失败，则返回错误码，
     * 注意：错误码小于0
     * @param registerRO
     * @return
     */
    @Override
    public Integer register(UserRegisterRO registerRO) {
        try {
            User user = userDao.selectUserByUserName(registerRO.getUserName());
            if (null != user) {
                // 用户名已经存在，不允许注册
                LOGGER.info("existing user: " + registerRO.getUserName());
                return CommonError.DUPLICATE_USER_NAME.getErrorCode();
            }
            user = new User();
            user.setUserName(registerRO.getUserName());
            user.setPassword(UserUtils.encryptPassword(registerRO.getPassword()));
            userDao.insertSelective(user);
            return user.getId();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return CommonError.SYSTEM_ERROR.getErrorCode();
        }
    }

    @Override
    public Integer login(UserLoginRO loginRO, String sessionId) {
        User user = null;
        try {
            user = userDao.selectUserByUserName(loginRO.getUserName());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            CommonError.SYSTEM_ERROR.getErrorMsg();
        }
        if (null == user) {
            // 通过用户名查询不到用户，说明用户不存在
            return CommonError.NO_USER.getErrorCode();
        }
        boolean success = UserUtils.verifyPassword(loginRO.getPassword(), user.getPassword());
        if (!success) {
            return CommonError.WRONG_PASSWORD.getErrorCode();
        }
        redisTemplate.opsForValue().set("sessionId:" + user.getUserName(), sessionId, 1 * 24, TimeUnit.HOURS);
        return CommonResponse.SUCCESS.getCode();
    }

    @Override
    public Integer logout(UserLogoutRO logoutRO) {
        String key = new StringBuffer("sessionId:").append(logoutRO.getUserName()).toString();
        if (!redisTemplate.hasKey(key)) {
            // 用户不存在
            return CommonError.NO_USER.getErrorCode();
        }
        String sessionId = (String) redisTemplate.opsForValue().get("sessionId:" + logoutRO.getUserName());
        if (!logoutRO.getSessionId().equals(sessionId)) {
            return CommonError.WRONG_SESSION.getErrorCode();
        }
        redisTemplate.delete(key);
        return CommonError.SUCCESS.getErrorCode();
    }
}