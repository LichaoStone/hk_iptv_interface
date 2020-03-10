package com.hicon.frame.page;

/**
 * @作者 栗超
 * @时间 2018年5月25日 上午8:51:57
 * @说明
 */
public class PageContext extends Page {
	
	private static final long               serialVersionUID = 1L;
	private static ThreadLocal<PageContext> context          = new ThreadLocal<PageContext>();

	/**
	 * 获取context
	 * @return
	 */
	public static PageContext getContext() {
		PageContext ci = (PageContext) context.get();
		
		if (ci == null) {
			ci = new PageContext();
			context.set(ci);
		}
		
		return ci;
	}
    
	/**
	 * 移除context
	 */
	public static void removeContext() {
		context.remove();
	}
    
	protected void initialize(){
	}
}