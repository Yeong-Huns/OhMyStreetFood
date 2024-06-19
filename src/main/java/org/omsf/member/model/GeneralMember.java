package org.omsf.member.model;

import java.sql.Date;

import lombok.Builder;
import lombok.NoArgsConstructor;

public class GeneralMember extends Member {
	
	@Builder
	public GeneralMember(String username, String nickName, String password, String memberType,
			String loginType, Date createdAt, Date modifiedAt) {
		super(username, nickName, password, memberType, loginType, createdAt, modifiedAt);
	}

}
