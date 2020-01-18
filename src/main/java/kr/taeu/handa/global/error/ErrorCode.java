package kr.taeu.handa.global.error;

import lombok.Getter;

@Getter
public enum ErrorCode {
	// Common
	INVALID_TYPE_VALUE(400, "C001", "입력값이 올바르지 않습니다."),
	
	// TodoItem
	ITEM_NOT_FOUND(400, "T001", "아이템을 찾을 수 없습니다."),
	
	// Member
	MEMBER_NOT_FOUND(400, "M001", "회원을 찾을 수 없습니다."),
	EMAIL_DUPLICATION(400, "M002", "이미 존재하는 이메일입니다.");
	
	private final String code;
	private final String message;
	private final int status;
	
	ErrorCode(final int status, final String code, final String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}
}
