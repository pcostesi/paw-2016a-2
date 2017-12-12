package ar.edu.itba.webapp.config;

import ar.edu.itba.webapp.auth.RestAuthenticationEntryPoint;
import ar.edu.itba.webapp.auth.ScrumlrUserDetailsService;
import ar.edu.itba.webapp.auth.hmac.AuthorizationHeaderHMACFilter;
import ar.edu.itba.webapp.auth.hmac.ScrumlrHMACTokenAuthenticationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@ComponentScan( {"ar.edu.itba.webapp.auth"})
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(WebAuthConfig.class);

    @Autowired
    private
    ScrumlrHMACTokenAuthenticationProvider hmacAuthProvider;

    @Autowired
    ScrumlrUserDetailsService userDetailsService;

    @Autowired
    private
    RestAuthenticationEntryPoint authenticationEntrypoint;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        // Go full REST and disallow sessions. We no longer need RememberMe
        // or login forms. Or even /login and /logout because we use HMAC :)
        final AuthenticationManager am = this.authenticationManagerBean();
        final AuthorizationHeaderHMACFilter filter = new AuthorizationHeaderHMACFilter(am, authenticationEntrypoint);
        http
         .cors()
         .and().addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
         .authenticationProvider(hmacAuthProvider)
         .sessionManagement()
         .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
         .and().authorizeRequests()
         .antMatchers(HttpMethod.POST, "/api/user").permitAll()
         .antMatchers("/admin/**").hasRole("ADMIN")
         .antMatchers("/**").authenticated()
         .and().exceptionHandling()
         .authenticationEntryPoint(authenticationEntrypoint)
         .and().csrf()
         .disable()
        ;
        logger.debug("Spring Security configured, up 'n running");
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS"));
        configuration.addAllowedHeader("Authorization");
        configuration.addAllowedHeader("Content-Type");
        configuration.addAllowedHeader("Origin");
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    public void configure(final WebSecurity http) throws Exception {
        http.ignoring()
         .antMatchers("/favicon.ico", "/401", "/403", "/500", "/404", "/error/**");
    }
}
