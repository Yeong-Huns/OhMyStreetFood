package org.omsf.report.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.omsf.report.model.Report;
import org.omsf.report.service.LogStoreService;
import org.omsf.report.service.ReportService;
import org.omsf.store.service.StoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ReportController {	
	// yunbin
	private final ReportService reportService;
	private final StoreService storeService;
	

	// leejongseop
	private final LogStoreService logStoreService;
	

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
	
	// leejongseop
	@GetMapping("/admin/store/log/{storeId}")
	public String showLogList(@PathVariable("storeId") int storeId, Model model) {
		List<Map<String, Object>> list = logStoreService.getLogListByStoreNo(storeId);
		log.info("로그 정보 : {}", list);
		model.addAttribute("list", list);
		return "admin/logList";
	}
	
	@ResponseBody
	@PostMapping("/admin/store/log/update")
	public boolean updateStore(int logNo) {
		log.info("로그 id : {}", logNo);
		try {
			logStoreService.updateStore(logNo);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@ResponseBody
	@GetMapping("/admin/store/log/{storeId}/api")
	public ResponseEntity<?> getLogNextPage(@PathVariable("storeId") int storeId, 
			@RequestParam(value = "page", defaultValue = "2") int page){
		log.info("ajax로 로그 정보 호출");
		List<Map<String, Object>> list = logStoreService.getLogListJSONByStoreNo(storeId, page);
		log.info("로그 api 결과 정보 : {}",list.toString());
		if(list.size() < 1) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return ResponseEntity.status(HttpStatus.OK)
				.body(list);
	}
	
	
}
