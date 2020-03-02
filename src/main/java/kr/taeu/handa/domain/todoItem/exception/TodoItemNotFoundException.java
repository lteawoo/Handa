package kr.taeu.handa.domain.todoItem.exception;

import kr.taeu.handa.global.error.ErrorCode;
import kr.taeu.handa.global.error.exception.BusinessException;

public class TodoItemNotFoundException extends BusinessException {

	public TodoItemNotFoundException(Long id) {
		super("TodoItem id=" + id, ErrorCode.OBJECT_NOT_FOUND);
	}
}
