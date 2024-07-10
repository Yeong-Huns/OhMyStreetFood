package org.omsf.member.dao;

import java.util.Optional;

import org.omsf.member.model.GeneralMember;

/**
* @packageName    : org.omsf.member.dao
* @fileName       : GeneralMemberRepository.java
* @author         : leeyunbin
* @date           : 2024.06.18
* @description    :
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2024.06.18        leeyunbin       최초 생성
*/

public interface GeneralMemberRepository extends MemberRepository<GeneralMember> { // yunbin
	void insertGeneralMember(GeneralMember GeneralMember);
	boolean checkMemberNickName(String nickName);
	Optional<GeneralMember> findByUsername(String username);
	void updateMember(GeneralMember generalMember);
}
