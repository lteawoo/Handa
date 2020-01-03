package kr.taeu.handa.todoItem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kr.taeu.handa.todoItem.domain.TodoItem;
import kr.taeu.handa.todoItem.domain.TodoItemRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestTodoItemService {
	List<TodoItem> items;
	TodoItemService service;
	TodoItemRepository repo;
	
	@BeforeEach
	public void setUp() {
		//given
		this.items = new ArrayList<TodoItem>();
		
		TodoItem item;
		
		item = TodoItem.builder()
				.content("바나나를 먹어야해")
				.done(false)
				.build();
		this.items.add(item);
		
		item = TodoItem.builder()
				.content("호일을 사야해")
				.done(false)
				.build();
		this.items.add(item);
		
		item = TodoItem.builder()
				.content("커밋 해야해")
				.done(false)
				.build();
		this.items.add(item);
		
		this.repo = mock(TodoItemRepository.class);
		when(this.repo.findAll()).thenReturn(this.items);
		when(this.repo.findById(1L)).thenReturn(Optional.of(this.items.get(0)));
		when(this.repo.findById(2L)).thenReturn(Optional.of(this.items.get(1)));
		when(this.repo.findById(3L)).thenReturn(Optional.of(this.items.get(2)));
		
		this.service = new TodoItemService(this.repo);
	}
	
	@Test
	public void 모든_아이템_조회() {
		List<TodoItem> list = new ArrayList<TodoItem>();
		
		//when
		list = this.service.list();
		
		//then
		assertIterableEquals(list, this.items);
	}
	
	@Test
	public void 개별_아이템_조회() {
		TodoItem a, b;
		
		for(int index = 0; index < items.size(); index++) {
			a = items.get(index);
			b = this.service.findById(index + 1);
			compare(a, b);
		}
	}
	
	private void compare(TodoItem a, TodoItem b) {
		assertEquals(a.getId(), b.getId());
		assertEquals(a.getContent(), b.getContent());
		assertEquals(a.isDone(), b.isDone());
	}
}
