package com.baidu.stock.material.domain;

import java.io.Serializable;
import java.util.List;

public class StockConcept  implements Serializable  {
	
	private static final long serialVersionUID = 7534592724567242377L;
	
	private String conceptName;
	private String conceptCode;
	private String conceptDesc;
	
	private List<String> stockCodes;

	public String getConceptName() {
		return conceptName;
	}

	public void setConceptName(String conceptName) {
		this.conceptName = conceptName;
	}

	public String getConceptCode() {
		return conceptCode;
	}

	public void setConceptCode(String conceptCode) {
		this.conceptCode = conceptCode;
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

	public String getConceptDesc() {
		return conceptDesc;
	}

	public void setConceptDesc(String conceptDesc) {
		this.conceptDesc = conceptDesc;
	}
	
}
