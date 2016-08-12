package com.sbs.internetbanking.springconfig;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.sbs.internetbanking.auth.SBSAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SpringWebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Autowired
	@Qualifier("authenticationSuccessHandler")
	SBSAuthenticationSuccessHandler authenticationSuccessHandler;
	
	@Autowired
	@Qualifier("authenticationProvider")
	AuthenticationProvider authenticationProvider;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.authenticationProvider(authenticationProvider).userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
		}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		 CharacterEncodingFilter filter = new CharacterEncodingFilter();
	        filter.setEncoding("UTF-8");
	        filter.setForceEncoding(true);
	        http.addFilterBefore(filter,CsrfFilter.class);
		
		http
		.requiresChannel().anyRequest().requiresSecure()
		    .and()
		.exceptionHandling()
			.accessDeniedPage("/403")
			.and()
	    .authorizeRequests()
	    	.antMatchers("/forgotPassword").permitAll()
	    	.antMatchers("/signup").permitAll()
	    	.antMatchers("/getUser").permitAll()
	    	.antMatchers("/getEmail").permitAll()
	    	.antMatchers("/getSecurityAnswer").permitAll()
	    	.antMatchers("/getOTP").permitAll()
	    	.antMatchers("/register").permitAll()
	    	.antMatchers("/getStates").permitAll()
	    	.antMatchers("/getSecurityQuestions").permitAll()
	    	.antMatchers("/getUserSecQuestions").permitAll()
	    	.antMatchers("/changePassword").permitAll()
	    	.antMatchers("/verifyOTPAndResetPassword").permitAll()
	    	.antMatchers("/verifyAnswersAndGenerateOTP").permitAll()
	    	.antMatchers("/about").permitAll()
	    	.antMatchers("/forgotPassword").permitAll()
	    	.antMatchers("/contactUs").permitAll()
	    	.antMatchers("/resources/**").permitAll()
	    	.antMatchers("/admin/**")
			.access("hasRole('ROLE_MANAGER')")
			.anyRequest().authenticated()
	    	.and()
		.formLogin().loginPage("/login").permitAll()
		    .usernameParameter("username")
		    .passwordParameter("password")
		    .defaultSuccessUrl( "/welcome" )
		    .successHandler(authenticationSuccessHandler)
		    .failureUrl("/login?error")
		    .and()
		.logout()
			.deleteCookies( "JSESSIONID" )
            .invalidateHttpSession( true )
		    .permitAll()
		    .and()
		 .csrf()
		;	
	}
}