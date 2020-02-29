package kr.taeu.handa.domain.member.exception;

import kr.taeu.handa.domain.member.domain.model.Email;
import kr.taeu.handa.global.error.exception.RequestConflictException;

public class EmailAlreadyExistsException extends RequestConflictException {
	public EmailAlreadyExistsException(Email email) {
		super(email.getValue() + " is already exists");
	}
}
