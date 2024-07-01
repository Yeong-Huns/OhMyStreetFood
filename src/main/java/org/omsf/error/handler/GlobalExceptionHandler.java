package org.omsf.error.handler;

import lombok.extern.slf4j.Slf4j;
import org.omsf.error.Exception.CustomBaseException;
import org.omsf.error.Exception.ErrorCode;
import org.omsf.error.Exception.ResourceNotFoundException;
import org.omsf.error.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : org.omsf.error.handler
 * fileName       : GlobalExceptionHandler
 * author         : Yeong-Huns
 * date           : 2024-06-18
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-18        Yeong-Huns       최초 생성
 */
@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class  GlobalExceptionHandler {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handle(HttpRequestMethodNotSupportedException e){
        log.error("Response: {}", ErrorResponse.of(ErrorCode.METHOD_NOT_ALLOWED , " [Detail log] : "+e.getMessage()));
        return createErrorResponse(ErrorCode.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(CustomBaseException.class)
    protected ResponseEntity<ErrorResponse> handle(CustomBaseException e){
        log.error("Response: {}", ErrorResponse.of(e.getErrorCode(),  " [Detail Message] : "+e.getMessage()));
        return createErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException e){
        log.error("MethodArgumentNotValidException", e);
        return createErrorResponse(ErrorCode.INVALID_INPUT_VALUE);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ErrorResponse> handle(HttpMessageNotReadableException e){
        log.error("HttpMessageNotReadableException", e);
        return createErrorResponse(ErrorCode.MESSAGE_NOT_READABLE);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handle(ResourceNotFoundException e){
        log.error("Response: {}", ErrorResponse.of(ErrorCode.RESOURCE_NOT_FOUND , " [Detail Message] : "+e.getMessage()));
        return createErrorResponse(ErrorCode.RESOURCE_NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handle(Exception e){
        log.error("Response: {}", ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR , " [Detail Message] : "+e.getMessage()));
        return createErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR);
    }
    //최종 에러처리

    private ResponseEntity<ErrorResponse> createErrorResponse(ErrorCode errorCode){
        return new ResponseEntity<>(
                ErrorResponse.of(errorCode),
                errorCode.getStatus());
    }
}