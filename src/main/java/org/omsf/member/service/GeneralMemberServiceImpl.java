package org.omsf.member.service;

import org.omsf.member.dao.GeneralMemberRepository;
import org.omsf.member.model.Member;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class GeneralMemberServiceImpl implements GeneralMemberService {
	private final GeneralMemberRepository generalMemberRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public void insertGeneralMember(Member generalMember) {
		generalMember.setPassword(passwordEncoder.encode(generalMember.getPassword()));
		generalMemberRepository.insertGeneralMember(generalMember);
	}

	@Override
	public boolean checkMemberId(String username) {
		return generalMemberRepository.checkMemberId(username);
	}

	@Override
	public boolean checkMemberNickName(String nickName) {
		return generalMemberRepository.checkMemberNickName(nickName);
	}
}