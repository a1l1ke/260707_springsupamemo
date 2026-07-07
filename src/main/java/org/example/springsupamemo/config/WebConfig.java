package org.example.springsupamemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration // Bean 등록 -> viewResolver
@EnableWebMvc
@ComponentScan(basePackages = "org.example.springsupamemo")
public class WebConfig implements WebMvcConfigurer {
    // 1. ViewResolver -> Controller -> String(return) -> 해당 문구를 뷰와 연결
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/"); // 접두사
        resolver.setSuffix(".jsp"); // 접미사
        return resolver;
    }

    // 2. 리소스 연결
    // webapp 내부의 경로에 assets라는 폴더를 연결하여 css, js, 파일들을 제공
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/assets/**")
                .addResourceLocations("/assets/");
        System.out.println("WebConfig.addResourceHandlers");
    }
}
