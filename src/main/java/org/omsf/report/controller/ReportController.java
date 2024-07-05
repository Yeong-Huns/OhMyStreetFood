package org.omsf.report.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.omsf.report.model.Report;
import org.omsf.report.service.LogStoreService;
import org.omsf.report.service.ReportService;
import org.omsf.store.service.StoreService;
import org.omsf.store.service.ViewCountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	private final ViewCountService viewCountService;

	// leejongseop
	private final LogStoreService logStoreService;
	
	@PostMapping("/store/report/{storeNo}")
	public ResponseEntity<?> processStoreReport(@Valid @ModelAttribute Report report, BindingResult result, Principal principal) {
	    if (result.hasErrors()) {
	        // 유효성 검사 오류가 있는 경우 JSON으로 오류 메시지 반환
	        Map<String, String> errors = new HashMap<>();
	        for (FieldError error : result.getFieldErrors()) {
	            errors.put(error.getField(), error.getDefaultMessage());
	        }
	        Map<String, Object> response = new HashMap<>();
	        response.put("errors", errors);
	        return ResponseEntity.badRequest().body(response);
	    }
	    report.setUsername(principal.getName());
	    reportService.insertReport(report);
	    
	    // 성공적으로 신고가 접수된 경우 JSON으로 성공 메시지와 신고한 가게 번호 반환
	    Map<String, Object> response = new HashMap<>();
	    response.put("storeNo", report.getStoreNo());
	    return ResponseEntity.ok(response);
	}
	
	@GetMapping("/admin/reports")
	public String showReportList(Model model) {
		Map<Integer, List<Report>> groupedReports = reportService.getReportsGroupedByStoreNo();
		model.addAttribute("groupedReports", groupedReports);
		
		return "admin/reportList";
	}
	
	@PostMapping("/admin/deleteStore")
	@ResponseBody
	public boolean deleteStore(int storeNo) {
		try {
			storeService.deleteStore(storeNo);
			viewCountService.removeStoreRankings(storeNo);
			return true;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	@PostMapping("/admin/deleteReport")
	@ResponseBody
	public boolean deleteReport(int reportNo) {
		try {
			reportService.deleteReport(reportNo);
			return true;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	// leejongseop - 로그 페이지 응답
	@GetMapping("/admin/store/log/{storeId}")
	public String showLogList(@PathVariable("storeId") int storeId, Model model) {
		List<Map<String, Object>> list = logStoreService.getLogListByStoreNo(storeId);
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
	// leejongseop - 로그 무한 스크롤 요청
	@ResponseBody
	@GetMapping("/admin/store/log/{storeId}/api")
	public ResponseEntity<?> getLogNextPage(@PathVariable("storeId") int storeId, 
			@RequestParam(value = "page", defaultValue = "2") int page){
		List<Map<String, Object>> list = logStoreService.getLogListJSONByStoreNo(storeId, page);
		if(list.size() < 1) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return ResponseEntity.status(HttpStatus.OK)
				.body(list);
	}
	
	
}
