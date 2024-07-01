package org.omsf.report.dao;

import java.util.List;
import java.util.Map;

// leejongseop
public interface LogStoreRepository {

	List<Map<String, Object>> getLogListByStoreNo(int storeNo);
	void updateStore(int logId);
}
