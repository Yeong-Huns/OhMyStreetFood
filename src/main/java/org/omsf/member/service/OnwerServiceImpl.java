package org.omsf.member.service;

import org.omsf.member.dao.OwnerRepository;
import org.omsf.member.model.Owner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OnwerServiceImpl implements OwnerService {
	private final OwnerRepository ownerRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public void insertOwner(Owner owner) {
		owner.setPassword(passwordEncoder.encode(owner.getPassword()));
		ownerRepository.insertOwner(owner);
	}
	
	@Override
	public boolean checkMemberId(String username) {
		return ownerRepository.checkMemberId(username);
	}

}
