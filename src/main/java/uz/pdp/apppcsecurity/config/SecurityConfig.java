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


/*
*
* @Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {



     * huquqlarni berish
     *

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {


        *  auth
                .inMemoryAuthentication()
                .withUser("director").password(passwordEncoder().encode("director")).roles("DIRECTOR")  //hamaa ishni qilaoladi
                .and()
                .withUser("manager").password(passwordEncoder().encode("manager")).roles("MANAGER") //faqat ko'ra oladi
                .and()
                .withUser("worker").password(passwordEncoder().encode("worker")).roles("WORKER"); //hech ish qila olmaydi



        auth
                .inMemoryAuthentication()
                .withUser("director").password(passwordEncoder().encode("director")).roles("DIRECTOR").authorities(
                        "GET_ALL_PRODUCT","ADD_PRODUCT", "EDITE_PRODUCT","DELETE_PRODUCT", "GET_ONE_PRODUCT") //hamaa ishni qilaoladi
                .and()
                .withUser("manager").password(passwordEncoder().encode("manager")).roles("DIRECTOR").authorities(
                        "GET_ALL_PRODUCT","ADD_PRODUCT", "EDITE_PRODUCT", "GET_ONE_PRODUCT") //faqat ko'ra oladi
                .and()
                .withUser("worker").password(passwordEncoder().encode("worker")).roles("WORKER"); //hech ish qila olmaydi


    }


     * xavfsizlikni basic orqali taminlash
     *
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests()
//                .antMatchers(HttpMethod.DELETE,"/api/product/*").hasAuthority("DELETE_PRODUCT")
//                .antMatchers("/api/product/**").hasAnyAuthority("GET_ALL_PRODUCT","ADD_PRODUCT", "EDITE_PRODUCT","DELETE_PRODUCT", "GET_ONE_PRODUCT")
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();


        *  http
                .csrf().disable()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
        *

    }


     * Parollarni yasash uchun
     *

    /@/Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}



 * //                //.antMatchers(HttpMethod.GET, "/api/product/*").hasAnyRole("DIRECTOR", "MANAGER", "WORKER")
 * //                .antMatchers(HttpMethod.GET,"/api/product/*").hasRole("WORKER")
 * //                .antMatchers(HttpMethod.GET, "/api/product/**").hasAnyRole("DIRECTOR", "MANAGER") //role  =  MANAGER bo'lganlar faqat GET qilaoladi
 * //                .antMatchers("/api/product/**").hasRole("DIRECTOR") //faqat role  =  DIRECTOR bo'lganlar /api/product dan keyin hamma ishni qila olsin (**)
 * //

 */

