package kr.taeu.handa.domain.member.dto;

import java.time.LocalDateTime;

import kr.taeu.handa.domain.member.domain.Member;
import kr.taeu.handa.domain.member.domain.model.Email;
import lombok.Getter;

@Getter
public class MemberResponse {
	private Email email;
	private LocalDateTime lastModifiedDate;
	private LocalDateTime createdDate;

	public MemberResponse(Member member) {
		this.email = member.getEmail();
		this.lastModifiedDate = member.getLastModifiedDate();
		this.createdDate = member.getCreatedDate();
	}
}
