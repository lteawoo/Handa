package kr.taeu.handa.todoItem.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import kr.taeu.handa.global.error.ErrorExceptionController;
import kr.taeu.handa.todoItem.dao.TodoItemRepository;
import kr.taeu.handa.todoItem.service.TodoItemService;

@ExtendWith(MockitoExtension.class)
public class TestTodoItemController {
	
	@InjectMocks
	TodoItemController todoItemController;
	
	@Mock
	TodoItemService todoItemService;
	
	@Mock
	TodoItemRepository todoItemRepository;
	
	private MockMvc mockMvc;
	
	@BeforeEach
	private void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(todoItemController)
				.setControllerAdvice(new ErrorExceptionController())
				.build();
	}
}
