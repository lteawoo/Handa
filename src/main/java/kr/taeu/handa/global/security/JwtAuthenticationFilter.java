package kr.taeu.handa.global.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Claims;

/*
 * UsernamePasswordAuthenticationFilter 전에 등록되는 필터
 * 로그인 정보를 인터셉트하여...
 */
public class JwtAuthenticationFilter extends GenericFilterBean {
	private final JwtTokenProvider jwtTokenProvider;
	
	
	public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	/*
	 * Request로 들어오는 Jwt Token의 유효성을 검증(jwtTokenProvider.validateToken)하는 filter를 filterChain에 등록합니다.
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String token = jwtTokenProvider.parseToken((HttpServletRequest)request);
		
		if(token != null && jwtTokenProvider.validateToken(token)) {
			Authentication auth = this.getAuthentication(token);
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
		chain.doFilter(request, response);
	}
	
//	private Authentication getAuthentication(String token) {
//		UserDetails userDetails = userDetailsService.loadUserByUsername(jwtTokenProvider.getMemberInfo(token));
//		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
//	}
	
	private Authentication getAuthentication(String token) {
		/*
		 * Authenticaion을 얻어오기 위해서는 토큰을 까야함
		 * 토큰을 까서 해당 정보로 DB에 갖다와서 Role을 가져와야한다...?
		 */
		Claims c = jwtTokenProvider.getClaims(token);
		
		return new UsernamePasswordAuthenticationToken(c, null);
	}
}
