package kr.taeu.handa.global.config;

import javax.sql.DataSource;

import org.h2.server.web.WebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class AppConfig implements WebMvcConfigurer {
	private DataSource dataSource;
	
	public AppConfig(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Bean
	public JdbcTemplate getJdbcTemplate() {
		log.info("get jdbcTemplate...");
		return new JdbcTemplate(dataSource);
	}
	
	@Bean
	public ServletRegistrationBean<WebServlet> h2servletRegistration() {
		ServletRegistrationBean<WebServlet> registrationBean = new ServletRegistrationBean<WebServlet>(new WebServlet());
		registrationBean.addUrlMappings("/h2-console/*");
		return registrationBean;
	}
}
