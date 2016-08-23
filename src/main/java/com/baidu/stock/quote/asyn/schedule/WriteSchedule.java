package com.baidu.stock.quote.asyn.schedule;

import com.baidu.stock.quote.asyn.RecordBundle;

/**
 * 
 * @author dengjianli
 *
 */
public abstract class WriteSchedule implements IWriteSchedule{
	protected RecordBundle bundle;
	public void setBundle(RecordBundle bundle){
		this.bundle = bundle;
	}

}
