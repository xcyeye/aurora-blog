package xyz.xcye.core.entity;

import lombok.Data;

import java.util.List;

@Data
public class PageData<T>{
    /**
     * 总数
     */
    private long total;
    /**
     * 总页数
     */
    private int pages;


    private List<T> result;

    public static <T> PageData<T> getInstance(List<T> res){
        PageData<T> data = new PageData<>();
        data.setTotal(res.size());
        data.setPages(1);
        data.setResult(res);
        return data;
    }

}