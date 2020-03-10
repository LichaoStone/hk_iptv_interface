package com.hicon.frame.aspect.pointcut;

import org.aspectj.lang.annotation.Pointcut;

/**
 * 
 * @作者 栗超
 * @说明
 */
public class SystemArchitecture {
	
	@Pointcut("execution(* *(..))")
	public void anyMethod(){
	}
	
	@Pointcut("execution(public * *(..))")
	public void anyPublicMethod(){
	}

	@Pointcut("execution(* com.hicon..*..*.service..*(..))")
	public void globalServiceLayer(){
	}

	@Pointcut("execution(* com.hicon..*..*.controller..*(..))")
	public void globalControllerLayer(){
	}
}
