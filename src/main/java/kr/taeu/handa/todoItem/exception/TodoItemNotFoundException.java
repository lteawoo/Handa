package kr.taeu.handa.todoItem.exception;

import kr.taeu.handa.global.error.ErrorCode;

public class TodoItemNotFoundException extends BusinessException {
	
	public TodoItemNotFoundException(long id) {
		super("TodoItem id=" + id, ErrorCode.TODOITEM_NOT_FOUND);		
	}
}
