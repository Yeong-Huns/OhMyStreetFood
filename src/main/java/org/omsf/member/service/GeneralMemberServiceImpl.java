package org.omsf.member.service;

import java.util.Optional;

import org.omsf.member.dao.GeneralMemberRepository;
import org.omsf.member.dao.MemberRepository;
import org.omsf.member.model.GeneralMember;
import org.omsf.member.model.Member;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class GeneralMemberServiceImpl implements GeneralMemberService { // yunbin
	private final GeneralMemberRepository generalMemberRepository;
	private final MemberRepository<Member> memberRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public void insertGeneralMember(GeneralMember generalMember) {
		generalMember.setPassword(passwordEncoder.encode(generalMember.getPassword()));
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
		generalMemberRepository.updateMember(generalMember);
	}
}