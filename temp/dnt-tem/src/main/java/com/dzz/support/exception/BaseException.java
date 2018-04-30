package com.dzz.support.exception;


import com.dzz.support.enums.RespCodeEnum;
import org.springframework.http.HttpStatus;

/**
 * base exception
 *
 * @author Jeffrey
 * @since 2017/7/5 17:58
 */
public class BaseException extends RuntimeException {

    /**
     * default http code
     */
    public static final HttpStatus DEFAULT_HTTP_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

    /**
     * default response code
     */
    public static final RespCodeEnum DEFAULT_RESPONSE_CODE = RespCodeEnum.CUSTOM_ERROR;

    /**
     * http status
     */
    protected final HttpStatus httpStatus;

    /**
     * response code
     */
    protected final int respCode;

    /**
     * message
     */
    protected final String message;

    public BaseException(String message) {
        super(message);
        this.message = message;
        this.httpStatus = DEFAULT_HTTP_STATUS;
        this.respCode = DEFAULT_RESPONSE_CODE.code();
    }

    public BaseException(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
        this.respCode = DEFAULT_RESPONSE_CODE.code();
    }

    public BaseException(String message, HttpStatus httpStatus, RespCodeEnum respCodeEnum) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
        this.respCode = respCodeEnum.code();
    }

    public BaseException(String message, RespCodeEnum respCodeEnum) {
        super(message);
        this.message = message;
        this.httpStatus = DEFAULT_HTTP_STATUS;
        this.respCode = respCodeEnum.code();
    }

    public BaseException(String message, Exception e) {
        super(message, e);
        this.message = message;
        this.httpStatus = DEFAULT_HTTP_STATUS;
        this.respCode = DEFAULT_RESPONSE_CODE.code();
    }

    public BaseException(String message, HttpStatus httpStatus, Exception e) {
        super(message, e);
        this.message = message;
        this.httpStatus = httpStatus;
        this.respCode = DEFAULT_RESPONSE_CODE.code();
    }

    public BaseException(String message, HttpStatus httpStatus, int respCode, Exception e) {
        super(message, e);
        this.message = message;
        this.httpStatus = httpStatus;
        this.respCode = respCode;
    }

    public BaseException(String message, HttpStatus httpStatus, RespCodeEnum respCodeEnum,
        Exception e) {
        super(message, e);
        this.message = message;
        this.httpStatus = httpStatus;
        this.respCode = respCodeEnum.code();
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public int getRespCode() {
        return respCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BaseException{");
        sb.append("httpStatus=").append(httpStatus);
        sb.append(", respCode=").append(respCode);
        sb.append(", message='").append(message).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
