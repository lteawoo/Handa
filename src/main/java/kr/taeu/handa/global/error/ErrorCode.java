package kr.taeu.handa.global.error;

import lombok.Getter;

@Getter
public enum ErrorCode {
	// Common
	INPUT_VALUE_INVALID(400, "C001", "입력값이 올바르지 않습니다."),
	
	// Todo_Item
	TODO_ITEM_NOT_FOUND(400, "T001", "아이템을 찾을 수 없습니다.");
	
	private final String code;
	private final String message;
	private final int status;
	
	ErrorCode(final int status, final String code, final String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}
}
