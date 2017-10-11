package com.ouyeel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

@Configuration
//@EnableWebMvc
public class MvcConfig {
    @Bean
    public FreeMarkerViewResolver freeMarkerViewResolver(){
        System.out.println("MvcConfig.freeMarkerViewResolver()");
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setPrefix("");
        resolver.setSuffix(".ftl");
        resolver.setContentType("text/html; charset=UTF-8");
        resolver.setRequestContextAttribute("request");
        return resolver;
    }
}
