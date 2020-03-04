package kr.taeu.handa.member.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.taeu.handa.domain.member.controller.MemberController;
import kr.taeu.handa.domain.member.domain.model.Email;
import kr.taeu.handa.domain.member.domain.model.Name;
import kr.taeu.handa.domain.member.domain.model.Password;
import kr.taeu.handa.domain.member.dto.SignUpRequest;
import kr.taeu.handa.domain.member.service.MemberDetailsService;
import kr.taeu.handa.global.error.ErrorCode;
import kr.taeu.handa.global.error.ErrorResponse;
import kr.taeu.handa.global.error.FieldError;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {MemberController.class})
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
	
	@Test
	@WithAnonymousUser
	public void 데이터누락_회원가입_요청() throws Exception {
		//given
		SignUpRequest signUpRequest = SignUpRequest.builder()
				.email(new Email(""))
				.name(null)
				//.password(new Password("12345")
				.build();
		given(memberDetailsService.createMember(any(SignUpRequest.class))).willReturn(signUpRequest.toEntity());
		
		//when
		MvcResult result = mockMvc.perform(post("/member/signup")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(signUpRequest)))
				.andDo(print())
				.andReturn();
	
		//then
			ErrorResponse dto = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorResponse.class);
			assertEquals(dto.getCode(), ErrorCode.INVALID_INPUT_VALUE.getCode());
			assertEquals(dto.getMessage(), ErrorCode.INVALID_INPUT_VALUE.getMessage());
			
			for(FieldError fieldError : dto.getErrors()) {
				
			}
			
//				.andExpect(status().isBadRequest())
//				.andExpect(MockMvcResultMatchers.jsonPath("code").value(ErrorCode.INVALID_INPUT_VALUE.getCode()));
	}
	
	@Test
	@WithMockUser(username = "test@taeu.kr", password = "12345", roles="MEMBER")
	public void 인증_후_Authentication_확인() throws Exception{
		mockMvc.perform(get("/member/welcome"))
				.andDo(print())
				.andExpect(MockMvcResultMatchers.content().string("test@taeu.kr hello!"));
	}
}
