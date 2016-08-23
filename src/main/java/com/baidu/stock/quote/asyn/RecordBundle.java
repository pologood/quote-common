package com.baidu.stock.quote.asyn;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 *  一次输出的结果集对象
 * @author dengjianli
 *
 */
public class RecordBundle{
	/**
	 * 输出结果集内的记录数
	 */
	private int count = 0;
	/**
	 * 最后需要输出的时间
	 */
	private Date flushTime;
	/**
	 * 结果集
	 */
	private List<Object> records;
	
	private String consumerName;
	
	public RecordBundle(int flushInterval){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MILLISECOND, flushInterval);
		flushTime = calendar.getTime();
		records = new LinkedList<Object>();
	}
	
	public int getCount()
	{
		return count;
	}
	public void setCount(int count)
	{
		this.count = count;
	}
	public Date getFlushTime()
	{
		return flushTime;
	}
	public void setFlushTime(Date flushTime)
	{
		this.flushTime = flushTime;
	}
	public List<Object> getRecords()
	{
		return records;
	}
	public void setRecords(List<Object> records)
	{
		this.records = records;
	}
	
	public String getConsumerName() {
		return consumerName;
	}

	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}

	public void reset(int flushInterval){
		count = 0;
		records.clear();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MILLISECOND, flushInterval);
		flushTime = calendar.getTime();
	}
	
	public void add(Object node){
		count = count + 1;
		records.add(node);
	}
	
}
