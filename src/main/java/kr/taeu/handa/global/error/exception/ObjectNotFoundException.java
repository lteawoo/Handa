package kr.taeu.handa.global.error.exception;

import kr.taeu.handa.global.error.ErrorCode;

public class ObjectNotFoundException extends BusinessException {

	public ObjectNotFoundException(String message) {
		super(message, ErrorCode.OBJECT_NOT_FOUND);
	}
}
