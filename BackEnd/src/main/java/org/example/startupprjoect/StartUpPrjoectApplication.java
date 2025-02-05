package org.example.startupprjoect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableAspectJAutoProxy
public class StartUpPrjoectApplication {

    public static void main(String[] args) {
        SpringApplication.run(StartUpPrjoectApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Changed from /api/** to /** to ensure all paths are covered
                        .allowedOrigins("http://localhost:3000", "http://localhost:3001", "http://localhost:9090")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "PATCH")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .exposedHeaders("Authorization")
                        .maxAge(3600L); // 1 hour cache for preflight requests
            }
        };
    }
}