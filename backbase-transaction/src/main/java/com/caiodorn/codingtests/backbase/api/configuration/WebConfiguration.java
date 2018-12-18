package com.caiodorn.codingtests.backbase.api.configuration;

import com.caiodorn.codingtests.backbase.api.authentication.AuthenticationFilter;
import com.caiodorn.codingtests.backbase.api.authentication.LoginFilter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@EnableWebSecurity
@Import({SwaggerConfiguration.class})
@ComponentScan(basePackages = "com.caiodorn.codingtests.backbase")
public class WebConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

        return objectMapper;
    }

    @Bean
    LoginFilter loginFilter() {
        return new LoginFilter();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                // uncomment the line below to get security out of the way if needed. E.g.: where did decimal digits go?! Check via browser (address bar) to find out ;)
                //.antMatchers(HttpMethod.GET, "/current-accounts/**").permitAll()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security", "/swagger-ui.html", "/webjars/**", "/swagger-resources/configuration/ui", "/swagger-resources/configuration/security").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(loginFilter(), UsernamePasswordAuthenticationFilter.class)
                //performs authentication via filter/in-memory user data instead of having a separate "LoginController" plus service
                .addFilterBefore(new AuthenticationFilter("/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class);

        httpSecurity.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.inMemoryAuthentication()
                .withUser("john doe")
                .password("{noop}1234")
                .roles("USER");
    }

}
