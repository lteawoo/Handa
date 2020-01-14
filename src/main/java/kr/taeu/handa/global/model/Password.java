package kr.taeu.handa.global.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Password {
	
	@Column(name= "PASSWORD", nullable=false)
	@NotEmpty
	private String value;
	
	private Password(String value) {
		this.value = value;
	}
	
	public static Password build(final String value) {
		return new Password(value);
	}
	
	public void changePassword(final String newPassword, final String oldPassword) {
		
	}
}
