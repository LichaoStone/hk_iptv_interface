package com.hicon.utils.http;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @author mayi
 *
 */
public class JsonUtil {

	private JsonUtil(){}

	public static <T> T parseObject(String json,Class<T> clazz){
		return JSON.parseObject(json, clazz);
	}

	public static String toJSONString(Object object){
		return JSON.toJSONString(object);
	}
}
