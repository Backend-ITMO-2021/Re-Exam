package com.chatty.oauth;

import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import com.chatty.service.UserService;
import org.springframework.stereotype.Component;


@Configuration
@EnableWebSecurity
public class GoogleLoginConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .formLogin().disable()
            .httpBasic().disable()
            .logout().disable()
            .csrf().disable()
            .headers()
                .frameOptions()
                .disable()
            .and()
            .authorizeRequests()
                .antMatchers("/webjars/**", "/h2/**").permitAll()
                .anyRequest().authenticated()
                .and()
            .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
            .oauth2Login()
//                .userInfoEndpoint()
//                    .userService(customOAuth2UserService)
//                .and()
                .successHandler(customAuthenticationSuccessHandler)
        ;
    }

    @Component
    public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
                Map<String, Object> attributes = oidcUser.getAttributes();
                String email = (String) attributes.get("email");
                String name = (String) attributes.get("name");
                userService.processOAuthPostLogin(email, name);
                httpServletResponse.sendRedirect("http://localhost:8080/authorized");
            }
    }
}
