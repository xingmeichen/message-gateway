package com.mabel.pojo.ro;

import com.sun.istack.internal.NotNull;
import lombok.Data;

/**
 * @project: message-gateway
 * @description:
 * @author: Mabel.Chen
 * @create: 2022-02-26
 **/
@Data
public class UserLogoutRO {

    @NotNull
    private String userName;
    @NotNull
    private String sessionId;
}