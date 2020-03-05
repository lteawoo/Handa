package kr.taeu.handa.domain.todoItem.dto;

import javax.validation.constraints.NotNull;

import kr.taeu.handa.domain.member.domain.Member;
import kr.taeu.handa.domain.todoItem.domain.TodoItem;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ModifyDoneRequest {
	@NotNull
	private Member member;
	
	@NotNull
	private TodoItem todoItem;
	
	@NotNull
	private boolean done;

	@Builder
	public ModifyDoneRequest(Member member, TodoItem todoItem, boolean done) {
		this.member = member;
		this.todoItem = todoItem;
		this.done = done;
	}

	public TodoItem toEntity() {
		return TodoItem.builder()
				.done(this.done)
				.build();
	}
}
