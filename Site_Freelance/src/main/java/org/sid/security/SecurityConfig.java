package org.sid.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.Md4PasswordEncoder;
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    @Autowired
	private DataSource dataSource;
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery("select name as principal,password as credentials from FREELANCER where name=?").authoritiesByUsernameQuery("select name as principal , role as role from users_role where name=?").rolePrefix("Role_").passwordEncoder(new Md4PasswordEncoder());	
		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//http.formLogin();
		//http.authorizeRequests().antMatchers("/freelancerPageSerach","/offres","/offremotclé","/particulierinscription","/freelancerinscription","/loginFreelancer","/connexionFreelancer","/loginParticulier","/connexionParticulier").hasRole("USER");
		http.authorizeRequests().antMatchers("/forgotPasswordFreelancerPage","/resetPasswordFreelancer","/forgotPasswordParticulierPage","/resetPasswordParticulierToPrfile").hasRole("FREELANCER");
		http.exceptionHandling().accessDeniedPage("/403");
	}

}
