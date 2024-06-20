package org.omsf.member.model;

import java.sql.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class Owner extends Member{
	private String BankAccount;
	
	@Builder
	public Owner(String username, String password, String memberType, String loginType, Date createdAt,
			Date modifiedAt, String BankAccount) {
		super(username, password, memberType, loginType, createdAt, modifiedAt);
		this.BankAccount=BankAccount;
	}
	
	@Builder
	public Owner(String memberType) {
		super(memberType);
	}

}
