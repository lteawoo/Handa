package kr.taeu.handa.global.security.rest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.taeu.handa.domain.member.exception.EmailNotFoundException;
import kr.taeu.handa.domain.member.exception.InvalidPasswordException;
import kr.taeu.handa.global.error.ErrorCode;
import kr.taeu.handa.global.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;

/*
 * 인증 실패에 대한 전략
 */
@Slf4j
public class RestAuthenticationFailuerHandler implements AuthenticationFailureHandler {
	private ObjectMapper objectMappger = new ObjectMapper();

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		ErrorResponse er = null;

		if (EmailNotFoundException.class == exception.getClass()) {
			er = new ErrorResponse(ErrorCode.REQUEST_CONFILICT_EXCEPTION, exception);
			response.setStatus(HttpStatus.CONFLICT.value());
		} else if (InvalidPasswordException.class == exception.getClass()) {
			er = new ErrorResponse(ErrorCode.REQUEST_CONFILICT_EXCEPTION, exception);
			response.setStatus(HttpStatus.CONFLICT.value());
		} else {
			er = new ErrorResponse(ErrorCode.REQUEST_CONFILICT_EXCEPTION, exception);
			response.setStatus(HttpStatus.CONFLICT.value());
		}

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.getOutputStream().println(objectMappger.writeValueAsString(er));
	}
}
