package com.mabel.utils;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @project: message-gateway
 * @description: 接口异常拦截器
 * @author: Mabel.Chen
 * @create: 2022-02-27
 **/
@RestControllerAdvice
public class RestExceptionHandler {

    /***
     * 拦截捕捉自定义异常 {@link javax.validation.ConstraintViolationException}
     */
    @ResponseBody
    @ExceptionHandler(value = ConstraintViolationException.class)
    public Map handleConstraintViolationException(ConstraintViolationException ex) {
        Map map = new HashMap();
        Set<ConstraintViolation<?>> constraintViolationSet = ex.getConstraintViolations();
        Iterator<ConstraintViolation<?>> iterator = constraintViolationSet.iterator();
        while (iterator.hasNext()) {
            map.put("message", "参数错误：" + iterator.next().getMessage());
        }
        map.put("code", 400);
        return map;
    }
}