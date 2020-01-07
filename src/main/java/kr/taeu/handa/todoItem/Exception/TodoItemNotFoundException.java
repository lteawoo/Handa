package kr.taeu.handa.todoItem.Exception;

import lombok.Getter;

@Getter
public class TodoItemNotFoundException extends RuntimeException {
	private long id;
	
	public TodoItemNotFoundException(long id) {
		this.id = id;
	}
}
