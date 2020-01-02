package kr.taeu.handa.todoItem.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.taeu.handa.todoItem.domain.TodoItem;
import kr.taeu.handa.todoItem.service.TodoItemService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class TodoItemController {
	@Autowired
	private TodoItemService todoItemService;
	
	@GetMapping(value="/api/status")
	public String isRunning() {
		return "상태 : Alive";
	}
	
	@GetMapping(value="/api/item/list")
	public List<TodoItem> list(HttpServletRequest req) {
		log.info("call api from " + req.getRemoteAddr());
		return todoItemService.list();
	}
	
	@GetMapping(value="/api/item/write")
	public List<TodoItem> write(HttpServletRequest req) {
		log.info("call api from " + req.getRemoteAddr());
		return todoItemService.list();
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
