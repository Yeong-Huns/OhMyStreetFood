package org.omsf.member.service;

import java.io.IOException;
import java.util.Optional;

import org.omsf.member.dao.MemberRepository;
import org.omsf.member.dao.OwnerRepository;
import org.omsf.member.model.Member;
import org.omsf.member.model.Owner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

/**
* @packageName    : org.omsf.member.service
* @fileName       : OnwerServiceImpl.java
* @author         : leeyunbin
* @date           : 2024.06.18
* @description    :
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2024.06.18        leeyunbin       최초 생성
*/

@RequiredArgsConstructor
@Service
public class OnwerServiceImpl implements OwnerService {
	private final OwnerRepository ownerRepository;
	private final MemberRepository<Member> memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final UploadService uploadService;
	
	@Override
	public void insertOwner(Owner owner) {
		owner.setPassword(passwordEncoder.encode(owner.getPassword()));
		
		try {
			MultipartFile file = uploadService.getImageAsMultipartFile("https://avatar.iran.liara.run/public");
			String url = uploadService.uploadImage(file);
			owner.setProfileImage(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ownerRepository.insertOwner(owner);
	}
	
	@Override
	public boolean checkMemberId(String username) {
		return memberRepository.checkMemberId(username);
	}

	@Override
	public void deleteMember(String username) {

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
