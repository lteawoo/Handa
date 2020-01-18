package kr.taeu.handa.member.dto;

import javax.validation.Valid;

import kr.taeu.handa.global.model.Email;
import kr.taeu.handa.global.model.Password;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignInRequest {
	@Valid
	private Email email;
	
	@Valid
	private Password password;
	
	public SignInRequest(@Valid Email email, @Valid Password password) {
		this.email = email;
		this.password = password;
	}
}
