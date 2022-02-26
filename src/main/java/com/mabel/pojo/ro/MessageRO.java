package com.mabel.pojo.ro;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @project: message-gateway
 * @description:
 * @author: Mabel.Chen
 * @create: 2022-02-26
 **/
@Data
public class MessageRO {

    @Length(min = 1, max = 64, message = "标题必须是1-64个字符以内")
    private String title;
    private String content;
}