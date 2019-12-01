package com.fh.commons;

import java.io.Serializable;

public class Page implements Serializable {
    //当前页
    private int page;
    //每页条数
    private int size;
    //开始下标
    private int start;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
