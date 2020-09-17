package com.ph.pojo;

/**
 * @author penghong
 * @date 2020/9/179:31
 */
import java.util.List;
public class Pager<T> {
    private List<T> datas;
    //初始页
    private int offset;
    //每页记录数
    private int size;
    private int totalSize;
    private long total;

    public List<T> getDatas() {
        return datas;
    }
    public void setDatas(List<T> datas) {
        this.datas = datas;
    }
    public int getOffset() {
        return offset;
    }
    public void setOffset(int offset) {
        this.offset = offset;
    }
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
    public int getTotalSize() {
        return totalSize;
    }
    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }
    public long getTotal() {
        return total;
    }
    public void setTotal(long total) {
        this.total = total;
    }
}
