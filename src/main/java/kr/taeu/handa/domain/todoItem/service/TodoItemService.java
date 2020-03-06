package kr.taeu.handa.domain.todoItem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
@RequiredArgsConstructor
public class TodoItemService {
	private final TodoItemRepository todoItemRepository;

	@Transactional(readOnly = true)
	public List<TodoItem> list() {
		List<TodoItem> todoItemList = (List<TodoItem>) todoItemRepository.findAll();
		todoItemList.forEach(item -> {
			log.info(item.toString());
		});

		return todoItemList;
	}

	@Transactional(readOnly = true)
	public TodoItem findById(Long id) {
		final Optional<TodoItem> todoItem = todoItemRepository.findById(id);
		todoItem.orElseThrow(() -> new TodoItemNotFoundException(id));
		return todoItem.get();
	}

	public TodoItem write(WriteItemRequest dto) {
		return todoItemRepository.save(dto.toEntity());
	}

	public TodoItem modifyContent(Long id, ModifyContentRequest dto) {
		final TodoItem todoItem = findById(id);
		todoItem.modifyContent(dto);
		return todoItem;
	}

	public TodoItem modifyDone(Long id, ModifyDoneRequest dto) {
		final TodoItem todoItem = findById(id);
		todoItem.modifyDone(dto);
		return todoItem;
	}

	public void delete(Long id) {
		final TodoItem todoItem = findById(id);
		todoItemRepository.delete(todoItem);
	}
}
