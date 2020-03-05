package kr.taeu.handa.todoItem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import kr.taeu.handa.domain.member.domain.Member;
import kr.taeu.handa.domain.member.domain.model.Email;
import kr.taeu.handa.domain.member.domain.model.Name;
import kr.taeu.handa.domain.member.domain.model.Password;
import kr.taeu.handa.domain.member.domain.model.Role;
import kr.taeu.handa.domain.member.dto.SignUpRequest;
import kr.taeu.handa.domain.member.service.MemberDetailsService;
import kr.taeu.handa.domain.todoItem.dao.TodoItemRepository;
import kr.taeu.handa.domain.todoItem.domain.TodoItem;
import kr.taeu.handa.domain.todoItem.dto.TodoItemDto;
import kr.taeu.handa.domain.todoItem.dto.WriteItemRequest;
import kr.taeu.handa.domain.todoItem.exception.TodoItemNotFoundException;
import kr.taeu.handa.domain.todoItem.service.TodoItemService;
import kr.taeu.handa.global.error.ErrorCode;

@ExtendWith(SpringExtension.class)
public class TestTodoItemService {
	@SpyBean
	private TodoItemService todoItemService;

	@MockBean
	private TodoItemRepository todoItemRepository;
	
	private Member member;
	
	List<TodoItem> list;

	@BeforeEach
	public void setUp() {
		SignUpRequest dto = SignUpRequest.builder()
				.email(new Email("test@taeu.kr"))
				.name(new Name("테스트계정"))
				.password(new Password("12345"))
				.build();
		
		this.member = Member.builder()
				.email(new Email("test@taeu.kr"))
				.name(new Name("테스트계정"))
				.password(new Password("12345"))
				.role(Role.MEMBER)
				.build();
		
		list = new ArrayList<TodoItem>();
		list.add(buildWriteItemRequest("바나나를 먹어야해").toEntity());
		list.add(buildWriteItemRequest("딸기를 먹어야해").toEntity());
		list.add(buildWriteItemRequest("호일을 사야해").toEntity());
	}

	private WriteItemRequest buildWriteItemRequest(String content) {
		return WriteItemRequest.builder()
				.member(this.member)
				.content(content)
				.build();
	}
	
	@Test
	public void 아이템_등록() {
		// given
		WriteItemRequest req = buildWriteItemRequest("바나나를 먹어야해");
		given(this.todoItemRepository.save(any())).willReturn(req.toEntity());
		
		// when
		TodoItem saved = this.todoItemService.write(req);
		
		// then
		assertEquals(req.getMember(), this.member);
		assertEquals(req.getContent(), saved.getContent());
	}

	@Test
	public void 모든_아이템_조회() {
		// given
		given(this.todoItemRepository.findAll()).willReturn(list);

		// when
		final List<TodoItem> listByService = this.todoItemService.list();

		// then
		verify(this.todoItemRepository, atLeastOnce()).findAll();
		assertIterableEquals(this.list, listByService);
	}

	@Test
	public void 개별_아이템_조회() {
		// given
		given(this.todoItemRepository.findById(1L)).willReturn(Optional.of(this.list.get(0)));
		given(this.todoItemRepository.findById(2L)).willReturn(Optional.of(this.list.get(1)));
		given(this.todoItemRepository.findById(3L)).willReturn(Optional.of(this.list.get(2)));

		// when
		final TodoItem todoItem1 = this.todoItemService.findById(1L);
		final TodoItem todoItem2 = this.todoItemService.findById(2L);
		final TodoItem todoItem3 = this.todoItemService.findById(3L);

		// then
		verify(this.todoItemRepository, times(3)).findById(anyLong());
		assertEquals(this.list.get(0), todoItem1);
		assertEquals(this.list.get(1), todoItem2);
		assertEquals(this.list.get(2), todoItem3);
	}

	@Test
	public void 없는_아이템_조회() {
		// given
		given(todoItemRepository.findById(any())).willReturn(Optional.empty());

		// when
		// this.service.findById(1L);
		TodoItemNotFoundException thrown = assertThrows(TodoItemNotFoundException.class,
				() -> this.todoItemService.findById(1L));

		// then
		verify(this.todoItemRepository, atLeastOnce()).findById(any());
		assertEquals(thrown.getErrorCode(), ErrorCode.OBJECT_NOT_FOUND);
	}

	private TodoItemDto.ModifyContentReq buildModifyContentReq(String content) {
		return TodoItemDto.ModifyContentReq.builder().content(content).build();
	}

	private TodoItemDto.ModifyDoneReq buildModfiyDoneReq(boolean done) {
		return TodoItemDto.ModifyDoneReq.builder().done(done).build();
	}

	@Test
	public void 아이템_삭제() {
		// given
		given(todoItemRepository.findById(1L)).willReturn(Optional.of(list.get(0)));
		TodoItem todoItem = this.todoItemService.findById(1L);

		// when
		this.todoItemService.delete(1L);

		// then
		assertEquals(false, this.todoItemService.list().stream().anyMatch(m -> m.getId() == todoItem.getId()));
	}
}
