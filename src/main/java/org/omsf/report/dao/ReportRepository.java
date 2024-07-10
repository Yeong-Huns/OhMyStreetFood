package org.omsf.report.dao;

import java.util.List;

import org.omsf.report.model.Report;

/**
* @packageName    : org.omsf.report.dao
* @fileName       : ReportRepository.java
* @author         : leeyunbin
* @date           : 2024.06.18
* @description    :
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2024.06.18        leeyunbin       최초 생성
*/

public interface ReportRepository {

	void insertReport(Report report);
	
	List<Report> getReports();

	void deleteReport(int reportNo);

}
