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

import com.ota.tools.RSATool;

public class RSAVerifyFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resHttp = (HttpServletResponse) response;
		String pathRSACODE = req.getSession().getServletContext().getRealPath("/License");
		String pathPublicKey = req.getSession().getServletContext().getRealPath("/PublicKey");
		/*
		File localEncryptFile = new File(req.getSession().getServletContext().getRealPath("/LocalEncryptFile"));
		if(!localEncryptFile.exists()) {
			try {
				String localPulbicKey = req.getSession().getServletContext().getRealPath("/LocalPublicKey");
				String localPirvateKey = req.getSession().getServletContext().getRealPath("/LocalPrivateKey");
				String weburl = "http://www.baidu.com";
				String datetime = OTAToolKit.getWebsiteDatetime(weburl);
				System.out.println(datetime);
				
				RSATool.generateKeyPair(localPulbicKey,localPirvateKey);
				Files.delete(Paths.get(localPirvateKey));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		*/
		String plaintext = RSATool.getPlaintext(pathRSACODE, pathPublicKey);
		int result = RSATool.parsePlaintext(plaintext);
		
		if(result == 0) {
			resHttp.setContentType("text/html; charset=UTF-8"); //转码
			PrintWriter out = resHttp.getWriter();
			out.flush();
			out.println("<script>");
			out.println("alert('Your license has expired, please contact DATAMAX and refresh your license！');");
			out.print("window.location.href=\"http://www.szdatamax.com/\"");
			out.println("</script>");
			return;
		}
		
		if(result < 0) {
			resHttp.setContentType("text/html; charset=UTF-8"); //转码
			PrintWriter out = resHttp.getWriter();
			out.flush();
			out.println("<script>");
			out.println("alert('Your license is illegal or your license or PublicKey does not exist, please contact DATAMAX and refresh your license！');");
			out.print("window.location.href=\"http://www.szdatamax.com/\"");
			out.println("</script>");
			return;
		}
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void destroy() {

	}
}
