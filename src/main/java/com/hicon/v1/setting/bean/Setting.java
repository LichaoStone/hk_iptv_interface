package com.hicon.v1.setting.bean;

import java.io.Serializable;
import java.util.Date;
/**
 *系统设置表
 */
public class Setting implements Serializable {
	
	private static final long serialVersionUID = 1552769868118010074l;
	/**
	 *自增ID
	 */
	private Integer id;  
	/**
	 *主键
	 */
	private String settingKey;  
	/**
	 *名称
	 */
	private String name;  
	/**
	 *内容
	 */
	private String content;  
	/**
	 *创建者
	 */
	private String creator;  
	/**
	 *创建时间
	 */
	private Date createTime;  
	/**
	 *更新时间
	 */
	private Date updateTime;  
  	
	public Integer getId(){  
		return id;  
	}  
	public void setId(Integer id){  
		this.id = id;  
	}  
	public String getSettingKey(){  
		return settingKey;  
	}  
	public void setSettingKey(String settingKey){  
		this.settingKey = settingKey;  
	}  
	public String getName(){  
		return name;  
	}  
	public void setName(String name){  
		this.name = name;  
	}  
	public String getContent(){  
		return content;  
	}  
	public void setContent(String content){  
		this.content = content;  
	}  
	public String getCreator(){  
		return creator;  
	}  
	public void setCreator(String creator){  
		this.creator = creator;  
	}  
	public Date getCreateTime(){  
		return createTime;  
	}  
	public void setCreateTime(Date createTime){  
		this.createTime = createTime;  
	}  
	public Date getUpdateTime(){  
		return updateTime;  
	}  
	public void setUpdateTime(Date updateTime){  
		this.updateTime = updateTime;  
	}  
}
