package com.mabel.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;

import java.security.SecureRandom;

/**
 * @project: message-gateway
 * @description:
 * @author: Mabel.Chen
 * @create: 2022-02-26 12:50
 **/
public class UserUtils {

    /***
     * 密码加密
     * @param password
     * @return
     */
    public static String encryptPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12, new SecureRandom("MsGateway_Encrypt_Salt".getBytes())));
    }

    /***
     * 密码校验
     * @param plainPassword
     * @param cypher
     * @return
     */
    public static boolean verifyPassword(String plainPassword, String cypher) {
        return BCrypt.checkpw(plainPassword, cypher);
    }
}