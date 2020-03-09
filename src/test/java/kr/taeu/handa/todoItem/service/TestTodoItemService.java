package kr.taeu.handa.todoItem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import kr.taeu.handa.domain.member.dao.MemberDetailsRepository;
import kr.taeu.handa.domain.member.domain.Member;
import kr.taeu.handa.domain.member.domain.model.Email;
import kr.taeu.handa.domain.member.domain.model.Name;
import kr.taeu.handa.domain.member.domain.model.Password;
import kr.taeu.handa.domain.member.domain.model.Role;
import kr.taeu.handa.domain.member.service.MemberDetailsService;
import kr.taeu.handa.domain.todoItem.dao.TodoItemRepository;
import kr.taeu.handa.domain.todoItem.domain.TodoItem;
import kr.taeu.handa.domain.todoItem.dto.ModifyContentRequest;
import kr.taeu.handa.domain.todoItem.dto.ModifyDoneRequest;
import kr.taeu.handa.domain.todoItem.dto.WriteItemRequest;
import kr.taeu.handa.domain.todoItem.exception.TodoItemNotFoundException;
import kr.taeu.handa.domain.todoItem.service.TodoItemService;
import kr.taeu.handa.global.error.ErrorCode;

@ExtendWith(SpringExtension.class)
public class TestTodoItemService {
	@SpyBean
	private TodoItemService todoItemService;
	
	@SpyBean
	private MemberDetailsService memberDetailsService;
	
	@MockBean
	private PasswordEncoder passwordEncoder;
	
	@MockBean
	private TodoItemRepository todoItemRepository;
	
	@MockBean
	private MemberDetailsRepository memberDetailsRepository;
	
	private Member member;

	@BeforeEach
	public void setUp() {		
		this.member = Member.builder()
				.email(new Email("test@taeu.kr"))
				.name(new Name("테스트계정"))
				.password(new Password("12345"))
				.role(Role.MEMBER)
				.build();
		
		given(this.memberDetailsRepository.findByEmail(any())).willReturn(Optional.of(this.member));
	}

	private WriteItemRequest buildWriteItemRequest(String content) {
		return WriteItemRequest.builder()
				.content(content)
				.build();
	}
	
	@Test
	public void 아이템_등록() {
		// given
		WriteItemRequest req = buildWriteItemRequest("바나나를 먹어야해");
		given(this.todoItemRepository.save(any())).willReturn(req.toEntity(this.member));
		
		// when
		TodoItem saved = this.todoItemService.write(this.member.getEmail().getValue(), req);
		
		// then
		assertEquals(this.member, saved.getMember());
		assertEquals(req.getContent(), saved.getContent());
		verify(this.todoItemRepository, only()).save(any());
	}
	
	@Test
	public void 아이템_내용_수정() {
		// given
		WriteItemRequest req = buildWriteItemRequest("바나나를 먹어야해");
		given(this.todoItemRepository.save(any())).willReturn(req.toEntity(this.member));
		
		ModifyContentRequest dto = ModifyContentRequest.builder()
				.content("바나나를 먹지말자")
				.build();
		
		// when
		TodoItem item = this.todoItemService.modifyContent(this.member.getEmail().getValue(), 1L, dto);
		
		// then
		assertEquals("바나나를 먹지말자", item.getContent());
		verify(this.todoItemRepository, only()).findById(1L);
	}
	
	@Test
	public void 아이템_완료여부_수정() {
		// given
		List<TodoItem> list = new ArrayList<TodoItem>();
		list.add(buildWriteItemRequest("바나나를 먹어야해").toEntity());
		list.add(buildWriteItemRequest("딸기를 먹어야해").toEntity());
		list.add(buildWriteItemRequest("호일을 사야해").toEntity());
		given(this.todoItemRepository.findById(1L)).willReturn(Optional.of(list.get(0)));
		given(this.todoItemRepository.findById(2L)).willReturn(Optional.of(list.get(1)));
		given(this.todoItemRepository.findById(3L)).willReturn(Optional.of(list.get(2)));
		
		ModifyDoneRequest modifyDoneRequest = ModifyDoneRequest.builder()
				.done(true)
				.build();
		
		// when
		TodoItem item = this.todoItemService.modifyDone(1L, modifyDoneRequest);
		
		// then
		assertTrue(item.isDone());
		verify(this.todoItemRepository, only()).findById(1L);
	}

	@Test
	public void 모든_아이템_조회() {
		// given
		List<TodoItem> list = new ArrayList<TodoItem>();
		list.add(buildWriteItemRequest("바나나를 먹어야해").toEntity());
		list.add(buildWriteItemRequest("딸기를 먹어야해").toEntity());
		list.add(buildWriteItemRequest("호일을 사야해").toEntity());
		given(this.todoItemRepository.findAll()).willReturn(list);

		// when
		final List<TodoItem> listByService = this.todoItemService.list();

		// then
		verify(this.todoItemRepository, atLeastOnce()).findAll();
		assertIterableEquals(list, listByService);
	}

	@Test
	public void 개별_아이템_조회() {
		// given
		List<TodoItem> list = new ArrayList<TodoItem>();
		list.add(buildWriteItemRequest("바나나를 먹어야해").toEntity());
		list.add(buildWriteItemRequest("딸기를 먹어야해").toEntity());
		list.add(buildWriteItemRequest("호일을 사야해").toEntity());
		given(this.todoItemRepository.findById(1L)).willReturn(Optional.of(list.get(0)));
		given(this.todoItemRepository.findById(2L)).willReturn(Optional.of(list.get(1)));
		given(this.todoItemRepository.findById(3L)).willReturn(Optional.of(list.get(2)));

		// when
		final TodoItem todoItem1 = this.todoItemService.findById(1L);
		final TodoItem todoItem2 = this.todoItemService.findById(2L);
		final TodoItem todoItem3 = this.todoItemService.findById(3L);

		// then
		verify(this.todoItemRepository, times(3)).findById(anyLong());
		assertEquals(list.get(0), todoItem1);
		assertEquals(list.get(1), todoItem2);
		assertEquals(list.get(2), todoItem3);
	}

	@Test
	public void 없는_아이템_조회() {
		// given
		given(todoItemRepository.findById(any())).willReturn(Optional.empty());

		// when
		TodoItemNotFoundException thrown = assertThrows(TodoItemNotFoundException.class,
				() -> this.todoItemService.findById(1L));

		// then
		verify(this.todoItemRepository, atLeastOnce()).findById(any());
		assertEquals(thrown.getErrorCode(), ErrorCode.OBJECT_NOT_FOUND);
	}

	@Test
	public void 아이템_삭제() {
		// given
		List<TodoItem> list = new ArrayList<TodoItem>();
		list.add(buildWriteItemRequest("바나나를 먹어야해").toEntity());
		list.add(buildWriteItemRequest("딸기를 먹어야해").toEntity());
		list.add(buildWriteItemRequest("호일을 사야해").toEntity());
		given(todoItemRepository.findById(1L)).willReturn(Optional.of(list.get(0)));
		TodoItem todoItem = this.todoItemService.findById(1L);

		// when
		this.todoItemService.delete(1L);

		// then
		assertEquals(false, this.todoItemService.list().stream().anyMatch(m -> m.getId() == todoItem.getId()));
	}
}
