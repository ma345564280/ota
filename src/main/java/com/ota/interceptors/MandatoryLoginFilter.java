package com.ota.interceptors;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MandatoryLoginFilter implements Filter {
	// private static final String FILTERED_REQUEST =
	// "@@session_context_filtered_request";

	private String[] allowURLs = { "login.html", "header.html", "sidebar.html" };

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resHttp = (HttpServletResponse) response;
		String reqURL = req.getRequestURI();
		boolean isallow = false;

		for (String urlCheck : allowURLs) {
			if (reqURL.indexOf(urlCheck) >= 0) {
				isallow = true;
				break;
			}
		}

		if (isallow) {
			chain.doFilter(request, response);
		} else {
			if (req.getSession().getAttribute("role") == null
					|| req.getSession().getAttribute("role").equals("")) {
				System.out.println("forbide");
				resHttp.sendRedirect("/ota/login.html");
				return;
			} else {
				chain.doFilter(request, response);
			}
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void destroy() {

	}
}
