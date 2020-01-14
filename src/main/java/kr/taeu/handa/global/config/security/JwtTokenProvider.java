package kr.taeu.handa.global.config.security;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {
	
	@Value("spring.jwt.secret")
	private String secretKey;
	
	private long tokenValidMilisecond = 1000L * 60 * 60; // 1시간만 유효
	
	private final UserDetailsService userDetailsService;
	
	public JwtTokenProvider(long tokenValidMilisecond, UserDetailsService userDetailsService) {
		this.tokenValidMilisecond = tokenValidMilisecond;
		this.userDetailsService = userDetailsService;
	}

	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}
	
	// Jwt 토큰 생성
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
}
