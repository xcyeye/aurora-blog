package xyz.xcye.data.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

/**
 * 这是查询的条件
 * @author qsyyke
 */

@ToString
@Getter
public class Condition<T> {

    public static final Integer PAGE_NUM = 0;
    public static final Integer PAGE_SIZE = 10;
    public static final Integer NAX_PAGE_SIZE = 10000;
    public static final String ORDER_BY = "";

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 根据唯一id
     */
    @Setter
    private T uid;

    /**
     * 其他的uid
     */
    @Setter
    private T otherUid;

    /**
     * 是否显示
     */
    @Setter
    private Boolean show;

    /**
     * 状态，比如发送状态，发布状态
     */
    @Setter
    private Boolean status;

    /**
     * 是否删除，并不是物理删除，而是逻辑删除
     */
    @Setter
    private Boolean delete;

    /**
     * 查询的关键词
     */
    @Setter
    private String keyword;

    /**
     * 查询时页数
     */
    private Integer pageNum;

    /**
     * 查询数据时，每页返回多少条记录
     */
    private Integer pageSize;

    /**
     * 排序字段
     */
    private String orderBy;

    /**
     * 其他的字段
     */
    @Getter
    @Setter
    private Object otherField;

    public Condition() {
        this.init();
    }

    private void init() {
        this.pageNum = PAGE_NUM;
        this.pageSize = PAGE_SIZE;
        this.orderBy = ORDER_BY;
    }

    /**
     * 初始化开始时间条件，如果时间不是yyyy-MM-dd HH:mm:ss格式，则修改成这种格式
     */
    private String initTime(String dateStr) {
        return parse(dateStr);
    }

    /**
     * 根据uid，创建一个条件实例
     * @param uid
     * @param <T>
     * @param isUid true表示是uid字段，false表示是otherUid字段
     * @return
     */
    public static <T> Condition<T> instant(T uid, Boolean isUid, Boolean delete, Boolean status) {
        Condition<T> condition = new Condition<>();
        if (isUid) {
            condition.setUid(uid);
        }else {
            condition.setOtherUid(uid);
        }
        condition.setDelete(delete);
        condition.setStatus(status);
        return condition;
    }

    public static <T> Condition<T> instant(T uid, T otherUid) {
        Condition<T> condition = new Condition<>();

        condition.setUid(uid);
        condition.setOtherUid(otherUid);
        condition.setDelete(null);
        condition.setStatus(null);
        return condition;
    }

    public static <T> Condition<T> instant(T uid, Boolean isUid) {
        Condition<T> condition = new Condition<>();
        if (isUid) {
            condition.setUid(uid);
        }else {
            condition.setOtherUid(uid);
        }
        return condition;
    }

    /**
     * 根据keyword，创建一个条件实例
     * @param keyword
     * @param <T>
     * @return
     */
    public static <T> Condition<T> instant(String keyword, Boolean delete, Boolean status) {
        Condition<T> condition = new Condition<>();
        condition.setKeyword(keyword);
        condition.setDelete(delete);
        condition.setStatus(status);
        return condition;
    }

    public static <T> Condition<T> instant(String keyword) {
        Condition<T> condition = new Condition<>();
        condition.setKeyword(keyword);
        return condition;
    }


    private static String parse(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 先进行yyyy-MM-dd HH:mm:ss解析
        Date parse = null;
        try {
            parse = format.parse(dateStr);
        } catch (Exception e) {
            // 进行yyyy-MM-dd HH:mm解析
            try {
                parse = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dateStr);
            } catch (Exception ex) {
                // 进行yyyy-MM-dd解析
                try {
                    parse = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
                } catch (Exception exc) {
                    // 如果这是还是报错的话，则直接返回宇宙出生时间o(╥﹏╥)o
                    return format.format(new Date(0));
                }
            }
        }

        return format.format(parse);
    }




    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy.replaceAll("[A-Z]", "_$0").toLowerCase();
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = Optional.ofNullable(pageNum).orElse(PAGE_NUM);
    }

    public void setPageSize(Integer pageSize) {
        if (pageSize != null && pageSize > NAX_PAGE_SIZE) {
            // 超过最大size长度
            pageSize = NAX_PAGE_SIZE;
        }
        this.pageSize = Optional.ofNullable(pageSize).orElse(PAGE_SIZE);
    }

    public void setStartTime(String startTime) {
        this.startTime = parse(startTime);
    }

    public void setEndTime(String endTime) {
        this.endTime = parse(endTime);
    }
}
