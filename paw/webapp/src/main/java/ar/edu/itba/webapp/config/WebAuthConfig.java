package ar.edu.itba.webapp.config;

import java.util.concurrent.TimeUnit;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import ar.edu.itba.webapp.auth.ScrumlrAuthenticationProvider;
import ar.edu.itba.webapp.auth.ScrumlrUserDetailsService;
import org.slf4j.Logger;

@Configuration
@EnableWebSecurity
@ComponentScan({"ar.edu.itba.webapp.auth"})
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    static Logger logger = LoggerFactory.getLogger(WebAuthConfig.class);
    
	@Autowired
	ScrumlrAuthenticationProvider authProvider;
	
	@Autowired
	ScrumlrUserDetailsService userDetailsService;
	
	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		
		http.authenticationProvider(authProvider)
			.userDetailsService(userDetailsService)
			.sessionManagement()
			.invalidSessionUrl("/login")
			.and().authorizeRequests()
				.antMatchers("/login").anonymous()
				.antMatchers("/**").authenticated()
				.antMatchers("/admin/**").hasRole("ADMIN")
			.and().formLogin()
				.usernameParameter("j_username")
				.passwordParameter("j_password")
				.defaultSuccessUrl("/", false)
			.and().logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login")
			.and().exceptionHandling()
				.accessDeniedPage("/403")
			.and().rememberMe()
				.rememberMeParameter("j_rememberme")
				.key("this shouldn't be under version control, 12factorize it!")
				.tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(60))
				.userDetailsService(userDetailsService)
			.and().csrf()
				.disable()
			;
		logger.debug("Spring Security configured, up 'n running");
	}
	
	@Override
	public void configure(final WebSecurity http) throws Exception {
		http.ignoring()
			.antMatchers("/styles/**", "/scripts/**", "/images/**", "/favicon.ico", "/403");
	}
}
