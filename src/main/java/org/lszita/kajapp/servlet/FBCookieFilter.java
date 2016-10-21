package org.lszita.kajapp.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = {"/app", "/", "/login"})
public class FBCookieFilter implements Filter{
	
	private static final String[] SECURE = {"/app"};

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String requestedResource = req.getRequestURI();
		
		if(hasFaceBookCookie(req) && isSecureResource(requestedResource)){
			chain.doFilter(request, response);
		}
		if(hasFaceBookCookie(req) && !isSecureResource(requestedResource)){
			res.sendRedirect("/app"); 
		}
		if(!hasFaceBookCookie(req) && !isSecureResource(requestedResource)){
			chain.doFilter(request, response); 
		}
		if(!hasFaceBookCookie(req) && isSecureResource(requestedResource)){
			res.sendRedirect("/login"); 
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}
	
	private static boolean hasFaceBookCookie(HttpServletRequest req){
		Cookie[] cookies = req.getCookies();
		if(cookies == null) return false;
		
		for(int i = 0; i < cookies.length; i++){
			if("fbsr_297459477289247".equals(cookies[i].getName()))
				return true;
		}
		return false;
	}
	
	private static boolean isSecureResource(String resource){
		for(int i = 0; i < SECURE.length; i++){
			if(resource != null && resource.equals(SECURE[i]))
				return true;
		}
		return false;
	}
}
