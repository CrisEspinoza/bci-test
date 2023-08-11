package bci.test.bcitest;

import bci.test.bcitest.configuration.JWTAuthorizationFilter;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.h2.tools.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.sql.SQLException;

@SpringBootApplication
@EnableEncryptableProperties
public class BCITestApplication {

	public static void main(String[] args) {
		SpringApplication.run(BCITestApplication.class, args);
	}

	@Bean(initMethod = "start", destroyMethod = "stop")
	public Server h2Server() throws SQLException {
		return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
	}

	@EnableWebSecurity
	@Configuration
	static
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
					.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
					.authorizeRequests()
					.antMatchers(HttpMethod.POST, "/api/v1/user").permitAll()
					.antMatchers(HttpMethod.GET, "/api/v1/user/login").permitAll()
					.antMatchers("/v2/api-docs",
							"/configuration/ui",
							"/swagger-resources/**",
							"/configuration/security",
							"/swagger-ui.html",
							"/webjars/**").permitAll()
					.anyRequest().authenticated();
		}
	}

}
