package com.ota.interceptors;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class AccessControlFilter implements HandlerInterceptor{

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		
	}

	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
		return false;
	}
	
	/*
	public void destroy() {
        // TODO Auto-generated method stub
 
    }
 
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();
        // 登陆url
        String loginUrl = httpRequest.getContextPath() + "/login.html";
 
        String url = httpRequest.getRequestURI();
        String path = url.substring(url.lastIndexOf("/"));
        System.out.println("url:" + url);
        System.out.println("path:" + path);
        System.out.println("loginUrl:" + loginUrl);
        // 超时处理，ajax请求超时设置超时状态，页面请求超时则返回提示并重定向
        if(path.equals("/login.html") || path.contains("css") || 
           path.contains("js")) {
        	chain.doFilter(request, response);
        } else if (session.getAttribute("businessid") == null) {
//            // 判断是否为ajax请求
//            if (httpRequest.getHeader("x-requested-with") != null
//                    && httpRequest.getHeader("x-requested-with")
//                            .equalsIgnoreCase("XMLHttpRequest")) {
//                httpResponse.addHeader("sessionstatus", "timeOut");
//                httpResponse.addHeader("loginPath", loginUrl);
//                chain.doFilter(request, response);// 不可少，否则请求会出错
//            } else {
//            }
            String str = "<script language='javascript'>"
            		+ "window.top.location.href='"
            		+ loginUrl
            		+ "';</script>";
            response.setContentType("text/html;charset=UTF-8");// 解决中文乱码
            try {
            	PrintWriter writer = response.getWriter();
            	writer.write(str);
            	writer.flush();
            	writer.close();
            } catch (Exception e) {
            	e.printStackTrace();
            }
        } else {
            chain.doFilter(request, response);
        }
        
    }
	*/
}
