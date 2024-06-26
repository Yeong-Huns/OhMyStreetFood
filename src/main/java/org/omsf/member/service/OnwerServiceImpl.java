package org.omsf.member.service;

import java.util.Optional;

import org.omsf.member.dao.MemberRepository;
import org.omsf.member.dao.OwnerRepository;
import org.omsf.member.model.Member;
import org.omsf.member.model.Owner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OnwerServiceImpl implements OwnerService {
	private final OwnerRepository ownerRepository;
	private final MemberRepository<Member> memberRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public void insertOwner(Owner owner) {
		owner.setPassword(passwordEncoder.encode(owner.getPassword()));
		ownerRepository.insertOwner(owner);
	}
	
	@Override
	public boolean checkMemberId(String username) {
		return memberRepository.checkMemberId(username);
	}

	@Override
	@Transactional
	public void deleteMember(String username) {
		memberRepository.deleteMember(username);
		// 가게 삭제
		// 메뉴 삭제
		// 리뷰 삭제
	}

	@Override
	public Optional<Owner> findByUsername(String username) {
		return ownerRepository.findByUsername(username);
	}

	@Override
	public void updateMember(Owner owner) {
		if (!owner.getPassword().isEmpty())
			owner.setPassword(passwordEncoder.encode(owner.getPassword()));
		
		ownerRepository.updateMember(owner);
	}

	@Override
	public boolean checkBusinessRegistrationNumber(String businessRegistrationNumber) {
		return ownerRepository.checkBusinessRegistrationNumber(businessRegistrationNumber);
	}

}
