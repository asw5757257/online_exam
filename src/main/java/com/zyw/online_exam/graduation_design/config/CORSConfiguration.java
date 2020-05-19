package com.zyw.online_exam.graduation_design.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/25 14:54
 */
@Configuration
public class CORSConfiguration implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //所有请求都允许跨域
        registry.addMapping("/**")
                         .allowedOrigins("*")
                         .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                         .maxAge(3600)
                        .allowCredentials(true);
    }
}
