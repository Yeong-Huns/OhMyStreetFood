package org.omsf.report.service;

import java.util.List;
import java.util.Map;

//leejongseop
public interface LogStoreService {

	List<Map<String, Object>> getLogListByStoreNo(int storeNo);
	void updateStore(int logId);
}
