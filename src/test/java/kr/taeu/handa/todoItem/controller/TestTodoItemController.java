package kr.taeu.handa.todoItem.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.taeu.handa.Application;
import kr.taeu.handa.domain.member.service.MemberDetailsService;
import kr.taeu.handa.domain.todoItem.controller.TodoItemController;
import kr.taeu.handa.domain.todoItem.dto.WriteItemRequest;
import kr.taeu.handa.domain.todoItem.service.TodoItemService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class TestTodoItemController {
	@Autowired
	private MockMvc mockMvc;
	
	@InjectMocks
	private TodoItemService todoItemService;
	
	@InjectMocks
	private MemberDetailsService memberDetailsService;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	private WriteItemRequest buildWriteItemRequest(String content) {
		return WriteItemRequest.builder()
				.content(content)
				.build();
	}
	
	@Test
	public void 특정시간이후_목록_조회() throws Exception {
		mockMvc.perform(get("/api/item/changedList/20200408101010")
				.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1ODYzMzk3MzUsInVzZXJfbmFtZSI6InRlc3RAdGFldS5rciIsImF1dGhvcml0aWVzIjpbIk1FTUJFUiJdLCJqdGkiOiIyZWRmMmQ1Zi1hYmM1LTQwNzktODEyNC0wNWY1MjI2ZTIxNjMiLCJjbGllbnRfaWQiOiJ0YWV1X2NsaWVudCIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdfQ.EB1HplQvYEWCzY7PV-W87-oamVJPFgADmvmr2ll25JE"))
				.andDo(print());
	}
}
