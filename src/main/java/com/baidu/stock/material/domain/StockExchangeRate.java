package com.baidu.stock.material.domain;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class StockExchangeRate  implements Serializable  {

	private static final long serialVersionUID = 858719159432066663L;
	//美元
	private double usd;
	//港元
	private double hkd;
	//公布日期
	private Date publicDate;
	
	
	public double getUsd() {
		return usd;
	}
	public void setUsd(double usd) {
		this.usd = usd;
	}
	public double getHkd() {
		return hkd;
	}
	public void setHkd(double hkd) {
		this.hkd = hkd;
	}
	@JSONField (format="yyyy-MM-dd HH:mm:ss")  
	public Date getPublicDate() {
		return publicDate;
	}
	public void setPublicDate(Date publicDate) {
		this.publicDate = publicDate;
	}
	
	
}
