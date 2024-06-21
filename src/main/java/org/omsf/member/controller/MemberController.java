package org.omsf.member.controller;

import javax.validation.Valid;

import org.omsf.member.model.GeneralMember;
import org.omsf.member.model.Owner;
import org.omsf.member.service.GeneralMemberService;
import org.omsf.member.service.OwnerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MemberController { // yunbin
	
	private final GeneralMemberService generalMemberService;
	private final OwnerService ownerService;
	
	@GetMapping("/signin")
    public String showSignInPage() {
        return "member/signin";
    }

    @GetMapping("/signup/{memberType}")
    public String showGeneralSignUpPage(@PathVariable String memberType, Model model) {
    	if (memberType.equals("general")) {
    		GeneralMember generalMember = GeneralMember.builder().memberType(memberType).build();
    		model.addAttribute("member", generalMember);
    	}
    	else {
    		Owner owner = Owner.builder().memberType(memberType).build();
    		model.addAttribute("member", owner);
    	}
    	
        return "member/signup";
    }

    
    @PostMapping("/signup/general")
    public String processSignUp(@Valid @ModelAttribute("member") GeneralMember generalMember,
                                      BindingResult result,
                                      Model model,
                                      RedirectAttributes redirectAttributes) {
        try {
        	if (!generalMember.getPasswordConfirm().isEmpty() &&
        			!generalMember.getPassword().equals(generalMember.getPasswordConfirm())) {
			    result.rejectValue("passwordConfirm", "passwordInCorrect", "비밀번호가 일치하지 않습니다.");
			    model.addAttribute("member", generalMember);
			    System.out.println(generalMember);
			    System.out.println(generalMember.getMemberType());
			    return "member/signup";
			}
        	
        	if (result.hasErrors()) {
				model.addAttribute("member", generalMember);
				System.out.println(generalMember);
				System.out.println(generalMember.getMemberType());
				return "member/signup";
			}

			generalMemberService.insertGeneralMember(generalMember);
			redirectAttributes.addFlashAttribute("success", true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

        return "redirect:/signin";
    }
    
    @PostMapping("/signup/owner")
    public String processSignUp(@Valid @ModelAttribute("member") Owner owner,
                                      BindingResult result,
                                      Model model) {
        try {
        	if (!owner.getPasswordConfirm().isEmpty() &&
        			!owner.getPassword().equals(owner.getPasswordConfirm())) {
			    result.rejectValue("passwordConfirm", "passwordInCorrect", "비밀번호가 일치하지 않습니다.");
			    model.addAttribute("owner", owner);
			    return "member/signup";
			}
        	
        	if (result.hasErrors()) {
				model.addAttribute("owner", owner);
			    return "member/signup";
			}

			ownerService.insertOwner(owner);
			model.addAttribute("owner", owner);
			model.addAttribute("success", true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

        return "store/addStoreOwner";
    }
    
    @PostMapping("/signup/confirmId")
    @ResponseBody
    public ResponseEntity<Boolean> comfirmId(String username, String memberType) {
    	boolean result = true;
		
		if(username.trim().isEmpty()) {
			result = false;
		} else {
			if (memberType.equals("general")) {
				if (generalMemberService.checkMemberId(username)) {
					result = false;
				} else {
					result = true;
				}
			} else {
				if (ownerService.checkMemberId(username)) {
					result = false;
				} else {
					result = true;
				}
			}
			
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @PostMapping("/signup/confirmNickName")
    @ResponseBody
    public ResponseEntity<Boolean> comfirmNickName(String nickName) {
    	boolean result = true;
		
		if(nickName.trim().isEmpty()) {
			result = false;
		} else {
			if (generalMemberService.checkMemberNickName(nickName)) {
				result = false;
			} else {
				result = true;
			}
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mypage")
    public String showMypage() {
    	return "/member/mypage";
    }
    
}
