package com.hicon.utils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.protocol.HTTP;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * 
 * @作者 栗超
 * @时间 2018年5月12日 下午2:32:16
 * @说明
 */
public class HttpUtils {
	private static final Logger logger = Logger.getLogger(HttpUtils.class);
	
	public static void main(String[] args) {
		System.out.println(reqForGet("http://127.0.0.1:8080/integrate_pipe/testController/test?1"));
		
		try {
			//发送http请求的json格式参数需要编码再发送，否则无法接收
			String encoderJson = URLEncoder.encode("{\"code\":500,\"results\":{\"ret\":500,\"msg\":\"失败\"}}", HTTP.UTF_8);
			System.out.println(encoderJson);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 客户端用Post方法向服务器提交请求,并获取服务器响应信息
	 * @param postURL 
	 * @param context post消息体内容
	 * @return 服务器响应
	 */
	public static String reqForPost(String postURL, String context) {
			
		try {
	    	URL url = new URL(postURL);
	    	URLConnection urlConn = url.openConnection();
	    		
	    	HttpURLConnection httpUrlConn = (HttpURLConnection) urlConn;
	        // 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在
	    	// http正文内，因此需要设为true, 默认情况下是false;
	    	httpUrlConn.setDoOutput(true);
	        // 设置是否从httpUrlConnection读入，默认情况下是true
	    	httpUrlConn.setDoInput(true);
	        // Post 请求不能使用缓存
	    	httpUrlConn.setUseCaches(false);
	    	// 设定请求的方法为"POST"，默认是GET
	    	httpUrlConn.setRequestMethod("POST");   
	    	
	    	OutputStreamWriter wr = new OutputStreamWriter(httpUrlConn.getOutputStream(),"UTF-8");
	    	wr.write(context);
	    	wr.flush();
	    		
	    	BufferedReader in = new BufferedReader(new InputStreamReader(httpUrlConn.getInputStream(),"utf-8"));
	    	String line;
	    	StringBuffer sb = new StringBuffer();
	    	while ((line = in.readLine()) != null) {
	    		sb.append(line);
	    	}
	    	wr.close();
	    	in.close();
	    		
	    	return sb.toString();
		} catch (Exception e) {
			logger.error("【reqForPost】失败:",e);
		}
			
		return null;
	}
		
	/**
	 * 客户端用Get方法向服务器提交请求,并获取服务器响应信息
	 * @param getURL url
	 * @return 服务器响应消息体内容
	 */
	public static String reqForGet(String getURL) {
		
		try {
				
	    	URL url = new URL(getURL);
	    	URLConnection urlConn = url.openConnection();
	    	HttpURLConnection httpUrlConn = (HttpURLConnection) urlConn;
	    		
	        // 默认情况下是false;
	    	httpUrlConn.setDoOutput(false);
	        // 设置是否从httpUrlConnection读入，默认情况下是true
	    	httpUrlConn.setDoInput(true);
	        // Get 请求不能使用缓存
	    	httpUrlConn.setUseCaches(false);
	    	//读写均使用utf-8编码
	    	httpUrlConn.setRequestProperty("contentType", "utf-8"); 
	    	// 设定请求的方法为"GET"，默认是GET
	    	httpUrlConn.setRequestMethod("GET");
	    		

	    	BufferedReader in = new BufferedReader(new InputStreamReader(httpUrlConn.getInputStream(),"utf-8"));
	    	String line;
	    	StringBuffer sb = new StringBuffer();
	    	while ((line = in.readLine()) != null) {
	    		sb.append(line);
	    	}
	    	in.close();
	    	return sb.toString();
		} catch (Exception e) {
			logger.error("【reqForGet】失败:",e);
		}
		return "";
	}
		
		
	/**
	 * 模拟form表单的形式
	 * @param urlStr 请求地址
	 * @param textMap 文本参数
	 * @param fileMap 文件参数
	 * @return 返回结果
	 */
	 public static String postFileForNomal(String urlStr, Map<String, String> textMap, Map<String, String> fileMap) {  
		 
		 String res = "";
		 
		 HttpClient httpClient = new HttpClient();
	     PostMethod post = new PostMethod(urlStr);
		 
	     try {
	    		
	        post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
	        post.setRequestHeader("content-type","application/x-www-form-urlencoded");
	        httpClient.setConnectionTimeout(10*1000);
	        	
	        NameValuePair[] data = new NameValuePair[textMap.size()];
	        	
	        if (textMap != null) {  
	  	         StringBuffer strBuf = new StringBuffer();  
	  	         Iterator iter = textMap.entrySet().iterator();  
	  	           
	  	         int i = 0;
	  	           
	  	         while (iter.hasNext()) {  
	  	            Map.Entry entry = (Map.Entry) iter.next();  
	  	               String inputName = (String) entry.getKey();  
	  	               String inputValue = (String) entry.getValue();  
	  	               if (inputValue == null) {  
	  	                    continue;  
	  	               }  
	  	              
	  	             NameValuePair nvp = new NameValuePair(inputName,inputValue);
	  	              
	  	             data[i] = nvp;
	  	             
	  	             i++;
	  	           }  
	  	       }  
	        	
	        	
	        	post.setRequestBody(data);
	        	
	        	int stat = httpClient.executeMethod(post);
	        	if(stat==200){
	        		res = post.getResponseBodyAsString();
	        	}
	        	
	        	
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        } finally {  
	        	post.releaseConnection();   
	        }
	    return res;  
	}  
		
	/**
	* 从输入流读数据
	* @param in 输入流
	* @return 读取的内容
	*/
	public static String readBuffer(InputStreamReader in) {
		try {
			BufferedReader reader = new BufferedReader(in);
			String line = "";
			StringBuffer sb = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
		
	/**
	*  响应调用者
	*  
	* @param req
	* @param res
	* @param s
	*/
	public static void res2Client(HttpServletRequest req,
				HttpServletResponse res, String s) {
		try {
			res.setContentType("text/plain;charset=gb2312");
			PrintWriter out = res.getWriter();
			out.print(s);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	/**
	 *  获取请求客户端IP(包括穿透nginx集群代理)
	 */
	public static String getRemoteAddr(HttpServletRequest req) {
		String ip = req.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getRemoteAddr();
		}
		return ip;
	}

	
	/**
	 * map转换请求参数
	 * @param paramValues
	 * @return
	 */
	public static String mapToParams(Map<String, String> paramValues)  
    {  
        String params="";  
        
        if(paramValues != null ){
        	if(paramValues.size() > 0){
        		 Set<String> key = paramValues.keySet();  
        	        String beginLetter="";  
        	  
        	        for (Iterator<String> it = key.iterator(); it.hasNext();) {  
        	            String s = (String) it.next();  
        	            if (params.equals(""))  
        	            {  
        	                params += beginLetter + s + "=" + paramValues.get(s);  
        	            }  
        	            else  
        	            {  
        	                params += "&" + s + "=" + paramValues.get(s);  
        	            }  
        	        }  
        	}
        }
       
        return params;  
    }  
}
