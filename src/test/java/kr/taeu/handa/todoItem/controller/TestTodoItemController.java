package kr.taeu.handa.todoItem.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.taeu.handa.domain.member.service.MemberDetailsService;
import kr.taeu.handa.domain.todoItem.controller.TodoItemController;
import kr.taeu.handa.domain.todoItem.dto.WriteItemRequest;
import kr.taeu.handa.domain.todoItem.service.TodoItemService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = { TodoItemController.class } )
public class TestTodoItemController {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private TodoItemService todoItemService;
	
	@MockBean
	private MemberDetailsService memberDetailsService;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	private WriteItemRequest buildWriteItemRequest(String content) {
		return WriteItemRequest.builder()
				.content(content)
				.build();
	}
	
	@Test
	@WithMockUser(username = "test@taeu.kr", password = "12345", roles="MEMBER")
	public void 인증정보_테스트() throws Exception {
		mockMvc.perform(get("/api/item/test"))
				.andDo(print());
	}
}
