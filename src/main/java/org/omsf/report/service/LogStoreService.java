package org.omsf.report.service;

import java.util.List;
import java.util.Map;
/**
 * author         : leejongseop
 * description    :
 * ===========================================================
 *    AUTHOR            NOTE
 * -----------------------------------------------------------
 *  leejongseop       최초 생성
 */
//leejongseop
public interface LogStoreService {

	List<Map<String, Object>> getLogListByStoreNo(int storeNo);
	void updateStore(int logId);
	
	List<Map<String, Object>> getLogListJSONByStoreNo(int storeNo, int page);
}
