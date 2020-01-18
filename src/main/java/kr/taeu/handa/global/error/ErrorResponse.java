package kr.taeu.handa.global.error;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {
	private String message;
	private String code;
	private int status;
	private List<FieldError> errors;
	
	private ErrorResponse(final ErrorCode code, final List<FieldError> errors) {
		this.message = code.getMessage();
		this.code = code.getCode();
		this.status = code.getStatus();
		this.errors = errors;
	}
	
	private ErrorResponse(final ErrorCode code) {
		this.message = code.getMessage();
		this.code = code.getCode();
		this.status = code.getStatus();
		this.errors = new ArrayList<>();
	}
	
	public static ErrorResponse build(final ErrorCode code, final BindingResult bindingResult) {
		return new ErrorResponse(code, FieldError.build(bindingResult));
	}
	
	public static ErrorResponse build(final ErrorCode code) {
		return new ErrorResponse(code);
	}
	
	public static ErrorResponse build(final ErrorCode code, final List<FieldError> errors) {
		return new ErrorResponse(code, errors);
	}
	
	public static ErrorResponse build(MethodArgumentTypeMismatchException e) {
		final String value = e.getValue() == null ? "" : e.getValue().toString();
		final List<ErrorResponse.FieldError> errors = ErrorResponse.FieldError.build(e.getName(), value, e.getErrorCode());
		return new ErrorResponse(ErrorCode.INVALID_TYPE_VALUE, errors);
	}
	
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class FieldError {
		private String field;
		private String value;
		private String reason;
		
	
		private FieldError(String field, String value, String reason) {
			this.field = field;
			this.value = value;
			this.reason = reason;
		}
		
		public static List<FieldError> build(final String field, final String value, final String reason) {
			List<FieldError> fieldErrors = new ArrayList<>();
			fieldErrors.add(new FieldError(field, value, reason));
			return fieldErrors;
		}
		
		private static List<FieldError> build(final BindingResult bindingResult) {
			final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
			return fieldErrors.stream()
					.map(error -> new FieldError(
							error.getField(),
							error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
							error.getDefaultMessage()))
					.collect(Collectors.toList());
		}
	}
}
