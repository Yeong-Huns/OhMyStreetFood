package org.omsf.store.service;

import org.omsf.store.dao.LikeRepository;
import org.omsf.store.model.Like;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

	private final LikeRepository likeRepository;
	
	@Override
	public void insertLike(Like like) {
		likeRepository.insertLike(like);
	}

	@Override
	public void deleteLike(Like like) {
		likeRepository.deleteLike(like);
	}

	@Override
	public Integer isLike(Like like) {
		return likeRepository.isLike(like);
	}

}
