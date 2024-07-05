package org.omsf.member.service;

import org.omsf.member.model.Owner;

public interface OwnerService extends MemberService<Owner> { // yunbin

	void insertOwner(Owner owner);

	void updateMember(Owner owner);

	boolean checkBusinessRegistrationNumber(String businessRegistrationNumber);

}
