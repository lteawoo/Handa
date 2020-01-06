package kr.taeu.handa.todoItem.error;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ErrorExceptionController {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		log.error(e.getMessage());
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
	
	private ErrorResponse buildFieldError(ErrorCode errorCode, List<ErrorResponse.FieldError> errors) {
		return ErrorResponse.builder()
				.code(errorCode.getCode())
				.status(errorCode.getStatus())
				.message(errorCode.getMessage())
				.errors(errors)
				.build();
	}
}
