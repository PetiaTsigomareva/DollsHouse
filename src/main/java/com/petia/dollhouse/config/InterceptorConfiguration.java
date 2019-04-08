package com.petia.dollhouse.config;


import com.petia.dollhouse.web.interceptors.FaviconInterceptor;
import com.petia.dollhouse.web.interceptors.TitleInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.petia.dollhouse.web.interceptors.AuthorizationInterceptor;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {
    private final AuthorizationInterceptor authorizationInterceptor;
    private final TitleInterceptor titleInterceptor;
    private final FaviconInterceptor faviconInterceptor;

    @Autowired
    public InterceptorConfiguration(AuthorizationInterceptor authorizationInterceptor, TitleInterceptor titleInterceptor, FaviconInterceptor faviconInterceptor) {
        this.authorizationInterceptor = authorizationInterceptor;
        this.titleInterceptor = titleInterceptor;
        this.faviconInterceptor = faviconInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //TODO
        //registry.addInterceptor(this.authorizationInterceptor);
        registry.addInterceptor(this.titleInterceptor);
        registry.addInterceptor(this.faviconInterceptor);
    }
}
