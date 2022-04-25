package xyz.xcye.common.dto;

import lombok.Data;
import xyz.xcye.common.constant.PaginationConstant;
import xyz.xcye.common.util.DateUtils;
import xyz.xcye.common.util.NameUtils;

/**
 * 这是查询的条件
 * @author qsyyke
 */

@Data
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
    private Long uid;

    /**
     * 其他的uid
     */
    private T otherUid;

    /**
     * 是否显示
     */
    private Boolean show;

    /**
     * 状态，比如发送状态，发布状态
     */
    private Boolean status;

    /**
     * 查询的关键词
     */
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

    public void setStartTime(String startTime) {
        this.startTime = this.initTime(startTime);
    }

    public void setEndTime(String endTime) {
        this.endTime = this.initTime(endTime);
    }

    /**
     * 初始化排序字段
     * @param orderBy
     */
    public void setOrderBy(String orderBy) {
        if (orderBy != null) {
            orderBy = NameUtils.getUnderlineName(orderBy);
        }else {
            orderBy = NameUtils.getUnderlineName(PaginationConstant.ORDER_BY);
        }
        this.orderBy = orderBy;
    }

    public void setPageNum(Integer pageNum) {
        if (pageNum == null) {
            pageNum = PaginationConstant.PAGE_NUM;
        }
        this.pageNum = pageNum;
    }

    public void setPageSize(Integer pageSize) {
        if (pageSize == null) {
            pageSize = PaginationConstant.PAGE_SIZE;
        }
        this.pageSize = pageSize;
    }

    /**
     * 初始化开始时间条件，如果时间不是yyyy-MM-dd HH:mm:ss格式，则修改成这种格式
     */
    private String initTime(String dateStr) {
        return DateUtils.parse(dateStr);
    }

    public static ConditionDTO init(ConditionDTO condition) {
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
