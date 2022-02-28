package com.mabel.service;

import com.mabel.pojo.ro.SmsRO;
import com.mabel.pojo.vo.SmsVO;

/**
 * @project: message-gateway
 * @description: 调用短信平台Service 接口
 * 具体实现：
 * @see com.mabel.service.impl.SmsServiceImpl
 * @author: Mabel.Chen
 * @create: 2022-02-26
 **/
public interface SmsService {

    /***
     * 调用短信平台发送短信
     * @param smsRO
     * @return
     */
    SmsVO sendSms(SmsRO smsRO);

    /***
     *
     * @return
     */
    SmsVO reset();

    SmsVO currentStatus();
}