package xyz.xcye.aurora.manager.aop;

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
public class AuroraGlobalLogAop {

    /**
     * 记录所有插入数据的日志
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(public * xyz.xcye.*.service..*.insert*(..))")
    public Object saveInsertLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();

        // 方法的参数
        Object[] args = proceedingJoinPoint.getArgs();
        // 打印插入日志
        log.info(LogUtils.insert(0,0,false, false,args));

        //记录时间
        Object result = proceedingJoinPoint.proceed();

        if (result instanceof ModifyResult) {
            ModifyResult modifyResult = (ModifyResult) result;
            if (modifyResult.isSuccess()) {
                log.info(LogUtils.insert(modifyResult.getUid(),modifyResult.getAffectedRows(),true, true, args));
            }else {
                log.info(LogUtils.insert(modifyResult.getUid(),modifyResult.getAffectedRows(),true, false, modifyResult.getMessage()));
            }
        }else {
            // 返回值不是ModifyResult类型
            log.info(LogUtils.insert(0,0,true, true,result));
        }
        return result;
    }

    /**
     * 记录所有更新数据的日志
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(public * xyz.xcye.*.service..*.update*(..))")
    public Object saveUpdateLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 方法的参数
        Object[] args = proceedingJoinPoint.getArgs();
        // 打印插入日志
        log.info(LogUtils.update(0,0,false, true, args));

        Object result = proceedingJoinPoint.proceed();

        if (result instanceof ModifyResult) {
            ModifyResult modifyResult = (ModifyResult) result;
            if (modifyResult.isSuccess()) {
                log.info(LogUtils.update(modifyResult.getUid(),modifyResult.getAffectedRows(),true, true,args));
            }else {
                log.info(LogUtils.update(modifyResult.getUid(),modifyResult.getAffectedRows(),true, false,modifyResult.getMessage()));
            }
        }else {
            // 返回值不是ModifyResult类型
            log.info(LogUtils.update(0,0,true, true, result));
        }
        return result;
    }

    /**
     * 记录所有删除数据的日志
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(public * xyz.xcye.*.service..*.delete*(..))")
    public Object saveDeleteLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 方法的参数
        Object[] args = proceedingJoinPoint.getArgs();
        // 打印插入日志
        Object result = proceedingJoinPoint.proceed();

        if (result instanceof ModifyResult) {
            ModifyResult modifyResult = (ModifyResult) result;
            if (modifyResult.isSuccess()) {
                log.info(LogUtils.delete(0,true, true,modifyResult.getAffectedRows()));
            }else {
                log.info(LogUtils.delete(0,true, false, 0,modifyResult.getMessage()));
            }
        }else {
            // 返回值不是ModifyResult类型
            log.info(LogUtils.delete(0,true, true,1));
        }
        return result;
    }

    /**
     * 记录所有更新数据的日志
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    //@Around("execution(public * *..service..query*(..))")
    @Around("execution(public * xyz.xcye.*.service..*.query*(..))")
    public Object saveQueryLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 方法的参数
        Object[] args = proceedingJoinPoint.getArgs();
        // 打印插入日志
        log.info(LogUtils.query(args));
        return proceedingJoinPoint.proceed();
    }
}
