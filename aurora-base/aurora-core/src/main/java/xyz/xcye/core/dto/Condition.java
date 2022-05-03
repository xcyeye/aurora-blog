package xyz.xcye.core.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import xyz.xcye.core.constant.PaginationConstant;
import xyz.xcye.core.util.DateUtils;
import xyz.xcye.core.util.NameUtils;

import java.util.Optional;

/**
 * 这是查询的条件
 * @author qsyyke
 */

@ToString
@Getter
public class Condition<T> {
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

    public Condition() {
        this.init();
    }

    public void setStartTime(String startTime) {
        this.startTime = this.initTime(startTime);
    }

    public void setEndTime(String endTime) {
        this.endTime = this.initTime(endTime);
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = NameUtils.getUnderlineName(orderBy);
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = Optional.ofNullable(pageNum).orElse(PaginationConstant.PAGE_NUM);
    }

    public void setPageSize(Integer pageSize) {
        if (pageSize != null && pageSize > PaginationConstant.NAX_PAGE_SIZE) {
            // 超过最大size长度
            pageSize = PaginationConstant.NAX_PAGE_SIZE;
        }
        this.pageSize = Optional.ofNullable(pageSize).orElse(PaginationConstant.PAGE_SIZE);
    }

    /**
     * 初始化开始时间条件，如果时间不是yyyy-MM-dd HH:mm:ss格式，则修改成这种格式
     */
    private String initTime(String dateStr) {
        return DateUtils.parse(dateStr);
    }

    /**
     * 初始化查询条件，防止一些数据被返回
     * @param
     * @return
     */
    public void init() {
        this.status = Optional.ofNullable(this.status).orElse(true);
        this.show = Optional.ofNullable(this.show).orElse(true);
        this.delete = Optional.ofNullable(this.delete).orElse(false);
        // 设置分页的数据
        this.pageNum = Optional.ofNullable(this.pageNum).orElse(PaginationConstant.PAGE_NUM);
        this.pageSize = Optional.ofNullable(this.pageSize).orElse(PaginationConstant.PAGE_SIZE);
        this.orderBy = Optional.ofNullable(this.orderBy).orElse(PaginationConstant.ORDER_BY);
    }

    /**
     * 根据uid，创建一个条件实例
     * @param uid
     * @param uidType
     * @param <T>
     * @param isUid true表示是uid字段，false表示是otherUid字段
     * @return
     */
    public static <T> Condition<T> instant(T uid, Class<T> uidType, boolean isUid) {
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
     * @param uidType
     * @param <T>
     * @return
     */
    public static <T> Condition<T> instant(String keyword, Class<T> uidType) {
        Condition<T> condition = new Condition<>();
        condition.setKeyword(keyword);
        return condition;
    }
}
