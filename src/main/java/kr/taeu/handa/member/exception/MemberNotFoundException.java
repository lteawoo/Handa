package kr.taeu.handa.member.exception;

import kr.taeu.handa.global.error.ErrorCode;
import kr.taeu.handa.global.error.exception.BusinessException;
import kr.taeu.handa.global.model.Email;

public class MemberNotFoundException extends BusinessException {
	public MemberNotFoundException(Email email) {
		super("Member email : " + email.getValue(), ErrorCode.MEMBER_NOT_FOUND);
	}
}
