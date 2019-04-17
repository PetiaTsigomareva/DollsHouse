package com.petia.dollhouse.web.interceptors;


import com.petia.dollhouse.web.annotations.PageTitle;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TitleInterceptor extends HandlerInterceptorAdapter {
    public static final String TITLE_LABLE = "title";
    public static final String TITLE = "Doll House Reservation System - ";

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView == null) {
            modelAndView = new ModelAndView();
        } else {
            if (handler instanceof HandlerMethod) {
                PageTitle methodAnnotation = ((HandlerMethod) handler).getMethodAnnotation(PageTitle.class);
                if (methodAnnotation != null) {
                    modelAndView
                            .addObject(TITLE_LABLE, TITLE + methodAnnotation.value());
                }
            }
        }
    }
}
