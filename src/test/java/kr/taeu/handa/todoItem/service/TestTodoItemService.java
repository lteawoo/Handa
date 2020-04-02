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
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
		given(this.todoItemRepository.findByIdAndMember(anyLong(), any())).willReturn(Optional.of(req.toEntity(this.member)));
		ModifyContentRequest dto = ModifyContentRequest.builder()
				.content("바나나를 먹지말자")
				.build();
		
		// when
		TodoItem item = this.todoItemService.modifyContent(this.member.getEmail().getValue(), 1L, dto);
		
		// then
		assertEquals("바나나를 먹지말자", item.getContent());
		verify(this.todoItemRepository, only()).findByIdAndMember(1L, this.member);
	}
	
	@Test
	public void 아이템_완료여부_수정() {
		// given
		WriteItemRequest req = buildWriteItemRequest("바나나를 먹어야해");
		given(this.todoItemRepository.findByIdAndMember(anyLong(), any())).willReturn(Optional.of(req.toEntity(this.member)));
		ModifyDoneRequest modifyDoneRequest = ModifyDoneRequest.builder()
				.done(true)
				.build();
		
		// when
		TodoItem item = this.todoItemService.modifyDone(this.member.getEmail().getValue(), 1L, modifyDoneRequest);
		
		// then
		assertTrue(item.isDone());
		verify(this.todoItemRepository, only()).findByIdAndMember(1L, this.member);
	}

	@Test
	public void 모든_아이템_조회() {
		// given
		List<TodoItem> list = new ArrayList<TodoItem>();
		list.add(buildWriteItemRequest("바나나를 먹어야해").toEntity(this.member));
		list.add(buildWriteItemRequest("딸기를 먹어야해").toEntity(this.member));
		list.add(buildWriteItemRequest("호일을 사야해").toEntity(this.member));
		given(this.todoItemRepository.findAll()).willReturn(list);

		// when
		final List<TodoItem> listByService = this.todoItemService.list(this.member.getEmail().getValue());

		// then
		verify(this.todoItemRepository, atLeastOnce()).findAll();
		assertIterableEquals(list, listByService);
	}


	@Test
	public void 없는_아이템_조회() {
		// given
		given(todoItemRepository.findByIdAndMember(anyLong(), any())).willReturn(Optional.empty());

		// when
		TodoItemNotFoundException thrown = assertThrows(TodoItemNotFoundException.class,
				() -> this.todoItemService.findByIdAndEmail(this.member.getEmail(), 1L));

		// then
		verify(this.todoItemRepository, atLeastOnce()).findByIdAndMember(1L, this.member);
		assertEquals(thrown.getErrorCode(), ErrorCode.OBJECT_NOT_FOUND);
	}
	
	@Test
	public void 순서_변경() {
		//5번째 아이템을 2번째로 옮기는 경우
		//부동소숫점을 이용하즈아!!
		// given
		Double to = 2.0;
		Double from = 5.0;
		List<TodoItem> list = new ArrayList<TodoItem>();
		list.add(buildWriteItemRequest("1:바나나를 먹어야해").toEntity(this.member));
		list.add(buildWriteItemRequest("2:딸기를 먹어야해").toEntity(this.member));
		list.add(buildWriteItemRequest("3:호일을 사야해").toEntity(this.member));
		list.add(buildWriteItemRequest("4:감자를 사야해").toEntity(this.member));
		list.add(buildWriteItemRequest("5:고구마를 사야해").toEntity(this.member));
		
		// when
		todoItemService.changeOrder(this.member.getEmail().getValue(), from, to);
	}
}
