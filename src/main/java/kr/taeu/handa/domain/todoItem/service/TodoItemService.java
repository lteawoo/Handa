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
import kr.taeu.handa.domain.todoItem.dto.ModifyPositionRequest;
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
		List<TodoItem> todoItemList = (List<TodoItem>) todoItemRepository.findAllByMemberOrderByPosition(member);
//		todoItemList.forEach(item -> {
//			log.info(item.toString());
//		});

		return todoItemList;
	}

	@Transactional(readOnly = true)
	public TodoItem findByIdAndEmail(Email email, Long id) {
		final Member member = memberDetailsService.findByEmail(email);
		final Optional<TodoItem> todoItem = todoItemRepository.findByIdAndMember(id, member);
		todoItem.orElseThrow(() -> new TodoItemNotFoundException(email, id));
		
		return todoItem.get();
	}
	
	@Transactional(readOnly = true)
	private Double findNextPosition(Member member) {
		Double nextPosition = 0.0;
		final Double position = todoItemRepository.findMaxPosition(member);
		
		if(position == 0.0) {
			nextPosition = 1000.0;
		} else {
			nextPosition = (Math.floor(position / 1000.0) + 1) * 1000.0;
		}
		
		return nextPosition;
	}

	@Transactional
	public TodoItem write(String username, WriteItemRequest dto) {
		final Member member = memberDetailsService.findByEmail(new Email(username));
		final Double position = findNextPosition(member);
		
		dto.setPosition(position);
		
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
	
	@Transactional
	public TodoItem changePosition(String username, Long id, ModifyPositionRequest dto) {
		final TodoItem todoItem = this.findByIdAndEmail(new Email(username), id);
		//TODO 동일한 order로 요청이 들어온경우..(모바일-웹 동시에?..)
//		final Member member = memberDetailsService.findByEmail(new Email(username));
//		
//		final Optional<TodoItem> temp = todoItemRepository.findByPositionAndMember(dto.getPosition(), member);
		
		todoItem.modifyPosition(dto);
		return todoItem;
	}
}
