package org.aleksa.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AdminAuth adminAuth;
    private final UserAuth userAuth;

    @Autowired
    public WebConfig(AdminAuth adminAuth, UserAuth userAuth) {
        this.adminAuth = adminAuth;
        this.userAuth = userAuth;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Exclude auth endpoints from all interceptors
        String[] publicEndpoints = {
            "/api/auth/**", 
            "/user/register", 
            "/user/login", 
            "/screening/**",
            "/api/screening/**", 
            "/api/movies/latest",
            "/api/movies/top-rated",
            "/api/movies/**",
            "/api/review/movie/**"
        };
        
        // Admin only endpoints
        registry
                .addInterceptor(adminAuth)
                .addPathPatterns("/user/all")
                .addPathPatterns("/**/add")
                .addPathPatterns("/**/update/**")
                .addPathPatterns("/**/delete/**")
                .addPathPatterns("/reservation/all")
                .excludePathPatterns(publicEndpoints);

        // Authenticated user endpoints
        registry.addInterceptor(userAuth)
                .addPathPatterns("/review/**")
                .addPathPatterns("/watch/**")
                .addPathPatterns("/reservation/**")
                .addPathPatterns("/user/profile")
                .excludePathPatterns("/review/movie/**")
                .excludePathPatterns("/reservation/all")
                .excludePathPatterns(publicEndpoints);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5500")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
