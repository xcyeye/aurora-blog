package xyz.xcye.aurora.manager.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 记录所有controller的执行时间
 * @author qsyyke
 */

@Slf4j
@Component
@Aspect
public class AuroraGlobalLogRequestAop {

    /**
     * 打印处理请求耗时
     * @param point
     * @return
     */
    @Around("execution(public * xyz.xcye.*.controller..*(..))")
    public Object logProcessRequestTime(ProceedingJoinPoint point) throws Throwable {

        // 开始时间
        long startTimeMillis = System.currentTimeMillis();

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        // 获取执行方法
        MethodSignature signature = (MethodSignature) point.getSignature();
        String methodName = signature.getMethod().getName();
        // 获取请求地址和请求类型
        String uri = "";
        String requestMethod= "";
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            uri = request.getRequestURI();
            requestMethod = request.getMethod();
        }

        // 获取方法参数
        Object[] args = point.getArgs();
        StringBuilder builder = new StringBuilder();
        for (Object arg : args) {
            String simpleName = arg.getClass().getSimpleName();
            builder.append(simpleName);
            builder.append(",");
        }

        String allArgTypes = builder + "";
        if (!"".equals(allArgTypes)) {
            allArgTypes = allArgTypes.substring(0, builder.length() - 1);
        }

        Object result = point.proceed();
        // 结束时间
        long endTimeMillis = System.currentTimeMillis();
        log.info("请求{}({}) 被{}({})方法处理，最终耗时{} (ms)", uri, requestMethod, methodName, allArgTypes, (endTimeMillis - startTimeMillis));
        return result;
    }
}
