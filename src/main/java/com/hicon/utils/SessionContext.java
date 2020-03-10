package com.hicon.utils;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 *  根据sessionId获取session信息
 * @author lichao
 * @时间 2019-05-10
 */
public class SessionContext {
	private static SessionContext              instance;
    private        HashMap<String,HttpSession> sessionMap;

    private SessionContext() {  
        sessionMap = new HashMap<String,HttpSession>();  
    }  

    public static SessionContext getInstance() {
        if (instance == null) {  
            instance = new SessionContext();
        }  
        return instance;  
    }  

    public synchronized void addSession(HttpSession session) {  
        if (session != null) {  
            sessionMap.put(session.getId(), session);   
        }  
    }  

    public synchronized void delSession(HttpSession session) {  
        if (session != null) {   
            sessionMap.remove(session.getId());  
        }  
    }  

    public synchronized HttpSession getSession(String sessionID) {  
        if (sessionID == null) {  
            return null;  
        }  
        return sessionMap.get(sessionID);  
    }  
}
