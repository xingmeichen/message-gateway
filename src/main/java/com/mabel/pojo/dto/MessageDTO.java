package com.mabel.pojo.dto;

import com.mabel.pojo.ro.MessageRO;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

/**
 * @project: message-gateway
 * @description:
 * @author: Mabel.Chen
 * @create: 2022-02-26
 **/
@Data
@Accessors(chain = true)
public class MessageDTO {

    @NotBlank(message = "手机号不能为空")
    private String tels;

    @NotBlank(message = "消息优先级不能为空")
    private String qos;

    @NotBlank(message = "用户名不能为空")
    private String userName;

    @NotBlank(message = "会话ID不能为空")
    private String sessionId;

    @Transient
    private MessageRO messageRO;

    public static MessageDTO parseMessageDTO(String tel, String qos, String userName, String sessionId,  MessageRO msgRO) {
        MessageDTO dto = new MessageDTO();
        dto.setTels(tel)
                .setQos(qos)
                .setUserName(userName)
                .setSessionId(sessionId)
                .setMessageRO(msgRO);
        return dto;
    }
}