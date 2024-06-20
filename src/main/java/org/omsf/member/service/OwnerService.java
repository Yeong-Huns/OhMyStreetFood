package org.omsf.member.service;

import org.omsf.member.model.Owner;

public interface OwnerService extends MemberService<Owner> {

	void insertOwner(Owner owner);

}
