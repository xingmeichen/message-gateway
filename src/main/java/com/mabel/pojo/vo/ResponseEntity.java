package com.mabel.pojo.vo;

import com.mabel.pojo.po.CommonError;
import com.mabel.pojo.po.CommonResponse;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @project: message-gateway
 * @description:
 * @author: Mabel.Chen
 * @create: 2022-02-26
 **/
@Data
@Accessors(chain = true)
public class ResponseEntity {
    private String message;
    private Integer code;

    public static ResponseEntity success() {
        ResponseEntity responseEntity = new ResponseEntity();
        CommonResponse msg = CommonResponse.SUCCESS;
        responseEntity.setMessage(msg.getMessage()).setCode(msg.getCode());
        return responseEntity;
    }

    public static ResponseEntity wrongParam(String errorMsg) {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setCode(CommonResponse.WRONG_PARAMETER.getCode())
                .setMessage(errorMsg);
        return responseEntity;
    }

    public static ResponseEntity parseResponse(CommonResponse commonResponse) {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setCode(commonResponse.getCode())
                .setMessage(commonResponse.getMessage());
        return responseEntity;
    }

    public static ResponseEntity parseResponse(CommonError code) {
        CommonResponse commonResponse = code.getResponse();
        return parseResponse(commonResponse);
    }

    public static ResponseEntity parseResponseWithErrorMsg(CommonError code) {
        ResponseEntity responseEntity = parseResponse(code);
        responseEntity.setMessage(code.getErrorMsg());
        return responseEntity;
    }
}