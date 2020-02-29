package kr.taeu.handa.domain.member.dto;

import kr.taeu.handa.domain.member.domain.Member;
import kr.taeu.handa.domain.member.domain.model.Email;
import lombok.Getter;

@Getter
public class MemberResponse {
	private Email email;

	public MemberResponse(Member member) {
		this.email = member.getEmail();
	}
}
