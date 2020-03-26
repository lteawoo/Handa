package kr.taeu.handa.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import kr.taeu.handa.domain.member.service.MemberDetailsService;
import kr.taeu.handa.global.security.CustomAuthenticationProvider;
import kr.taeu.handa.global.security.rest.RestAuthenticationEntryPoint;
import kr.taeu.handa.global.security.rest.RestAuthenticationFailuerHandler;
import kr.taeu.handa.global.security.rest.RestAuthenticationSuccessHandler;
import kr.taeu.handa.global.security.rest.filter.RestAuthenticationFilter;
import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final MemberDetailsService memberDetailsService;
	private final PasswordEncoder passwordEncoder;
	
	@Bean
	public AuthenticationProvider customAuthenticationProvider() {
		CustomAuthenticationProvider provider = new CustomAuthenticationProvider(memberDetailsService, passwordEncoder);
		return provider;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder builder) throws Exception {
		builder.authenticationProvider(customAuthenticationProvider());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
	    return super.authenticationManagerBean();
	}
	
	@Bean
	public RestAuthenticationFilter restAuthenticationFilter() throws Exception {
		RestAuthenticationFilter restAuthenticationFilter = new RestAuthenticationFilter(
				new AntPathRequestMatcher("/member/signin", HttpMethod.POST.name()));
		restAuthenticationFilter.setAuthenticationManager(this.authenticationManager());
		restAuthenticationFilter.setAuthenticationFailureHandler(new RestAuthenticationFailuerHandler());
		restAuthenticationFilter.setAuthenticationSuccessHandler(new RestAuthenticationSuccessHandler());
		return restAuthenticationFilter;
	}

	/*
	 * CORS 설정
	 */
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedOrigin("*");
		configuration.addAllowedMethod("*");
		configuration.addAllowedHeader("*");
		configuration.setAllowCredentials(true);
		configuration.setMaxAge(3600L);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
//				.antMatchers("/member/signup", "/member/signin").anonymous()
//				.antMatchers("/member/test").permitAll()
				.antMatchers("/api/**").authenticated()
			.and()
				.cors()
			.and()
				.exceptionHandling().authenticationEntryPoint(new RestAuthenticationEntryPoint())
			.and()
				.formLogin().disable()
				.csrf().disable()
				.httpBasic().disable();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
			.antMatchers("/v2/api-docs",
					"/configuration/ui",
					"/swagger-resources/**",
					"/configuration/security",
					"/swagger-ui.html",
					"/webjars/**",
					"/h2-console/**",
					"/js/**");
	}
}
