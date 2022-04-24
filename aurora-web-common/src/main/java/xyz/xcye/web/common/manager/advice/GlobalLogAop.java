package xyz.xcye.web.common.manager.advice;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.util.LogUtils;

import java.lang.reflect.Method;

/**
 * 这是一个日志功能的aop
 * @author qsyyke
 */

@Slf4j
@Component
@Aspect
public class GlobalLogAop {

    //private Logger logger = (Logger) LoggerFactory.getLogger("sdf");

    /*@Around("execution(public * xyz.xcye.admin.controller.*.*(..))")
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
    }*/

    /**
     * 记录所有插入数据的日志
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(public * *..service..insert*(..))")
    public Object saveInsertLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        String className = proceedingJoinPoint.getTarget().getClass().getName();
        String methodName = method.getName();

        // 方法的参数
        Object[] args = proceedingJoinPoint.getArgs();
        // 打印插入日志
        log.info(LogUtils.insert(0,0,false,args));

        //记录时间
        Object result = proceedingJoinPoint.proceed();

        if (result instanceof ModifyResult) {
            ModifyResult modifyResult = (ModifyResult) result;
            log.info(LogUtils.insert(modifyResult.getUid(),modifyResult.getAffectedRows(),true,args));
        }else {
            // 返回值不是ModifyResult类型
            log.info(LogUtils.insert(0,0,true,result));
        }
        return result;
    }

    /**
     * 记录所有更新数据的日志
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(public * *..service..update*(..))")
    public Object saveUpdateLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 方法的参数
        Object[] args = proceedingJoinPoint.getArgs();
        // 打印插入日志
        log.info(LogUtils.update(0,0,false,args));

        Object result = proceedingJoinPoint.proceed();

        if (result instanceof ModifyResult) {
            ModifyResult modifyResult = (ModifyResult) result;
            log.info(LogUtils.update(modifyResult.getUid(),modifyResult.getAffectedRows(),true,args));
        }else {
            // 返回值不是ModifyResult类型
            log.info(LogUtils.update(0,0,true,result));
        }
        return result;
    }

    /**
     * 记录所有删除数据的日志
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(public * *..service..delete*(..))")
    public Object saveDeleteLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 方法的参数
        Object[] args = proceedingJoinPoint.getArgs();
        // 打印插入日志
        Object result = proceedingJoinPoint.proceed();

        if (result instanceof ModifyResult) {
            ModifyResult modifyResult = (ModifyResult) result;
            log.info(LogUtils.delete(0,true,modifyResult.getAffectedRows()));
        }else {
            // 返回值不是ModifyResult类型
            log.info(LogUtils.delete(0,true,1));
        }
        return result;
    }

    /**
     * 记录所有更新数据的日志
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(public * *..service..query*(..))")
    public Object saveQueryLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 方法的参数
        Object[] args = proceedingJoinPoint.getArgs();
        // 打印插入日志
        log.info(LogUtils.query(args));
        return proceedingJoinPoint.proceed();
    }
}
