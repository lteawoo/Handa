package kr.taeu.handa.member.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kr.taeu.handa.global.error.ErrorCode;
import kr.taeu.handa.global.model.Email;
import kr.taeu.handa.global.model.Password;
import kr.taeu.handa.member.dao.MemberRepository;
import kr.taeu.handa.member.domain.Member;
import kr.taeu.handa.member.domain.UniqueCode;
import kr.taeu.handa.member.dto.SignUpRequest;
import kr.taeu.handa.member.exception.EmailDuplicateException;
import kr.taeu.handa.member.exception.MemberNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class TestMemberService {
	@InjectMocks
	MemberService memberService;
	
	@Mock
	MemberRepository repo;
	
	@Test
	public void 없는_회원_조회() {
		//given
		given(this.repo.findByEmail(any(Email.class))).willReturn(Optional.empty());
		
		//when
		MemberNotFoundException thrown = assertThrows(MemberNotFoundException.class, 
				() -> this.memberService.findByEmail(Email.build("lteawoo@naver.com")));
		log.info("error : ", thrown);
		
		//then
		assertEquals(thrown.getErrorCode(), ErrorCode.MEMBER_NOT_FOUND);
	}
	
	@Test
	public void 이미_가입한_이메일로_회원가입() {
		//given
		SignUpRequest dto = new SignUpRequest(Email.build("lteawoo@naver.com"),
				"이태우",
				Password.build("12345"));
		given(this.repo.existsByEmail(any(Email.class))).willReturn(true);
		
		//when
		EmailDuplicateException thrown = assertThrows(EmailDuplicateException.class,
				() -> this.memberService.signUp(dto));
		log.info("error : ", thrown);
		
		//then
		assertEquals(thrown.getErrorCode(), ErrorCode.EMAIL_DUPLICATION);
	}
	
	@Test
	public void 가입하지_않은_이메일로_회원가입() {
		//given
		Email email = Email.build("lteawoo@naver.com");
		UniqueCode code = UniqueCode.generateCode();
		SignUpRequest dto = new SignUpRequest(email,
				"이태우",
				Password.build("12345"));
		
		given(this.repo.existsByEmail(email)).willReturn(false);
		given(this.repo.save(any(Member.class))).willReturn(dto.toEntity(code));
		
		//when
		Member signedMember = this.memberService.signUp(dto);
		
		//then
		verify(this.repo, atLeastOnce()).existsByEmail(email);
		verify(this.repo, atLeastOnce()).save(any());
		assertEquals(dto.getEmail(), signedMember.getEmail());
	}
}
