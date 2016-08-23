package com.baidu.stock.material.domain;

import java.io.Serializable;

public class StockInform implements Serializable {

	private static final long serialVersionUID = 2151905266314808427L;

	private String inform = null ;
	private Long updatetime = null;
	
	public String getInform() {
		return inform;
	}
	public void setInform(String inform) {
		this.inform = inform;
	}
	public Long getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Long updatetime) {
		this.updatetime = updatetime;
	}
	
	
}
