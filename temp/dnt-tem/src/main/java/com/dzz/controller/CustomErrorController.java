package com.dzz.controller;

import com.dzz.entity.WebResponse;
import com.dzz.support.exception.ExceptionGenerator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletRequestAttributes;
import springfox.documentation.annotations.ApiIgnore;

/**
 * custom error controller
 *
 * @author Jeffrey
 * @since 2017/07/06 13:48
 */
@RestController
@ApiIgnore
public class CustomErrorController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @Autowired
    private ErrorAttributes errorAttributes;

    @RequestMapping(ERROR_PATH)
    public WebResponse error(HttpServletRequest request, HttpServletResponse response) {
        Throwable throwable = getException(request);
        response.setStatus(getErrorStatus(response));
        return WebResponse
            .createResp(getErrorRespCode(throwable), getErrorMessage(throwable), false, null);
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    /**
     * 获取异常
     *
     * @param request HttpServletRequest
     * @return Throwable
     */
    private Throwable getException(HttpServletRequest request) {
        ServletRequestAttributes requestAttributes = new ServletRequestAttributes(request);
        Throwable throwable = errorAttributes.getError(requestAttributes);
        if (throwable == null) {
            throwable = new Throwable(
                (String) errorAttributes.getErrorAttributes(requestAttributes, false)
                    .get("message"));
        }
        return throwable;
    }

    /**
     * 获取错误状态码
     *
     * @param response HttpServletResponse
     * @return response http status
     */
    private int getErrorStatus(HttpServletResponse response) {
        return response.getStatus();
    }

    /**
     * 获取错误状态码
     *
     * @param throwable Throwable
     * @return response http status
     */
    private int getErrorRespCode(Throwable throwable) {
        return ExceptionGenerator.SYSTEM_ERROR.respCode();
    }

    /**
     * 获取错误信息
     *
     * @param throwable Throwable
     * @return error message
     */
    private String getErrorMessage(Throwable throwable) {
        return throwable.getMessage();
    }
}
