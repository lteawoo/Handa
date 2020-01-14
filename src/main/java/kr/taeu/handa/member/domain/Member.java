package kr.taeu.handa.member.domain;

import java.time.LocalDateTime;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import kr.taeu.handa.global.model.Email;
import kr.taeu.handa.global.model.Password;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="MEMBER")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", updatable = false)
	private long id;
	
	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "EMAIL", nullable = false, unique = true, updatable = false, length = 50))
	private Email email;
	
	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "UNIQUE_CODE", nullable = false, unique = true, updatable = false, length = 32))
	private UniqueCode uniqueCode;
	
	@Column(name = "NICKNAME", length = 50)
	private String name;
	
	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "PASSWORD", nullable = false, length = 50))
	private Password password;
	
	@CreationTimestamp
	@Column(name = "CREATE_DT", nullable = false, updatable = false)
	private LocalDateTime createDt;
	
	@UpdateTimestamp
	@Column(name = "UPDATE_DT", nullable = false)
	private LocalDateTime updateDt;
	
	@Builder
	public Member(Email email, UniqueCode uniqueCode, String name, Password password) {
		this.email = email;
		this.uniqueCode = uniqueCode;
		this.name = name;
		this.password = password;
	}
	
	public void updateProfile(final String name) {
		this.name = name;
	}
}
