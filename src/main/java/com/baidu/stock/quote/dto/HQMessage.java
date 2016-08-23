package com.baidu.stock.quote.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author dengjianli
 *
 */
public class HQMessage<T> implements Serializable{
	private static final long serialVersionUID = 1L;

	/*
	 * 1:股票代码表 2:行情数据 3:实时状态
	 */
	private int messageType=1;
	
	private long lastRefeshTimestamp;
	/*
	 * 行情数据集合
	 */
	private List<T> bodys;
	
	public int getMessageType() {
		return messageType;
	}
	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}
	public long getLastRefeshTimestamp() {
		return lastRefeshTimestamp;
	}
	public void setLastRefeshTimestamp(long lastRefeshTimestamp) {
		this.lastRefeshTimestamp = lastRefeshTimestamp;
	}
	public List<T> getBodys() {
		return bodys;
	}
	public void setBodys(List<T> bodys) {
		this.bodys = bodys;
	}
	
}
