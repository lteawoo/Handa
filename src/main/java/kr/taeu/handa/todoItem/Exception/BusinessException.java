package kr.taeu.handa.todoItem.Exception;

import kr.taeu.handa.error.ErrorCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
	private ErrorCode errorCode;
	
	public BusinessException(String message, ErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
	
	public BusinessException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
