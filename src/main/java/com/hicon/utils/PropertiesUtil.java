/**
 * 加载指定配置文件公共类文件
 */
package com.hicon.utils;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 加载指定配置文件公共类
 * @作者 栗超
 * @时间 2018年5月11日 下午3:09:25
 * @说明
 */
public class PropertiesUtil {
	private static final Logger log = Logger.getLogger(PropertiesUtil.class);

	@SuppressWarnings("finally")
	public static Properties init(String path) {
		Properties properties = new Properties();
		try {
			InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream(path);
			if (in == null) {
				throw new RuntimeException("PropertiesUtil读取配置过程中" + path+ "文件找不到！");
			}
			properties.load(in);
		} catch (IOException e) {
				log.error("PropertiesUtil 加载" + path + "文件出错", e);
		} finally {
			return properties;
		}
	}

	/**
	 * 获取项目默认对应的配置文件中的值
	 * @param key
	 * @return
	 */
	public static String getValue(String key) {
		return PropertiesUtil.init("pipe.properties").getProperty(key);
	}
}
