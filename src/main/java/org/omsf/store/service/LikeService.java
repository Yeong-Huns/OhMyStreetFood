package org.omsf.store.service;

import org.omsf.store.model.Like;

public interface LikeService {

	Integer isLike(Like like);
	void insertLike(Like like);
	void deleteLike(Like like);
}
