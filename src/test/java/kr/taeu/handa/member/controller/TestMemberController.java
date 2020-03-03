package kr.taeu.handa.member.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.taeu.handa.domain.member.controller.MemberController;
import kr.taeu.handa.domain.member.domain.model.Email;
import kr.taeu.handa.domain.member.domain.model.Name;
import kr.taeu.handa.domain.member.domain.model.Password;
import kr.taeu.handa.domain.member.domain.model.Role;
import kr.taeu.handa.domain.member.dto.SignUpRequest;
import kr.taeu.handa.domain.member.service.MemberDetailsService;

@ExtendWith(SpringExtension.class)
//@WebMvcTest(controllers = {MemberController.class}, includeFilters = @ComponentScan.Filter(classes = {EnableWebSecurity.class}))
@WebMvcTest(controllers = {MemberController.class})
//@ContextConfiguration
public class TestMemberController {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private MemberDetailsService memberDetailsService;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Test
	@WithAnonymousUser
	public void 회원가입_요청() throws Exception{
		//given
		SignUpRequest signUpRequest = SignUpRequest.builder()
				.email(new Email("taeu@test.kr"))
				.name(new Name("테스트계정"))
				.password(new Password("12345"))
				.role(Role.MEMBER)
				.build();
		given(memberDetailsService.createMember(any(SignUpRequest.class))).willReturn(signUpRequest.toEntity());
		
		//when
		mockMvc.perform(post("/member/signup")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(signUpRequest)))
				.andDo(print())
	
		//then
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("email.value").value("taeu@test.kr"));
	}
}
