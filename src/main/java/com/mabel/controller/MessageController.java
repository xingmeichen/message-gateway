package com.mabel.controller;

import com.mabel.pojo.dto.MessageDTO;
import com.mabel.pojo.po.CommonError;
import com.mabel.pojo.po.CommonResponse;
import com.mabel.pojo.ro.MessageRO;
import com.mabel.pojo.vo.ResponseEntity;
import com.mabel.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @project: message-gateway
 * @description: 消息接口
 * @author: Mabel.Chen
 * @create: 2022-02-26
 **/
@RestController
@Validated
public class MessageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private MessageService messageServiceImpl;

    @PostMapping("/directmessage")
    public ResponseEntity directMessage(@NotBlank(message = "手机号不能为空")
//                                        @Pattern(regexp = "^(((13[0-9])|(14[579])|(15([0-3]|[5-9]))|(16[6])|(17[0135678])|(18[0-9])|(19[89]))\\d{8})$", message = "手机号格式错误")
                                        @RequestParam(name = "tels")
                                                String tels,
                                        @Pattern(regexp = "[1|2|3]{1}", message = "消息优先级只能是1，2，3中的任意一个")
                                        @RequestParam(name = "qos")
                                                String qos,
                                        @Pattern(regexp = "[a-zA-Z\\d]{3,32}", message = "非法用户")
                                        @RequestParam(name = "userName")
                                                String userName,
                                        @NotBlank(message = "会话ID不能为空")
                                        @RequestParam(name = "sessionId")
                                                String sessionId,
                                        @Validated @RequestBody MessageRO msgRO, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                String errorMessage = bindingResult.getFieldError().getDefaultMessage();
                return ResponseEntity.wrongParam(errorMessage);
            }
            MessageDTO dto = MessageDTO.parseMessageDTO(tels, qos, userName, sessionId, msgRO);
            Integer code = messageServiceImpl.directMessage(dto);
            return ResponseEntity.parseResponse(CommonError.getEnumByCode(code));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity.parseResponse(CommonResponse.INTERNAL_SYSTEM_ERROR);
        }
    }
}