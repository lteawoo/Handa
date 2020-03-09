package kr.taeu.handa.domain.todoItem.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.taeu.handa.domain.member.domain.Member;
import kr.taeu.handa.domain.todoItem.domain.TodoItem;
import kr.taeu.handa.domain.todoItem.dto.ModifyContentRequest;
import kr.taeu.handa.domain.todoItem.dto.ModifyDoneRequest;
import kr.taeu.handa.domain.todoItem.dto.TodoItemResponse;
import kr.taeu.handa.domain.todoItem.dto.WriteItemRequest;
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
	public TodoItemResponse write(Principal principal, @RequestBody @Valid final WriteItemRequest dto) {
		log.info(principal.getName());
		return new TodoItemResponse(todoItemService.write(principal.getName(), dto));
	}

	@PostMapping(value = "/api/item/modifyContent/{id}")
	public TodoItemResponse modifyContent(@PathVariable final Long id,
			@RequestBody @Valid final ModifyContentRequest dto) {
		return new TodoItemResponse(todoItemService.modifyContent(id, dto));
	}

	@PostMapping(value = "/api/item/modifyDone/{id}")
	public TodoItemResponse modifyDone(@PathVariable final Long id,
			@RequestBody @Valid final ModifyDoneRequest dto) {
		return new TodoItemResponse(todoItemService.modifyDone(id, dto));
	}

	@DeleteMapping(value = "/api/item/delete/{id}")
	public void delete(@PathVariable final Long id) {
		todoItemService.delete(id);
	}
	
	@GetMapping(value = "/api/item/test")
	public String test(Authentication authentication) {
		return authentication.getPrincipal().toString();
	}
}
