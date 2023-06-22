package by.kislyakoff.HomeBudgetApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
		.authorizeHttpRequests(auth -> auth
				.antMatchers(HttpMethod.GET, "/webjars/**", "/css/**", "/js/**", "/login*").permitAll()
				.antMatchers("/homebudget/users/**").hasAnyAuthority("ADMIN")
				.anyRequest().authenticated())
		.formLogin(form -> form
				.loginPage("/login")
				.loginProcessingUrl("/process_login")
				.defaultSuccessUrl("/homebudget", true)
				.failureUrl("/login?error"))
		.csrf(csrf -> csrf.disable());
		return http.build();
	}
	
	@Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
	}
	
	@Bean
	PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
