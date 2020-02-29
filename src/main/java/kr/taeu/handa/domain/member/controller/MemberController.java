package kr.taeu.handa.domain.member.controller;

import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.taeu.handa.domain.member.domain.Member;
import kr.taeu.handa.domain.member.dto.MemberResponse;
import kr.taeu.handa.domain.member.dto.SignUpRequest;
import kr.taeu.handa.domain.member.service.MemberDetailsService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
	private final MemberDetailsService memberDetailsService;

	@GetMapping("/welcome")
	public String welcome(Authentication authentication) {
		return authentication.getName() + " hello!";
	}

	@PostMapping("/signup")
	public MemberResponse signUp(@RequestBody @Valid final SignUpRequest signUpRequest) {
		final Member member = this.memberDetailsService.createMember(signUpRequest);

		return new MemberResponse(member);
	}
}
