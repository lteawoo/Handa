package kr.taeu.handa.member.controller;

import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.taeu.handa.global.model.Password;
import kr.taeu.handa.member.dto.MemberResponse;
import kr.taeu.handa.member.dto.SignUpRequest;
import kr.taeu.handa.member.service.MemberService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;
	
	@PostMapping(value="/api/member/signUp")
	public MemberResponse signUp(@RequestBody @Valid final SignUpRequest req) {
		return new MemberResponse(memberService.signUp(req));
	}
	
	@PostMapping(value="/api/member/signIn")
	public MemberResponse signIn(@RequestBody @Valid final SignInRequest req) {
		return new MemberResponse(memberService.signIn(req));
	}
}
