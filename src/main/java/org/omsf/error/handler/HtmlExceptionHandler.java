package org.omsf.error.handler;

import lombok.extern.slf4j.Slf4j;
import org.omsf.error.Exception.CustomBaseException;
import org.omsf.error.Exception.ErrorCode;
import org.omsf.error.Exception.ResourceNotFoundException;
import org.omsf.error.response.ErrorResponse;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * packageName    : org.omsf.error.handler
 * fileName       : HtmlExceptionHandler
 * author         : Yeong-Huns
 * date           : 2024-06-30
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-30        Yeong-Huns       최초 생성
 */

@Slf4j
@ControllerAdvice(annotations = Controller.class)
public class HtmlExceptionHandler{
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ModelAndView handle(HttpRequestMethodNotSupportedException e){
        log.error("Response: {}", ErrorResponse.of(ErrorCode.METHOD_NOT_ALLOWED , " 🥲[상세 로그] : "+e.getMessage()));
        return createErrorModelAndView(ErrorCode.METHOD_NOT_ALLOWED, e.getMessage());
    }

    @ExceptionHandler(CustomBaseException.class)
    protected ModelAndView handle(CustomBaseException e){
        log.error("Response: {}", ErrorResponse.of(e.getErrorCode(),  " 🥲[상세 메세지] : "+e.getMessage()));
        return createErrorModelAndView(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ModelAndView handle(MethodArgumentNotValidException e){
        log.error("MethodArgumentNotValidException", e);
        return createErrorModelAndView(ErrorCode.INVALID_INPUT_VALUE, e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ModelAndView handle(HttpMessageNotReadableException e){
        log.error("HttpMessageNotReadableException", e);
        return createErrorModelAndView(ErrorCode.MESSAGE_NOT_READABLE, e.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ModelAndView handle(ResourceNotFoundException e){
        log.error("Response: {}", ErrorResponse.of(ErrorCode.RESOURCE_NOT_FOUND , " 🥲[상세 메세지] : "+e.getMessage()));
        return createErrorModelAndView(ErrorCode.RESOURCE_NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    protected ModelAndView handle(Exception e){
        log.error("Response: {}", ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR , " 🥲[상세 메세지] : "+e.getMessage()));
        return createErrorModelAndView(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ModelAndView handle(AccessDeniedException e) {
        log.error("Response: {}", ErrorResponse.of(ErrorCode.METHOD_NOT_ALLOWED, " 🥲[상세 메세지] : " + e.getMessage()));
        return createErrorModelAndView(ErrorCode.METHOD_NOT_ALLOWED, e.getMessage());
    }


    private ModelAndView createErrorModelAndView(ErrorCode errorCode, String detailMessage) {
        ModelAndView mav = new ModelAndView("error/errorPage"); // 에러 페이지로 이동
        mav.addObject("code", errorCode.getCode());
        mav.addObject("message", errorCode.getMessage());
        mav.addObject("detail", detailMessage);
        return mav;
    }
}
