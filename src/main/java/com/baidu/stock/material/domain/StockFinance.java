package com.baidu.stock.material.domain;

import java.io.Serializable;

public class StockFinance  implements Serializable {
	
	private static final long serialVersionUID = -2529457967762827850L;
	//股票编码
	private String stockCode;
	//市场
	private String exchange;
	//总股本
	private long totalAmount;
	//流通A股
	private long aamount;
	//每股净资产
	private float netAssets;
	//每股收益
	private float stockProfit;
	//每股销售额
	private float sellAmountPS;
	//财报日期
	private String date;
	//去年度净利润
	private float lyr;
	//近12个月净利润
	private float ttm;
	//最新年化净利润
	private float mrq;
	public String getStockCode() {
		return stockCode;
	}
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}
	public String getExchange() {
		return exchange;
	}
	public void setExchange(String exchange) {
		this.exchange = exchange;
	}
	public long getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(long totalAmount) {
		this.totalAmount = totalAmount;
	}
	public long getAamount() {
		return aamount;
	}
	public void setAamount(long aamount) {
		this.aamount = aamount;
	}
	public float getNetAssets() {
		return netAssets;
	}
	public void setNetAssets(float netAssets) {
		this.netAssets = netAssets;
	}
	public float getStockProfit() {
		return stockProfit;
	}
	public void setStockProfit(float stockProfit) {
		this.stockProfit = stockProfit;
	}
	public float getSellAmountPS() {
		return sellAmountPS;
	}
	public void setSellAmountPS(float sellAmountPS) {
		this.sellAmountPS = sellAmountPS;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public float getLyr() {
		return lyr;
	}
	public void setLyr(float lyr) {
		this.lyr = lyr;
	}
	public float getTtm() {
		return ttm;
	}
	public void setTtm(float ttm) {
		this.ttm = ttm;
	}
	public float getMrq() {
		return mrq;
	}
	public void setMrq(float mrq) {
		this.mrq = mrq;
	}
	public String toString() {
		return "StockFinance [stockCode=" + stockCode + ", exchange="
				+ exchange + ", totalAmount=" + totalAmount + ", aamount="
				+ aamount + ", netAssets=" + netAssets + ", stockProfit="
				+ stockProfit + ", sellAmountPS=" + sellAmountPS + ", date="
				+ date + ", lyr=" + lyr + ", ttm=" + ttm + ", mrq=" + mrq + "]";
	}
	
	
}
