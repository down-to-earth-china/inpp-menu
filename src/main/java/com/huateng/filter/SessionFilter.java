package com.huateng.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * session过滤器
 *
 * @author shuaion 2017/11/13
 **/
public class SessionFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        String url = request.getRequestURI();
        System.out.println("url="+url);

        HttpSession session = request.getSession();
        if(url.indexOf("/login")!=-1){
            filterChain.doFilter(request,response);
        }else if(url.indexOf("css")!=-1&&url.indexOf("js")!=-1){
            filterChain.doFilter(request,response);
        }else if (url.indexOf("/user/index")!=-1){
            filterChain.doFilter(request,response);
        }else if(session!=null&&session.getAttribute("loginUser")!=null){
            filterChain.doFilter(request,response);
        }else{
            response.sendRedirect("/user/index");

        }

    }
}
