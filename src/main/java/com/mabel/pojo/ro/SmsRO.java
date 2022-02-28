package com.mabel.pojo.ro;

import com.mabel.pojo.dto.MessageDTO;
import com.mabel.utils.ZonedDateUtil;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @project: message-gateway
 * @description:
 * @author: Mabel.Chen
 * @create: 2022-02-26
 **/
@Data
@Accessors(chain = true)
public class SmsRO {

    private String qos;
    private String acceptor_tel;
    private TemplateParam template_param;

    /***
     * 时间戳，格式为 yyyy-MM-dd HH:mm:ss
     */
    private String timestamp;

    @Data
    public static class TemplateParam {
        private String title;
        private String content;
    }

    public static SmsRO parse(MessageDTO msgDTO) {
        SmsRO ro = new SmsRO();
        TemplateParam tp = new TemplateParam();
        MessageRO msgRO = msgDTO.getMessageRO();
        tp.setTitle(msgRO.getTitle()).setContent(msgRO.getContent());
        ro.setTemplate_param(tp)
                .setQos(msgDTO.getQos()).setAcceptor_tel(msgDTO.getTels())
                .setTimestamp(ZonedDateUtil.formatDate(new Date()));
        return ro;
    }
}