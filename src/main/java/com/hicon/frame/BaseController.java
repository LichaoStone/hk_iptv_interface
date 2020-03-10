package com.hicon.frame;


import com.alibaba.fastjson.JSONObject;
import com.hicon.frame.page.PageContext;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * controller基础类
 * @作者 栗超
 * @说明
 */
public class BaseController extends Constants {
	 private static final Logger logger = Logger.getLogger(BaseController.class);
	 
	 @InitBinder
	 public void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
			if (request.getCharacterEncoding() == null || !request.getCharacterEncoding().toLowerCase().equals("utf-8")) {
				logger.info("getCharacterEncoding:" + request.getCharacterEncoding() + ":",new Exception("打印堆栈"));
			}
			
			request.setCharacterEncoding("utf-8");
			binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	 }
	 
	 /**
	  * 发送json
	  * @param json
	  * @param response
	  */
	 protected static void sendJson(String json, HttpServletResponse response){
		  try {
				logger.info("json===>\n" + json);
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out;
				
				out = response.getWriter();
				out.print(json);
				out.flush();
				out.close();
			} catch (IOException e) {
				logger.error("BaseController.sendJson出错:",e);
			}
      }
	 

	/**
	 * 获取json串-方法1
	 * @param CODE
	 * @param RET
	 * @return
	 */
	protected static String getJson(int CODE,int RET) {
		    JSONObject jsonObj = new JSONObject();  
			jsonObj.put("code",CODE);
		    
	    	JSONObject jsonObj2 = new JSONObject();
	    	jsonObj2.put("ret",RET);
	    	jsonObj2.put("msg",RET_INFO.get(RET));
	    	
	    	jsonObj.put("results",jsonObj2);
	    	logger.info("组装完成返回json:" + jsonObj.toString());
	    	return jsonObj.toString();
	  }

	/**
	 * 获取json串-方法2 Map
	 * @param CODE
	 * @param RET
	 * @param object
	 * @return
	 */
	protected static String getJson(int CODE,int RET,Map<?,?> object) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("code", CODE);
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("ret", RET);
			map1.put("msg", RET_INFO.get(RET));
			map1.put("data", object);
			map.put("results", map1);
			return net.sf.json.JSONObject.fromObject(map).toString();
	}


	/**
	 * 组装返回json串-方法3 List
	 * @return
	 */
	protected static String getJson(int CODE,int RET,List<?> resultList) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", CODE);
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("ret", RET);
		map1.put("msg", RET_INFO.get(RET));


		PageContext pageContext=PageContext.getContext();
		Map<String,Object> pageInfoMap=new HashMap<String,Object>();

		//总数量
		Integer totalCount=pageContext.getTotalRows();
		pageInfoMap.put("totalCount",pageContext.getTotalRows());

		//查询结果数量
		Integer listSize=0;
		if (resultList!=null&&resultList.size()>0) {
			listSize=resultList.size();
			pageInfoMap.put("listSize",resultList.size());
		}else{
			pageInfoMap.put("listSize", 0);
		}

		//开始条数
		Integer pageStartRow=pageContext.getPageStartRow();
		pageInfoMap.put("pageStartRow",pageStartRow);

		//每页多少条
		Integer pageSize=pageContext.getPageSize();
		pageInfoMap.put("pageSize",pageSize);

		//是否有下一页
        if(totalCount>(listSize+pageStartRow)){
				pageInfoMap.put("isNextPage",true);
		}else{
			pageInfoMap.put("isNextPage",false);
		}

		map1.put("pageInfo",pageInfoMap);
		map1.put("data", resultList);

		map.put("results", map1);
		return net.sf.json.JSONObject.fromObject(map).toString();
	}

	/**
	 * 获取json串-方法4 String
	 * @param CODE
	 * @param RET
	 * @param object
	 * @return
	 */
	protected static String getJson(int CODE,int RET,String object) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", CODE);
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("ret", RET);
		map1.put("msg", RET_INFO.get(RET));
		map1.put("data", object);
		map.put("results", map1);
		return net.sf.json.JSONObject.fromObject(map).toString();
	}



	  /**
	   * 判断list集合是否为空
	   * 	空:true
	   * 	非空:false
	   * @param list
	   * @return
	   */
	  protected static boolean isNullList(List<?> list) {
		    boolean resFlag=true;
		    
		    if (list!=null&&list.size()>0) {
		    	resFlag=false;
			}
	    	return resFlag;
	  }


	  public static void main(String[] args) {

	  }
}
