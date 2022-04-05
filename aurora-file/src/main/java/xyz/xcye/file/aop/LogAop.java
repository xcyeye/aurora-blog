package xyz.xcye.file.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import xyz.xcye.common.util.ObjectConvertJson;

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
        //方法开始执行的时间戳
        long startTimeMillis = System.currentTimeMillis();

        //执行的目标controller类
        Signature signature = point.getSignature();

        //controller控制器类全限定名称
        String declaringTypeName = signature.getDeclaringTypeName();
        //所有参数
        Object[] args = point.getArgs();
        Object proceed = point.proceed();

        String jsonToString = ObjectConvertJson.jsonToString(args);

        log.info("访问{}类中的{}方法，参数为{},总共花费{}ms",declaringTypeName,"",jsonToString,(System.currentTimeMillis() - startTimeMillis));
        return proceed;
    }
}
