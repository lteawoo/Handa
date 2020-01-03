package kr.taeu.handa.todoItem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kr.taeu.handa.todoItem.domain.TodoItem;
import kr.taeu.handa.todoItem.domain.TodoItemRepository;
import kr.taeu.handa.todoItem.dto.TodoItemDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class TestTodoItemService {
	
	@InjectMocks
	TodoItemService service;
	
	@Mock
	TodoItemRepository repo;

	List<TodoItem> list;
	
	/*
	 * repository 데이터를 대신할 테스트 데이터 생성
	 */
	@BeforeEach
	public void setUp() {
		list = new ArrayList<TodoItem>();
		list.add(buildWriteReq("바나나를 먹어야해", false).toEntity());
		list.add(buildWriteReq("딸기를 먹어야해", false).toEntity());
		list.add(buildWriteReq("호일을 사야해", true).toEntity());
	}
	
	private TodoItemDto.WriteReq buildWriteReq(String content, boolean done) {
		return TodoItemDto.WriteReq.builder()
				.content(content)
				.done(done)
				.build();
	}
	
	@Test
	public void 모든_아이템_조회() {
		//given
		given(this.repo.findAll()).willReturn(list);
		
		//when
		final List<TodoItem> listByService = this.service.list();
		
		//then
		verify(repo, atLeastOnce()).findAll();
		assertIterableEquals(list, listByService);
	}

	@Test
	public void 개별_아이템_조회() {
		//given
		given(this.repo.findById(1L)).willReturn(Optional.of(list.get(0)));
		given(this.repo.findById(2L)).willReturn(Optional.of(list.get(1)));
		given(this.repo.findById(3L)).willReturn(Optional.of(list.get(2)));
		
		//when
		final TodoItem todoItem1 = this.service.findById(1);
		final TodoItem todoItem2 = this.service.findById(2);
		final TodoItem todoItem3 = this.service.findById(3);
		
		//then
		verify(repo, times(3)).findById(anyLong());
		assertEquals(list.get(0), todoItem1);
		assertEquals(list.get(1), todoItem2);
		assertEquals(list.get(2), todoItem3);
	}
	
	@Test
	public void 아이템_수정() {
		//given
		final TodoItemDto.ModifyContentReq modifyContentReq = buildModfiyContentReq("내용 변경 테스트!");
		final TodoItemDto.ModifyDoneReq modifyDoneReq = buildModfiyDoneReq(true);
		given(repo.findById(1L)).willReturn(Optional.of(list.get(0)));
		given(repo.findById(2L)).willReturn(Optional.of(list.get(1)));
		
		//when
		final TodoItem todoItem1 = this.service.modifyContent(1L, modifyContentReq);
		final TodoItem todoItem2 = this.service.modifyDone(2L, modifyDoneReq);
		
		//then
		assertEquals(modifyContentReq.getContent(), todoItem1.getContent());
		assertEquals(modifyDoneReq.isDone(), todoItem2.isDone());
	}
	
	private TodoItemDto.ModifyContentReq buildModfiyContentReq(String content) {
		return TodoItemDto.ModifyContentReq.builder()
				.content(content)
				.build();
	}
	
	private TodoItemDto.ModifyDoneReq buildModfiyDoneReq(boolean done) {
		return TodoItemDto.ModifyDoneReq.builder()
				.done(done)
				.build();
	}
	
	@Test
	public void 아이템_삭제() {
		//given
		given(repo.findById(1L)).willReturn(Optional.of(list.get(0)));
		TodoItem todoItem = this.service.findById(1L);
		
		//when
		this.service.delete(1L);
		
		//then
		assertEquals(false, this.service.list().stream()
				.anyMatch(m -> m.getId() == todoItem.getId()));
	}
}
