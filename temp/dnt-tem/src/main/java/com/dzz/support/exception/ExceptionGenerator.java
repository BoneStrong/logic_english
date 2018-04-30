package com.dzz.support.exception;

import com.dzz.support.enums.RespCodeEnum;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;

/**
 * BaseException枚举
 *
 * @author Jeffrey
 * @since 2017/07/06 18:17
 */
public enum ExceptionGenerator {

    /**
     * system error
     */
    SYSTEM_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, RespCodeEnum.SYSTEM_ERROR, "系统错误,请稍后再试!"),

    /**
     * login timeout
     */
    LOGIN_TIMEOUT(HttpStatus.PAYMENT_REQUIRED, RespCodeEnum.LOGIN_TIMEOUT, "登录超时!"),

    /**
     * unauthorized
     */
    UNAUTHORIZED(HttpStatus.PAYMENT_REQUIRED, RespCodeEnum.UNAUTHORIZED, "无权限!"),

    /**
     * request param not validate
     */
    REQUEST_PARAM_NOT_VALIDATE(HttpStatus.BAD_REQUEST, RespCodeEnum.PARAM_VALIDATE_ERROR,
        "参数校验不合法!");

    /**
     * response code map
     */
    private static final Map<String, Map<String, Object>> EXCEPTION_MAP = new HashMap<>();

    static {
        for (ExceptionGenerator exceptionGenerator : values()) {
            Map<String, Object> map = new HashMap<>(5);
            map.put("httpStatus", exceptionGenerator.httpStatus.value());
            map.put("respCode", exceptionGenerator.respCode());
            map.put("message", exceptionGenerator.message);
            EXCEPTION_MAP.put(exceptionGenerator.toString(), map);
        }
    }

    /**
     * http code
     */
    private HttpStatus httpStatus;

    /**
     * RespCodeEnum
     */
    private RespCodeEnum respCodeEnum;

    /**
     * response message
     */
    private String message;

    /**
     * BaseException
     */
    private BaseException exception;

    ExceptionGenerator(HttpStatus httpStatus, RespCodeEnum respCodeEnum, String message) {
        this.httpStatus = httpStatus;
        this.respCodeEnum = respCodeEnum;
        this.message = message;
        this.exception = new BaseException(message, httpStatus, respCodeEnum);
    }

    public BaseException exception() {
        return this.exception;
    }

    public int respCode() {
        return this.respCodeEnum.code();
    }

    public HttpStatus httpStatus() {
        return this.httpStatus;
    }

    public static Map<String, Map<String, Object>> getEnumValues() {
        return Collections.unmodifiableMap(EXCEPTION_MAP);
    }

    public static BaseException create(String message, HttpStatus httpStatus,
        RespCodeEnum respCodeEnum) {
        return new BaseException(message, httpStatus, respCodeEnum);
    }

    public static BaseException create(String message, RespCodeEnum respCodeEnum) {
        return new BaseException(message, BaseException.DEFAULT_HTTP_STATUS, respCodeEnum);
    }

    public static BaseException create(String message, HttpStatus httpStatus) {
        return new BaseException(message, httpStatus, BaseException.DEFAULT_RESPONSE_CODE);
    }

    public static BaseException create(String message) {
        return new BaseException(message, BaseException.DEFAULT_HTTP_STATUS,
            BaseException.DEFAULT_RESPONSE_CODE);
    }
}
