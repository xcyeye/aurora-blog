package xyz.xcye.data.entity;

import lombok.Data;

import java.util.List;

@Data
public class PageData<T> {
    /**
     * 总数
     */
    private Long total;
    /**
     * 总页数
     */
    private Integer pages;

    private Integer pageSize;
    private Integer pageNum;

    private List<T> result;

    public static <T> PageData<T> getInstance(List<T> res) {
        PageData<T> data = new PageData<>();
        data.setTotal((long) res.size());
        data.setPages(1);
        data.setResult(res);
        return data;
    }

    @Override
    public String toString() {
        return "PageData{" +
                "total=" + total +
                ", pages=" + pages +
                ", result=" + result +
                '}';
    }
}