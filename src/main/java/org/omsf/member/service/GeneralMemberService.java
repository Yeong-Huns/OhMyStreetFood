package org.omsf.member.service;

import org.omsf.member.model.GeneralMember;

/**
* @packageName    : org.omsf.member.service
* @fileName       : GeneralMemberService.java
* @author         : leeyunbin
* @date           : 2024.06.18
* @description    :
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2024.06.18        leeyunbin       최초 생성
*/

public interface GeneralMemberService extends MemberService<GeneralMember>{

	void insertGeneralMember(GeneralMember generalMember);
	boolean checkMemberNickName(String nickName);
	void updateMember(GeneralMember generalMember);
	
}
