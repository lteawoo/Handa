package kr.taeu.handa.global.error;

import lombok.Getter;

@Getter
public enum ErrorCode {
	OBJECT_NOT_FOUND("E001", "Object Not Found", 400), REQUEST_CONFILICT_EXCEPTION("E002", "Request Conflict", 409),
	INVALID_INPUT_VALUE("E003", "Invalid Input Value", 400),

	UNAUTHORIZED("S001", "Unauthorized", 401),

	EMAIL_ALREADY_EXISTS_EXCEPTION("M001", "Email Already Exists", 409);

	private final String code;
	private final String message;
	private final int status;

	ErrorCode(final String code, final String message, final int status) {
		this.code = code;
		this.message = message;
		this.status = status;
	}
}
