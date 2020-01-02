package kr.taeu.handa.todoItem.dto;

import kr.taeu.handa.todoItem.domain.TodoItem;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TodoItemDto {
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class WriteReq {
		private String content;
		private boolean done;
		
		@Builder
		public WriteReq(String content, boolean done) {
			this.content = content;
			this.done = done;
		}
		
		public TodoItem toEntity() {
			return TodoItem.builder()
					.content(this.content)
					.done(this.done)
					.build();
		}
	}
	
	@Getter
	public static class Res {
		private String content;
		private boolean done;
		
		public Res(TodoItem todoItem) {
			this.content = todoItem.getContent();
			this.done = todoItem.isDone();
		}
	}
}
