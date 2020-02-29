package kr.taeu.handa.global.error.exception;

import kr.taeu.handa.global.error.ErrorCode;

public class RequestConflictException extends BusinessException {
	public RequestConflictException(String message) {
		super(message, ErrorCode.REQUEST_CONFILICT_EXCEPTION);
	}
}
