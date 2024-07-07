package org.omsf.member.service;

import java.util.Optional;

import org.omsf.member.dao.MemberRepository;
import org.omsf.member.model.Member;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService<Member> { // yunbin

	private final MemberRepository<Member> memberRepository;
	
	@Override
	public boolean checkMemberId(String username) {
		return memberRepository.checkMemberId(username);
	}

	@Override
	public Optional<Member> findByUsername(String username) {
		return memberRepository.findByUsername(username);
	}

	@Override
	public void deleteMember(String username) {
		memberRepository.deleteMember(username);
	}

}
