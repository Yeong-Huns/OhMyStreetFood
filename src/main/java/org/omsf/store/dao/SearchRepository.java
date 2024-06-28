package org.omsf.store.dao;

import java.util.List;
import java.util.Map;

public interface SearchRepository {
	void insertKeyword(Map<String, Object> params);
    List<Map<String, Object>> getAllKeywords();
}
