package org.omsf.member.model;

import java.sql.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Member {
	@NotEmpty(message = "아이디(이메일) 입력은 필수입니다.")
	@Email(message = "유효한 이메일 주소를 입력하세요.")
	private String username;
	
	@NotEmpty(message = "닉네임 입력은 필수입니다.")
	private String nickName;
	
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$",
            message = "비밀번호는 8~16자의 영문 대/소문자, 숫자, 특수문자를 포함해야 합니다.")
	@NotEmpty(message = "비밀번호 입력은 필수입니다.")
	private String password;
	
	private String memberType; 
	private String loginType;
	private Date createdAt;
	private Date modifiedAt;
	
}
