package kr.taeu.handa.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import kr.taeu.handa.global.security.rest.RestAuthenticationEntryPoint;
import kr.taeu.handa.global.security.rest.RestAuthenticationFailuerHandler;
import kr.taeu.handa.global.security.rest.RestAuthenticationSuccessHandler;
import kr.taeu.handa.global.security.rest.filter.RestAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public RestAuthenticationFilter restAuthenticationFilter() throws Exception {
		RestAuthenticationFilter restAuthenticationFilter = new RestAuthenticationFilter(
				new AntPathRequestMatcher("/member/signin", HttpMethod.POST.name()));
		restAuthenticationFilter.setAuthenticationManager(this.authenticationManager());
		restAuthenticationFilter.setAuthenticationFailureHandler(new RestAuthenticationFailuerHandler());
		restAuthenticationFilter.setAuthenticationSuccessHandler(new RestAuthenticationSuccessHandler());
		return restAuthenticationFilter;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/member/signup", "/member/signin").anonymous()
				.anyRequest().authenticated()
			.and()
				.exceptionHandling().authenticationEntryPoint(new RestAuthenticationEntryPoint())
			.and()
				.formLogin().disable()
				.csrf().disable();
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
