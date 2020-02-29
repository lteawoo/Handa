package kr.taeu.handa.global.error;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;

import lombok.Getter;

@Getter
public class FieldError {
	private String field;
	private String value;
	private String message;

	private FieldError(final String field, final String value, final String message) {
		this.field = field;
		this.value = value;
		this.message = message;
	}

	public static List<FieldError> parse(final BindingResult bindingResult) {
		final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
		return fieldErrors.stream()
				.map(error -> new FieldError(error.getField(),
						error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
						error.getDefaultMessage()))
				.collect(Collectors.toList());
	}
}
