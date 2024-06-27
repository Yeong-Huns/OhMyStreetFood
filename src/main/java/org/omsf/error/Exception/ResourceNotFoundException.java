package org.omsf.error.Exception;

/**
 * packageName    : org.omsf.error.Exception
 * fileName       : ResourceNotFoundException
 * author         : Yeong-Huns
 * date           : 2024-06-18
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-18        Yeong-Huns       최초 생성
 */
public class ResourceNotFoundException extends CustomBaseException {
    public ResourceNotFoundException() {
        super(ErrorCode.RESOURCE_NOT_FOUND);
    }
}
