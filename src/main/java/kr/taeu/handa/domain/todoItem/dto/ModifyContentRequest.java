package kr.taeu.handa.domain.todoItem.dto;

import javax.validation.constraints.NotEmpty;

import kr.taeu.handa.domain.todoItem.domain.TodoItem;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class ModifyContentRequest {
	@NotEmpty
	private final String content;

	@Builder
	private ModifyContentRequest(@NonNull String content) {
		this.content = content;
	}

	public TodoItem toEntity() {
		return TodoItem.builder()
				.content(this.content)
				.build();
	}
}
