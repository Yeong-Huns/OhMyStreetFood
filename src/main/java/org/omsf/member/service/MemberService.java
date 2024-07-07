package org.omsf.member.service;

import java.util.Optional;

import org.omsf.member.model.Member;

public interface MemberService<T extends Member> { // yunbin
	boolean checkMemberId(String username);
	Optional<T> findByUsername(String username);
	void deleteMember(String username);
}
