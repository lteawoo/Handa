package kr.taeu.handa.todoItem.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.taeu.handa.todoItem.domain.TodoItem;
import kr.taeu.handa.todoItem.dto.TodoItemDto;
import kr.taeu.handa.todoItem.dto.TodoItemDto.Res;
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
	public List<TodoItem> list(HttpServletRequest req) {
		log.info("call api from " + req.getRemoteAddr());
		return todoItemService.list();
	}
	
	@PostMapping(value="/api/item/write")
	public Res write(@RequestBody final TodoItemDto.WriteReq dto, HttpServletRequest req) {
		log.info(dto.toEntity().getContent());
		log.info("call api from " + req.getRemoteAddr());
		return new TodoItemDto.Res(todoItemService.write(dto));
	}
	
	@GetMapping(value="/api/item/modify")
	public String modify() {
		return "modify Item";
	}
	
	@GetMapping(value="/api/item/delete")
	public String delete() {
		return "delete Item";
	}
}
