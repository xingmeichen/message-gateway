package com.mabel.service.impl;

import com.mabel.pojo.ro.SmsRO;
import com.mabel.pojo.vo.SmsVO;
import com.mabel.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @project: message-gateway
 * @description: 调用短信平台接口的 Service
 * 接口定义
 * @see com.mabel.service.SmsService
 * @author: Mabel.Chen
 * @create: 2022-02-26
 **/
@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${sms.baseUrl}")
    private String smsBaseUrl;

    @Value("${sms.sendSms}")
    private String sendSms;

    @Value("${sms.reset}")
    private String reset;

    @Value("${sms.currentStatus}")
    private String currentStatus;

    /***
     * 调用短信平台发送短信
     * 注意：
     * 1、接口调用频率限流，每秒最多允许10个请求 。
     * 2、手机号码限流，每个手机号码每秒只能发送一次消息 。
     * 3、违反上述两条规则任何一条，短信平台将拒绝消息网关任意请求3秒。
     * @param smsRO
     * @return
     */
    @Override
    public SmsVO sendSms(SmsRO smsRO) {
        String url = smsBaseUrl + sendSms;
        HttpEntity<SmsRO> entity = new HttpEntity<>(smsRO);
        ResponseEntity<SmsVO> voEntity = restTemplate.postForEntity(url, entity, SmsVO.class);;
        return voEntity.getBody();
    }

    /***
     * 调用短信平台重置短信发送统计数据
     * @return
     */
    @Override
    public SmsVO reset() {
        String url = smsBaseUrl + reset;
        ResponseEntity<SmsVO> voEntity = restTemplate.postForEntity(url,null, SmsVO.class);
        return voEntity.getBody();
    }

    /***
     * 查询当前短信发送统计数据
     * @return
     */
    @Override
    public SmsVO currentStatus() {
        String url = smsBaseUrl + currentStatus;
        ResponseEntity<SmsVO> voEntity = restTemplate.getForEntity(url, null, SmsVO.class);
        return voEntity.getBody();
    }
}