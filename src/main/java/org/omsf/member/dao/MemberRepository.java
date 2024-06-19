package org.omsf.member.dao;

import org.omsf.member.model.Member;

public interface MemberRepository<T extends Member> {
	boolean getMemberId(String id);
}
