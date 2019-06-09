package desafio.pitang.seguranca;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable().authorizeRequests().antMatchers("/h2-console/**").permitAll()
				.antMatchers("/signup").permitAll().antMatchers("/signin").permitAll().antMatchers("/me").permitAll()
				.anyRequest().authenticated();

		httpSecurity.headers().frameOptions().disable();
	}

}