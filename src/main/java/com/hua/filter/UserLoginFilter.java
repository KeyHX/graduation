package com.hua.filter;

import com.hua.utils.JWTUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = "/api/*")
public class UserLoginFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if(httpRequest.getRequestURI().contains("/api/login")){
            chain.doFilter(request,response);
            return;
        } else if (httpRequest.getRequestURI().contains("/api/register")) {
            chain.doFilter(request,response);
            return;
        }
        try {
            //只要token验证成功，就说明登录有效，不需要进行其他验证操作
            String token = ((HttpServletRequest) request).getHeader("Authentication");
            if(JWTUtil.validateToken(token)){
                chain.doFilter(httpRequest,response);
            }else{
                request.getRequestDispatcher("/api/unauthorized").forward(httpRequest,response);
            }

        } catch (RuntimeException exception) {
            request.getRequestDispatcher("/api/unauthorized").forward(httpRequest,response);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
