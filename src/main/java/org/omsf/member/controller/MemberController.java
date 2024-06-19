package org.omsf.member.controller;

import javax.validation.Valid;

import org.omsf.member.model.GeneralMember;
import org.omsf.member.service.GeneralMemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MemberController { // yunbin
	
	private final GeneralMemberService generalMemberService;
	
	@GetMapping("/signin")
    public String showSignInPage() {
        return "signin";
    }

    @GetMapping("/signup/general")
    public String showGeneralSignUpPage(@ModelAttribute GeneralMember generalMember) {
        return "signupGeneral";
    }
    
    @PostMapping("/signup/general")
    public String signUpGeneralMember(@ModelAttribute("generalMember") @Valid GeneralMember generalMember,
    		@RequestParam("passwordConfirm") String passwordConfirm,
                                      BindingResult result,
                                      Model model) {
        if (result.hasErrors()) {
        	model.addAttribute("generalMember", generalMember);
            return "signupGeneral";
        }

        if (!generalMember.getPassword().equals(passwordConfirm)) {
            result.rejectValue("password", "passwordInCorrect", "비밀번호가 일치하지 않습니다.");
            model.addAttribute("generalMember", generalMember);
            return "signupGeneral";
        }

        generalMemberService.insertGeneralMember(generalMember);
        model.addAttribute("success", true);

        return "redirect:/signin";
    }
    
    @PostMapping("/signup/confirm")
    @ResponseBody
    public ResponseEntity<Boolean> comfirmId(String username) {
    	boolean result = true;
		
		if(username.trim().isEmpty()) {
			result = false;
		} else {
			if (generalMemberService.getMemberId(username)) {
				result = false;
			} else {
				result = true;
			}
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    
}
