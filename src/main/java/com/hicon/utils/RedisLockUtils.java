/**
* @Title: RedisLockUtils.java
* @Package com.hicon.utils
* @Description: TODO
* @author mayi
* @date 2019年12月30日
* @version V1.0
*/
package com.hicon.utils;

/**
* @ClassName: RedisLockUtils
* @Description: redis加锁简单处理
* @author mayi
* @date 2019年12月30日
*
*/
public class RedisLockUtils {

	/**
	 *
	* @Title: lock
	* @Description: 加锁操作
	* @param @param key 加锁key
	* @param @param value 值
	* @param @param time 过期时间
	* @param @return    参数
	* @return boolean    返回类型
	* @throws
	* @author mayi
	* @date 2019年12月30日
	 */
	public static boolean lock(String key,String value,int time) {
		long count = RedisUtil.setnx(key, value);
		RedisUtil.expire(key, time);
		if (count > 0) {
			return true ;
		}
		return false ;
	}
	/**
	 *
	* @Title: unLock
	* @Description: TODO 删除锁
	* @param @param key    参数
	* @return void    返回类型
	* @throws
	* @author mayi
	* @date 2019年12月30日
	 */
	public static void unLock(String key) {
		RedisUtil.deleteObjectByKey(key);
	}
}
