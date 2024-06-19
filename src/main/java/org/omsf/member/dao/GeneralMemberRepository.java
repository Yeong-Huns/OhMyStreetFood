package org.omsf.member.dao;

import org.omsf.member.model.GeneralMember;
import org.omsf.member.model.Member;

public interface GeneralMemberRepository extends MemberRepository<GeneralMember> {
	void insertGeneralMember(Member GeneralMember);
	boolean checkMemberNickName(String nickName);
}
