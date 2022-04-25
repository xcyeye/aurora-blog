package xyz.xcye.common.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import xyz.xcye.common.constant.PaginationConstant;
import xyz.xcye.common.util.DateUtils;
import xyz.xcye.common.util.NameUtils;

import java.util.Objects;

/**
 * 这是查询的条件
 * @author qsyyke
 */

@ToString
@Getter
public class ConditionDTO<T> {
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
    @Accessors(chain = true)
    private Long uid;

    /**
     * 其他的uid
     */
    @Setter
    @Accessors(chain = true)
    private T otherUid;

    /**
     * 是否显示
     */
    @Setter
    @Accessors(chain = true)
    private Boolean show;

    /**
     * 状态，比如发送状态，发布状态
     */
    @Setter
    @Accessors(chain = true)
    private Boolean status;

    /**
     * 查询的关键词
     */
    @Setter
    @Accessors(chain = true)
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

    public ConditionDTO setStartTime(String startTime) {
        this.startTime = this.initTime(startTime);
        return this;
    }

    public ConditionDTO setEndTime(String endTime) {
        this.endTime = this.initTime(endTime);
        return this;
    }

    /**
     * 初始化排序字段
     * @param orderBy
     */
    public ConditionDTO setOrderBy(String orderBy) {
        orderBy = NameUtils.getUnderlineName(Objects.requireNonNullElse(orderBy, PaginationConstant.ORDER_BY));
        this.orderBy = orderBy;
        return this;
    }

    public ConditionDTO setPageNum(Integer pageNum) {
        if (pageNum == null) {
            pageNum = PaginationConstant.PAGE_NUM;
        }
        this.pageNum = pageNum;
        return this;
    }

    public ConditionDTO setPageSize(Integer pageSize) {
        if (pageSize == null) {
            pageSize = PaginationConstant.PAGE_SIZE;
        }
        this.pageSize = pageSize;
        return this;
    }

    /**
     * 初始化开始时间条件，如果时间不是yyyy-MM-dd HH:mm:ss格式，则修改成这种格式
     */
    private String initTime(String dateStr) {
        return DateUtils.parse(dateStr);
    }

    /**
     * 初始化查询条件，防止一些数据被返回
     * @param condition
     * @return
     */
    public ConditionDTO<T> init(ConditionDTO<T> condition) {
        if (condition == null) {
            condition = new ConditionDTO<>();
        }
        if (condition.status == null) {
            condition.setStatus(true);
        }
        if (condition.show == null) {
            condition.setShow(true);
        }
        if (condition.pageNum == null) {
            condition.setPageNum(0);
        }
        if (condition.pageSize == null) {
            condition.setPageSize(10);
        }
        return condition;
    }
}
