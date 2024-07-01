package org.omsf.store.service;

import java.util.List;

import org.omsf.store.model.Like;

public interface LikeService {

	Integer isLike(Like like);
	void insertLike(Like like);
	void deleteLike(Like like);
	
	// yunbin
	List<Like> getLikesByUsername(String username);
}
