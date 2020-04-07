package org.sid.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	@Configuration
	@Order(1)
	public static class App1ConfigurationAdapter extends WebSecurityConfigurerAdapter {
		public App1ConfigurationAdapter() {
			super();
		}

		@Autowired
		private DataSource dataSource;

		@Bean
		public PasswordEncoder passwordEncoder() {
			return new org.sid.security.PasswordEncoder();
		}

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.jdbcAuthentication().dataSource(dataSource)
					.usersByUsernameQuery(
							"select email as principal, password as credentials, 1 from freelancer where email=?")
					.authoritiesByUsernameQuery(
							"select email as principal, 'FREELANCER' as role from freelancer where email=?")
					.rolePrefix("ROLE_").passwordEncoder(passwordEncoder());
		}

		@Bean
		public static PasswordEncoder encoder() {
			return new BCryptPasswordEncoder();
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable();
			http.antMatcher("/AA*").authorizeRequests().anyRequest().hasRole("FREELANCER");
			;
			http.formLogin().loginPage("/AAloginFreelancer").permitAll()
					.failureUrl("/AAloginErrorFreelancer?error=loginError")
					.successForwardUrl("/AAconnexionSuccessFreelancer");
			http.logout().logoutUrl("/AAlogout_Freelancer").logoutSuccessUrl("/").deleteCookies("JSESSIONID");
			http.exceptionHandling().accessDeniedPage("/403");
		}

		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/resources/static/**");
		}
	}

	@Configuration
	@Order(2)
	public static class App2ConfigurationAdapter extends WebSecurityConfigurerAdapter {
		public App2ConfigurationAdapter() {
			super();
		}

		@Autowired
		private DataSource dataSource;

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.jdbcAuthentication().dataSource(dataSource)
					.usersByUsernameQuery(
							"select email as principal, password as credentials, 1 from particulier where email=?")
					.authoritiesByUsernameQuery(
							"select email as principal, 'PARTICULIER' as role from particulier where email=?")
					.rolePrefix("ROLE_").passwordEncoder(new org.sid.security.PasswordEncoder());
		}

		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/resources/static/**");
		}

		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable();
			http.antMatcher("/BB*").authorizeRequests().anyRequest().hasRole("PARTICULIER");
			;
			http.formLogin().loginPage("/BBloginParticulier").permitAll()
					.failureUrl("/BBloginErrorParticulier?error=loginError")
					.successForwardUrl("/BBconnexionSuccessParticulier");
			http.logout().logoutUrl("/BBlogout_Particulier").logoutSuccessUrl("/").deleteCookies("JSESSIONID");
			http.exceptionHandling().accessDeniedPage("/403");
		}
	}
}