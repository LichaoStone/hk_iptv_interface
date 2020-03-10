package com.hicon.frame.aspect;

import com.hicon.frame.aspect.annotation.PaginationService;
import com.hicon.frame.page.PageContext;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


@Aspect
@Component
public class PaginationServiceAspectAdvice {
	private static final Logger logger = Logger.getLogger(PaginationServiceAspectAdvice.class);
	@Around(value = "com.hicon.frame.aspect.pointcut.SystemArchitecture.anyMethod() "
			+ "&& @annotation(paginationService)")
	public Object paginationService(ProceedingJoinPoint jp,PaginationService paginationService) throws Throwable {
		logger.info("--paginationService--");

		PageContext context = PageContext.getContext();


		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		ServletRequestAttributes sra = (ServletRequestAttributes) ra;
		HttpServletRequest request = sra.getRequest();  //获取request 可以从中获取参数或cookie


		if (request == null) {
			throw new RuntimeException("分页方法的传入参数错误");
		}

		String start = request.getParameter("page");
		String length = request.getParameter("step");
		String sqlWhere=request.getParameter("sqlWhere");
		String sortWhere=request.getParameter("sortWhere");



		logger.info("【分页】service--AOP--start="+start+",length="+length+",sqlWhere="+sqlWhere+",sortWhere="+sortWhere);

		if(start==null ||"".equals(start) || "0".equals(start)){
			start="1";
		}

		if(length==null || "".equals(length)){
			length="18";
		}
		Integer pageStartRow = 0 ;
		//计算起始行
		try {
			pageStartRow = (Integer.parseInt(start)-1) * Integer.parseInt(length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		context.setPageStartRow(pageStartRow);
		context.setPageSize(Integer.parseInt(length));
		context.setSqlWhere(sqlWhere);
		context.setSortWhere(sortWhere);


		context.setPaginationController(true);
		context.setPaginationService(true);

		Object o = null;
		try{
			o = jp.proceed();
		}catch(Throwable t){
			throw t;
		}finally{
			context.setPaginationService(false);
		}
		return o;
	}
}
