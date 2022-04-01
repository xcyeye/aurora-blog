package xyz.xcye.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.logging.Logger;

/**
 * @author qsyyke
 */

@Slf4j
@Component
@Aspect
public class LogAop {

    //private Logger logger = (Logger) LoggerFactory.getLogger("sdf");

    @Around("execution(public * xyz.xcye.controller.*.*(..))")
    public Object noteLog(ProceedingJoinPoint point) throws Throwable {
        //logger.info("索拉卡粉红色的开发和电饭锅考虑收到法国航空");
        String name = point.getTarget().getClass().getName();
        long currentTimeMillis = System.currentTimeMillis();
        Object proceed = point.proceed();
        log.info("{}方法执行结束，执行耗时{}",name,(System.currentTimeMillis() - currentTimeMillis));
        return proceed;
    }
}
