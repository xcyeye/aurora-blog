package xyz.xcye.common.constant;

/**
 * 和seata相关的常量
 * @author qsyyke
 */


public class SeataConstant {
    /** 如果需要seata进行跨服务传递，那么请求头中存放xid的header名字 **/
    public static final String GLOBAL_XID_REQUEST_HEADER_NAME = "transactionalXid";
}
