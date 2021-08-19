package com.trungtamjava;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

import com.trungtamjava.entity.User;
import com.trungtamjava.security.JwtTokenFilter;
import com.trungtamjava.security.JwtTokenProvider;
import com.trungtamjava.service.impl.AuditorAwareImpl;
import com.trungtamjava.ultil.RoleEnum;

@SpringBootApplication
public class TicKetProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicKetProjectApplication.class, args);
	}
	
	@Autowired
	private UserDetailsService userDetailsService;
	
    //
	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Configuration
	@Order(1)
	public class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

		@Bean
		@Override
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return super.authenticationManagerBean();
		}
		
        //
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.cors().and().csrf().disable();

			// No session will be created or used by spring security
			http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

			http.antMatcher("/api/**").authorizeRequests().antMatchers("/api/admin/**")
					.hasAnyRole(RoleEnum.ADMIN.getRoleName()).antMatchers("/api/member/**").authenticated().anyRequest()
					.permitAll().and().httpBasic();
			// Apply JWT
			http.addFilterBefore(new JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
		}
	}


	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
		return bCryptPasswordEncoder;
	}

	@Bean
	public AuditorAware<User> auditorAware() {
		return new AuditorAwareImpl();
	}

	@Bean
	public SpringSecurityDialect springSecurityDialect() {
		SpringSecurityDialect dialect = new SpringSecurityDialect();
		return dialect;
	}
	
    //
	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}
	    
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
		configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

}
