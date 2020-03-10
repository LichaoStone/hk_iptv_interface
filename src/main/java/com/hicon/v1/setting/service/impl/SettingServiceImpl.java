/**
* @Title: SettingServiceImpl.java
* @Package com.hicon.v1.setting.service.impl
* @Description: TODO
* @author mayi
* @date 2020年2月24日
* @version V1.0
*/
package com.hicon.v1.setting.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hicon.v1.setting.bean.query.SettingVoQuery;
import com.hicon.v1.setting.bean.vo.SettingVo;
import com.hicon.v1.setting.mapper.SettingMapper;
import com.hicon.v1.setting.service.SettingServiceApi;

/**
* @ClassName: SettingServiceImpl
* @Description: TODO
* @author mayi
* @date 2020年2月24日
*
*/
@Service
public class SettingServiceImpl implements SettingServiceApi{

	@Resource
	private SettingMapper settingMapper ;
	@Override
	public SettingVo getSettingByName(String name) {
		SettingVoQuery query = new SettingVoQuery();
		query.setName(name);
		List<SettingVo> list = settingMapper.getListByQuery(query);
		if (list == null) {
			list = new ArrayList<SettingVo>(0);
		}
		return list.get(0);
	}

}
