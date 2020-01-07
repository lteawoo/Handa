package kr.taeu.handa.todoItem.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class TestTodoItemRepository {
	@Autowired
	private TodoItemRepository todoItemRepository;
	
	@AfterEach
	public void cleanup() {
		/* 이후 테스트 코드에 영향이 없게
		 * 테스트 메서드 끝날때 마다 전체 삭제
		 */
		todoItemRepository.deleteAll();
	}
	
	@Test
	public void 아이템_저장_삭제_불러오기() {
		//given
		todoItemRepository.save(TodoItem.builder()
				.content("바나나를 먹자")
				.done(false)
				.build());
		todoItemRepository.save(TodoItem.builder()
				.content("호일을 사자")
				.done(false)
				.build());
		todoItemRepository.save(TodoItem.builder()
				.content("오늘도 커밋하자")
				.done(false)
				.build());
		todoItemRepository.deleteById(3L);
		
		//when
		List<TodoItem> itemList = (List<TodoItem>) todoItemRepository.findAll();
		
		//then
		TodoItem item = itemList.get(0);
		assertEquals("바나나를 먹자", item.getContent());
		item = itemList.get(1);
		assertEquals("호일을 사자", item.getContent());
		assertEquals(2, itemList.size());
	}
}
