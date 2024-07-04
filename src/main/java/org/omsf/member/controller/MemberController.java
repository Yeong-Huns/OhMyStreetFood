package org.omsf.member.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.omsf.member.model.GeneralMember;
import org.omsf.member.model.Member;
import org.omsf.member.model.Owner;
import org.omsf.member.service.EmailService;
import org.omsf.member.service.GeneralMemberService;
import org.omsf.member.service.MemberService;
import org.omsf.member.service.OwnerService;
import org.omsf.review.model.Review;
import org.omsf.review.service.ReviewService;
import org.omsf.store.model.Like;
import org.omsf.store.model.Store;
import org.omsf.store.service.LikeService;
import org.omsf.store.service.StoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MemberController { // yunbin

	private final GeneralMemberService generalMemberService;
	private final OwnerService ownerService;
	private final MemberService<Member> memberService;
	private final EmailService emailService;
	private final StoreService storeService;
	private final LikeService likeService;
	private final ReviewService reviewService;

	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;

	@GetMapping("/signin")
	public String showSignInPage(Model model) {
		return "member/signin";
	}

	@GetMapping("/signup/{memberType}")
	public String showGeneralSignUpPage(@PathVariable String memberType, Model model) {
		if (memberType.equals("general")) {
			GeneralMember generalMember = GeneralMember.builder().memberType(memberType).build();
			model.addAttribute("member", generalMember);
		} else {
			Owner owner = Owner.builder().memberType(memberType).build();
			model.addAttribute("member", owner);
		}

		return "member/signup";
	}

	@PostMapping("/signup/general")
	public String processSignUp(@Valid @ModelAttribute("member") GeneralMember generalMember, BindingResult result,
			Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		try {
			if (!generalMember.getPasswordConfirm().isEmpty()
					&& !generalMember.getPassword().equals(generalMember.getPasswordConfirm())) {
				result.rejectValue("passwordConfirm", "passwordInCorrect", "비밀번호가 일치하지 않습니다.");
				model.addAttribute("member", generalMember);
				return "member/signup";
			}

			if (result.hasErrors()) {
				model.addAttribute("member", generalMember);
				return "member/signup";
			}
			String rawPassword = generalMember.getPassword();
			
			generalMemberService.insertGeneralMember(generalMember);
			redirectAttributes.addFlashAttribute("success", true);
			
			authenticateUserAndSetSession(generalMember.getUsername(), rawPassword, request);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/";
	}

	@PostMapping("/signup/owner")
	public String processSignUp(@Valid @ModelAttribute("member") Owner owner, BindingResult result, Model model, HttpServletRequest request) {
		try {
			if (!owner.getPasswordConfirm().isEmpty() && !owner.getPassword().equals(owner.getPasswordConfirm())) {
				result.rejectValue("passwordConfirm", "passwordInCorrect", "비밀번호가 일치하지 않습니다.");
				model.addAttribute("owner", owner);
				return "member/signup";
			}

			if (result.hasErrors()) {
				model.addAttribute("owner", owner);
				return "member/signup";
			}

			String rawPassword = owner.getPassword();
			
			ownerService.insertOwner(owner);
			model.addAttribute("success", true);
			
			authenticateUserAndSetSession(owner.getUsername(), rawPassword, request);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "store/addStore";
	}

	@PostMapping({ "/signup/confirmId", "/findPassword/confirmId" })
	@ResponseBody
	public ResponseEntity<Boolean> comfirmId(String username) {
		boolean result = true;

		if (username.trim().isEmpty()) {
			result = false;
		} else {
			if (memberService.checkMemberId(username)) {
				result = false;
			} else {
				result = true;
			}
		}

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping({ "/signup/confirmNickName", "/modifyMember/confirmNickName" })
	@ResponseBody
	public ResponseEntity<Boolean> comfirmNickName(String nickName) {
		boolean result = true;

		if (nickName.trim().isEmpty()) {
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

	@PostMapping("/signup/confirmBusinessRegistrationNumber")
	@ResponseBody
	public ResponseEntity<Boolean> comfirmBusinessRegistrationNumber(String businessRegistrationNumber) {
		boolean result = true;

		if (businessRegistrationNumber.trim().isEmpty()) {
			result = false;
		} else {
			if (ownerService.checkBusinessRegistrationNumber(businessRegistrationNumber)) {
				result = false;
			} else {
				result = true;
			}
		}

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/mypage")
	public String showMypage(Model model, Principal principal) {
		// 등록한 가게 리스트 전달
		List<Store> registeredStores = storeService.getStoresByUsername(principal.getName());
		model.addAttribute("registeredStores", registeredStores);
		
		// 찜한 가게 리스트 전달
		List<Like> likes = likeService.getLikesByUsername(principal.getName());
		List<Store> likeStores = new ArrayList<>();
		
		for(Like like : likes) {
			likeStores.add(storeService.getStoreByNo(like.getStoreStoreNo()));
		}
		model.addAttribute("likeStores", likeStores);
		
		// 리뷰 리스트 전달
		List<Review> reviews = reviewService.getReviewsByUsername(principal.getName());
		List<Store> reviewStores = new ArrayList<>();
		
		model.addAttribute("reviews", reviews);
		for(Review review : reviews) {
			reviewStores.add(storeService.getStoreByNo(review.getStoreStoreNo()));
		}
		model.addAttribute("reviewStores", reviewStores);
		
		// 사용자 정보 전달
		for (String authority : currentUserAuthority()) {
			if (authority.equals("ROLE_USER")) {
				Optional<GeneralMember> _member = generalMemberService.findByUsername(principal.getName());

				if (_member.isPresent()) {
					GeneralMember member = _member.get();
					model.addAttribute("member", member);
				}
			}
		}

		return "member/mypage";
	}

	@PostMapping("/findPassword")
	public ResponseEntity<String> processFindPassword(@RequestParam String username) {
	    try {
	        emailService.sendEmail(username);
	        return ResponseEntity.ok()
	                             .contentType(MediaType.APPLICATION_JSON)
	                             .body("임시비밀번호를 전송 했습니다.");
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.badRequest()
	                             .contentType(MediaType.APPLICATION_JSON)
	                             .body("서버 오류입니다. 다시 시도해주세요.");
	    }
	}

	@PostMapping("/confirmPassword")
	@ResponseBody
	public boolean confirmPassword(Principal principal, String password, Model model, HttpSession session) {
		Optional<Member> _member = memberService.findByUsername(principal.getName());

		if (_member.isPresent()) {
			Member member = _member.get();
			session.setAttribute("passwordConfirmed", true);
			return passwordEncoder.matches(password, member.getPassword());
		}
		return false;
	}

	@GetMapping("/modifyMember")
	public String showModifyMemberFormPage(Model model, Principal principal, HttpSession session) {
		Boolean passwordConfirmed = (Boolean) session.getAttribute("passwordConfirmed");

		if (passwordConfirmed != null && passwordConfirmed) {
	        for (String authority : currentUserAuthority()) {
	            if (authority.equals("ROLE_USER")) {
	                Optional<GeneralMember> _member = generalMemberService.findByUsername(principal.getName());

	                if (_member.isPresent()) {
	                    GeneralMember member = _member.get();
	                    model.addAttribute("member", member);
	                }
	            } else if (authority.equals("ROLE_OWNER")) {
	                Optional<Owner> _member = ownerService.findByUsername(principal.getName());

	                if (_member.isPresent()) {
	                    Owner member = _member.get();
	                    model.addAttribute("member", member);
	                }
	            }
	        }
	        return "member/modify";
	    } else {
	        // 비밀번호 확인이 필요한 경우 처리
	        return "redirect:/mypage"; // 예시로 비밀번호 확인 페이지로 리다이렉트
	    }

	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping("/modifyMember/general")
	public String processModifyMember(@Valid @ModelAttribute("member") GeneralMember generalMember,
			BindingResult result, Model model) {

		if (generalMember.getPassword() != null && !generalMember.getPassword().isEmpty()) {
			if (result.hasErrors()) {
				model.addAttribute("member", generalMember);
				return "member/modify";
			}

			if (!generalMember.getPassword().equals(generalMember.getPasswordConfirm())) {
				result.rejectValue("passwordConfirm", "passwordInCorrect", "비밀번호가 일치하지 않습니다.");
				model.addAttribute("member", generalMember);
				return "member/modify";
			}
		}

		generalMemberService.updateMember(generalMember);

		return "redirect:/mypage";
	}

	@PreAuthorize("hasRole('ROLE_OWNER')")
	@PostMapping("/modifyMember/owner")
	public String processModifyMember(@Valid @ModelAttribute("member") Owner owner, BindingResult result, Model model) {

		if (owner.getPassword() != null && !owner.getPassword().isEmpty()) {
			if (result.hasErrors()) {
				model.addAttribute("member", owner);
				return "member/modify";
			}

			if (!owner.getPassword().equals(owner.getPasswordConfirm())) {
				result.rejectValue("passwordConfirm", "passwordInCorrect", "비밀번호가 일치하지 않습니다.");
				model.addAttribute("member", owner);
				return "member/modify";
			}
		}
		ownerService.updateMember(owner);

		return "redirect:/mypage";
	}

	@PostMapping("/withdrawal")
	@ResponseBody
	@Transactional
	public ResponseEntity<?> processWithdrawal(Principal principal, HttpServletRequest request, HttpServletResponse response) {
	    try {
	        
	        for (String authority : currentUserAuthority()) {
	        	if (authority.equals("ROLE_OWNER")) {
					storeService.deleteStoreByUsername(principal.getName());
				}
			}
	        
	        memberService.deleteMember(principal.getName());

	        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
	        logoutHandler.logout(request, response, SecurityContextHolder.getContext().getAuthentication());

	        Map<String, String> responseBody = new HashMap<>();
	        responseBody.put("redirectUrl", "/signin?withdrawalSuccess");
	        return ResponseEntity.ok().body(responseBody);
	    } catch (Exception e) {
	        Map<String, String> errorResponse = new HashMap<>();
	        errorResponse.put("error", "서버 오류입니다. 다시 시도해 주세요.");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
	}

	// 현재 사용자 권한 가져옴
	private List<String> currentUserAuthority() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

		List<String> authorityList = authorities.stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());

		return authorityList;
	}
	
	private void authenticateUserAndSetSession(String username, String rawPassword, HttpServletRequest request) {
	    try {
	        UsernamePasswordAuthenticationToken authToken = 
	                new UsernamePasswordAuthenticationToken(username, rawPassword);
			Authentication authentication = authenticationManager.authenticate(authToken);
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}
