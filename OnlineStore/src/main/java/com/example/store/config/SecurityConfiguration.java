package com.example.store.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
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
                        .antMatchers("/shop/**").authenticated()
                        .antMatchers(HttpMethod.POST,"/api/**").hasAnyRole("ADMIN")
                        .antMatchers("/admin/**").hasAnyRole("ADMIN")
                        .anyRequest().permitAll()
                    .and()
                        .formLogin();
        }

        @Bean
        public JdbcUserDetailsManager users(DataSource dataSource) {
            return new JdbcUserDetailsManager(dataSource);
        }

        @Bean
        public BCryptPasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }


//    @Configuration
//    @EnableWebSecurity
//    @EnableGlobalMethodSecurity(prePostEnabled = true)
//    public class SecurityConfig extends WebSecurityConfigurerAdapter {
//        private DataSource dataSource;
//
//        @Autowired
//        public void setDataSource(DataSource dataSource) {
//            this.dataSource = dataSource;
//        }
//
//        @Override
//        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//            auth.jdbcAuthentication().dataSource(dataSource);
//        }
//
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//            http.authorizeRequests()
//                    .antMatchers("/").hasAnyRole("USER")
//                    .antMatchers("/admin/**").hasRole("ADMIN")
//                    .and()
//                    .formLogin()
//                    .loginPage("/login")
//                    .loginProcessingUrl("/authenticateTheUser")
//                    .permitAll();
//        }
//    }
}
