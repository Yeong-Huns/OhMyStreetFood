package org.omsf.member.dao;

import java.util.Optional;

import org.apache.ibatis.annotations.Param;
import org.omsf.member.model.Member;

/**
* @packageName    : org.omsf.member.dao
* @fileName       : MemberRepository.java
* @author         : leeyunbin
* @date           : 2024.07.08
* @description    :
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2024.06.18        leeyunbin       최초 생성
*/


public interface MemberRepository<T extends Member> { // yunbin
	boolean checkMemberId(String username);
	Optional<T> findByUsername(String username);
	void updatePassword(@Param("username") String username, @Param("temporaryPassword") String temporaryPassword);
	void deleteMember(String username);
}
