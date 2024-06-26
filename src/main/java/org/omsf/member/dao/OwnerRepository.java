package org.omsf.member.dao;

import java.util.Optional;

import org.omsf.member.model.Owner;

public interface OwnerRepository extends MemberRepository<Owner> {
	void insertOwner(Owner owner);
	Optional<Owner> findByUsername(String username);
	void updateMember(Owner owner);
	boolean checkBusinessRegistrationNumber(String businessRegistrationNumber);
}
