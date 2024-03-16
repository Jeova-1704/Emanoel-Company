package br.com.emanoelCompany.corp.services.authService;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class LoginInterceptorConfig implements WebMvcConfigurer, HandlerInterceptor {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .excludePathPatterns(
                        "/login",
                        "/error",
                        "/images/**",
                        "/img/**",
                        "/vendor/**",
                        "/js/**",
                        "/css/**"
                );
    }

}
