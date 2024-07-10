package org.omsf.member.dao;

import java.util.Optional;

import org.omsf.member.model.Owner;

/**
* @packageName    : org.omsf.member.dao
* @fileName        : OwnerRepository.java
* @author        : leeyunbin
* @date            : 2024.07.08
* @description            :
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2024.07.08        leeyunbin       최초 생성
*/

public interface OwnerRepository extends MemberRepository<Owner> { // yunbin
	void insertOwner(Owner owner);
	Optional<Owner> findByUsername(String username);
	void updateMember(Owner owner);
	boolean checkBusinessRegistrationNumber(String businessRegistrationNumber);
}
