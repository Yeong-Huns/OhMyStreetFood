package org.omsf.report.service;

import java.util.List;

import org.omsf.report.model.Report;

public interface ReportService {

	void insertReport(Report report);

	List<Report> getReports();

}
