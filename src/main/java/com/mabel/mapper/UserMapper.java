package com.mabel.mapper;

import com.mabel.pojo.model.User;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @project: message-gateway
 * @description:
 * @author: Mabel.Chen
 * @create: 2022-02-26
 **/
@org.apache.ibatis.annotations.Mapper
@Repository
public interface UserMapper extends Mapper<User> {
}