package kr.taeu.handa.domain.todoItem.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.lang.NonNull;

import kr.taeu.handa.domain.member.domain.Member;
import kr.taeu.handa.domain.todoItem.domain.TodoItem;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WriteItemRequest {
	@NotEmpty
	private String content;
	
	private boolean done;

	@Builder
	public WriteItemRequest(@NonNull final String content) {
		this.content = content;
		this.done = false;
	}

	public TodoItem toEntity(final Member member) {
		return TodoItem.builder()
				.member(member)
				.content(this.content)
				.done(this.done)
				.build();
	}
}
