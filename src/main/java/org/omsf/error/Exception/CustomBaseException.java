package org.omsf.error.Exception;

import lombok.Getter;

/**
 * packageName    : org.omsf.error.Exception
 * fileName       : CustomBaseException
 * author         : Yeong-Huns
 * date           : 2024-06-18
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-18        Yeong-Huns       최초 생성
 */
@Getter
public class CustomBaseException extends RuntimeException{
    private final ErrorCode errorCode;

    public CustomBaseException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public CustomBaseException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}