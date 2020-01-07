package kr.taeu.handa.error;

import lombok.Getter;

@Getter
public enum ErrorCode {
	TODOITEM_NOT_FOUND("TI_001", "해당 아이템이 없습니다.", 404),
	INPUT_VALUE_INVALID("CM_001", "입력값이 올바르지 않습니다.", 400);
	
	private final String code;
	private final String message;
	private final int status;
	
	ErrorCode(String code, String message, int status) {
		this.code = code;
		this.message = message;
		this.status = status;
	}
}
