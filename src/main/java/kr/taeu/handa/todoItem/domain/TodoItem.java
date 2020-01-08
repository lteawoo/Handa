package kr.taeu.handa.todoItem.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import kr.taeu.handa.todoItem.dto.TodoItemDto.ModifyContentReq;
import kr.taeu.handa.todoItem.dto.TodoItemDto.ModifyDoneReq;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TODOITEM")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodoItem{
//	@Id
//	private String email;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(length = 500, nullable = false)
	private String content;
	
	private boolean done;
	
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
