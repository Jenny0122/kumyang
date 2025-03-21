package com.wisenut.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
public class KumYangSearchApplication extends SpringBootServletInitializer {

    public static void main( String[] args ) {
        SpringApplication.run( com.wisenut.spring.KumYangSearchApplication.class , args );
    }

    @Override
    protected SpringApplicationBuilder configure( SpringApplicationBuilder application ) {

        return application.sources( com.wisenut.spring.KumYangSearchApplication.class );
    }

    @Bean
    public WebMvcConfigurer corsConfigurer( ) {
        return new WebMvcConfigurer( ) {
            @Override
            public void addCorsMappings( CorsRegistry registry ) {
                registry.addMapping( "/" )
                        .allowedOrigins( "*" );
            }
        };
    }

}