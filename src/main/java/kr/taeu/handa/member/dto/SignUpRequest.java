package kr.taeu.handa.member.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import kr.taeu.handa.global.model.Email;
import kr.taeu.handa.global.model.Password;
import kr.taeu.handa.member.domain.Member;
import kr.taeu.handa.member.domain.UniqueCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpRequest {
	@Valid
	private Email email;
	
	@NotEmpty
	private String name;
	
	@Valid
	private Password password;
	
	public SignUpRequest(@Valid Email email, String name, @Valid Password password) {
		this.email = email;
		this.name = name;
		this.password = password;
	}
	
	public Member toEntity(final UniqueCode uniqueCode) {
		return Member.builder()
				.email(this.email)
				.name(this.name)
				.uniqueCode(uniqueCode)
				.build();
	}
}
