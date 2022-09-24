package com.algafoodapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    //Habilitar CORS globalmente no projeto API

    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
               // .allowedOrigins("*");
                 .allowedMethods("*");
               // .maxAge(30)
    }

    //Aplicando Etag na aplicação
    @Bean
    public Filter shallowEtagHeaderFiter(){
        return new ShallowEtagHeaderFilter();

    }
}