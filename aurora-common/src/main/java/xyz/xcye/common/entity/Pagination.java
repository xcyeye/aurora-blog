package xyz.xcye.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.xcye.common.util.NameUtil;

/**
 * 分页实体
 * @author qsyyke
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Pagination {
    /**
     * 查询时页数
     */
    private int pageNum;
    /**
     * 查询数据时，每页返回多少条记录
     */
    private int pageSize;
    /**
     * 排序字段
     */
    private String orderBy;
}
