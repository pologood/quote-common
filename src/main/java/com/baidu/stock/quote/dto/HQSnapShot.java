package com.baidu.stock.quote.dto;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.baidu.stock.util.HQConstant;
/**
 * 行情快照对象
 * 
 * @author dengjianli
 * 
 */
public class HQSnapShot implements Cloneable,Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 股票基本资料
	 */
	private StockBasic stockBasic = new StockBasic();
	
	/**
	 * 日期 格式yyyymmdd
	 */
	public String date;
	
	/**
	 * 时间 格式HHmmss
	 */
	public String time;

	/**
	 * 唯一标识。格式exchange_code
	 */
	public String compositID;
	/**
	 * 最新价
	 */
	public float close;

	/**
	 * 开盘价
	 */
	public float open;

	/**
	 * z最高价
	 */
	public float high;

	/**
	 * 最低价
	 */
	public float low;

	/**
	 * 昨收盘价
	 */
	public float preClose;

	/**
	 * 成交总量 (股)
	 * 
	 */
	public long volume;

	/**
	 * 成交笔数
	 */
	public int dealCount;

	/**
	 * 总金额
	 */
	public double amount;

	/**
	 * 买5档价
	 */
	public float[] buyp = new float[5];

	/**
	 * 买5档量
	 */
	public long[] buyv = new long[5];
	
	/**
	 * 买5档总委托笔数
	 */
	public long[] buyn = new long[5];

	/**
	 * 卖5档价
	 */
	public float[] sellp = new float[5];

	/**
	 * 卖5档量
	 */
	public long[] sellv = new long[5];
	
	/**
	 * 卖5档总委托笔数
	 */
	public long[] selln = new long[5];

	/**
	 * 总市值
	 */
	public double capitalization;

	/**
	 * 流通市值
	 */
	public double currencyValue;

	/**
	 * 换手率
	 */
	public float turnoverRatio;
	/**
	 * mrq市盈率
	 */
	public float mrqPeratio;
	/**
	 * TTM市盈率
	 */
	public float ttmPeratio;
	/**
	 * LYR市盈率
	 */
	public float lyrPeratio;
	/**
	 * 市净率
	 */
	public float bvRatio;
	/**
	 * 市销率
	 */
	public float psRatio;

	/**
	 * 量比
	 */
	public float volumeRatio;
	/**
	 * 委比
	 */
	public float weibiRatio;

	/**
	 * 涨跌额
	 */
	public float netChange;
	/**
	 * 涨跌幅
	 */
	public float netChangeRatio;
	/**
	 * 振幅
	 */
	public float amplitudeRatio;
	/**
	 * 涨停
	 */
	public float limitUp;
	/**
	 * 跌停
	 */
	public float limitDown;
	/**
	 * 总股本
	 */
	public long totalAmount;
	// 每股净资产
	public float netAssets;
	// 每股收益
	public float stockProfit;
	/**
	 * 流通股本
	 */
	public long tradeVolume;
	/**
	 * 涨跌平数量
	 */
	public int riseNum;
	public int fallNum;
	public int fairNum;
	// 大资金净流入
	public float bigNetflows = 0.00f;
	// 内盘
	public int inSide;
	// 外盘
	public int outSide;

	// 增量数
	public long nowVolume = 0;
	// 增量额
	public double nowAmount = 0.00f;

	public long startTimeStamp;// 测试用
	
	// -------------------------------------------------------------------------------------
	
	// 股票状态，标识熔断的数据
    private String phaseCode;
    // 行情类别
    private String streamId;
    // 证券代码源
    private String codeSource;
    // 频道代码
    private int channelNo;
    // 统计量指标样本个数
    private int stockNum;
    // 升跌1
    private double  riseAndFall1;
    // 升跌2
    private double  riseAndFall2;
    // 均买价
    private double  allBuyPrice;
    // 总买量
    private double  allBuyCount;
    // 均卖价
    private double  allSellPrice;
    // 总卖量
    private double  allSellCount;
    // 股票市盈率1
    private double  peRatio1;
    // 股票市盈率2
    private double  peRatio2;
    // 基金T-1日净值
    private double  netWorth;
    // 基金实时参考净值
    private double  consultNetWorth;
    // 合约持仓量
    private double  openPosition;
    
	public StockBasic getStockBasic() {
		return stockBasic;
	}

	public void setStockBasic(StockBasic stockBasic) {
		this.stockBasic = stockBasic;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getCompositID() {
		return compositID;
	}

	public void setCompositID(String compositID) {
		this.compositID =compositID;
	}

	public float getClose() {
		return close;
	}

	public void setClose(float close) {
		this.close = close;
	}

	public float getOpen() {
		return open;
	}

	public void setOpen(float open) {
		this.open = open;
	}

	public float getHigh() {
		return high;
	}

	public void setHigh(float high) {
		this.high = high;
	}

	public float getLow() {
		return low;
	}

	public void setLow(float low) {
		this.low = low;
	}

	public float getPreClose() {
		return preClose;
	}

	public void setPreClose(float preClose) {
		this.preClose = preClose;
	}

	public long getVolume() {
		return volume;
	}

	public void setVolume(long volume) {
		this.volume = volume;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public float[] getBuyp() {
		return buyp;
	}

	public void setBuyp(float[] buyp) {
		this.buyp = buyp;
	}

	public float[] getSellp() {
		return sellp;
	}

	public void setSellp(float[] sellp) {
		this.sellp = sellp;
	}

	public long[] getBuyv() {
		return buyv;
	}

	public void setBuyv(long[] buyv) {
		this.buyv = buyv;
	}

	public long[] getSellv() {
		return sellv;
	}

	public void setSellv(long[] sellv) {
		this.sellv = sellv;
	}

	public int getDealCount() {
		return dealCount;
	}

	public void setDealCount(int dealCount) {
		this.dealCount = dealCount;
	}

	public double getCapitalization() {
		return capitalization;
	}

	public void setCapitalization(double capitalization) {
		this.capitalization = capitalization;
	}

	public double getCurrencyValue() {
		return currencyValue;
	}

	public void setCurrencyValue(double currencyValue) {
		this.currencyValue = currencyValue;
	}

	public float getTurnoverRatio() {
		return turnoverRatio;
	}

	public void setTurnoverRatio(float turnoverRatio) {
		this.turnoverRatio = turnoverRatio;
	}

	public float getMrqPeratio() {
		return mrqPeratio;
	}

	public void setMrqPeratio(float mrqPeratio) {
		this.mrqPeratio = mrqPeratio;
	}

	public float getTtmPeratio() {
		return ttmPeratio;
	}

	public void setTtmPeratio(float ttmPeratio) {
		this.ttmPeratio = ttmPeratio;
	}

	public float getLyrPeratio() {
		return lyrPeratio;
	}

	public void setLyrPeratio(float lyrPeratio) {
		this.lyrPeratio = lyrPeratio;
	}

	public float getBvRatio() {
		return bvRatio;
	}

	public void setBvRatio(float bvRatio) {
		this.bvRatio = bvRatio;
	}

	public float getPsRatio() {
		return psRatio;
	}

	public void setPsRatio(float psRatio) {
		this.psRatio = psRatio;
	}

	public float getVolumeRatio() {
		return volumeRatio;
	}

	public void setVolumeRatio(float volumeRatio) {
		this.volumeRatio = volumeRatio;
	}

	public float getWeibiRatio() {
		return weibiRatio;
	}

	public void setWeibiRatio(float weibiRatio) {
		this.weibiRatio = weibiRatio;
	}

	public float getNetChange() {
		return netChange;
	}

	public void setNetChange(float netChange) {
		this.netChange = netChange;
	}

	public float getNetChangeRatio() {
		return netChangeRatio;
	}

	public void setNetChangeRatio(float netChangeRatio) {
		this.netChangeRatio = netChangeRatio;
	}

	public float getAmplitudeRatio() {
		return amplitudeRatio;
	}

	public void setAmplitudeRatio(float amplitudeRatio) {
		this.amplitudeRatio = amplitudeRatio;
	}

	public float getLimitUp() {
		return limitUp;
	}

	public void setLimitUp(float limitUp) {
		this.limitUp = limitUp;
	}

	public float getLimitDown() {
		return limitDown;
	}

	public void setLimitDown(float limitDown) {
		this.limitDown = limitDown;
	}

	public int getRiseNum() {
		return riseNum;
	}

	public void setRiseNum(int riseNum) {
		this.riseNum = riseNum;
	}

	public int getFallNum() {
		return fallNum;
	}

	public void setFallNum(int fallNum) {
		this.fallNum = fallNum;
	}

	public int getFairNum() {
		return fairNum;
	}

	public void setFairNum(int fairNum) {
		this.fairNum = fairNum;
	}

	public float getBigNetflows() {
		return bigNetflows;
	}

	public void setBigNetflows(float bigNetflows) {
		this.bigNetflows = bigNetflows;
	}

	public long getStartTimeStamp() {
		return startTimeStamp;
	}

	public void setStartTimeStamp(long startTimeStamp) {
		this.startTimeStamp = startTimeStamp;
	}

	public int getInSide() {
		return inSide;
	}

	public void setInSide(int inSide) {
		this.inSide = inSide;
	}

	public int getOutSide() {
		return outSide;
	}

	public long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public void setOutSide(int outSide) {
		this.outSide = outSide;
	}

	public long getTradeVolume() {
		return tradeVolume;
	}

	public void setTradeVolume(long tradeVolume) {
		this.tradeVolume = tradeVolume;
	}

	public long getNowVolume() {
		return nowVolume;
	}

	public void setNowVolume(long nowVolume) {
		this.nowVolume = nowVolume;
	}

	public double getNowAmount() {
		return nowAmount;
	}

	public void setNowAmount(double nowAmount) {
		this.nowAmount = nowAmount;
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

	public String toJSON() {
		return JSON.toJSONString(this);
	}

	public HQSnapShot clone() throws CloneNotSupportedException {
		HQSnapShot hq = (HQSnapShot) super.clone();
		return hq;
	}

	public long[] getBuyn() {
		return buyn;
	}

	public void setBuyn(long[] buyn) {
		this.buyn = buyn;
	}

	public long[] getSelln() {
		return selln;
	}

	public void setSelln(long[] selln) {
		this.selln = selln;
	}

	public String getPhaseCode() {
		return phaseCode;
	}

	public void setPhaseCode(String phaseCode) {
		this.phaseCode = phaseCode;
	}

	public String getStreamId() {
		return streamId;
	}

	public void setStreamId(String streamId) {
		this.streamId = streamId;
	}

	public String getCodeSource() {
		return codeSource;
	}

	public void setCodeSource(String codeSource) {
		this.codeSource = codeSource;
	}

	public int getChannelNo() {
		return channelNo;
	}

	public void setChannelNo(int channelNo) {
		this.channelNo = channelNo;
	}

	public int getStockNum() {
		return stockNum;
	}

	public void setStockNum(int stockNum) {
		this.stockNum = stockNum;
	}

	public double getRiseAndFall1() {
		return riseAndFall1;
	}

	public void setRiseAndFall1(double riseAndFall1) {
		this.riseAndFall1 = riseAndFall1;
	}

	public double getRiseAndFall2() {
		return riseAndFall2;
	}

	public void setRiseAndFall2(double riseAndFall2) {
		this.riseAndFall2 = riseAndFall2;
	}

	public double getAllBuyPrice() {
		return allBuyPrice;
	}

	public void setAllBuyPrice(double allBuyPrice) {
		this.allBuyPrice = allBuyPrice;
	}

	public double getAllBuyCount() {
		return allBuyCount;
	}

	public void setAllBuyCount(double allBuyCount) {
		this.allBuyCount = allBuyCount;
	}

	public double getAllSellPrice() {
		return allSellPrice;
	}

	public void setAllSellPrice(double allSellPrice) {
		this.allSellPrice = allSellPrice;
	}

	public double getAllSellCount() {
		return allSellCount;
	}

	public void setAllSellCount(double allSellCount) {
		this.allSellCount = allSellCount;
	}

	public double getPeRatio1() {
		return peRatio1;
	}

	public void setPeRatio1(double peRatio1) {
		this.peRatio1 = peRatio1;
	}

	public double getPeRatio2() {
		return peRatio2;
	}

	public void setPeRatio2(double peRatio2) {
		this.peRatio2 = peRatio2;
	}

	public double getNetWorth() {
		return netWorth;
	}

	public void setNetWorth(double netWorth) {
		this.netWorth = netWorth;
	}

	public double getConsultNetWorth() {
		return consultNetWorth;
	}

	public void setConsultNetWorth(double consultNetWorth) {
		this.consultNetWorth = consultNetWorth;
	}

	public double getOpenPosition() {
		return openPosition;
	}

	public void setOpenPosition(double openPosition) {
		this.openPosition = openPosition;
	}
}
