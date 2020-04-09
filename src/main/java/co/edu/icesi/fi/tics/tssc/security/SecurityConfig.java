package co.edu.icesi.fi.tics.tssc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private LoggingAccessDeniedHandler accessDeniedHandler;

	@Autowired
	private MyCustomAdminDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// auth.authenticationProvider(authenticationProvider());
		auth.userDetailsService(userDetailsService);
	}
	

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		
//		httpSecurity.formLogin().loginPage("/login").permitAll().and().authorizeRequests().antMatchers("/**").authenticated().anyRequest().permitAll().and().httpBasic().and().logout()
//		.invalidateHttpSession(true).clearAuthentication(true)
//		.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout")
//		.permitAll().and().exceptionHandling().accessDeniedHandler(accessDeniedHandler);
			

         httpSecurity.authorizeRequests().antMatchers("/**").authenticated().and().authorizeRequests()
        .antMatchers("/index").permitAll()
		.antMatchers("/game/**").hasRole("admin")
		.antMatchers("/story/**").hasRole("admin")
		.antMatchers("/**").hasRole("superadmin")

		
		.and()
		
		.formLogin().loginPage("/login").permitAll().and()
		.logout().invalidateHttpSession(true).clearAuthentication(true)
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout")
		.permitAll().and().exceptionHandling().accessDeniedPage("/error");
		
		
	}

}
