package com.dzz.support.exception;

import com.dzz.entity.WebResponse;

import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * global exception handler
 *
 * @author Jeffrey
 * @since 2017/05/05 16:55
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private static final String LOG_MESSAGE = "--- system error! --- Host: {}, Url: {}, ERROR: {}";

    private static final String PARAM_ERROR_MESSAGE = "Parameter check error!";

    /**
     * 未定义系统异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public WebResponse sysErrorHandler(HttpServletRequest req, Exception e) {
        printErrorLog(req, e);
        return WebResponse
            .createResp(ExceptionGenerator.SYSTEM_ERROR.respCode(), e.getMessage(), false, null);
    }

    /**
     * 参数校验异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public WebResponse argumentErrorHandler(HttpServletRequest req,
        ConstraintViolationException e) {
        printErrorLog(req, e);
        return WebResponse
            .createResp(ExceptionGenerator.REQUEST_PARAM_NOT_VALIDATE.respCode(), PARAM_ERROR_MESSAGE,
                false,
                e.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(",")));
    }

    /**
     * 参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public WebResponse argumenErrorHandler(HttpServletRequest req,
        MethodArgumentNotValidException e) {
        printErrorLog(req, e);
        return WebResponse
            .createResp(ExceptionGenerator.REQUEST_PARAM_NOT_VALIDATE.respCode(), PARAM_ERROR_MESSAGE,
                false,
                e.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining(",")));
    }

    /**
     * 处理BaseException
     */
    @ExceptionHandler(BaseException.class)
    @ResponseBody
    public WebResponse serviceErrorHandler(HttpServletRequest req, HttpServletResponse res,
        BaseException e) {
        printWarnLog(req, e);
        res.setStatus(e.getHttpStatus().value());
        return WebResponse.createResp(e.getRespCode(), e.getMessage(), false, null);
    }

    /**
     * 打印错误日志(会打印出堆栈信息)
     */
    private void printErrorLog(HttpServletRequest req, Exception e) {
        LOGGER.error(LOG_MESSAGE, req.getRemoteHost(), req.getRequestURL(), e);
    }

    /**
     * 打印警告日志(仅打印错误信息)
     */
    private void printWarnLog(HttpServletRequest req, Exception e) {
        LOGGER.warn(LOG_MESSAGE, req.getRemoteHost(), req.getRequestURL(), e.getMessage());
    }
}
