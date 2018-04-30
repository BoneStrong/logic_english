package com.dzz.controller;

import com.dzz.entity.WebResponse;
import com.dzz.support.constant.SysConstant;
import com.dzz.support.exception.ExceptionGenerator;
import com.dzz.support.enums.RespCodeEnum;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * system Controller
 *
 * @author Jeffrey
 * @since 2017/10/13 14:16
 */
@RestController
@RequestMapping("/system")
public class SystemController {

    /**
     * 自定义debug模式
     */
    @Value("${" + SysConstant.GATEWAY_DEBUG_KEY + "}")
    private String debug;

    /**
     * service id
     */
    @Value("${" + SysConstant.GATEWAY_ID_KEY + "}")
    private String serviceId;

    /**
     * 获取是否开启自定义debug模式
     *
     * @return WebResponse
     */
    @GetMapping("debug")
    @ApiOperation(value = "获取是否开启自定义debug模式")
    public WebResponse isDebug() {
        return WebResponse.createSuccessResp(debug);
    }

    /**
     * 获取service id
     *
     * @return WebResponse
     */
    @GetMapping("serviceId")
    @ApiOperation(value = "获取service id")
    public WebResponse getServiceId() {
        return WebResponse.createSuccessResp(serviceId);
    }

    /**
     * 获取异常枚举
     *
     * @return WebResponse
     */
    @GetMapping("exceptions")
    @ApiOperation(value = "获取异常枚举")
    public WebResponse getExceptionEnums() {
        return WebResponse.createSuccessResp(ExceptionGenerator.getEnumValues());
    }

    /**
     * 获取请求异常响应码枚举
     *
     * @return WebResponse
     */
    @GetMapping("responseCodes")
    @ApiOperation(value = "获取请求异常响应码枚举")
    public WebResponse getRespCodeEnums() {
        return WebResponse.createSuccessResp(RespCodeEnum.getEnumValues());
    }
}
