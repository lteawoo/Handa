package kr.taeu.handa.global.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import kr.taeu.handa.global.error.exception.BusinessException;

@ControllerAdvice
public class ControllerExceptionHandler {

	/*
	 * 비지니스 로직에서 발생하는 예외 처리
	 */
	@ExceptionHandler(BusinessException.class)
	protected ResponseEntity<ErrorResponse> handleBusinessException(final BusinessException e) {
		final ErrorResponse response = new ErrorResponse(e.getErrorCode(), e);
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
	}

	/*
	 * @Valid 예외 처리
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
			final MethodArgumentNotValidException e) {
		final ErrorResponse response = new ErrorResponse(ErrorCode.INVALID_INPUT_VALUE, e.getBindingResult());
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
	}
}
