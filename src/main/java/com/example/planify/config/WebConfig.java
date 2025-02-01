package com.example.planify.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") //  모든 엔드포인트에 CORS 허용
                        .allowedOrigins("http://localhost:3000") //  프론트엔드 도메인 허용
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") //  허용할 HTTP 메서드
                        .allowedHeaders("*"); //  모든 헤더 허용
            }
        };
    }
}