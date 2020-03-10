/**
* @Title: SettingController.java
* @Package com.hicon.v1.setting.controller
* @Description: TODO
* @author mayi
* @date 2020年2月24日
* @version V1.0
*/
package com.hicon.v1.setting.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hicon.frame.BaseController;
import com.hicon.frame.apiversion.ApiVersion;
import com.hicon.utils.RedisUtil;
import com.hicon.v1.setting.bean.vo.SettingVo;
import com.hicon.v1.setting.service.SettingServiceApi;

/**
* @ClassName: SettingController
* @Description: TODO
* @author mayi
* @date 2020年2月24日
*
*/
@Controller
@ApiVersion(1)
@RequestMapping("/{version}")
public class SettingController extends BaseController{

	public static final Logger logger = Logger.getLogger(SettingController.class);
	
	private final String SCREEN_BIND_CONTENT = "setting:screenBindContent" ;
	
	private final String QUESTION_LIST = "setting:comquestionContent" ;
	
	private final String ABOUTUS_AGREEMENT = "setting:userAgreement" ;
	
	private final String ABOUTUS_POLICY = "setting:privacyPolicy" ;
	
	@Resource
	private SettingServiceApi settingServiceImpl;
	
	@RequestMapping(value="/stb/bindTips",method= RequestMethod.GET)
    @ResponseBody
    private String bindTips(HttpServletRequest request, HttpServletResponse response){

		try{
        	Map<String,Object> map = new HashMap<String, Object>();
        	String explain =  RedisUtil.getString(SCREEN_BIND_CONTENT);
        	if (explain != null && explain.trim().length() > 0) {
        		map.put("explain",explain);
			}else {
	        	SettingVo vo = settingServiceImpl.getSettingByName("screenBindContent");
	            if (vo != null ) {
	            	map.put("explain", vo.getContent());
				}
			}
			return getJson(CODE.SUCCESS,RET.SUCCESS,map);

		}catch(Exception e) {
            logger.error("获得绑定说明页异常:",e);
			return getJson(CODE.FAIL,RET.FAIL);

		}
    }
	
	@RequestMapping(value="/question/list",method= RequestMethod.GET)
    @ResponseBody
    private String questionList(HttpServletRequest request, HttpServletResponse response){

		try{
        	Map<String,Object> map = new HashMap<String, Object>();
        	String explain =  RedisUtil.getString(QUESTION_LIST);
        	if (explain != null && explain.trim().length() > 0) {
        		map.put("content",explain);
			}else {
	        	SettingVo vo = settingServiceImpl.getSettingByName("comquestionContent");
	            if (vo != null ) {
	            	map.put("content", vo.getContent());
				}
			}
			return getJson(CODE.SUCCESS,RET.SUCCESS,map);

		}catch(Exception e) {
            logger.error("获得绑定说明页异常:",e);
			return getJson(CODE.FAIL,RET.FAIL);

		}
    }
	@RequestMapping(value="/aboutus/agreement",method= RequestMethod.GET)
    @ResponseBody
    private String aboutusAgreement(HttpServletRequest request, HttpServletResponse response){

		try{
			String type = request.getParameter("type");
			String agreement = ABOUTUS_AGREEMENT ;
			if ("2".equals(type)) {
				agreement = ABOUTUS_POLICY ;
			}
        	Map<String,Object> map = new HashMap<String, Object>();
        	map.put("title", "用户协议");
        	String explain =  RedisUtil.getString(agreement);
        	if (explain != null && explain.trim().length() > 0) {
        		map.put("content",explain);
			}else {
	        	String name = "userAgreement" ;
	        	if ("2".equals(type)) {
	        		name = "privacyPolicy" ;
				}
	        	SettingVo vo = settingServiceImpl.getSettingByName(name);
	            if (vo != null ) {
	            	map.put("content", vo.getContent());
				}
			}
        	
			return getJson(CODE.SUCCESS,RET.SUCCESS,map);

		}catch(Exception e) {
            logger.error("获得绑定说明页异常:",e);
			return getJson(CODE.FAIL,RET.FAIL);

		}
    }
}
