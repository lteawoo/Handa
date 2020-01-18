package kr.taeu.handa.member.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UniqueCode {
	
	@Column(name = "UNIQUE_CODE", length = 50)
	private String value;
	
	private UniqueCode(String value) {
		this.value = value;
	}
	
	public static UniqueCode generateCode() {
		return new UniqueCode(UUID.randomUUID().toString().replace("-", ""));
	}
}
