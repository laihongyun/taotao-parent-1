package com.taotao.common.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @program: taotao-admin
 * @description: 通用查询结果接受类--页面类
 * @author: lhy
 * @create: 2020-07-17 17:41
 **/
public class DataGridResult implements Serializable {

    private long total;
    private List<?> rows;

    public DataGridResult() {
    }

    public DataGridResult(long total, List<?> rows) {
        this.total = total;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "DataGridResult{" +
                "total=" + total +
                ", rows=" + rows +
                '}';
    }
}
