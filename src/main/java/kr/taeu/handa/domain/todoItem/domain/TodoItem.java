package kr.taeu.handa.domain.todoItem.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import kr.taeu.handa.domain.member.domain.Member;
import kr.taeu.handa.domain.todoItem.dto.TodoItemDto.ModifyContentReq;
import kr.taeu.handa.domain.todoItem.dto.TodoItemDto.ModifyDoneReq;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "TODOITEM")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodoItem {
	@ManyToOne
	@JoinColumn(name = "MEMBER_ID", updatable = false, nullable = false)
	private Member member;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", updatable = false)
	private Long id;

	@Column(name = "CONTENT", length = 500, nullable = false)
	private String content;

	@Column(name = "DONE", nullable = false)
	private boolean done;

	@Column(name = "LAST_MODIFIED_DATE")
	@LastModifiedDate
	private LocalDateTime lastModifiedDate;
	
	@Column(name = "CREATED_DATE", nullable=false, updatable = false)
	@CreatedDate
	private LocalDateTime createdDate; 

	@Builder
	public TodoItem(Member member, String content, boolean done) {
		this.member = member;
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
