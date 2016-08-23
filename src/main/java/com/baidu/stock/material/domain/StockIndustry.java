package com.baidu.stock.material.domain;

import java.io.Serializable;
import java.util.List;

public class StockIndustry  implements Serializable  {

	private static final long serialVersionUID = -8879992908728624231L;
	
	private String industryName;
	private String industryCode;
	
	private List<String> stockCodes;

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public String getIndustryCode() {
		return industryCode;
	}

	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
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
