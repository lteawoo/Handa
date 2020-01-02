package kr.taeu.handa.todoItem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.taeu.handa.todoItem.domain.TodoItem;
import kr.taeu.handa.todoItem.repository.TodoItemRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TodoItemService {
	@Autowired
	private TodoItemRepository todoItemRepository;
	
	public List<TodoItem> list() {
		todoItemRepository.findAll().forEach(item->{
			log.info(item.toString());
		});
		
		return (List<TodoItem>)todoItemRepository.findAll();
	}
}
