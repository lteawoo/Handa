package kr.taeu.handa.domain.todoItem.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import kr.taeu.handa.domain.todoItem.dto.ModifyContentRequest;
import kr.taeu.handa.domain.todoItem.dto.ModifyDoneRequest;
import kr.taeu.handa.domain.todoItem.dto.ModifyPositionRequest;
import kr.taeu.handa.domain.todoItem.dto.TodoItemResponse;
import kr.taeu.handa.domain.todoItem.dto.WriteItemRequest;
import kr.taeu.handa.domain.todoItem.service.TodoItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TodoItemController {
	private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();	
	private final TodoItemService todoItemService;

	@GetMapping(value = "/api/item/list")
	public List<TodoItemResponse> list(Principal principal) {
		List<TodoItemResponse> list = todoItemService.list(principal.getName()).stream()
				.map(item -> new TodoItemResponse(item))
				.collect(Collectors.toList());
		return list;
	}

	@PostMapping(value = "/api/item/write")
	public TodoItemResponse write(Principal principal, @RequestBody @Valid final WriteItemRequest dto) {
		log.info(principal.getName());
		return new TodoItemResponse(todoItemService.write(principal.getName(), dto));
	}

	@PostMapping(value = "/api/item/modifyContent/{id}")
	public TodoItemResponse modifyContent(Principal principal, @PathVariable final Long id,
			@RequestBody @Valid final ModifyContentRequest dto) {
		return new TodoItemResponse(todoItemService.modifyContent(principal.getName(), id, dto));
	}

	@PostMapping(value = "/api/item/modifyDone/{id}")
	public TodoItemResponse modifyDone(Principal principal, @PathVariable final Long id,
			@RequestBody @Valid final ModifyDoneRequest dto) {
		return new TodoItemResponse(todoItemService.modifyDone(principal.getName(), id, dto));
	}
	
	@PostMapping(value = "/api/item/modifyPosition/{id}")
	public TodoItemResponse modifyPosition(Principal principal, @PathVariable final Long id,
			@RequestBody @Valid final ModifyPositionRequest dto) {
		return new TodoItemResponse(todoItemService.changePosition(principal.getName(), id, dto));
	}

	@DeleteMapping(value = "/api/item/delete/{id}")
	public void delete(Principal principal, @PathVariable final Long id) {
		todoItemService.delete(principal.getName(), id);
	}
}
