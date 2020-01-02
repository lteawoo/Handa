package kr.taeu.handa.todoItem.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import kr.taeu.handa.todoItem.domain.TodoItem;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class TestTodoItemRepository {
	@Autowired
	private TodoItemRepository todoItemRepository;
	
	@BeforeEach
	public void setUp() {
		todoItemRepository.deleteAll();
		
		todoItemRepository.save(new TodoItem("test", true))
	}
	
	@Test
	public void TodoItem_List_테스트() {
		
	}
}
