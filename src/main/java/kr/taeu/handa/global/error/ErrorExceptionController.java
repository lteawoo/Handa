package kr.taeu.handa.global.error;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import kr.taeu.handa.global.error.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ErrorExceptionController {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		log.error("handleMethodArgumentNotValidException", e);
		final BindingResult bindingResult = e.getBindingResult();
		final List<FieldError> errors = bindingResult.getFieldErrors();
		
		return buildFieldError(ErrorCode.INPUT_VALUE_INVALID,
				errors.parallelStream()
					.map(error -> ErrorResponse.FieldError.builder()
							.reason(error.getDefaultMessage())
							.field(error.getField())
							.value((String) error.getRejectedValue())
							.build())
					.collect(Collectors.toList()));
	}
	
	/**
	 * 비즈니스 로직에서의 에러 검출 시 발생
	 */
	@ExceptionHandler(BusinessException.class)
	protected ErrorResponse handleBusinessException(final BusinessException e) {
		log.error("handleBusinessException", e);
		return buildError(e.getErrorCode());
	}
	
	private ErrorResponse buildFieldError(ErrorCode errorCode, List<ErrorResponse.FieldError> errors) {
		return ErrorResponse.builder()
				.code(errorCode.getCode())
				.status(errorCode.getStatus())
				.message(errorCode.getMessage())
				.errors(errors)
				.build();
	}
	
	private ErrorResponse buildError(ErrorCode errorCode) {
		return ErrorResponse.builder()
				.code(errorCode.getCode())
				.status(errorCode.getStatus())
				.message(errorCode.getMessage())
				.build();
	}
}
