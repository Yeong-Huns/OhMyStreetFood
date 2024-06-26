package org.omsf.member.service;

import org.omsf.member.model.GeneralMember;
import org.omsf.member.model.Member;

public interface GeneralMemberService extends MemberService<GeneralMember>{

	void insertGeneralMember(GeneralMember generalMember);
	boolean checkMemberNickName(String nickName);
	void updateMember(GeneralMember generalMember);
	
}
