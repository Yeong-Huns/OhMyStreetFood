package org.omsf.store.dao;

import java.util.List;

import org.omsf.store.model.Like;

public interface LikeRepository {

	Integer isLike(Like like);
	void insertLike(Like like);
	void deleteLike(Like like);
	
	// yunbin
	List<Like> getLikesByUsername(String username);
}
