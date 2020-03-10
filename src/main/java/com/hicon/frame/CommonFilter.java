package com.hicon.frame;

import com.hicon.utils.DateUtil;
import com.hicon.utils.MD5;
import com.hicon.utils.PropertiesUtil;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * 全局过滤器
 * @作者 栗超
 * @说明
 */
public class CommonFilter extends BaseController implements Filter{
	private static final Logger logger = Logger.getLogger(CommonFilter.class);
	
	public static void main(String[] args) {
		System.out.println("传入时间:"+DateUtil.date2msec("2018-05-24 17:00:00"));
		System.out.println("传入时间:"+DateUtil.date2msec("2018-05-24 17:00:01"));
		System.out.println(Long.valueOf(DateUtil.date2msec("2018-05-24 17:00:00"))+600000);
	}
	
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest) request).getSession();
		logger.info("commonFilter--session"+session.getId()+":START...");
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		//请求url
		String requestUrl = httpRequest.getRequestURI();
		String queryString=httpRequest.getQueryString();
		logger.info("请求url:"+requestUrl);
		logger.info("查询参数:"+queryString);
		
		
		String access_permissions=PropertiesUtil.getValue("access_permissions");
		if (ACCESS_PERMISSIONS.ON.equalsIgnoreCase(access_permissions)){//开启了接口签名验证
			logger.info("开启接口签名验证... ...");
			
			if (queryString!=null&&!"".equals(queryString)&&queryString.length()>0) {//已传参数
				//校验接口合法性
				StringBuffer sBu=new StringBuffer();
				String   _timestamp=httpRequest.getParameter("_timestamp");
				String   _sign=httpRequest.getParameter("_sign");
				
				
				if ("".equals(_timestamp)||_timestamp==null||_timestamp.isEmpty()) {
					sendJson(getJson(CODE.SUCCESS,RET.MISSING_PARAMETER),httpResponse);
					logger.info("验证失败,"+RET_INFO.get(RET.MISSING_PARAMETER)+",_timestamp为空");
					logger.info("commonFilter--session"+session.getId()+":END");
					return;
				}
				
				if ("".equals(_sign)||_sign==null||_sign.isEmpty()) {
					sendJson(getJson(CODE.SUCCESS,RET.MISSING_PARAMETER),httpResponse);
					logger.info("验证失败,"+RET_INFO.get(RET.MISSING_PARAMETER)+",_sign为空");
					logger.info("commonFilter--session"+session.getId()+":END");
					return;
				}
				
				
				if (requestUrl.indexOf("/integrate_pipe/ws")==-1) {//非ws请求验证过期时间
					//检验时间是否过期
					Long   _timestamp_receive=Long.valueOf(_timestamp);
					Long   curr_time=Long.valueOf(DateUtil.date2msec(DateUtil.getTimeToSec()));
					Long   deadline_time=Long.valueOf(PropertiesUtil.getValue("deadline_time"));
					if ((_timestamp_receive+deadline_time)<=curr_time){//过期
						sendJson(getJson(CODE.SUCCESS,RET.LOSE_EFFICACY),httpResponse);
						logger.info("验证失败,"+RET_INFO.get(RET.LOSE_EFFICACY));
						logger.info("commonFilter--session"+session.getId()+":END");
						return;
					}
				}
				
				
				//拼装接口签名串
				sBu.append(PropertiesUtil.getValue("_appid"));
				Enumeration enu=httpRequest.getParameterNames();
				Map<String,String> params=new HashMap<String,String>();
				while(enu.hasMoreElements()){
					String paraName=(String)enu.nextElement();
					if (!"_sign".equals(paraName)&&!"_timestamp".equals(paraName)) {
						params.put(paraName,httpRequest.getParameter(paraName));
						//sBu.append(paraName).append(httpRequest.getParameter(paraName));
					}
				} 
				 
				
				if (params!=null&&!params.isEmpty()) {
					 List<String> keys = new ArrayList<String>(params.keySet());
				     Collections.sort(keys);//参数排序
				     
				     for (int i = 0; i < keys.size(); i++) {
				            String key = keys.get(i);
				            String value = params.get(key);
				            sBu.append(key).append(value);
				      }
				}
				
				sBu.append("_timestamp").append(_timestamp).append(PropertiesUtil.getValue("_appid"));
				 
				logger.info("MD5加密前："+sBu.toString());
				String secretString=MD5.getMD5String(sBu.toString());
				logger.info("MD5加密后："+secretString);
				
				if(!secretString.equalsIgnoreCase(_sign)){//签名验证不通过
					sendJson(getJson(CODE.SUCCESS,RET.ILLEGAL),httpResponse);
					logger.info("验证失败,"+RET_INFO.get(RET.ILLEGAL));
					logger.info("commonFilter--session"+session.getId()+":END");
					return;
				}
				
				logger.info("commonFilter--session"+session.getId()+":END");
				filterChain.doFilter(httpRequest,httpResponse);
			}else {//未传参
				logger.info("验证失败,"+RET_INFO.get(RET.MISSING_PARAMETER));
				logger.info("commonFilter--session"+session.getId()+":END");
				sendJson(getJson(CODE.SUCCESS,RET.MISSING_PARAMETER),httpResponse);
			}
		}else{
			logger.info("未开启接口签名验证,直接放行... ...");
			logger.info("commonFilter--session"+session.getId()+":END");
			filterChain.doFilter(httpRequest,httpResponse);
		}
	}

	public void init(FilterConfig config) throws ServletException {
	}
}
