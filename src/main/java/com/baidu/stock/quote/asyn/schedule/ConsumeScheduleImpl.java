package com.baidu.stock.quote.asyn.schedule;

import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.ExecutorChannel;
import com.baidu.stock.quote.asyn.AsynConfig;
import com.baidu.stock.quote.asyn.AsynUtil;
import com.baidu.stock.quote.asyn.RecordBundle;
import com.baidu.stock.quote.asyn.Tail;

/**
 * IConsumeSchedule的默认实现
 * @author dengjianli
 *
 */
public class ConsumeScheduleImpl implements IConsumeSchedule{
	private static final Log Logger = LogFactory.getLog(ConsumeScheduleImpl.class);
	
	/**
	 * 输出数据队列
	 */
	private BlockingQueue<Object> queue;
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 负责的分页对象
	 */
	private RecordBundle bundle;
	/**
	 * 同步输出channel
	 */
	private DirectChannel directChannel;
	private ExecutorChannel executorChannel;
	private IWriteSchedule writeSchedule ;
	public ConsumeScheduleImpl(DirectChannel directChannel,ExecutorChannel executorChannel){
		this.directChannel=directChannel;
		this.executorChannel=executorChannel;
	}
	
	/**
	 * 内部检查
	 * @return
	 */
	public boolean checkInnerElement(){
		if (queue == null || name == null)
			return false;
		else
			return true;
	}
	
	public void init(){
		if (!checkInnerElement()){
			Logger.error("ConsumerSchedule start Error!,please check innerElement");
			throw new java.lang.RuntimeException("ConsumerSchedule start Error!,please check innerElement");
		}
		bundle = new RecordBundle(AsynConfig.getInstance().getFlushInterval());
	}
	
	/**
	 * 轮询消费
	 */
	public void run(){
		init();
		try{
			while(true)
			consume(queue);
		} 
		catch (ExecutionException e){
			Logger.error("ConsumerSchedule ExecutionException!",e);
		} 
		catch (TimeoutException e){
			Logger.error("ConsumerSchedule TimeoutException!",e);
		}
		catch (InterruptedException e){
			Logger.error("ConsumerSchedule is stop!");
		} 
		catch (Exception e){
			Logger.error("ConsumerSchedule error!",e);
		}
	}
	/**
	 * 消费queue消息
	 */
	public void consume(BlockingQueue<Object> queue) throws Exception{
		if (needFlush(bundle)){
			flush();
		}
		//考虑到行情3秒更新，这里采用阻塞处理，不用轮询方式
		Object node = queue.take();
		if (node != null){
			if(node instanceof Tail){
			flush();	
		}else{
			bundle.add( node);
		}
		}
	}

	/**
	 * 判断是否超过阀值或间隔时间到达
	 */
	public boolean needFlush(RecordBundle bundle){
		boolean result = false;
		//到了输出间隔时间
		if (bundle.getCount() > 0 && bundle.getFlushTime().before(new Date())){
			result = true;
			return result;
		}
		//创建输出任务去输出
		if (bundle.getCount() >= AsynConfig.getInstance().getBundleMaxCount()){
			result = true;
			return result;
		}
		return result;
	}

	public BlockingQueue<Object> getQueue(){
		return queue;
	}


	public void setQueue(BlockingQueue<Object> queue){
		this.queue = queue;
	}


	public String getName(){
		return name;
	}


	public void setName(String name){
		this.name = name;
	}

	/**
	 * 输出数据,这里包括定时和数据阀值输出
	 */
	public void flush() throws Exception{
		if (bundle.getCount()== 0){
			return;
		}
		if(writeSchedule==null){
		writeSchedule = AsynUtil.getInstanceByInterface(IWriteSchedule.class
				,Thread.currentThread().getContextClassLoader(),AsynConfig.getInstance().getScheduleOutImplClassPath(),true);
		}
		if (writeSchedule == null){
			throw new java.lang.RuntimeException("Please config IWriteSchedule Implement Class");
		}
		writeSchedule.setBundle(bundle);
		writeSchedule.setHQDirectChannel(directChannel);
		writeSchedule.setHQExecutorChannel(executorChannel);
		//这里同步处理
		bundle.setConsumerName(name);
		writeSchedule.onRecordBundle(bundle);
    	bundle.reset(AsynConfig.getInstance().getFlushInterval());
	}


}
