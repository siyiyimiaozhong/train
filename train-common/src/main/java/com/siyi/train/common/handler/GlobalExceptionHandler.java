package com.siyi.train.common.handler;

import com.siyi.train.common.constant.ResultCode;
import com.siyi.train.common.exception.BusinessException;
import com.siyi.train.common.vo.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

/**
 * @ClassName: GlobalExceptionHandler
 * @Auther: Chengxin Zhang
 * @Date: 2023/8/18 21:42
 * @Description: 统一异常处理、数据预处理等
 * @Version 1.0.0
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 表单绑定到 java bean 出错时，会抛出 BindException 异常
     * @param request
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    public Result<Object> bindException(HttpServletRequest request, HttpServletResponse response, BindException e) {
        log.error("业务异常:", e);
        String message = e.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return Result.error(message);
    }

    /**
     * 将请求体解析并绑定到 java bean 时，如果出错，则抛出 MethodArgumentNotValidException 异常
     * @param request
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Object> methodArgumentNotValidException(HttpServletRequest request, HttpServletResponse response, MethodArgumentNotValidException e) {
        log.error("参数异常:", e);
        return Result.error(e.getBindingResult().getFieldError().getDefaultMessage());
    }

    /**
     * 业务自定义异常捕获
     * @param request
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public Result<Object> businessException(HttpServletRequest request, HttpServletResponse response, BusinessException e) {
        log.error("业务异常:", e);
        return Result.error(e.getResultCode());
    }

    /**
     * 其他异常捕获
     * @param request
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result<Object> error(HttpServletRequest request, HttpServletResponse response, Exception e) {
        log.error("系统异常：", e);
        return Result.error(ResultCode.SERVER_ERROR);
    }
}