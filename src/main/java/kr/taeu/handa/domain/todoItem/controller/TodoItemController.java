package kr.taeu.handa.domain.todoItem.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.taeu.handa.domain.todoItem.domain.TodoItem;
import kr.taeu.handa.domain.todoItem.dto.TodoItemDto;
import kr.taeu.handa.domain.todoItem.service.TodoItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TodoItemController {
	private final TodoItemService todoItemService;

	@GetMapping(value = "/api/item/list")
	public List<TodoItem> list() {
		return todoItemService.list();
	}

	@PostMapping(value = "/api/item/write")
	public TodoItemDto.Res write(@RequestBody @Valid final TodoItemDto.WriteReq dto) {
		return new TodoItemDto.Res(todoItemService.write(dto));
	}

	@PostMapping(value = "/api/item/modifyContent/{id}")
	public TodoItemDto.Res modifyContent(@PathVariable final Long id,
			@RequestBody @Valid final TodoItemDto.ModifyContentReq dto) {
		return new TodoItemDto.Res(todoItemService.modifyContent(id, dto));
	}

	@PostMapping(value = "/api/item/modifyDone/{id}")
	public TodoItemDto.Res modifyDone(@PathVariable final Long id,
			@RequestBody @Valid final TodoItemDto.ModifyDoneReq dto) {
		return new TodoItemDto.Res(todoItemService.modifyDone(id, dto));
	}

	@DeleteMapping(value = "/api/item/delete/{id}")
	public void delete(@PathVariable final Long id) {
		todoItemService.delete(id);
	}
}
