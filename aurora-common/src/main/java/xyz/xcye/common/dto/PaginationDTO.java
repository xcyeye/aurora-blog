package xyz.xcye.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.xcye.common.util.NameUtils;

/**
 * 分页实体
 * @author qsyyke
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaginationDTO {
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

    /**
     * 初始化Pagination对象，也就是设置判断一下属性值是否有效，对驼峰命名的字段转换成下划线
     * @param pagination
     * @param defaultPageNum
     * @param defaultPageSize
     * @return
     */
    public static PaginationDTO initPagination(PaginationDTO pagination, int defaultPageNum, int defaultPageSize) {
        if (pagination == null) {
            pagination = new PaginationDTO();
        }
        if (pagination.getPageNum() == 0) {
            pagination.setPageNum(defaultPageNum);
        }

        if (pagination.getPageSize() == 0) {
            pagination.setPageSize(defaultPageSize);
        }

        //将orderBy中的驼峰命名转换为下划线
        if (pagination.getOrderBy() != null) {
            pagination.setOrderBy(NameUtils.getUnderlineName(pagination.getOrderBy()));
        }else {
            pagination.setOrderBy(NameUtils.getUnderlineName(""));
        }
        return pagination;
    }
}
