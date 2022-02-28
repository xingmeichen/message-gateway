package com.mabel.utils;

/**
 * @project: message-gateway
 * @description:
 * @author: Mabel.Chen
 * @create: 2022-02-27
 **/
public class RedisKey {

    private static final String KEY_PREFIX = "com.mabel";

    public static String userKey(String userName) {
        return String.join(":", KEY_PREFIX, "user", userName);
    }

    public static String sessionKey(String userName) {
        return String.join(":", KEY_PREFIX, "sessionId", userName);
    }

    public static String smsTelKey(String tel) {
        return String.join(":", KEY_PREFIX, "sms", "tel", tel);
    }

    public static String smsRateKey(String host, String port, String tel) {
        return String.join(":", KEY_PREFIX, "sms", "rate", host, port, tel);
    }

    public static String smsRateKeyPattern(String host, String port) {
        return String.join(":", KEY_PREFIX, "sms", "rate", host, port, "*");
    }
}