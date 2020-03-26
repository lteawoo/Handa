package kr.taeu.handa.global.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	private final static String REALM="TAEU_REALM"; 
	private final DataSource dataSource;
	private final AuthenticationManager authenticationManager;
	private final UserDetailsService memberDetailsService;
	private final PasswordEncoder passwordEncoder;
	
	/*
	 * jwt 인증키 설정
	 * 이 키는 Auth server와 Resource server가 동일해야함.
	 */
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey("taeu_key");
		return converter;
	}
	
	/*
	 * 토큰 스토어 JwtTokenStore 사용
	 * jwt를 사용하므로 토큰 정보를 토큰 자체에서 관리함 -> DB에 토큰 정보관리 불필요
	 */
	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}
	
	@Bean
	public JdbcApprovalStore approvalStore() {
		return new JdbcApprovalStore(dataSource);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		/* oauth cors 대응 */
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedOrigin("*");
		configuration.addAllowedMethod("*");
		configuration.addAllowedHeader("*");
		configuration.setAllowCredentials(true);
		configuration.setMaxAge(3600L);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/oauth/**", configuration);
		CorsFilter corsFilter = new CorsFilter(source);
		security.addTokenEndpointAuthenticationFilter(corsFilter);
		
		security.realm(REALM + "/client");
		security.tokenKeyAccess("permitAll()");
		security.checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
			.withClient("taeu_client")
			.authorizedGrantTypes("authorization_code", "password", "refresh_token", "client_credentials")
			.scopes("read", "write")
			.secret(passwordEncoder.encode("taeu_secret"))
			.accessTokenValiditySeconds(120)
			.refreshTokenValiditySeconds(600);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.accessTokenConverter(accessTokenConverter())
			.tokenStore(tokenStore())
			.approvalStore(approvalStore())
			.authenticationManager(authenticationManager)
			.userDetailsService(memberDetailsService);
	}
	
}
