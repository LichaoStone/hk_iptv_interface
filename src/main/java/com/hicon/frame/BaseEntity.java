package com.hicon.frame;
import java.io.Serializable;

/**
 * 基础bean类
 * @作者 栗超
 * @说明
 */
public class BaseEntity implements Serializable{
	private static final long serialVersionUID = -3508901349776012782L;
	
	/**接口签名验证:默认值见配置文件**/
	private String _appid;
	/**接口签名验证:时间戳，毫秒**/
	private String _timestamp;
	/**接口签名验证:加密后字符串**/
	private String _sign;
	/**接口签名验证:1.md5**/
	private String _secret;
	
	/**创建时间**/
	private String createTime;
	/**修改时间**/
	private String updateTime;
	/**状态**/
	private String status;
	/**创建人**/
	private String creator;
	/**排序**/
	private String orderNum;
	/**计数**/
	private Integer countNum;

	/**
	 * 开始时间
	 */
	private String beginDate;
	/**
	 * 结束时间
	 */
	private String endDate;
	 
	/**
	 * 主键key，用于详情页查询 
	 */
	private String key;

	/**
	 * 图片前缀
	 */
	private String prifixImgUrl;

	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String get_appid() {
		return _appid;
	}
	public void set_appid(String _appid) {
		this._appid = _appid;
	}
	public String get_timestamp() {
		return _timestamp;
	}
	public void set_timestamp(String _timestamp) {
		this._timestamp = _timestamp;
	}
	public String get_sign() {
		return _sign;
	}
	public void set_sign(String _sign) {
		this._sign = _sign;
	}
	public String get_secret() {
		return _secret;
	}
	public void set_secret(String _secret) {
		this._secret = _secret;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public Integer getCountNum() {
		return countNum;
	}
	public void setCountNum(Integer countNum) {
		this.countNum = countNum;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getPrifixImgUrl() {
		return prifixImgUrl;
	}

	public void setPrifixImgUrl(String prifixImgUrl) {
		this.prifixImgUrl = prifixImgUrl;
	}
}
