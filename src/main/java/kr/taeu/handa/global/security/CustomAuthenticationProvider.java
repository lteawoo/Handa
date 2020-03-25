package kr.taeu.handa.global.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.taeu.handa.domain.member.domain.model.Email;
import kr.taeu.handa.domain.member.exception.InvalidPasswordException;
import kr.taeu.handa.domain.member.service.MemberDetailsService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
	private final MemberDetailsService memberDetailsService;
	private final PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

		Email email = new Email(token.getName());
		UserDetails user = memberDetailsService.loadUserByUsername(email.getValue());
		String password = user.getPassword();

		if (!passwordEncoder.matches(String.valueOf(token.getCredentials()), password)) {
			throw new InvalidPasswordException();
		}

		return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}
}
