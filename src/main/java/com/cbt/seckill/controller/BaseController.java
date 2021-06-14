package com.cbt.seckill.controller;

import com.cbt.seckill.error.BusinessException;
import com.cbt.seckill.error.EmBusinessError;
import com.cbt.seckill.response.CommonReturnType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class BaseController {

    public static final String CONTENT_TYPE_FORMED = "application/x-www-form-urlencoded";

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public Object handlerException(HttpServletRequest request, Exception exception) {
        Map<String, Object> map = new HashMap<>();
        if (exception instanceof BusinessException) {
            BusinessException businessException = (BusinessException) exception;
            map.put("errCode", businessException.getErrorCode());
            map.put("errMsg", businessException.getErrorMsg());
        } else {
            map.put("errCode", EmBusinessError.UNKNOWN_ERROR.getErrorCode());
            map.put("errMsg", EmBusinessError.UNKNOWN_ERROR.getErrorMsg());
        }
        log.error(exception.getMessage(), exception);
        return CommonReturnType.create(map, "fail");
    }
}
