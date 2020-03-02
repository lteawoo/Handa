package kr.taeu.handa.domain.member.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Name {
	@Column(name = "NAME", length = 30, nullable = false)
	@NotEmpty
	private String value;
	
	public Name(String value) {
		this.value = value;
	}
}
