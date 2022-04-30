package xyz.xcye.web.common.manager.seata;

import io.seata.core.context.RootContext;
import io.seata.core.exception.TransactionException;
import io.seata.core.model.TransactionManager;
import io.seata.tm.DefaultTransactionManager;
import org.springframework.util.StringUtils;

/**
 * 因为如果我们在某个操作方法上，添加了@GlobalTransactional注解，可能会存在xid为null的情况，也就是没有绑定，这个时候
 * 我们就需要手动开启一个seata的全局事务
 * @author qsyyke
 * @date Created in 2022/4/28 20:21
 */


public class SeataInitTransaction {
    public static void init(String applicationId, String transactionServiceGroup, String name, int timeout)
            throws TransactionException {
        if (!StringUtils.hasLength(RootContext.getXID())) {
            // 没有开启全局事务，或者是使用了注解，但是xid为null
            TransactionManager tm = new DefaultTransactionManager();
            String xid = tm.begin(applicationId, transactionServiceGroup, name, timeout);
        }
    }
}
