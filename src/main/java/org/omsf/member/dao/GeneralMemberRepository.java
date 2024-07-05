package org.omsf.member.dao;

import java.util.Optional;

import org.omsf.member.model.GeneralMember;
import org.omsf.member.model.Member;

public interface GeneralMemberRepository extends MemberRepository<GeneralMember> { // yunbin
	void insertGeneralMember(GeneralMember GeneralMember);
	boolean checkMemberNickName(String nickName);
	Optional<GeneralMember> findByUsername(String username);
	void updateMember(GeneralMember generalMember);
}
