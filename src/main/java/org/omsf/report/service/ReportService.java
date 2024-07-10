package org.omsf.report.service;

import java.util.List;
import java.util.Map;

import org.omsf.report.model.Report;

/**
* @packageName    : org.omsf.report.service
* @fileName       : ReportService.java
* @author         : leeyunbin
* @date           : 2024.06.18
* @description    :
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2024.06.18        leeyunbin       최초 생성
*/

public interface ReportService {

	void insertReport(Report report);

	Map<Integer, List<Report>> getReportsGroupedByStoreNo();

	void deleteReport(int reportNo);

}
