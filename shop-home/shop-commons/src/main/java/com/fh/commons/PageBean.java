package com.fh.commons;

public class PageBean {
    //当前页
    private int draw;
    //开始下标
    private int start;
    //每页条数
    private int length;

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
