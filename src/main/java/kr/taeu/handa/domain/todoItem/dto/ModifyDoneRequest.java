package kr.taeu.handa.domain.todoItem.dto;

import javax.validation.constraints.NotNull;

import org.springframework.lang.NonNull;

import kr.taeu.handa.domain.todoItem.domain.TodoItem;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ModifyDoneRequest {
	@NotNull
	private final boolean done;

//	private ModifyDoneRequest(Builder builder) {
//		this.done = builder.done;
//	}
	
	@Builder
	private ModifyDoneRequest(@NonNull boolean done) {
		this.done = done;
	}

	public TodoItem toEntity() {
		return TodoItem.builder()
				.done(this.done)
				.build();
	}
	
//	public static class Builder {
//		private final boolean done;
//		
//		public Builder(boolean done) {
//			this.done = done;
//		}
//		
//		public ModifyDoneRequest build() {
//			return new ModifyDoneRequest(this);
//		}
//	}
}
