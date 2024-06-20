package org.omsf.member.dao;

import org.omsf.member.model.Owner;

public interface OwnerRepository extends MemberRepository<Owner> {
	void insertOwner(Owner owner);
}
