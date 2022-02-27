package com.mabel.service.impl;

import com.mabel.configuration.ServerInfoConfiguration;
import com.mabel.pojo.dto.MessageDTO;
import com.mabel.pojo.po.CommonError;
import com.mabel.pojo.ro.SmsRO;
import com.mabel.pojo.vo.SmsVO;
import com.mabel.service.MessageService;
import com.mabel.service.SmsService;
import com.mabel.utils.RedisKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private ServerInfoConfiguration serverInfoConfiguration;

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
        /***
         * 调用短信平台发送短信之前进行限流控制, 如果被限制，直接返回服务器内部错误
         */
        if (!isAbleToSend(msgDTO.getTels())) {
            return CommonError.SYSTEM_ERROR.getErrorCode();
        }
        return sendSms(msgDTO);
    }

    public Integer sendSms(MessageDTO msgDTO) {
        SmsRO smsRO = SmsRO.parse(msgDTO);
        SmsVO smsVO = smsServiceImpl.sendSms(smsRO);
        String code = smsVO.getRes_code();
        switch (code) {
            case "0": {
                /***
                 * 发送短信成功后设置调用记录用于控制调用频率，防止因为调用频率过高而被限制接口调用
                 */
                recordTel(smsRO.getAcceptor_tel());
                recordRate(smsRO.getAcceptor_tel(), 1);
                return CommonError.SUCCESS.getErrorCode();
            }
            case "400": {
                return CommonError.WRONG_PARAMETER.getErrorCode();
            }
            case "409": case "429": {
                if (isAbleToSend(smsRO.getAcceptor_tel())) {
                    // 如果是被短信平台限制调用接口，则主动限制3秒内不能发送请求
                    recordRate("", 3);
                }
                return CommonError.SYSTEM_ERROR.getErrorCode();
            }
            default:{
                return CommonError.SYSTEM_ERROR.getErrorCode();
            }
        }
    }

    /***
     * 记录手机号发送短信记录
     * @param tel
     */
    private void recordTel(String tel) {
        redisTemplate.opsForValue().set(RedisKey.smsTelKey(tel), "", 1, TimeUnit.SECONDS);
    }

    /***
     * 记录接口调用短信频率
     */
    private void recordRate(String tel, int timeout) {
        String host = serverInfoConfiguration.getHost();
        String port = serverInfoConfiguration.getPort();
        redisTemplate.opsForValue().set(RedisKey.smsRateKey(host, port, tel), "", timeout, TimeUnit.SECONDS);
    }

    /***
     * 检查是否可以调用发送短信的接口
     * 不可以返回 false, 否则返回 true
     * @return
     */
    private boolean isAbleToSend(String tel) {
        if (!isTelAble(tel)) {
            // 当前手机号被限流
            return false;
        }
        // 如果手机号允许发送，则查询是否有频率限制
        return isRateAble();
    }

    /***
     * 查询当前手机号是否被限制
     * 如果被限制返回 false, 否则返回true
     * @param tel
     * @return
     */
    private boolean isTelAble(String tel) {
        if (redisTemplate.hasKey(RedisKey.smsTelKey(tel))) {
            // 当前手机号被限流
            return false;
        }
        return true;
    }

    /***
     * 查询调用频率是否被限制
     * 如果被限制返回 false, 否则返回true
     * @return
     */
    private boolean isRateAble() {
        String host = serverInfoConfiguration.getHost();
        String port = serverInfoConfiguration.getPort();
        Set keys = redisTemplate.keys(RedisKey.smsRateKeyPattern(host, port));
        if (null != null && 10 < keys.size()) {
            return false;
        }
        return true;
    }
}