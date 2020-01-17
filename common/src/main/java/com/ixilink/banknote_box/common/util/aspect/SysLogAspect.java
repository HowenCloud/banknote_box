package com.ixilink.banknote_box.common.util.aspect;

import com.ixilink.banknote_box.common.dao.SysLogMapper;
import com.ixilink.banknote_box.common.pojo.SysLog;
import com.ixilink.banknote_box.common.pojo.User;
import com.ixilink.banknote_box.common.util.HttpContextUtils;
import com.ixilink.banknote_box.common.util.RedisUtil;
import com.ixilink.banknote_box.common.util.ValidatePermissionsUtil;
import com.ixilink.banknote_box.common.util.annotation.SysLogAnnotation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;


@Aspect
@Component
public class SysLogAspect {

	@Resource
	private SysLogMapper sysLogMapper;
	@Resource
	private RedisUtil redisUtil;

	@Pointcut("@annotation(com.ixilink.banknote_box.common.util.annotation.SysLogAnnotation)")
	public void logPointCut() {
	}
	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long beginTime = System.currentTimeMillis();
		//执行方法
		Object result = point.proceed();
		//执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;
		//保存日志
		saveSysLog(point, time);
		return result;
	}

	private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		SysLog sysLog = new SysLog();
		SysLogAnnotation annotation = method.getAnnotation(SysLogAnnotation.class);
		if(annotation != null){
			String[] str = annotation.value().split(":");
			//注解上的描述
			sysLog.setOperationObject(Integer.parseInt(str[0]));
			sysLog.setOperationContent(str[1]);
		}
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		User user = ValidatePermissionsUtil.getUser(request,redisUtil);
		String name = user.getName();
		Integer libraryId = user.getLibraryId();
		sysLog.setCreateTime(new Date().getTime());
		sysLog.setOperator(name);
		sysLog.setLibraryId(libraryId);
		sysLogMapper.Insert(sysLog);
	}
}
