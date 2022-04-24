package xyz.xcye.common.dto;

import lombok.Builder;
import lombok.Data;
import xyz.xcye.common.util.DateUtils;

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
     * 是否删除
     */
    private Boolean show;

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
        System.out.println("set方法");
        this.startTime = this.initTime(startTime);
    }

    public void setEndTime(String endTime) {
        this.endTime = this.initTime(endTime);
    }

    /**
     * 初始化开始时间条件，如果时间不是yyyy-MM-dd HH:mm:ss格式，则修改成这种格式
     */
    private String initTime(String dateStr) {
        return DateUtils.parse(dateStr);
    }

    public void init() {
        if (this.show == null) {
            this.show = false;
        }
    }
}
