package kr.taeu.handa.todoItem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import kr.taeu.handa.domain.member.dao.MemberDetailsRepository;
import kr.taeu.handa.domain.member.domain.Member;
import kr.taeu.handa.domain.member.domain.model.Email;
import kr.taeu.handa.domain.member.domain.model.Name;
import kr.taeu.handa.domain.member.domain.model.Password;
import kr.taeu.handa.domain.member.domain.model.Role;
import kr.taeu.handa.domain.todoItem.dao.TodoItemRepository;
import kr.taeu.handa.domain.todoItem.domain.TodoItem;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(SpringExtension.class)
//@DataJpaTest
@SpringBootTest
//@EnableJpaAuditing
public class TestTodoItemRepository {
	@Autowired
	private TodoItemRepository todoItemRepository;
	@Autowired
	private MemberDetailsRepository memberDetailsRepository;
	
	private Member member;
	
	@BeforeEach
	public void setup() {
		this.member = Member.builder()
				.email(new Email("test@taeu.kr"))
				.name(new Name("테스트"))
				.password(new Password("12345"))
				.role(Role.MEMBER)
				.build();
		this.memberDetailsRepository.save(this.member);
	}

	@AfterEach
	public void cleanup() {
		/*
		 * 이후 테스트 코드에 영향이 없게 테스트 메서드 끝날때 마다 전체 삭제
		 */
		todoItemRepository.deleteAll();
	}

	@Test
	public void 아이템_저장_삭제_불러오기() {
		// given
		todoItemRepository.save(TodoItem.builder().content("바나나를 먹자").done(false).build());
		todoItemRepository.save(TodoItem.builder().content("호일을 사자").done(false).build());
		todoItemRepository.save(TodoItem.builder().content("오늘도 커밋하자").done(false).build());
		todoItemRepository.deleteById(3L);

		// when
		List<TodoItem> itemList = (List<TodoItem>) todoItemRepository.findAll();

		// then
		TodoItem item = itemList.get(0);
		assertEquals("바나나를 먹자", item.getContent());
		item = itemList.get(1);
		assertEquals("호일을 사자", item.getContent());
		assertEquals(2, itemList.size());
	}
	
	@Test
	public void 아이템_저장() {
		// given
		TodoItem todoItem = TodoItem.builder()
				.member(this.member)
				.content("바나나를 먹자")
				.done(false)
				.build();
		
		// when
		this.todoItemRepository.save(todoItem);
		
		// then
		assertTrue(this.todoItemRepository.existsById(todoItem.getId()));
		assertEquals(this.member, todoItem.getMember());
		assertEquals(todoItem.getContent(), todoItem.getContent());
		assertEquals(todoItem.isDone(), todoItem.isDone());
	}
	
	@Test
	public void 아이템_전체_조회() {
		// given
		TodoItem todoItem1 = TodoItem.builder()
				.member(this.member)
				.content("바나나를 먹자")
				.position(3000.0)
				.done(false)
				.build();
		this.todoItemRepository.save(todoItem1);
		TodoItem todoItem2 = TodoItem.builder()
				.member(this.member)
				.content("딸기를 먹자")
				.position(2000.0)
				.done(false)
				.build();
		this.todoItemRepository.save(todoItem2);
		
		List<TodoItem> list = this.todoItemRepository.findByMemberOrderByPosition(this.member);
		
		for(TodoItem item : list) {
			log.info(item.getContent());
		}
		
		assertTrue(list.size() == 2);
	}
	
	@Test
	public void 아이템_조회() {
		// given
		TodoItem todoItem = TodoItem.builder()
				.member(this.member)
				.content("바나나를 먹자")
				.done(false)
				.build();
		this.todoItemRepository.save(todoItem);
		
		// when
		Optional<TodoItem> finded = this.todoItemRepository.findByIdAndMember(todoItem.getId(), this.member);
		TodoItem findedItem = finded.get();
		
		// then
		assertTrue(this.todoItemRepository.existsById(findedItem.getId()));
		assertEquals(todoItem.getContent(), findedItem.getContent());
		assertEquals(todoItem.isDone(), findedItem.isDone());
	}
	
	@Test
	public void 아이템_삭제() {
		// given
		TodoItem todoItem = TodoItem.builder()
				.member(this.member)
				.content("바나나를 먹자")
				.done(false)
				.build();
		this.todoItemRepository.save(todoItem);
		
		// when
		this.todoItemRepository.delete(todoItem);
		
		// then
		assertTrue(!this.todoItemRepository.existsById(todoItem.getId()));
	}
	
	
	@Test
	public void Position최대값조회() {
		// given
		TodoItem todoItem = TodoItem.builder()
				.member(this.member)
				.content("바나나를 먹자")
				.position(1000.0)
				.done(false)
				.build();
		this.todoItemRepository.save(todoItem);
		todoItem = TodoItem.builder()
				.member(this.member)
				.content("딸기를 먹자")
				.position(2000.0)
				.done(false)
				.build();
		this.todoItemRepository.save(todoItem);
		
		// when
		Double max = this.todoItemRepository.findMaxPosition(member);
		
		// then
		assertEquals(2000.0, max);
	}
}