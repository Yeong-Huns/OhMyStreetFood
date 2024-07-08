package org.omsf.store.service;

import java.util.List;
import java.util.Map;
/**
* @packageName    : org.omsf.store.service
* @fileName       : SearchService.java
* @author         : iamjaeeuncho
* @date           : 2024.06.20
* @description    :
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2024.06.20        iamjaeeuncho       최초 생성
*/

public interface SearchService {
    void insertKeyword(String userIp, String keyword);
    List<Map<String, Object>> getAllKeywords();
}
