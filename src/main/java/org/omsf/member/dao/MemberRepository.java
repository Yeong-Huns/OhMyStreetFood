package org.omsf.member.dao;

import java.util.Optional;

import org.apache.ibatis.annotations.Param;
import org.omsf.member.model.Member;

public interface MemberRepository<T extends Member> { // yunbin
	boolean checkMemberId(String username);
	Optional<T> findByUsername(String username);
	void updatePassword(@Param("username") String username, @Param("temporaryPassword") String temporaryPassword);
	void deleteMember(String username);
}
