package org.omsf.member.service;

import javax.mail.internet.MimeMessage;

import org.omsf.member.dao.MemberRepository;
import org.omsf.member.model.Member;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

/**
* @packageName    : org.omsf.member.service
* @fileName       : EmailService.java
* @author         : leeyunbin
* @date           : 2024.06.18
* @description    :
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2024.06.18        leeyunbin       최초 생성
*/

@RequiredArgsConstructor
@Service
public class EmailService {

	private final JavaMailSenderImpl mailSender;
	private final MemberRepository<Member> memberRepository;
	private final PasswordEncoder passwordEncoder;
	
	// 보내는 사람
	@Value("${mail.username}")
	String from;

	public void sendEmail(String username) throws Exception {
		String temporaryPassword = getTemporaryPassword();
		
		// 메일 내용
		String subject = "[OhMyStreetFood] 임시 비밀번호 안내 이메일입니다.";
		String content = "안녕하세요. OhMyStreetFood 입니다. 회원님의 임시 비밀번호는 " + temporaryPassword
				+ "입니다. 로그인 후 비밀번호를 변경해주세요.";

		try {
			MimeMessage mail = mailSender.createMimeMessage();
			MimeMessageHelper mailHelper = new MimeMessageHelper(mail, "UTF-8");

			mailHelper.setFrom(from); // 보내는 사람
			mailHelper.setTo(username); // 받는 사람
			mailHelper.setSubject(subject); // 제목
			mailHelper.setText(content); // 내용

			// 메일 전송
			mailSender.send(mail);
			updatePassword(username, temporaryPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getTemporaryPassword() {
		char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
				'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

		String str = "";

		// 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 구문을 작성함
		int idx = 0;
		for (int i = 0; i < 10; i++) {
			idx = (int) (charSet.length * Math.random());
			str += charSet[idx];
		}
		return str;
	}
	
	private void updatePassword(String username, String temporaryPassword) {
		String encodePassword = passwordEncoder.encode(temporaryPassword);
		memberRepository.updatePassword(username, encodePassword);
	}
}
