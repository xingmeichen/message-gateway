package com.mabel.service;

import com.mabel.pojo.dto.MessageDTO;

/**
 * @project: message-gateway
 * @description: 消息 Service 接口
 * 具体实现：
 * @see com.mabel.service.impl.MessageServiceImpl
 * @author: Mabel.Chen
 * @create: 2022-02-26
 **/
public interface MessageService {

    Integer directMessage(MessageDTO messageDTO);
}