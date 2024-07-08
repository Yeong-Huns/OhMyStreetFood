package org.omsf.member.service;

import java.util.Optional;

import org.omsf.member.model.Member;

/**
* @packageName    : org.omsf.member.service
* @fileName       : MemberService.java
* @author         : leeyunbin
* @date           : 2024.06.18
* @description    :
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2024.06.18        leeyunbin       최초 생성
*/

public interface MemberService<T extends Member> { 
	boolean checkMemberId(String username);
	Optional<T> findByUsername(String username);
	void deleteMember(String username);
}
