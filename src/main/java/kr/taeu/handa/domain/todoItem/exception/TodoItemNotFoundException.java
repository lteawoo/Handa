package kr.taeu.handa.domain.todoItem.exception;

import kr.taeu.handa.global.error.ErrorCode;
import kr.taeu.handa.global.error.exception.BusinessException;

public class TodoItemNotFoundException extends BusinessException {

	public TodoItemNotFoundException(long id) {
		super("TodoItem id=" + id, ErrorCode.ITEM_NOT_FOUND);
	}
}
