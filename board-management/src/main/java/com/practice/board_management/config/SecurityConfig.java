package com.practice.board_management.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.board_management.controller.user.LoginFailureHandler;
import com.practice.board_management.controller.user.LoginSuccessJWTProvideHandler;
import com.practice.board_management.domain.users.UserRepository;
import com.practice.board_management.dto.user.JsonUsernamePasswordAuthenticationFilter;
import com.practice.board_management.dto.user.JwtAuthenticationProcessingFilter;
import com.practice.board_management.service.jwt.JwtService;
import com.practice.board_management.service.user.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    // 특정 HTTP 요청에 대한 웹 기반 보안 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http	.csrf(AbstractHttpConfigurer::disable)          //csrf 비활성화
                .httpBasic(AbstractHttpConfigurer::disable)     //브라우저 팝업 로그인창 비활성화
                .formLogin(AbstractHttpConfigurer::disable)     //스프링 기본 로그인 폼 비활성화
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(HttpMethod.GET, "/board/posts/*/comments").permitAll()
                        .requestMatchers("/board/users/signup",
                                "/board/users/login",
                                "/board/users/reissue",
                                "/board/users/me",
                                "/board/users/email-verification/send",
                                "/board/users/email-verification/confirm").permitAll() //이 URL들은 허용
                        .anyRequest().authenticated()) //나머지는 인증 필요
//				.formLogin(formLogin -> formLogin
//						.loginPage("/login")
//						.defaultSuccessUrl("/home"))
                .logout((logout) -> logout // 로그아웃시 이동할 URL 지정
                        .logoutSuccessUrl("/board/users/login")
                        .invalidateHttpSession(true))
                .sessionManagement(session -> session // 세션 생성 비활성화
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );
        http //커스텀 필터 등록
                .addFilterAfter(jsonUsernamePasswordLoginFilter(), LogoutFilter.class) //로그인 요청 처리 필터
                .addFilterBefore(jwtAuthenticationProcessingFilter(), JsonUsernamePasswordAuthenticationFilter.class); //모든 요청에서 JWT 검증 필요
        return http.build(); // 설정 완료 후 SecurityFilterChain 객체 반환
    }

    // 인증 관리자 관련 설정
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() throws Exception {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {//2 - AuthenticationManager 등록
        DaoAuthenticationProvider provider = daoAuthenticationProvider();//DaoAuthenticationProvider 사용
        return new ProviderManager(provider);
    }

    @Bean
    public LoginSuccessJWTProvideHandler loginSuccessJWTProvideHandler(){
        return new LoginSuccessJWTProvideHandler(jwtService, userRepository);
    }

    @Bean
    public LoginFailureHandler loginFailureHandler(){
        return new LoginFailureHandler();
    }

    @Bean
    public JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordLoginFilter() throws Exception {
        JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordLoginFilter = new JsonUsernamePasswordAuthenticationFilter(objectMapper);
        jsonUsernamePasswordLoginFilter.setAuthenticationManager(authenticationManager());
        jsonUsernamePasswordLoginFilter.setAuthenticationSuccessHandler(loginSuccessJWTProvideHandler());
        jsonUsernamePasswordLoginFilter.setAuthenticationFailureHandler(loginFailureHandler());
        return jsonUsernamePasswordLoginFilter;
    }

    @Bean
    public JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter(){
        JwtAuthenticationProcessingFilter jsonUsernamePasswordLoginFilter = new JwtAuthenticationProcessingFilter(jwtService, userRepository);

        return jsonUsernamePasswordLoginFilter;
    }

}