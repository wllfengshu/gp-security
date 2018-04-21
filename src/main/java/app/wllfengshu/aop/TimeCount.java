package app.wllfengshu.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import app.wllfengshu.util.LogUtils;

@Aspect
@Component
public class TimeCount {
	@Pointcut(value="execution(** app.wellcloud.qc.service.*Impl.*(..))")
	public void pointcut(){}
	@Around("pointcut()")
	public Object getTimeCount(ProceedingJoinPoint pj) throws Throwable{
		long beginTime = System.currentTimeMillis();
		Object o = null;
		o = pj.proceed();
		long endTime = System.currentTimeMillis();
		LogUtils.trace(this,"!!Service--"+pj.getSignature().getName()+ ",useTime:%sms #",endTime-beginTime);
		return o;
	}
}
