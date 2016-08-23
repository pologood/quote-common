/**
 * @author 作者 : 楚运动
 */
package com.baidu.stock.quote.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 股票证券实时状态
 * 
 * @author 楚运动
 */
public class SzRealtimeStatus implements Serializable {
	private StockBasic stockBasic = new StockBasic();
    // 成交时间（百分之一秒）14302506表示 14:30:25.06
    private String time;
    // 开关列表
    private List<SzSwitch> switchList;
    // 证券代码源
    private String codeSource;
    // 证券状态
    private String financialStatus;

    public StockBasic getStockBasic() {
		return stockBasic;
	}

	public void setStockBasic(StockBasic stockBasic) {
		this.stockBasic = stockBasic;
	}

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<SzSwitch> getSwitchList() {
        return switchList;
    }

    public void setSwitchList(List<SzSwitch> slist) {
        this.switchList = slist;
    }

	public String getCodeSource() {
		return codeSource;
	}

	public void setCodeSource(String codeSource) {
		this.codeSource = codeSource;
	}

	public String getFinancialStatus() {
		return financialStatus;
	}

	public void setFinancialStatus(String financialStatus) {
		this.financialStatus = financialStatus;
	}
}
