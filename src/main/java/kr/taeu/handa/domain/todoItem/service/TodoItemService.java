package kr.taeu.handa.domain.todoItem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.taeu.handa.domain.member.domain.Member;
import kr.taeu.handa.domain.member.domain.model.Email;
import kr.taeu.handa.domain.member.service.MemberDetailsService;
import kr.taeu.handa.domain.todoItem.dao.TodoItemRepository;
import kr.taeu.handa.domain.todoItem.domain.TodoItem;
import kr.taeu.handa.domain.todoItem.dto.ModifyContentRequest;
import kr.taeu.handa.domain.todoItem.dto.ModifyDoneRequest;
import kr.taeu.handa.domain.todoItem.dto.WriteItemRequest;
import kr.taeu.handa.domain.todoItem.exception.TodoItemNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodoItemService {
	private final TodoItemRepository todoItemRepository;
	private final MemberDetailsService memberDetailsService;

	@Transactional(readOnly = true)
	public List<TodoItem> list(String username) {
		Member member = memberDetailsService.findByEmail(new Email(username));
		List<TodoItem> todoItemList = (List<TodoItem>) todoItemRepository.findAllByMember(member);
//		todoItemList.forEach(item -> {
//			log.info(item.toString());
//		});

		return todoItemList;
	}

	@Transactional(readOnly = true)
	public TodoItem findByIdAndEmail(Email email, Long id) {
		Member member = memberDetailsService.findByEmail(email);
		final Optional<TodoItem> todoItem = todoItemRepository.findByIdAndMember(id, member);
		todoItem.orElseThrow(() -> new TodoItemNotFoundException(email, id));
		
		return todoItem.get();
	}

	@Transactional
	public TodoItem write(String username, WriteItemRequest dto) {
		Member member = memberDetailsService.findByEmail(new Email(username));
		
		return todoItemRepository.save(dto.toEntity(member));
	}

	@Transactional
	public TodoItem modifyContent(String username, Long id, ModifyContentRequest dto) {
		final TodoItem todoItem = this.findByIdAndEmail(new Email(username), id);
		
		todoItem.modifyContent(dto);
		return todoItem;
	}

	@Transactional
	public TodoItem modifyDone(String username, Long id, ModifyDoneRequest dto) {
		final TodoItem todoItem = this.findByIdAndEmail(new Email(username), id);
		
		todoItem.modifyDone(dto);
		return todoItem;
	}

	@Transactional
	public void delete(String username, Long id) {
		final TodoItem todoItem = this.findByIdAndEmail(new Email(username), id);
		
		todoItemRepository.delete(todoItem);
	}
}
