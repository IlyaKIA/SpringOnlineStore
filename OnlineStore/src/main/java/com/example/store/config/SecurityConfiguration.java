package com.example.store.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import javax.sql.DataSource;


@EnableWebSecurity
@Slf4j
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("Jdbc Authentication Manager");
        http.authorizeRequests()
            .antMatchers(HttpMethod.POST,"/api/**").hasAnyRole("ADMIN")
            .antMatchers("/auth/list/**").hasAnyRole("ADMIN")
            .antMatchers("/auth/enable/**").hasAnyRole("ADMIN")
            .antMatchers("/shop/add-product/**").hasAnyRole("ADMIN")
            .antMatchers("/shop/edit-product/**").hasAnyRole("ADMIN")
            .anyRequest().permitAll()
        .and()
            .formLogin()
            .defaultSuccessUrl("/shop")
        .and()
            .logout();
    }

    @Bean
    public JdbcUserDetailsManager users(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
