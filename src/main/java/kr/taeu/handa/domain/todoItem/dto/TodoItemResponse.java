package kr.taeu.handa.domain.todoItem.dto;

import kr.taeu.handa.domain.todoItem.domain.TodoItem;
import lombok.Getter;

@Getter
public class TodoItemResponse {
	private String content;
	private boolean done;
	
	public TodoItemResponse(TodoItem todoItem) {
		this.content = todoItem.getContent();
		this.done = todoItem.isDone();
	}
}