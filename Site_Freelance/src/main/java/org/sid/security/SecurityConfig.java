package org.sid.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("admin").password("{noop}123654").roles("ADMIN","USER");
		auth.inMemoryAuthentication().withUser("user").password("{noop}123654").roles("USER");
		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin();
		http.authorizeRequests().antMatchers("/freelancerPageSerach","/offres","/offremotclé","/particulierinscription","/freelancerinscription","/loginFreelancer","/connexionFreelancer","/loginParticulier","/connexionParticulier").hasRole("USER");
		http.authorizeRequests().antMatchers("/forgotPasswordFreelancerPage","/resetPasswordFreelancer","/forgotPasswordParticulierPage","/resetPasswordParticulierToPrfile").hasRole("ADMIN");
		
	}

}
