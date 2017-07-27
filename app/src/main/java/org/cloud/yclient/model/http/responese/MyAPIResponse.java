package org.cloud.yclient.model.http.responese;

/**
 * @author d05660ddw
 * @version 1.0 2017/7/8
 */

public class MyAPIResponse<T> {
    private int size;
    private int page;
    private int total;

    //用来模仿Data
    private T items;

    public T getItems() {
        return items;
    }

    public void setItems(T items) {
        this.items = items;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }


    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("size=" + size + " page=" + page);
        if (null != items) {
            sb.append(" subjects:" + items.toString());
        }
        return sb.toString();
    }
}
