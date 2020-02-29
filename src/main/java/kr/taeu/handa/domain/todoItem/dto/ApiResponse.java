package kr.taeu.handa.domain.todoItem.dto;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class ApiResponse<T> {
	private T data;
	private List<String> errors;
}
