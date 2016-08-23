package com.baidu.stock.material.domain;

import java.io.Serializable;
import java.util.List;

public class StockPlate  implements Serializable  {
	private static final long serialVersionUID = 8517689874203559644L;
	
	private String plateName;
	private String plateCode;
	
	private List<String> stockCodes;

	public String getPlateName() {
		return plateName;
	}

	public void setPlateName(String plateName) {
		this.plateName = plateName;
	}

	public String getPlateCode() {
		return plateCode;
	}

	public void setPlateCode(String plateCode) {
		this.plateCode = plateCode;
	}

	public List<String> getStockCodes() {
		return stockCodes;
	}

	public void setStockCodes(List<String> stockCodes) {
		this.stockCodes = stockCodes;
	}
	
	
}
