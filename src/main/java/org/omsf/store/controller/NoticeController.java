package org.omsf.store.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.omsf.store.model.NoticeDto;
import org.omsf.store.model.Pagenation;
import org.omsf.store.model.NoticeDto.NoticeDetailResponse;
import org.omsf.store.service.NoticeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/notice")
@RestController
public class NoticeController {
	
	private final NoticeService noticeService;
	
	@PostMapping("/create")
	public ResponseEntity<NoticeDto.Response> createNotice(@Valid @RequestBody NoticeDto.Create noticeCreateDto) {
		
		NoticeDto.Response response = noticeService.createNotice(noticeCreateDto);
        return ResponseEntity.ok(response);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<NoticeDetailResponse>> noticeList(
			Principal principal,
		    @RequestParam(defaultValue = "1") int pageNumber,
		    @RequestParam(defaultValue = "5") int pageSize) {
	
		List<NoticeDetailResponse> response =  noticeService.findNoticesByUsername(principal.getName(), pageNumber, pageSize);
		return ResponseEntity.ok(response);
	}
	
	public void testNotice() {
		//클라이언트에서 noticedto.create들어옴
		//서비스로 공지사항 생성 요청
		// 서비스에서 공지사항을 생성하고 rabbitmq에 메세지 보내서
		// 좋아요를 한 사람들에게 알림 전송
		NoticeDto.Create noticeDto =  NoticeDto.Create.builder()
				.storeNo(377)
				.title("부산녹차씨앗호떡 공지사항 입니다")
				.content("안녕하세요 ~ 잘 들리나요")
				.build();
	
		NoticeDto.Response response = noticeService.createNotice(noticeDto);
	}
	
}
