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
public class Email {
	
	@javax.validation.constraints.Email
	@Column(name = "EMAIL", length = 50)
	@NotEmpty
	private String value;
	
	private Email(final String value) {
		this.value = value;
	}
	
	public static Email build(final String value) {
		return new Email(value);
	}
	
	public String getDomain() {
		final int index = this.value.indexOf("@");
		return index == -1 ? null : this.value.substring(index + 1);
	}
	
	public String getId() {
		final int index = this.value.indexOf("@");
		return index == -1 ? null : this.value.substring(0, index);
	}
}
