package kr.taeu.handa.member.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import kr.taeu.handa.domain.member.dao.MemberDetailsRepository;
import kr.taeu.handa.domain.member.domain.Member;
import kr.taeu.handa.domain.member.domain.model.Email;
import kr.taeu.handa.domain.member.domain.model.Name;
import kr.taeu.handa.domain.member.domain.model.Password;
import kr.taeu.handa.domain.member.dto.SignUpRequest;
import kr.taeu.handa.domain.member.exception.EmailAlreadyExistsException;
import kr.taeu.handa.domain.member.service.MemberDetailsService;
import kr.taeu.handa.global.config.AppConfig;
import kr.taeu.handa.global.error.ErrorCode;
import lombok.extern.slf4j.Slf4j;

/*
 * SpringExtension 및 Spring boot test를 이용하여 테스트
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@Import(AppConfig.class)
public class TestMemberService {
	@SpyBean
	MemberDetailsService memberDetailsService;

	@MockBean
	MemberDetailsRepository memberDetailsRepository;

//	@Test
//	public void 없는_회원_조회() {
//		// given
//		given(this.memberDetailsRepository.findByEmail(any(Email.class))).willReturn(Optional.empty());
//
//		// when
//		EmailNotFoundException thrown = assertThrows(EmailNotFoundException.class,
//				() -> this.memberDetailsService.findByEmail(new Email("test@taeu.kr")));
//		log.info("error : ", thrown);
//
//		// then
//		assertEquals(thrown.getErrorCode(), ErrorCode.MEMBER_NOT_FOUND);
//	}

	@Test
	public void 이미_가입한_이메일로_회원가입() {
		// given
		SignUpRequest dto = SignUpRequest.builder()
				.email(new Email("test@taeu.kr"))
				.name(new Name("테스트계정"))
				.password(new Password("12345"))
				.build();
		given(this.memberDetailsRepository.existsByEmail(any(Email.class))).willReturn(true);

		// when
		EmailAlreadyExistsException thrown = assertThrows(EmailAlreadyExistsException.class,
				() -> this.memberDetailsService.createMember(dto));
		log.info("error : ", thrown);

		// then
		assertEquals(thrown.getErrorCode(), ErrorCode.REQUEST_CONFILICT_EXCEPTION);
	}

	@Test
	public void 가입하지_않은_이메일로_회원가입() {
		// given
		Email email = new Email("test@taeu.kr");
		SignUpRequest dto = SignUpRequest.builder()
				.email(email)
				.name(new Name("테스트 계정"))
				.password(new Password("12345"))
				.build();
		given(this.memberDetailsRepository.existsByEmail(any(Email.class))).willReturn(false);
		given(this.memberDetailsRepository.save(any(Member.class))).willReturn(dto.toEntity());

		// when
		Member signedMember = this.memberDetailsService.createMember(dto);

		// then
		verify(this.memberDetailsRepository, atLeastOnce()).existsByEmail(email);
		verify(this.memberDetailsRepository, atLeastOnce()).save(any());
		assertEquals(dto.getEmail(), signedMember.getEmail());
	}
}
