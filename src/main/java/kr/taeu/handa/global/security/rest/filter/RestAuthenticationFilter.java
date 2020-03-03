package kr.taeu.handa.global.security.rest.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.taeu.handa.domain.member.dto.SignInRequest;

public class RestAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	public RestAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
		super(requiresAuthenticationRequestMatcher);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		if (!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}

		/* ObjectMapper 사용시 기본생성자 필요 */
		SignInRequest signInRequest = new ObjectMapper().readValue(request.getReader(), SignInRequest.class);

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
				signInRequest.getEmail().getValue(), signInRequest.getPassword().getValue());

		return getAuthenticationManager().authenticate(authentication);
	}

}
