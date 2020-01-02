package kr.taeu.handa.todoItem.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class TodoItem{
//	@Id
//	private String email;
	
	@Id	@GeneratedValue
	private Long id;

	private String content;
	
	private boolean done;
	
	@Builder
	public TodoItem(String content, boolean done) {
		this.content = content;
		this.done = done;
	}
}
