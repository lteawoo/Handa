package kr.taeu.handa.todoItem.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestTodoItemController {
	private MockMvc mockMvc;
	
	@Autowired
	TodoItemController todoItemController;
	
	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(todoItemController)
//				.addFilters(new CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true))
				.alwaysDo(print())
				.build();
	}
	

	@Test
	public void Status_요청() throws Exception{
		mockMvc.perform(get("/api/status"))
				.andExpect(status().isOk())
				.andExpect(content().string("상태 : Alive"));
	}
}
