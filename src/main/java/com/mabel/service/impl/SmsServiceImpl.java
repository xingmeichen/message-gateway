package com.mabel.service.impl;

import com.mabel.pojo.ro.SmsRO;
import com.mabel.pojo.vo.SmsVO;
import com.mabel.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @project: message-gateway
 * @description: 调用短信平台Service
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

    @Override
    public SmsVO sendSms(SmsRO smsRO) {
        String url = smsBaseUrl + sendSms;
        HttpEntity<SmsRO> entity = new HttpEntity<>(smsRO);
        ResponseEntity<SmsVO> voEntity = restTemplate.exchange(url, HttpMethod.POST, entity, SmsVO.class);
        return voEntity.getBody();
    }

    @Override
    public SmsVO reset() {
        String url = smsBaseUrl + reset;
        ResponseEntity<SmsVO> voEntity = restTemplate.exchange(url, HttpMethod.POST, null, SmsVO.class);
        return voEntity.getBody();
    }

    @Override
    public SmsVO currentStatus() {
        String url = smsBaseUrl + currentStatus;
        ResponseEntity<SmsVO> voEntity = restTemplate.exchange(url, HttpMethod.GET, null, SmsVO.class);
        return voEntity.getBody();
    }
}