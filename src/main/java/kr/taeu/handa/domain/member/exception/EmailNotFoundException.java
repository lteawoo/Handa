package kr.taeu.handa.domain.member.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import kr.taeu.handa.domain.member.domain.model.Email;

public class EmailNotFoundException extends UsernameNotFoundException {
	public EmailNotFoundException(Email email) {
		super(email.getValue() + " is not found");
	}
}
