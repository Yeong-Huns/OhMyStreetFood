package org.omsf.main;

import java.util.ArrayList;
import java.util.List;

import org.omsf.store.model.Like;
import org.omsf.store.model.NoticeMessage;
import org.omsf.store.service.LikeService;
import org.omsf.store.service.NoticeService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NoticeMessageListener {
    
	private final LikeService likeService;
    private final NoticeService noticeService;
    
    public void receiveMessage(NoticeMessage message) {
    	Integer noticeNo = message.getNoticeNo();
        Integer storeNo = message.getStoreNo();
        
    	List<Like> likes = likeService.getLikesByStoreNo(storeNo);
        List<String> likeUsernames = new ArrayList<>();
        for (Like like : likes) {
            likeUsernames.add(like.getMemberUsername());
        }
       noticeService.sendToSubscribers(likeUsernames, noticeNo);
    }
    
}
