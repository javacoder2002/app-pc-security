package uz.pdp.apppcsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth
                .inMemoryAuthentication()
                .withUser("super_admin").password(passwordEncoder().encode("1")).roles("SUPER_ADMIN")
                .authorities("GET_ALL_COMPUTERS", "GET_ONE_COMPUTER", "ADD_COMPUTER", "EDITE_COMPUTER", "DELETE_COMPUTER")
                .and()
                .withUser("moderator").password(passwordEncoder().encode("2")).roles("MODERATOR")
                .authorities("ADD_COMPUTER", "EDITE_COMPUTER")
                .and()
                .withUser("operator").password(passwordEncoder().encode("3")).roles("OPERATOR")
                .authorities("GET_ALL_COMPUTERS", "GET_ONE_COMPUTER", "ADD_COMPUTER");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}




