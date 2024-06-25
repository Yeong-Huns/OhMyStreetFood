package org.omsf.report.dao;

import java.util.List;

import org.omsf.report.model.Report;

public interface ReportRepository {

	void insertReport(Report report);
	
	List<Report> getReports();

}
