package xyz.xcye.mybatis.entity;

import java.util.List;

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

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
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