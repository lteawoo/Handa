package kr.taeu.handa.domain.todoItem.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import kr.taeu.handa.domain.member.domain.Member;
import kr.taeu.handa.domain.todoItem.domain.TodoItem;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ModifyContentRequest {
	@NotNull
	private Member member;
	
	@NotNull
	private TodoItem todoItem;
	
	@NotEmpty
	private String content;

	@Builder
	public ModifyContentRequest(Member member, TodoItem todoItem, String content) {
		this.member = member;
		this.todoItem = todoItem;
		this.content = content;
	}

	public TodoItem toEntity() {
		return TodoItem.builder()
				.content(this.content)
				.build();
	}
}
