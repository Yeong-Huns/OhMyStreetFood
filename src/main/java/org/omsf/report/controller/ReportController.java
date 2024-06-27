package org.omsf.report.controller;

import java.security.Principal;
import java.util.List;

import org.omsf.report.model.Report;
import org.omsf.report.service.ReportService;
import org.omsf.store.service.StoreService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ReportController {	
	// yunbin
	private final ReportService reportService;
	private final StoreService storeService;
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/store/report/{storeNo}")
	public String showStoreReportPage(@PathVariable Integer storeNo, Model model) {
		model.addAttribute("storeNo", storeNo);
		return "store/report";
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/store/report/{storeNo}")
	public String processStoreReport(Report report, Principal principal) {
		report.setUsername(principal.getName());
		reportService.insertReport(report);
		return "store";
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/admin")
	public String showReportList(Model model) {
		List<Report> reports = reportService.getReports();
		model.addAttribute("reports", reports);
		
		return "admin/reportList";
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/deleteStore")
	@ResponseBody
	public boolean deleteStore(int storeNo) {
		try {
			storeService.deleteStore(storeNo);
			return true;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}
}
