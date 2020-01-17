package kr.taeu.handa.global.config.security;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {
	private String secretKey = "taeu";
	private long tokenValidMilisecond = 1000L * 60 * 60; // 1시간만 유효

	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}
	
	// Jwt 생성
	public String createToken(String userPk, List<String> roles) {
		Claims claims = Jwts.claims().setSubject(userPk);
		claims.put("roles", roles);
		LocalDateTime currentTime = LocalDateTime.now();
		
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
				.setExpiration(Date.from(currentTime
						.plusMinutes(tokenValidMilisecond)
						.atZone(ZoneId.systemDefault())
						.toInstant()))
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();
	}
	
	// Jwt의 유효성 + 만료일자 확인
	public boolean validateToken(String jwtToken) {
		LocalDateTime currentTime = LocalDateTime.now();
		Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);

		boolean isExpire = claims.getBody().getExpiration().before(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()));
		
		return isExpire;
	}
	
	// request header에서 Jwt 파싱
	public String parseToken(HttpServletRequest request) {
		return request.getHeader("X-JWT");
	}
	
	// Jwt에서 식별정보 추출
	public String getMemberInfo(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}
}
