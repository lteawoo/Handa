package kr.taeu.handa.member.exception;

import kr.taeu.handa.global.error.ErrorCode;
import kr.taeu.handa.global.error.exception.BusinessException;
import kr.taeu.handa.global.model.Email;

public class EmailDuplicateException extends BusinessException{
	
	public EmailDuplicateException(Email email) {
		super("Email duplicate : " + email.getValue(), ErrorCode.EMAIL_DUPLICATION);
	}
}
