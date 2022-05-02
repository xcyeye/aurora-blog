package xyz.xcye.wg.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 日志切面类
 * @author qsyyke
 */

@Component
@Slf4j
@Aspect
public class LogAop {
    /*@Around("execution(* xyz.xcye..*(..))")
    public Object recordLog(ProceedingJoinPoint joinPoint) {
        //对于所有标了@Log注解的方法进行记录

        //target目标类
        Object target = joinPoint.getTarget();

        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        Method targetMethod = signature.getMethod();

        Log logAnnotation = targetMethod.getAnnotation(Log.class);

        if (logAnnotation != null) {
            //记录日志
            log.error(target.getClass().getSimpleName() + "  类开始执行目标方法" + targetMethod.getName() + "执行时间" + DateUtils.format(new Date()));
        }

        Object proceed = null;
        try {
            proceed = joinPoint.proceed();
        }catch (Throwable e) {
            e.printStackTrace();
        }

        log.error(target.getClass().getSimpleName() + "  类执行完目标方法" + targetMethod.getName() + "执行时间" + DateUtils.format(new Date()));

        return proceed;
    }*/
}
