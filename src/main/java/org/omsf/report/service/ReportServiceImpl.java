package org.omsf.report.service;

import java.util.List;

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
	public List<Report> getReports() {
		return reportRepository.getReports();
	}

}
