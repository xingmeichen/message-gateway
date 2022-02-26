package com.mabel.dao;

import com.mabel.mapper.UserMapper;
import com.mabel.pojo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * @project: message-gateway
 * @description: User 数据库层 Service
 * @author: Mabel.Chen
 * @create: 2022-02-26
 **/
@Service
public class UserDao {

    @Autowired
    private UserMapper userMapper;

    public User selectUserByUserName(String userName) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("userName", userName);
        return userMapper.selectOneByExample(example);
    }

    public User insertSelective(User user) {
        user.setId(null);
        userMapper.insertSelective(user);
        return user;
    }
}