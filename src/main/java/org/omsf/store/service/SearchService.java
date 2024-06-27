package org.omsf.store.service;

import java.util.List;
import java.util.Map;

public interface SearchService {
    void insertKeyword(String userIp, String keyword);
    List<Map<String, Object>> getAllKeywords();
}
