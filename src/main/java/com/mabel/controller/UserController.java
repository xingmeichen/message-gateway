package com.mabel.controller;

import com.mabel.pojo.po.CommonError;
import com.mabel.pojo.po.CommonResponse;
import com.mabel.pojo.ro.UserLoginRO;
import com.mabel.pojo.ro.UserLogoutRO;
import com.mabel.pojo.ro.UserRegisterRO;
import com.mabel.pojo.vo.LoginSuccessResponse;
import com.mabel.pojo.vo.ResponseEntity;
import com.mabel.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @project: message-gateway
 * @description: 鉴权管理接口
 * @author: Mabel.Chen
 * @create: 2022-02-26
 **/
@RestController
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userServiceImpl;

    /***
     * 用户注册
     * @param registerRO
     * @return
     */
    @PostMapping("/auth/user/register")
    public ResponseEntity register(@Validated @RequestBody UserRegisterRO registerRO, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                String errorMessage = bindingResult.getFieldError().getDefaultMessage();
                return ResponseEntity.wrongParam(errorMessage);
            }
            Integer code = userServiceImpl.register(registerRO);
            if (code == CommonError.DUPLICATE_USER_NAME.getErrorCode()) {
                // 提示用户用户名已经存在
                return ResponseEntity.parseResponseWithErrorMsg(CommonError.getEnumByCode(code));
            }
            return ResponseEntity.parseResponse(CommonError.getEnumByCode(code));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity.parseResponse(CommonResponse.INTERNAL_SYSTEM_ERROR);
        }
    }

    /***
     * 用户登录
     * @param loginRO
     * @return
     */
    @PostMapping("/auth/user/login")
    public ResponseEntity login(HttpSession session, @Validated @RequestBody UserLoginRO loginRO, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                String errorMessage = bindingResult.getFieldError().getDefaultMessage();
                return ResponseEntity.wrongParam(errorMessage);
            }
            String sessionId = session.getId();
            Integer code = userServiceImpl.login(loginRO, sessionId);
            if (code < 0) {
                return ResponseEntity.parseResponse(CommonError.getEnumByCode(code));
            }
            ResponseEntity success = ResponseEntity.success();
            LoginSuccessResponse response = new LoginSuccessResponse();
            BeanUtils.copyProperties(success, response);
            response.setSessionId(sessionId);
            return response;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity.parseResponse(CommonResponse.INTERNAL_SYSTEM_ERROR);
        }
    }

    /***
     * 用户登出
     * @param logoutRO
     * @return
     */
    @PostMapping("/auth/user/logout")
    public ResponseEntity logout(@Validated @RequestBody UserLogoutRO logoutRO, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                String errorMessage = bindingResult.getFieldError().getDefaultMessage();
                return ResponseEntity.wrongParam(errorMessage);
            }
            Integer code = userServiceImpl.logout(logoutRO);
            return ResponseEntity.parseResponse(CommonError.getEnumByCode(code));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity.parseResponse(CommonResponse.INTERNAL_SYSTEM_ERROR);
        }
    }
}