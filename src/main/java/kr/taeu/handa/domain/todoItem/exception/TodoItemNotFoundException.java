package kr.taeu.handa.domain.todoItem.exception;

import kr.taeu.handa.domain.member.domain.model.Email;
import kr.taeu.handa.global.error.ErrorCode;
import kr.taeu.handa.global.error.exception.BusinessException;

public class TodoItemNotFoundException extends BusinessException {

	public TodoItemNotFoundException(Email email, Long id) {
		super(email + "'s item id: " + id + " is not found", ErrorCode.OBJECT_NOT_FOUND);
	}
}
