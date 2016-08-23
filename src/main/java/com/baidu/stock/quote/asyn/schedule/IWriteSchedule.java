package com.baidu.stock.quote.asyn.schedule;

import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.ExecutorChannel;
import com.baidu.stock.quote.asyn.RecordBundle;

/**
 * 输出任务接口定义,被用于ExecutorService定时执行
 * 或者周期性执行，内嵌入输出业务逻辑
 * @author dengjianli
 *
 */
public interface IWriteSchedule{

/**
 * 将bundle中的数据输出
 * @param bundle
 */
	public void onRecordBundle(RecordBundle bundle);
	

	/**
	 * 设置Bundle
	 * @param bundle
	 */
	public void setBundle(RecordBundle bundle);
	/**
	 * 设置输出同步channel
	 */
	public void setHQDirectChannel(DirectChannel directChannel);
	/**
	 * 设置输出异步channel
	 */
	public void setHQExecutorChannel(ExecutorChannel executorChannel);
}
