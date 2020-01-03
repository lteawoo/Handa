package kr.taeu.handa.todoItem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.taeu.handa.todoItem.domain.TodoItem;
import kr.taeu.handa.todoItem.domain.TodoItemRepository;
import kr.taeu.handa.todoItem.dto.TodoItemDto;
import kr.taeu.handa.todoItem.dto.TodoItemDto.ModifyContentReq;
import kr.taeu.handa.todoItem.dto.TodoItemDto.ModifyDoneReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TodoItemService {
	private final TodoItemRepository todoItemRepository;
	
	@Transactional(readOnly = true)
	public List<TodoItem> list() {
		todoItemRepository.findAll().forEach(item->{
			log.info(item.toString());
		});
		
		return (List<TodoItem>)todoItemRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public TodoItem findById(long id) {
		final Optional<TodoItem> todoItem = todoItemRepository.findById(id);
		//todoItem.orElse();
		return todoItem.get();
	}
	
	public TodoItem write(TodoItemDto.WriteReq dto) {
		return todoItemRepository.save(dto.toEntity());
	}
	
	public TodoItem modifyContent(ModifyContentReq dto) {
		final TodoItem todoItem = findById(dto.getId());
		todoItem.modifyContent(dto);
		return todoItem;
	}
	
	public TodoItem modifyDone(ModifyDoneReq dto) {
		final TodoItem todoItem = findById(dto.getId());
		todoItem.modifyDone(dto);
		return todoItem;
	}
}
