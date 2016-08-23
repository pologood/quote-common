package com.baidu.stock.material.domain;

import java.io.Serializable;

public class StockStopInfo  implements Serializable  {

	private static final long serialVersionUID = 3903080706610364888L;
	//编码
	private String stockCode;
	//市场
	private String exchange;
	//停牌开始时间
	private Integer stopStartDate;
	//停牌结束时间
	private Integer stopEndDate;
	//停牌提示信息
	private String stopInfo;
	
	public String getStockCode() {
		return stockCode;
	}
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}
	public Integer getStopStartDate() {
		return stopStartDate;
	}
	public void setStopStartDate(Integer stopStartDate) {
		this.stopStartDate = stopStartDate;
	}
	public Integer getStopEndDate() {
		return stopEndDate;
	}
	public void setStopEndDate(Integer stopEndDate) {
		this.stopEndDate = stopEndDate;
	}
	public String getStopInfo() {
		return stopInfo;
	}
	public void setStopInfo(String stopInfo) {
		this.stopInfo = stopInfo;
	}
	public String getExchange() {
		return exchange;
	}
	public void setExchange(String exchange) {
		this.exchange = exchange;
	}
	
	
	
	
	
}
