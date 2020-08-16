package com.taotao.common.vo;

import java.io.Serializable;
import java.util.List;

/**
 * EasyUI的DataGrid组件响应数据对应的类
 * @author LEE.SIU.WAH
 * @email lixiaohua7@163.com
 * @date 2017年11月26日 下午3:25:16
 * @version 1.0
 */
public class DataGridResult implements Serializable {
	
	private static final long serialVersionUID = 4762313204464937958L;
	/** 定义总记录数 */
	private Long total = 0L;
	/** 定义组件行中的数据 */
	private List<?> rows;
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public List<?> getRows() {
		return rows;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}
}