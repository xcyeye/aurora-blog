package xyz.xcye.aurora;

import xyz.xcye.common.entity.table.UserDO;

import java.util.Optional;

/**
 * @author qsyyke
 * @date Created in 2022/4/30 17:03
 */


public class Test<T> {
    public static void main(String[] args) {
        UserDO userDO = new UserDO();
        userDO.setUid(3L);
        userDO.setUid(Optional.ofNullable(userDO.getUid()).orElse(38456L));

        System.out.println(userDO.getUid());

        System.out.println(348763476);
        /*TransactionalTemplate template = new TransactionalTemplate();
        template.execute(new TransactionalExecutor() {
            @Override
            public Object execute() throws Throwable {
                System.out.println("执行ex");
                return null;
            }

            @Override
            public TransactionInfo getTransactionInfo() {
                System.out.println(34);
                return null;
            }
        });*/
        //log.info("创建一个全局事务{},{}", RootContext.inGlobalTransaction(), RootContext.getXID());
        //GlobalTransactionContext.getCurrent().begin();


        /*log.info("最开始的xid{}，是否绑定{}",RootContext.getXID(),RootContext.inGlobalTransaction());
        TransactionManager tm = TransactionManagerHolder.get();
        GlobalTransaction create = GlobalTransactionContext.getCurrentOrCreate();
        try {
            create.begin();
        } catch (TransactionException e) {
            e.printStackTrace();
        }*/
        /*String xid = null;
        try {
            xid = tm.begin("aurora-comment", "default", "comment", 10000);
        } catch (TransactionException e) {
            e.printStackTrace();
        }
        log.info("tm开启{}",xid);*/
        //log.info("是否绑定{},xid{}",RootContext.inGlobalTransaction(),RootContext.getXID());
    }
}
