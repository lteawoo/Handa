package kr.taeu.handa.todoItem.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import kr.taeu.handa.todoItem.domain.TodoItem;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TodoItemDto {
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class WriteReq {
		@NotEmpty
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
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class ModifyContentReq {
		@NotEmpty
		private String content;
		
		@Builder
		public ModifyContentReq(String content) {
			this.content = content;
		}
		
		public TodoItem toEntity() {
			return TodoItem.builder()
					.content(this.content)
					.build();
		}
	}
	
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class ModifyDoneReq {
		@NotNull
		private boolean done;
		
		@Builder
		public ModifyDoneReq(boolean done) {
			this.done = done;
		}
		
		public TodoItem toEntity() {
			return TodoItem.builder()
					.done(this.done)
					.build();
		}
	}
	
	@Getter
	public static class Res {
		private long id;
		private String content;
		private boolean done;
		
		public Res(TodoItem todoItem) {
			this.id = todoItem.getId();
			this.content = todoItem.getContent();
			this.done = todoItem.isDone();
		}
	}
}
