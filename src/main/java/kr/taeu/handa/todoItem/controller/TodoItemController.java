package kr.taeu.handa.todoItem.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.taeu.handa.todoItem.domain.TodoItem;
import kr.taeu.handa.todoItem.dto.TodoItemDto;
import kr.taeu.handa.todoItem.service.TodoItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TodoItemController {
	private final TodoItemService todoItemService;
	
	@GetMapping(value="/api/status")
	public String isRunning() {
		return "상태 : Alive";
	}
	
	@GetMapping(value="/api/item/list")
	public List<TodoItem> list() {
		return todoItemService.list();
	}
	
	@PostMapping(value="/api/item/write")
	public TodoItemDto.Res write(@RequestBody final TodoItemDto.WriteReq dto) {
		return new TodoItemDto.Res(todoItemService.write(dto));
	}
	
	@PostMapping(value="/api/item/modifyContent/{id}")
	public TodoItemDto.Res modifyContent(@PathVariable final long id, @RequestBody final TodoItemDto.ModifyContentReq dto) {
		return new TodoItemDto.Res(todoItemService.modifyContent(dto));
	}
	
	@PostMapping(value="/api/item/modifyDone/{id}")
	public TodoItemDto.Res modifyDone(@PathVariable final long id, @RequestBody final TodoItemDto.ModifyDoneReq dto) {
		return new TodoItemDto.Res(todoItemService.modifyDone(dto));
	}
	
	@GetMapping(value="/api/item/delete")
	public String delete() {
		return "delete Item";
	}
}
