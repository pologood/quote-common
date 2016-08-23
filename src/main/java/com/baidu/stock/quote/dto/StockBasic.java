package com.baidu.stock.quote.dto;

import com.baidu.stock.common.AssetType;
import com.baidu.stock.common.StockStatusType;

/**
 * 股票基本信息
 * 
 * @author zhangxinyu03
 */

public class StockBasic {
    // 交易所
    private String exchange = "";
    // 证券代码
    private String stockCode = "";
    // 证券名称
    private String stockName = "";
    // 0:停盘 1:退市 2:正常 3:未上市
    private int stockStatus = StockStatusType.NORMAL.value();
    // 0:股票 1:期货 2:期权 3:外汇 4指数 5:场内基金 6:债券、7:认购权证 8:认沽权证 9: 牛证 10:熊证
    // 11:其他 12:表示场外 13:表示货币 14:表示B股 15:回购 16:场内的货币基金 17:港股基金
    private int asset = AssetType.A_STOCK.value();

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public int getStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(int stockStatus) {
        this.stockStatus = stockStatus;
    }

    public int getAsset() {
        return asset;
    }

    public void setAsset(int asset) {
        this.asset = asset;
    }
}
