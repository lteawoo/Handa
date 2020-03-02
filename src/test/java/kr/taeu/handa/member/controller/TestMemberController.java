package kr.taeu.handa.member.controller;

import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import kr.taeu.handa.domain.member.controller.MemberController;
import kr.taeu.handa.domain.member.domain.model.Email;
import kr.taeu.handa.domain.member.domain.model.Name;
import kr.taeu.handa.domain.member.domain.model.Password;
import kr.taeu.handa.domain.member.domain.model.Role;
import kr.taeu.handa.domain.member.dto.SignUpRequest;
import kr.taeu.handa.domain.member.service.MemberDetailsService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MemberController.class)
public class TestMemberController {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private MemberDetailsService memberDetailsService;
	
	@Test
	public void 회원가입_요청() {
		//given
		SignUpRequest signUpRequest = SignUpRequest.builder()
				.email(new Email("taeu@test.kr"))
				.name(new Name("테스트계정"))
				.password(new Password("12345"))
				.role(Role.MEMBER)
				.build();
		given(memberDetailsService.createMember(signUpRequest)).willReturn(signUpRequest.toEntity());
		
		//when
		mockMvc
	}
}
