package kr.taeu.handa.member.dto;

import kr.taeu.handa.global.model.Email;
import kr.taeu.handa.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class MemberResponse {
	private Email email;
	
	private String name;
	
	public MemberResponse(final Member member) {
		this.email = member.getEmail();
		this.name = member.getName();
	}
}
