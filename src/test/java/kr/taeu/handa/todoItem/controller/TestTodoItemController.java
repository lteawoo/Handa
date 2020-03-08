package kr.taeu.handa.todoItem.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.taeu.handa.domain.member.domain.model.Email;
import kr.taeu.handa.domain.member.domain.model.Name;
import kr.taeu.handa.domain.member.domain.model.Password;
import kr.taeu.handa.domain.member.dto.SignUpRequest;
import kr.taeu.handa.domain.todoItem.controller.TodoItemController;
import kr.taeu.handa.domain.todoItem.dao.TodoItemRepository;
import kr.taeu.handa.domain.todoItem.dto.WriteItemRequest;
import kr.taeu.handa.domain.todoItem.service.TodoItemService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = { TodoItemController.class } )
public class TestTodoItemController {
	@MockBean
	TodoItemService todoItemService;

	@Mock
	TodoItemRepository todoItemRepository;

	@Autowired
	private MockMvc mockMvc;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	private WriteItemRequest buildWriteItemRequest(String content) {
		return WriteItemRequest.builder()
				.content(content)
				.build();
	}
	
	@Test
	@WithMockUser(username = "test@taeu.kr", password = "12345")
	public void 인증정보_테스트() throws Exception {
		mockMvc.perform(get("/api/item/test"))
				.andDo(print());
	}
}
