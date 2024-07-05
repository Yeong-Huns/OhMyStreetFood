package org.omsf.report.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

// leejongseop
public interface LogStoreRepository {

	List<Map<String, Object>> getLogListByStoreNo(int storeNo);
	void updateStore(int logId);
	
	List<Map<String, Object>> getLogListJSONByStoreNo(@Param("storeNo") int storeNo, @Param("page") int page);
}
