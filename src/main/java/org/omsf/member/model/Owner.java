package org.omsf.member.model;

import java.sql.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class Owner extends Member{
	private String BankAccount;
	
	@Builder
	public Owner(String username, String nickName, String password, String memberType, String loginType, Date createdAt,
			Date modifiedAt, String BankAccount) {
		super(username, nickName, password, memberType, loginType, createdAt, modifiedAt);
		this.BankAccount=BankAccount;
	}

}
