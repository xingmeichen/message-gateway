package com.mabel.utils;

import java.util.Arrays;

/**
 * @project: message-gateway
 * @description:
 * @author: Mabel.Chen
 * @create: 2022-02-27
 **/
public class RedisKey {

    public static String smsTelKey(String tel) {
        return String.join(":","sms","tel", tel);
    }

    public static String smsRateKey(String host, String port, String tel) {
        return String.join(":","sms", "rate", host, port, tel);
    }

    public static String smsRateKeyPattern(String host, String port) {
        return String.join(":","sms", "rate", host, port, "*");
    }
}