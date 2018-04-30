package com.dzz.controller;

import com.dzz.entity.WebResponse;
import com.dzz.service.TestService;
import com.dzz.support.exception.ExceptionGenerator;
import com.dzz.support.enums.RespCodeEnum;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试用controller
 *
 * @author Jeffrey
 * @since 2017/10/11 13:11
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    TestService testService;


    @GetMapping("/success")
    @ApiOperation(value = "测试正常响应")
    public WebResponse sucess() {
        return WebResponse.createSuccessResp("success");
    }

    @GetMapping("/exception")
    @ApiOperation(value = "测试系统异常响应")
    public WebResponse exception() {
        throw new RuntimeException("I am System Exception");
    }

    @GetMapping("/base-exception")
    @ApiOperation(value = "测试逻辑异常响应")
    public WebResponse baseException() {
        throw ExceptionGenerator.create("I am BaseException");
    }

    @GetMapping("/base-exception/404")
    @ApiOperation(value = "测试404逻辑异常响应")
    public WebResponse baseException404() {
        throw ExceptionGenerator.create("I am BaseException", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/base-exception/login-timeout")
    @ApiOperation(value = "测试自定义响应码逻辑异常响应")
    public WebResponse baseExceptionLoginTimeout() {
        throw ExceptionGenerator
            .create("I am BaseException", HttpStatus.PAYMENT_REQUIRED, RespCodeEnum.LOGIN_TIMEOUT);
    }

    @GetMapping("/show/exception")
    @ApiOperation(value = "显示所有异常枚举")
    public WebResponse showException() {
        return WebResponse.createSuccessResp(ExceptionGenerator.getEnumValues());
    }

    @GetMapping("/show/respCode")
    @ApiOperation(value = "显示所有响应码枚举")
    public WebResponse showRespCode() {
        return WebResponse.createSuccessResp(RespCodeEnum.getEnumValues());
    }
}
