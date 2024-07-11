package org.omsf.member.service;

import java.io.IOException;
import java.util.Optional;

import org.omsf.member.dao.GeneralMemberRepository;
import org.omsf.member.dao.MemberRepository;
import org.omsf.member.model.GeneralMember;
import org.omsf.member.model.Member;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

/**
* @packageName    : org.omsf.member.service
* @fileName       : GeneralMemberServiceImpl.java
* @author         : leeyunbin
* @date           : 2024.06.18
* @description    :
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2024.06.18        leeyunbin       최초 생성
*/

@Service
@RequiredArgsConstructor
public class GeneralMemberServiceImpl implements GeneralMemberService {
	private final GeneralMemberRepository generalMemberRepository;
	private final MemberRepository<Member> memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final UploadService uploadService;
	
	@Override
	public void insertGeneralMember(GeneralMember generalMember) {
		generalMember.setPassword(passwordEncoder.encode(generalMember.getPassword()));
		
		try {
			MultipartFile file = uploadService.getImageAsMultipartFile("https://avatar.iran.liara.run/public");
			String url = uploadService.uploadImage(file);
			generalMember.setProfileImage(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		generalMemberRepository.insertGeneralMember(generalMember);
	}

	@Override
	public boolean checkMemberId(String username) {
		return memberRepository.checkMemberId(username);
	}

	@Override
	public boolean checkMemberNickName(String nickName) {
		return generalMemberRepository.checkMemberNickName(nickName);
	}

	@Override
	public void deleteMember(String username) {
		memberRepository.deleteMember(username);
	}

	@Override
	public Optional<GeneralMember> findByUsername(String username) {
		return generalMemberRepository.findByUsername(username);
	}

	@Override
	@Transactional
	public void updateMember(GeneralMember generalMember) {
		if (!generalMember.getPassword().isEmpty())
			generalMember.setPassword(passwordEncoder.encode(generalMember.getPassword()));
		
		System.out.println("받은 사진");
		generalMemberRepository.updateMember(generalMember);
	}

}