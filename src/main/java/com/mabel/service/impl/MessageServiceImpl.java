package com.mabel.service.impl;

import com.mabel.pojo.dto.MessageDTO;
import com.mabel.pojo.po.CommonError;
import com.mabel.pojo.ro.SmsRO;
import com.mabel.pojo.vo.SmsVO;
import com.mabel.service.MessageService;
import com.mabel.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @project: message-gateway
 * @description: 消息管理 Service
 * 接口定义
 * @see com.mabel.service.MessageService
 * @author: Mabel.Chen
 * @create: 2022-02-26
 **/
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SmsService smsServiceImpl;

    @Override
    public Integer directMessage(MessageDTO msgDTO) {
        String key = new StringBuffer("sessionId:").append(msgDTO.getUserName()).toString();
        if (!redisTemplate.hasKey(key)) {
            // 用户不存在或者未经授权
            return CommonError.NO_AUTH.getErrorCode();
        }
        String sessionId = (String) redisTemplate.opsForValue().get(key);
        if (!msgDTO.getSessionId().equals(sessionId)) {
            // 未经授权
            return CommonError.NO_AUTH.getErrorCode();
        }
        // TODO, 调用短信模拟器获取消息, 以及消息频率控制
        sendSms(msgDTO);
        smsServiceImpl.reset();
        smsServiceImpl.currentStatus();
        return CommonError.SUCCESS.getErrorCode();
    }

    private SmsVO sendSms(MessageDTO msgDTO) {
        SmsRO smsRO = SmsRO.parse(msgDTO);
        SmsVO smsVO = smsServiceImpl.sendSms(smsRO);
        // TODO 解析结果
        return null;
    }
}