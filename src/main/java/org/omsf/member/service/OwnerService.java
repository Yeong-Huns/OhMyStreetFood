package org.omsf.member.service;

import org.omsf.member.model.Owner;

/**
* @packageName    : org.omsf.member.service
* @fileName       : OwnerService.java
* @author         : leeyunbin
* @date           : 2024.06.18
* @description    :
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2024.06.18        leeyunbin       최초 생성
*/

public interface OwnerService extends MemberService<Owner> {

	void insertOwner(Owner owner);
	void updateMember(Owner owner);
	boolean checkBusinessRegistrationNumber(String businessRegistrationNumber);

}
