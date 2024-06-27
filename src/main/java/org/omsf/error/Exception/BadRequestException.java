package org.omsf.error.Exception;

/**
 * packageName    : org.omsf.error.Exception
 * fileName       : BadRequestException
 * author         : Yeong-Huns
 * date           : 2024-06-18
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-18        Yeong-Huns       최초 생성
 */
public class BadRequestException extends CustomBaseException {
    public BadRequestException(ErrorCode errorCode){
        super(errorCode.getMessage(), errorCode);
    }
    public BadRequestException(){
        super(ErrorCode.INVALID_INPUT_VALUE);
    }
}