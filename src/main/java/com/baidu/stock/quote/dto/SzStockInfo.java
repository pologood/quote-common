package com.baidu.stock.quote.dto;

import java.io.Serializable;

/**
 * 股票信息（股票代码表的每一个数据单位）
 */
public class SzStockInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private StockBasic stockBasic = new StockBasic();
	private String date;
    // 证券代码源
    private String codeSource;
    // 昨收盘价
    private String preClose;
    // 币种
    private String currency;
    // 证券英文简称
    private String enName;
    // 上市日期
    private String listDate;
    // 数量单位
    private String qulityUnit;
    // 基础证券代码
    private String underlyingSecurityID;
    // 基础证券代码源
    private String underlyingSecurityIDSource;
    // 是否可作为融资融券可冲抵保证金证券
    private String gageFlag;
    // 是否为融资标的
    private String crdBuyUnderlying;
    // 是否为融券标的
    private String crdSellUnderlying;
    // T-1日基金净值
    private String fundNet;

    public StockBasic getStockBasic() {
		return stockBasic;
	}

	public void setStockBasic(StockBasic stockBasic) {
		this.stockBasic = stockBasic;
	}

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPreClose() {
        return preClose;
    }

    public void setPreClose(String preClose) {
        this.preClose = preClose;
    }

    public String getListDate() {
        return listDate;
    }

    public void setListDate(String listDate) {
        this.listDate = listDate;
    }

    public String getQulityUnit() {
        return qulityUnit;
    }

    public void setQulityUnit(String qulityUnit) {
        this.qulityUnit = qulityUnit;
    }

    public String getUnderlyingSecurityID() {
        return underlyingSecurityID;
    }

    public void setUnderlyingSecurityID(String underlyingSecurityID) {
        this.underlyingSecurityID = underlyingSecurityID;
    }

    public String getUnderlyingSecurityIDSource() {
        return underlyingSecurityIDSource;
    }

    public void setUnderlyingSecurityIDSource(String underlyingSecurityIDSource) {
        this.underlyingSecurityIDSource = underlyingSecurityIDSource;
    }

    public String getGageFlag() {
        return gageFlag;
    }

    public void setGageFlag(String gageFlag) {
        this.gageFlag = gageFlag;
    }

    public String getCrdBuyUnderlying() {
        return crdBuyUnderlying;
    }

    public void setCrdBuyUnderlying(String crdBuyUnderlying) {
        this.crdBuyUnderlying = crdBuyUnderlying;
    }

    public String getCrdSellUnderlying() {
        return crdSellUnderlying;
    }

    public void setCrdSellUnderlying(String crdSellUnderlying) {
        this.crdSellUnderlying = crdSellUnderlying;
    }

    public String getFundNet() {
        return fundNet;
    }

    public void setFundNet(String fundNet) {
        this.fundNet = fundNet;
    }

	public String getCodeSource() {
		return codeSource;
	}

	public void setCodeSource(String codeSource) {
		this.codeSource = codeSource;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
}