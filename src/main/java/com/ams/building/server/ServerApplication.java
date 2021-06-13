package com.ams.building.server;

import com.ams.building.server.bean.Account;
import com.ams.building.server.constant.RoleEnum;
import com.ams.building.server.exception.CustomAccessDeniedHandler;
import com.ams.building.server.sercurity.JwtTokenFilter;
import com.ams.building.server.sercurity.JwtTokenProvider;
import com.ams.building.server.service.impl.AuditorAwareImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableJpaRepositories
@EnableAsync
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @Autowired
    @Qualifier("accountServiceImpl")
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
        return bCryptPasswordEncoder;
    }

    @Configuration
    public class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.cors().and().csrf().disable();
            http.antMatcher("/api/**").
                    authorizeRequests().antMatchers("/api/admin/**")
                    .hasAnyAuthority(RoleEnum.ROLE_ADMIN.name())
                    .antMatchers("/api/manager-service/**")
                    .hasAnyAuthority(RoleEnum.ROLE_ADMIN.name(), RoleEnum.ROLE_MANAGER_SERVICE.name())
                    .antMatchers("/api/landlord/**")
                    .hasAnyAuthority(RoleEnum.ROLE_ADMIN.name(), RoleEnum.ROLE_MANAGER_SERVICE.name(), RoleEnum.ROLE_LANDLORD.name())
                    .antMatchers("/api/employee/**")
                    .hasAnyAuthority(RoleEnum.ROLE_ADMIN.name(), RoleEnum.ROLE_MANAGER_SERVICE.name(), RoleEnum.ROLE_EMPLOYEE.name())
                    .antMatchers("/api/tenant/**")
                    .hasAnyAuthority(RoleEnum.ROLE_ADMIN.name(), RoleEnum.ROLE_MANAGER_SERVICE.name(), RoleEnum.ROLE_EMPLOYEE.name(),
                            RoleEnum.ROLE_LANDLORD.name(), RoleEnum.ROLE_TENANT.name())
                    .antMatchers("/api/member/**").authenticated().anyRequest().permitAll();
            http.addFilterBefore(new JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        }
    }

    @Bean
    public AuditorAware<Account> auditorAware() {
        return new AuditorAwareImpl();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

}
