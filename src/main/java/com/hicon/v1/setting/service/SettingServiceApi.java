/**
* @Title: SettingServiceApi.java
* @Package com.hicon.v1.setting.service.impl
* @Description: TODO
* @author mayi
* @date 2020年2月24日
* @version V1.0
*/
package com.hicon.v1.setting.service;

import com.hicon.v1.setting.bean.vo.SettingVo;

/**
* @ClassName: SettingServiceApi
* @Description: TODO
* @author mayi
* @date 2020年2月24日
*
*/
public interface SettingServiceApi {

	/**
	 * 
	* @Title: getSettingByName
	* @Description: 根据名称获得设置信息
	* @param @param name
	* @param @return    参数
	* @return ServiceResult<SettingVo>    返回类型
	* @throws
	* @author mayi
	* @date 2020年2月18日
	 */
	public SettingVo getSettingByName(String name);
}
