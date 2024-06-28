package org.omsf.store.dao;

import org.omsf.store.model.Like;

public interface LikeRepository {

	Integer isLike(Like like);
	void insertLike(Like like);
	void deleteLike(Like like);
}
