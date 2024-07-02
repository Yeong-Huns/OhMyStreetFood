package org.omsf.report.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.omsf.report.dao.ReportRepository;
import org.omsf.report.model.Report;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReportServiceImpl implements ReportService{
	private final ReportRepository reportRepository;
	
	@Override
	public void insertReport(Report report) {
		reportRepository.insertReport(report);
	}
	
	@Override
	public Map<Integer, List<Report>> getReportsGroupedByStoreNo() {
		List<Report> reports = reportRepository.getReports();
		
		Map<Integer, List<Report>> groupedReports = reports.stream()
			    .collect(Collectors.groupingBy(Report::getStoreNo));
		
		return groupedReports;
	}

}
