package ar.edu.itba.webapp.config;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ar.edu.itba.webapp.auth.RestAuthenticationEntryPoint;
import ar.edu.itba.webapp.auth.ScrumlrUserDetailsService;
import ar.edu.itba.webapp.auth.hmac.AuthorizationHeaderHMACFilter;
import ar.edu.itba.webapp.auth.hmac.ScrumlrHMACTokenAuthenticationProvider;

import org.slf4j.Logger;

@Configuration
@EnableWebSecurity
@ComponentScan({"ar.edu.itba.webapp.auth"})
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    static Logger logger = LoggerFactory.getLogger(WebAuthConfig.class);
    
	@Autowired
	ScrumlrHMACTokenAuthenticationProvider hmacAuthProvider;
	
	@Autowired
	ScrumlrUserDetailsService userDetailsService;
	
	@Autowired
	RestAuthenticationEntryPoint authenticationEntrypoint;
	
	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		// Go full REST and disallow sessions. We no longer need RememberMe
		// or login forms. Or even /login and /logout because we use HMAC :)
		AuthenticationManager am = this.authenticationManagerBean();
		AuthorizationHeaderHMACFilter filter = new AuthorizationHeaderHMACFilter(am, authenticationEntrypoint);
		http
			.csrf()
				.disable()
                .headers()
                .frameOptions()
                	.disable()
            .and()
            	.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
            	.authenticationProvider(hmacAuthProvider)
            	.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and().authorizeRequests()
				.antMatchers("/user/new").anonymous()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/**").authenticated()
			.and().exceptionHandling()
				.authenticationEntryPoint(authenticationEntrypoint)
			;
		logger.debug("Spring Security configured, up 'n running");
	}
	
	@Override
	public void configure(final WebSecurity http) throws Exception {
		http.ignoring()
			.antMatchers("/favicon.ico", "/401", "/403", "/500", "/404", "/error/**");
	}
}
