package com.mabel.dao;

import com.mabel.pojo.model.User;
import com.mabel.utils.RedisKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @project: message-gateway
 * @description: 用户信息持久化 Service
 * @author: Mabel.Chen
 * @create: 2022-02-28
 **/
@Service
public class UserDao {

    @Autowired
    private RedisTemplate redisTemplate;

    public boolean addUser(User user) {
        redisTemplate.opsForValue().set(RedisKey.userKey(user.getUserName()), user.getPassword());
        return true;
    }

    public User queryUserByUserName(String userName) {
        if (!isUserExist(userName)) {
            return null;
        }
        String password = (String) redisTemplate.opsForValue().get(RedisKey.userKey(userName));
        User user = new User(userName, password);
        return user;
    }

    public boolean isUserExist(String userName) {
        return redisTemplate.hasKey(RedisKey.userKey(userName));
    }
}