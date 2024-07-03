package org.omsf.report.service;

import java.util.List;
import java.util.Map;

import org.omsf.report.model.Report;

public interface ReportService {

	void insertReport(Report report);

	Map<Integer, List<Report>> getReportsGroupedByStoreNo();

	void deleteReport(int reportNo);

}
