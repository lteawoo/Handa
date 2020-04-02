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
public class ModifyOrderRequest {
	@NotNull
	private Integer order;
	
	@Builder
	public ModifyOrderRequest(@NonNull Integer order) {
		this.order = order;
	}

	public TodoItem toEntity() {
		return TodoItem.builder()
				.order(this.order)
				.build();
	}
}
