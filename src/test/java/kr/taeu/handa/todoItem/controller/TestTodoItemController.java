package kr.taeu.handa.todoItem.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import kr.taeu.handa.domain.todoItem.controller.TodoItemController;
import kr.taeu.handa.domain.todoItem.dao.TodoItemRepository;
import kr.taeu.handa.domain.todoItem.service.TodoItemService;
import kr.taeu.handa.global.error.ControllerExceptionHandler;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = { TodoItemController.class } )
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
		this.mockMvc = MockMvcBuilders.standaloneSetup(todoItemController)
				.setControllerAdvice(new ControllerExceptionHandler()).build();
	}
}
