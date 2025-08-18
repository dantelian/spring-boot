package com.example.springbootsecurityjwt.config.sercurity;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 通过构造函数注入自定义UserDetailsService
    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/public").permitAll()
                        .anyRequest().authenticated()
                )
                .userDetailsService(userDetailsService)
//                .formLogin(Customizer.withDefaults());
                .formLogin(form -> form
                        .loginProcessingUrl("/login")
                        .successHandler(loginSuccessHandler())
                        .failureHandler(loginFailureHandler())
                        .permitAll())
//                .rememberMe(rememberMe -> rememberMe
//                        .key("mySecretKey") // 服务器端密钥
//                        .tokenValiditySeconds(7 * 24 * 60 * 60) // 7天有效期
//                        .userDetailsService(userDetailsService)) // 认证用户信息
                .csrf(csrf -> csrf.disable()); // 关闭csrf防护

        // 设置异常的EntryPoint的处理
        http.exceptionHandling(exceptions -> exceptions
                // 未登录
                .authenticationEntryPoint(new MyAuthenticationEntryPoint())
                // 权限不足
                .accessDeniedHandler(new MyAccessDeniedHandler()));

        return http.build();
    }

    // 自定义登录成功处理器
    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler() {
        return (request, response, authentication) -> {
            if (isAjaxRequest(request)) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"code\":200, \"message\":\"认证成功\"}");
            } else {
                response.sendRedirect("/");
            }
        };
    }

    // 自定义登录失败处理器
    @Bean
    public AuthenticationFailureHandler loginFailureHandler() {
        return (request, response, exception) -> {
            if (isAjaxRequest(request)) {
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"code\":401, \"message\":\"认证失败\"}");
            } else {
                response.sendRedirect("/login?error=true");
            }
        };
    }

    //判断是否ajax请求
    public boolean isAjaxRequest(HttpServletRequest request) {
        String xRequestedWith = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equals(xRequestedWith);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
