package com.hicon.utils.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * 
 * @author mayi
 *
 */
public class IOUtils extends org.apache.commons.io.IOUtils{
	
	private static final Logger logger = LoggerFactory.getLogger(IOUtils.class);
	
	public static byte[] InputStreamToByte(InputStream is) {
		byte[] data = null;
		try {
			if (is != null) {
				data = IOUtils.toByteArray(is);
			}
		} catch (IOException e) {
			logger.error("inputStream 转换字节 error：" + e.getMessage());
		}
		return data;
	}
	
	public static InputStream byteToInputStream(byte[] buf) {
		InputStream sbs = null;
		try {
			if (buf != null && buf.length > 0) {
				sbs = new ByteArrayInputStream(buf);
			}
		} catch (Exception e) {
			logger.error("字节转换inputStream error：" + e.getMessage());
		} 
		return sbs;
	}
	
	public static byte[] fileToByte(String url)  {
		byte[] data = null;
		InputStream is = getInputStream(url);
		if (is != null) {
			data = InputStreamToByte(is);
			IOUtils.closeQuietly(is);
		}
		return data;
	}
	
	public static InputStream getInputStream(String url) {
		InputStream is = null;
		if (StringUtils.isStrNotNull(url)) {
			try {
				URL imgUrl = new URL(url);
				is = imgUrl.openStream();
			} catch (IOException e) {
				logger.error(" url：" + url + "； 获取 inputStream error ：" + e.getMessage());
			}
		}
		return is;
	}
}
