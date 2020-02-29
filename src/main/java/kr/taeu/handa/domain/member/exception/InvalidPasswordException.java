package kr.taeu.handa.domain.member.exception;

import org.springframework.security.authentication.BadCredentialsException;

public class InvalidPasswordException extends BadCredentialsException {
	public InvalidPasswordException() {
		super("Invalid Password");
	}
}
