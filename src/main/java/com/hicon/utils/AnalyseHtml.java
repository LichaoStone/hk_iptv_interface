package com.hicon.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 分析html页面信息
 * 
 * @author tangming
 * @date 2017-10-10
 */
public class AnalyseHtml {

	/**
	 * 通过html文本信息解析需要内容
	 * 
	 * @param html 内容
	 * @param txt 是否只获取文本内容 true:获取text;false:获取html
	 * @param selector 可选项，通过选择器获取指定内容
	 * @return
	 */
	public static List<String> getHtmlByString(String html, boolean txt, String... selectors) {
		List<String> result = new ArrayList<String>();
		Document doc = Jsoup.parse(html);
		Element body = doc.body();
		if (selectors != null && selectors.length > 0) {
			for (String selector : selectors) {
				if (txt) {
					result.add(body.select(selector).text());
				} else {
					result.add(body.select(selector).html());
				}
			}
		} else {
			if (txt) {
				result.add(body.text());
			} else {
				result.add(body.html());
			}
		}
		return result;
	}

	/**
	 * 通过path路径获取html解析需要内容
	 * 
	 * @param path html路径
	 * @param txt 是否只获取文本内容 true:获取text;false:获取html
	 * @param selector 可选项，通过选择器获取指定内容
	 * @return
	 * @throws IOException
	 */
	public static List<String> getHtmlByPath(String path, boolean txt, String... selectors) throws IOException {
		List<String> result = new ArrayList<>();
		File input = new File(path);
		Document doc = Jsoup.parse(input, "UTF-8");
		Element body = doc.body();
		if (selectors != null && selectors.length > 0) {
			for (String selector : selectors) {
				if (txt) {
					result.add(body.select(selector).first().text());
				} else {
					//栗超 20171027
					//增加了.replaceAll("(?<=\\>)(?:\\s*\r?\n?)(?=\\<)","")
					//主要是为了适配秀米编辑器section标签样式不兼容问题,替换掉所有标签之间的空格
					//该方法为临时解决方法，未找到更好的解决方式
					result.add(body.select(selector).first().html().replaceAll("(?<=\\>)(?:\\s*\r?\n?)(?=\\<)",""));
				}
			}
		} else {
			if (txt) {
				result.add(body.text());
			} else {
				result.add(body.html());
			}
		}
		
		return result;
	}

	/**
	 * 通过请求地址获取html解析需要内容
	 * 
	 * @param html 请求地址
	 * @param txt 是否只获取文本内容 true:获取text;false:获取html
	 * @param selector 可选项，通过选择器获取指定内容
	 * @return
	 * @throws IOException
	 */
	public static List<String> getHtmlByRequest(String html, boolean txt, String... selectors) throws IOException {
		List<String> result = new ArrayList<>();
		Document doc = Jsoup.connect(html).get();
		Element body = doc.body();
		if (selectors != null && selectors.length > 0) {
			for (String selector : selectors) {
				if (txt) {
					result.add(body.select(selector).text());
				} else {
					result.add(body.select(selector).html());
				}
			}
		} else {
			if (txt) {
				result.add(body.text());
			} else {
				result.add(body.html());
			}
		}
		return result;
	}
}
