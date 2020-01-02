package kr.taeu.handa.todoItem.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodoItem implements Serializable{
//	@Id
//	private String email;
	
	@Id	@GeneratedValue
	private int id;

	private String content;
	
	private boolean done;
}
