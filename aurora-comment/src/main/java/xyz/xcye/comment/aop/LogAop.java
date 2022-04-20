package xyz.xcye.comment.aop;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.RequestFacade;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import xyz.xcye.common.util.ObjectConvertJson;

import java.util.ArrayList;
import java.util.List;

/**
 * 这是一个日志功能的aop
 * @author qsyyke
 */

@Slf4j
@Component
@Aspect
public class LogAop {

    //private Logger logger = (Logger) LoggerFactory.getLogger("sdf");

    @Around("execution(public * xyz.xcye.comment.controller.*.*(..))")
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

        List<Object> argList = new ArrayList<>();
        for (Object arg : args) {
            if (!(arg instanceof RequestFacade)) {
                argList.add(arg);
            }
        }

        String jsonToString = ObjectConvertJson.jsonToString(argList);

        log.info("访问{}类中的{}方法，参数为{},总共花费{}ms",declaringTypeName,"",jsonToString,(System.currentTimeMillis() - startTimeMillis));
        return proceed;
    }
}
