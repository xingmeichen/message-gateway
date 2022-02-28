package com.mabel.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @project: message-gateway
 * @description:
 * @author: Mabel.Chen
 * @create: 2022-02-27
 **/
@Configuration
public class ServerInfoConfiguration {

    @Autowired
    private Environment environment;

    private String host;
    private String port;

    public String getHost() {
        if (null != this.host) {
            return this.host;
        }
        try {
            this.host = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            System.out.println("Get Host error: " + e.getMessage());
        }
        return this.host;
    }

    public String getPort() {
        if (null != this.port) {
            return this.port;
        }
        this.port = environment.getProperty("local.server.port");
        return this.port;
    }
}