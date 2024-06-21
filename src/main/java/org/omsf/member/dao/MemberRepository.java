package org.omsf.member.dao;

import java.util.Optional;

import org.omsf.member.model.Member;

public interface MemberRepository<T extends Member> {
	boolean checkMemberId(String username);
	Optional<T> findByUsername(String username);
}
