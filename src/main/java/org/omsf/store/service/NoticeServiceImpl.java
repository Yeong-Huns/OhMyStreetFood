package org.omsf.store.service;

import java.util.List;

import org.omsf.store.dao.NoticeRepository;
import org.omsf.store.model.Notice;
import org.omsf.store.model.NoticeDto;
import org.omsf.store.model.NoticeDto.NoticeDetailRequest;
import org.omsf.store.model.NoticeDto.NoticeDetailResponse;
import org.omsf.store.model.NoticeMessage;
import org.omsf.store.model.Pagenation;
import org.omsf.store.model.UserNoticeStatus;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {
	
	private final NoticeRepository noticeRepository;
	private final RabbitTemplate rabbitTemplate;
	
    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;
    @Value("${rabbitmq.routing.key}")
    private String routingKey;
	
	@Override
	@Transactional
	public NoticeDto.Response createNotice(NoticeDto.Create noticeDTO) {
		Notice notice = noticeDTO.toEntity();
		noticeRepository.createNotice(notice);
		int noticeNo = notice.getNoticeNo();
		
		NoticeMessage message = NoticeMessage.builder()
				.storeNo(notice.getStoreNo())
				.noticeNo(noticeNo)
				.build();
		rabbitTemplate.convertAndSend(exchangeName, routingKey,  message);
		
		NoticeDto.Response noticeDto = NoticeDto.Response.of(notice);
		
		return noticeDto;
	}

	@Override
	public void sendToSubscribers(List<String> username, int noticeNo) {
		for (String user : username) {	
			UserNoticeStatus noticeStatus = UserNoticeStatus.builder()
					.username(user)
					.noticeNo(noticeNo)
					.build();
			
			noticeRepository.sendToSubscriber(noticeStatus);
		}
		
	}

	@Override
	public List<NoticeDetailResponse> findNoticesByUsername(String username, int pageNumber, int pageSize) {
		Pagenation page = Pagenation.builder()
				.pageNumber(pageNumber)
				.pageSize(pageSize)
				.offset((pageNumber - 1) * pageSize)
				.build();
		
		NoticeDetailRequest noticeRequest = NoticeDetailRequest.builder()
		.username(username)
		.page(page)
		.build();
		
		List<NoticeDetailResponse> noticeList = noticeRepository.findNoticesByUsername(noticeRequest);
		return noticeList;
	}

	@Override
	public void markNoticeAsRead(int noticeNo) {
		noticeRepository.markNoticeAsRead(noticeNo);
	}

	@Override
	public void markNoticeAsDeleted(int noticeNo) {
		noticeRepository.markNoticeAsDeleted(noticeNo);
	}
}
