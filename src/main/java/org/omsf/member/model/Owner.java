package org.omsf.member.model;

import java.sql.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class Owner extends Member{ // yunbin
	@NotEmpty(message = "사업자 등록 번호 입력은 필수입니다.")
	@Pattern(regexp = "\\d{10}", message = "10자리 숫자만 입력 가능합니다.")
	private String businessRegistrationNumber;
	
	@Builder
	public Owner(String username, String password, String memberType, Date createdAt,
			Date modifiedAt, String businessRegistrationNumber) {
		super(username, password, memberType, createdAt, modifiedAt);
		this.businessRegistrationNumber=businessRegistrationNumber;
	}
	
	@Builder
	public Owner(String memberType) {
		super(memberType);
	}

}
