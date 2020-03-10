package com.hicon.frame.aspect;

import com.hicon.frame.aspect.annotation.PaginationController;
import com.hicon.frame.page.PageContext;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Aspect
@Component
public class PaginationControllerAspectAdvice {
	private static final Logger logger = Logger.getLogger(PaginationControllerAspectAdvice.class);
   
	@Around(value="com.hicon.frame.aspect.pointcut.SystemArchitecture.globalControllerLayer() "
				  +" && @annotation(paginationController)")
	public Object paginationController(ProceedingJoinPoint jp,PaginationController paginationController) throws Throwable {
		logger.info("--paginationController--");
		
		PageContext context = PageContext.getContext();
		Object[] args = jp.getArgs();	
		if (args.length == 0) {
			throw new RuntimeException("分页方法的传入参数错误");
		}

		HttpServletRequest req = null;
		HttpServletResponse rep = null;
		for (int i = 0; i < args.length; i++) {
			if (args[i] instanceof HttpServletRequest) {
				req = (HttpServletRequest) args[i];
			}
			if(args[i] instanceof HttpServletResponse) {
				rep = (HttpServletResponse) args[i];
			}
		}

		if (req == null) {
			throw new RuntimeException("分页方法的传入参数错误");
		}
		
		String start = req.getParameter("pageStartRow");
		String length = req.getParameter("pageSize");
		
		if(start==null ||"".equals(start)){
			start="0";
		}
		
		if(length==null || "".equals(length)){
			length="18";
		}

		context.setPageStartRow(Integer.parseInt(start));
		context.setPageSize(Integer.parseInt(length));
		context.setPaginationController(true);
			
		Object o = null;
		try{
			o = jp.proceed();
		}catch(Throwable t){
			throw t;
		}finally{
			context.setPaginationController(false);
		}
		
		return o;
	}
}
