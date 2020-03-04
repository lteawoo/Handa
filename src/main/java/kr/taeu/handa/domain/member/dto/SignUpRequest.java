package kr.taeu.handa.domain.member.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import kr.taeu.handa.domain.member.domain.Member;
import kr.taeu.handa.domain.member.domain.model.Email;
import kr.taeu.handa.domain.member.domain.model.Name;
import kr.taeu.handa.domain.member.domain.model.Password;
import kr.taeu.handa.domain.member.domain.model.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpRequest {
	@Valid
	@NotNull
	private Email email;

	@Valid
	@NotNull
	private Name name;
	
	@Valid
	@NotNull
	private Password password;

	private Role role;

	@Builder
	private SignUpRequest(@Valid Email email, @Valid Name name, @Valid Password password) {
		this.email = email;
		this.name = name;
		this.password = password;
		this.role = Role.MEMBER;
	}

	public Member toEntity() {
		return Member.builder()
				.email(this.email)
				.name(this.name)
				.password(this.password)
				.role(this.role)
				.build();
	}
}
