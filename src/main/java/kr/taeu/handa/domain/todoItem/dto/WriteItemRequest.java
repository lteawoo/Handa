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
	@NotNull
	private final Member member;
	
	@NotEmpty
	private final String content;
	
	private final boolean done;

	@Builder
	private WriteItemRequest(@NonNull final Member member, @NonNull final String content) {
		this.member = member;
		this.content = content;
		this.done = false;
	}

	public TodoItem toEntity() {
		return TodoItem.builder()
				.member(this.member)
				.content(this.content)
				.done(this.done)
				.build();
	}
}
