package com.hicon.frame;

import com.hicon.utils.PropertiesUtil;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 解决Ajax跨域请求问题
 * @作者 lichao
 * @说明
 */
public class CORSFilter implements Filter{
	    private static final Logger logger = Logger.getLogger(CORSFilter.class);
	    public void init(FilterConfig filterConfig) throws ServletException {
	 
	    }
	    
	    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) 
	    		throws IOException, ServletException {
	    	
	    	String CORSIp=PropertiesUtil.getValue("CORSIp");
	    	logger.info("--CORSFilter--CORSIp:"+CORSIp);
	    	
	    	HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
	    	HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
	        httpResponse.addHeader("Access-Control-Allow-Origin",CORSIp);//ajax请求头部信息允许所有IP地址或者域名
	        httpResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");//请求方式
	        filterChain.doFilter(httpRequest,httpResponse);
	    }
	 
	    public void destroy() {
	 
	    }
}
