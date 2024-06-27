package org.omsf.error.Exception;

/**
 * packageName    : org.omsf.error.Exception
 * fileName       : NotFoundException
 * author         : Yeong-Huns
 * date           : 2024-06-18
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-18        Yeong-Huns       최초 생성
 */
public class NotFoundException extends CustomBaseException{
    public NotFoundException(ErrorCode errorCode){
        super(errorCode.getMessage(), errorCode);
    }
    public NotFoundException(){
        super(ErrorCode.NOT_FOUND);
    }
}
