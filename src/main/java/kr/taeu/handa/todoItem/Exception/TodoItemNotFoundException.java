package kr.taeu.handa.todoItem.Exception;

import kr.taeu.handa.error.ErrorCode;

public class TodoItemNotFoundException extends BusinessException {
	
	public TodoItemNotFoundException(long id) {
		super("TodoItem id=" + id, ErrorCode.TODOITEM_NOT_FOUND);		
	}
}
