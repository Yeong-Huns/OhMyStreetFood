package org.omsf.report.dao;

import java.util.List;

import org.omsf.report.model.Report;

// yunbin
public interface ReportRepository {

	void insertReport(Report report);
	
	List<Report> getReports();

	void deleteReport(int reportNo);

}
