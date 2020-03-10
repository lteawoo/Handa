package kr.taeu.handa.domain.member.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import kr.taeu.handa.domain.member.domain.model.Email;
import kr.taeu.handa.domain.member.domain.model.Name;
import kr.taeu.handa.domain.member.domain.model.Password;
import kr.taeu.handa.domain.member.domain.model.Role;
import kr.taeu.handa.domain.todoItem.domain.TodoItem;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "MEMBER")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", updatable = false)
	private Long id;

	@Embedded
	private Email email;
	
	@Embedded
	private Name name;

	@Embedded
	private Password password;

	@Column(name = "ROLE", nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	private Role role;
	
//	@OneToMany(mappedBy = "member")
//	private List<TodoItem> todoItems;
	
	@Column(name = "LAST_MODIFIED_DATE")
	@LastModifiedDate
	private LocalDateTime lastModifiedDate;
	
	@Column(name = "CREATED_DATE", nullable=false, updatable = false)
	@CreatedDate
	private LocalDateTime createdDate; 

	@Builder
	private Member(Email email, Name name, Password password, Role role) {
		this.email = email;
		this.name = name;
		this.password = password;
		this.role = role;
	}
}
