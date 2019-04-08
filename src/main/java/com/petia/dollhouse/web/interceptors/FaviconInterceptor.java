package com.petia.dollhouse.web.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class FaviconInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String link = "https://res.cloudinary.com/dollhouce-cloud/image/upload/v1554471285/dh_images/favicon.ico";

        if (modelAndView != null) {
            modelAndView.addObject("favicon", link);
        }
    }
}
