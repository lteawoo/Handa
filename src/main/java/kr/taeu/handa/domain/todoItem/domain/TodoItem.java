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

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.lang.NonNull;

import kr.taeu.handa.domain.member.domain.Member;
import kr.taeu.handa.domain.todoItem.dto.ModifyContentRequest;
import kr.taeu.handa.domain.todoItem.dto.ModifyDoneRequest;
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
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", updatable = false)
	private Long id;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "MEMBER_ID", updatable = false, nullable = false)
	private Member member;

	@Column(name = "CONTENT", length = 500, nullable = false)
	private String content;

	@Column(name = "DONE", nullable = false)
	private boolean done;
	
	@Column(name= "ORDER")
	private Double order;

	@Column(name = "LAST_MODIFIED_DATE")
	@LastModifiedDate
	private LocalDateTime lastModifiedDate;
	
	@Column(name = "CREATED_DATE", nullable=false, updatable = false)
	@CreatedDate
	private LocalDateTime createdDate; 

	@Builder
	public TodoItem(@NonNull Member member, @NonNull String content, @NonNull boolean done, @NonNull Double order) {
		this.member = member;
		this.content = content;
		this.done = done;
		this.order = order;
	}

	public void modifyContent(ModifyContentRequest dto) {
		this.content = dto.getContent();
	}

	public void modifyDone(ModifyDoneRequest dto) {
		this.done = dto.isDone();
	}
}
