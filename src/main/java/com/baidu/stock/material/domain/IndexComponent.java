package com.baidu.stock.material.domain;

import java.io.Serializable;
import java.util.List;

public class IndexComponent implements Serializable {

	private static final long serialVersionUID = -3049042202377304052L;
	
	private String exchange;
	private String indexCode;
	
	private List<String> stockCodes;


	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	public String getIndexCode() {
		return indexCode;
	}

	public void setIndexCode(String indexCode) {
		this.indexCode = indexCode;
	}

	public List<String> getStockCodes() {
		return stockCodes;
	}

	public void setStockCodes(List<String> stockCodes) {
		this.stockCodes = stockCodes;
	}
	
	public void setStockCode(String stockCode) {
		stockCodes.add(stockCode) ;
	}
	
}
