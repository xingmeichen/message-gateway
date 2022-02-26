package com.mabel.pojo.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @project: message-gateway
 * @description:
 * @author: Mabel.Chen
 * @create: 2022-02-26 15:16
 **/
@Data
@Accessors(chain = true)
public class LoginSuccessResponse extends ResponseEntity {

    private String sessionId;
}