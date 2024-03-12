package io.security.basicsecurity;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

// 스프링 부트 2.7 이상, 스프링 시큐리티 5.7.0 이상 -> deprecated (추후 지원X, 삭제예정)
// 스프링 부트 3.0 이상, 스프링 시큐리티6에서는 WebSecurityConfigurerAdapter 클래스가 제거됨.
@Configuration
@EnableWebSecurity
public class SecurtiyConfig /*extends WebSecurityConfigurerAdapter*/ {

    /**
     * 사용자 라이프 사이클
     * 1. 인증
     * 1-1. 인증 성공 (Remember-Me 쿠키 설정)
     * 1-2. 인증 실패 (쿠키가 존재하면 쿠키 무효화)
     * 2. 로그아웃 (쿠키가 존재하면 쿠키 무효화
     */

    @Autowired
    UserDetailsService userDetailsService;

    @SuppressWarnings("sadsa")
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 인가 정책
        http.authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/**").permitAll()
                        .anyRequest().authenticated() // 어떠한 요청도 인증됨
        );

        // 인증 정책
        http.formLogin(formLogin -> formLogin
//                .loginPage("/loginPage")                      // 사용자정의 로그인 페이지
                .defaultSuccessUrl("/home")                   // 로그인 성공 후 이동 페이지
                .failureUrl("/login") // 로그인 실패 후 이동 페이지
                .usernameParameter("userId")                  // 아이디 파라미터명 설정
                .passwordParameter("password")                // 비밀번호 파라미터명 설정
                .loginProcessingUrl("/login_proc")            // 로그인 Form Action Url
                .successHandler((request, response, authentication) -> { // 로그인 성공 후 핸들러
                    System.out.println("authentication-login: " + authentication.getName());
                    response.sendRedirect("/");
                })
                .failureHandler((request, response, exception) -> { // 로그인 실패 후 핸들러
                    System.out.println("exception: " + exception.getMessage());
                    response.sendRedirect("/login");
                })
//                .permitAll() // "/loginPage" -> 모든 접근 허가
        );
        // 로그아웃 정책
        http.logout(logout -> logout
                .logoutUrl("/logout")       // 로그아웃 처리 URL
                .logoutSuccessUrl("/login") // 로그아웃 성공 후 이동페이지
                .addLogoutHandler((request, response, authentication) -> { // 로그아웃 핸들러
                    HttpSession session = request.getSession();
                    session.invalidate(); //세션 무효화
                })
                .logoutSuccessHandler((request, response, authentication) -> { // 로그아웃 성공 후 핸들러
                    System.out.println("authentication-logout: " + authentication.getName());
                    response.sendRedirect("/login");
                })
                .deleteCookies("remember-me") // 로그아웃 후 쿠키삭제
        );
        // remember 정책
        http.rememberMe(httpSecurityRememberMeConfigurer -> httpSecurityRememberMeConfigurer
                .rememberMeParameter("remember") // 기본 파라미터명은 remember-me
                .tokenValiditySeconds(3600)      // 1시간 (기본 14일)
//                .alwaysRemember(true)            // 기능이 활성화 되지 않아도 항상 실행
                .userDetailsService(userDetailsService)
        );

        return http.build();
    }

//    // 스프링 시큐리티 6.1 이하 버전
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        // 인가 정책
//        http
//                .authorizeHttpRequests()
//                .anyRequest().authenticated(); // 어떠한 요청도 인증됨
//
//        // 인증 정책
//        http
//                .formLogin() // 인증 방식: formLogin 방식
//                .defaultSuccessUrl("/home")                   // 로그인 성공 후 이동 페이지
//                .failureUrl("/login") // 로그인 실패 후 이동 페이지
//                .usernameParameter("userId")                  // 아이디 파라미터명 설정
//                .passwordParameter("password")                // 비밀번호 파라미터명 설정
//                .loginProcessingUrl("/login_proc")            // 로그인 Form Action Url
//                .successHandler(new AuthenticationSuccessHandler() { // 로그인 성공 후 핸들러
//                    @Override
//                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//                        System.out.println("authentication: " + authentication.getName());
//                        response.sendRedirect("/");
//                    }
//                })
//                .failureHandler(new AuthenticationFailureHandler() { // 로그인 실패 후 핸들러
//                    @Override
//                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//                        System.out.println("exception: " + exception.getMessage());
//                        response.sendRedirect("/login");
//                    }
//                });
//    }

}

