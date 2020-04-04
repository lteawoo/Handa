package kr.taeu.handa.domain.todoItem.dto;

import javax.validation.constraints.NotNull;

import org.springframework.lang.NonNull;

import kr.taeu.handa.domain.todoItem.domain.TodoItem;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ModifyPositionRequest {
	@NotNull
	private Double position;
	
	@Builder
	public ModifyPositionRequest(@NonNull Double position) {
		this.position = position;
	}

	public TodoItem toEntity() {
		return TodoItem.builder()
				.position(this.position)
				.build();
	}
}
