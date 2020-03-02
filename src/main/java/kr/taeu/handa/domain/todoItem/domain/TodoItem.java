package kr.taeu.handa.domain.todoItem.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import kr.taeu.handa.domain.todoItem.dto.TodoItemDto.ModifyContentReq;
import kr.taeu.handa.domain.todoItem.dto.TodoItemDto.ModifyDoneReq;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TODOITEM")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodoItem {
//	@Id
//	private String email;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", updatable = false)
	private Long id;

	@Column(name = "CONTENT", length = 500, nullable = false)
	private String content;

	@Column(name = "DONE", nullable = false)
	private boolean done;

	@CreationTimestamp
	@Column(name = "CREATE_DT", nullable = false, updatable = false)
	private LocalDateTime createDt;

	@UpdateTimestamp
	@Column(name = "UPDATE_DT", nullable = false)
	private LocalDateTime updateDt;

	@Builder
	public TodoItem(String content, boolean done) {
		this.content = content;
		this.done = done;
	}

	public void modifyContent(ModifyContentReq dto) {
		this.content = dto.getContent();
	}

	public void modifyDone(ModifyDoneReq dto) {
		this.done = dto.isDone();
	}
}
