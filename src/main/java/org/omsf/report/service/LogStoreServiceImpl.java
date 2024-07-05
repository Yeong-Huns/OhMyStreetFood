package org.omsf.report.service;

import java.util.List;
import java.util.Map;

import org.omsf.report.dao.LogStoreRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
/**
 * author         : leejongseop
 * ===========================================================
          AUTHOR             NOTE
 * -----------------------------------------------------------
       leejongseop       최초 생성
 */
//leejongseop
@Service
@RequiredArgsConstructor
public class LogStoreServiceImpl implements LogStoreService {

	private final LogStoreRepository logStoreRepository;
	
	@Override
	public List<Map<String, Object>> getLogListByStoreNo(int storeNo) {
		return logStoreRepository.getLogListByStoreNo(storeNo);
	}

	@Override
	public void updateStore(int logId) {
		logStoreRepository.updateStore(logId);
	}

	@Override
	public List<Map<String, Object>> getLogListJSONByStoreNo(int storeNo, int page) {
		return logStoreRepository.getLogListJSONByStoreNo(storeNo, page);
	}

}
