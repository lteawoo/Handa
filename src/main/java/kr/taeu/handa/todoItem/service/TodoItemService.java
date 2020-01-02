package kr.taeu.handa.todoItem.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.taeu.handa.todoItem.domain.TodoItem;
import kr.taeu.handa.todoItem.domain.TodoItemRepository;
import kr.taeu.handa.todoItem.dto.TodoItemDto;
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
	
	public TodoItem write(TodoItemDto.WriteReq dto) {
		return todoItemRepository.save(dto.toEntity());
	}
}
