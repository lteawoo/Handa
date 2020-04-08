package kr.taeu.handa.domain.todoItem.dto;

import java.time.LocalDateTime;

import kr.taeu.handa.domain.todoItem.domain.TodoItem;
import lombok.Getter;

@Getter
public class TodoItemResponse {
	private Long id;
	private String content;
	private boolean done;
	private Double position;
	private LocalDateTime lastModifiedDate;
	
	public TodoItemResponse(TodoItem todoItem) {
		this.id = todoItem.getId();
		this.content = todoItem.getContent();
		this.done = todoItem.isDone();
		this.position = todoItem.getPosition();
		this.lastModifiedDate = todoItem.getLastModifiedDate();
	}
}
