package com.dzz.support.enums;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 响应状态码枚举
 *
 * @author Jeffrey
 * @since 2017/10/12 14:18
 */
public enum RespCodeEnum {

    /**
     * 成功
     */
    SUCCESS(10000, "success"),

    /**
     * 系统错误
     */
    SYSTEM_ERROR(20001, "system error"),

    /**
     * 登录超时
     */
    LOGIN_TIMEOUT(20002, "login timeout"),

    /**
     * 无权限
     */
    UNAUTHORIZED(20003, "unauthorized"),

    /**
     * 参数校验错误
     */
    PARAM_VALIDATE_ERROR(20004, "parameter validation error"),

    /**
     * zuul熔断
     */
    ZUUL_CIRCUIT_BREAKER(20005, "zuul circuit breaker"),

    /**
     * 自定义错误
     */
    CUSTOM_ERROR(30001, "custom error");

    /**
     * response code map
     */
    private static final Map<String, Map<String, Object>> RESP_CODE_MAP = new HashMap<>();

    static {
        for (RespCodeEnum codeEnum : values()) {
            Map<String, Object> map = new HashMap<>(3);
            map.put("code", codeEnum.code);
            map.put("value", codeEnum.value);
            RESP_CODE_MAP.put(codeEnum.toString(), map);
        }
    }

    /**
     * 响应码
     */
    private int code;

    /**
     * value
     */
    private String value;

    RespCodeEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int code() {
        return code;
    }

    public String value() {
        return value;
    }

    public static Map<String, Map<String, Object>> getEnumValues() {
        return Collections.unmodifiableMap(RESP_CODE_MAP);
    }
}
