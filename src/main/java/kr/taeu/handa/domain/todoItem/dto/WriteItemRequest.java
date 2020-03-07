package kr.taeu.handa.domain.todoItem.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.lang.NonNull;

import kr.taeu.handa.domain.member.domain.Member;
import kr.taeu.handa.domain.todoItem.domain.TodoItem;
import lombok.Builder;
import lombok.Getter;

@Getter
public class WriteItemRequest {
	@NotEmpty
	private final String content;
	
	private final boolean done;

	@Builder
	private WriteItemRequest(@NonNull final String content) {
		this.content = content;
		this.done = false;
	}

	public TodoItem toEntity() {
		return TodoItem.builder()
				.content(this.content)
				.done(this.done)
				.build();
	}
}
