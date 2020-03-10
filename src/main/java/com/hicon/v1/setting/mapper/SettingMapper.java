package com.hicon.v1.setting.mapper;

import java.util.List;

import com.hicon.v1.setting.bean.Setting;
import com.hicon.v1.setting.bean.query.SettingVoQuery;
import com.hicon.v1.setting.bean.vo.SettingVo;


public interface SettingMapper{

	/**
	 * 插入对象
	 * @param bean
	 * @return
	 */
	public int insert(Setting bean);
	/**
	 * 编辑对象
	 * @param bean
	 * @return
	 */
	public int update(Setting bean);
	/**
	 * 删除对象
	 * @param bean
	 * @return
	 */
	public int delete(Setting bean);
	/**
	 * 只修改需要编辑的字段
	 * @param bean
	 * @return
	 */
	public int updateBySelective(Setting bean);
	/**
	 * 根据Id获得对象
	 * @param key
	 * @return
	 */
	public SettingVo getSettingVoById(String key);
	
	/**
	 * 获得对象总数
	 * @param query
	 * @return
	 */
	
	public long getCountByQuery(SettingVoQuery query);
	/**
	 * 获得对象列表
	 * @param query
	 * @return
	 */
	public List<SettingVo> getListByQuery(SettingVoQuery query);
}