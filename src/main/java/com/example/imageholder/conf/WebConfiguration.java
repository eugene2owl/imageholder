package com.example.imageholder.conf;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

public class WebConfiguration extends WebMvcConfigurerAdapter {

    private static final int MAX_AGE = 3600;

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowedMethods("PUT", "DELETE", "GET", "POST", "OPTIONS")
                .allowCredentials(false).maxAge(MAX_AGE);
    }
}
