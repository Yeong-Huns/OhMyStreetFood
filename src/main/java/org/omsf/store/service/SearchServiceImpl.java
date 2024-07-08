package org.omsf.store.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.omsf.store.dao.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @packageName    : org.omsf.store.service
* @fileName       : SearchServiceImpl.java
* @author         : iamjaeeuncho
* @date           : 2024.06.20
* @description    :
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2024.06.20        iamjaeeuncho       최초 생성
*/

@Service
public class SearchServiceImpl implements SearchService{

	@Autowired
    private SearchRepository searchRepository;
	
    @Override
    public void insertKeyword(String userIp, String keyword) { 
        Map<String, Object> params = new HashMap<>();
        params.put("userIp", userIp);
        params.put("keyword", keyword);
        params.put("createdAt", new Timestamp(System.currentTimeMillis()));
        searchRepository.insertKeyword(params);
    }

    @Override
    public List<Map<String, Object>> getAllKeywords() {
        return searchRepository.getAllKeywords();
    }
}
