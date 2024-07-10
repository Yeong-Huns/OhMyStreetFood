package org.omsf.store.dao;

import java.util.List;
import java.util.Map;

/**
* @packageName    : org.omsf.store.dao
* @fileName       : SearchRepository.java
* @author         : iamjaeeuncho
* @date           : 2024.06.20
* @description    :
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2024.06.20        iamjaeeuncho       최초 생성
*/

public interface SearchRepository { 
	void insertKeyword(Map<String, Object> params);
    List<Map<String, Object>> getAllKeywords();
}
